package au.com.rest.test;

import au.com.rest.test.services.LoginServices;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class AppMain {

    private static final int DEFAULT_PORT = 8080;

    private final String contextPathSeparator = "/resttest";
    private final String addServlet = "/*";

    private int serverPort;

    public AppMain(final int serverPort) throws Exception {
        this.serverPort = serverPort;
        final Server server = configureServer();
        server.start();
        server.join();
    }



    /**
     * Configure the server - Jetty
     *
     * @return
     */
    private Server configureServer() {
//        Server server = new Server(8080);
//
//        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
//        context.setContextPath("/");
//        server.setHandler(context);

//        ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/webapi/*");
//        jerseyServlet.setInitOrder(1);
//        jerseyServlet.setInitParameter("jersey.config.server.provider.packages","com.example");
//
//        ServletHolder staticServlet = context.addServlet(DefaultServlet.class,"/*");
//        staticServlet.setInitParameter("resourceBase","src/main/webapp");
//        staticServlet.setInitParameter("pathInfoOnly","true");



        final Server server = new Server(serverPort);
        final ResourceConfig resourceConfig = new ResourceConfig();
        final ServletContainer servletContainer = new ServletContainer(resourceConfig);
        resourceConfig.packages(LoginServices.class.getPackage().getName());
        resourceConfig.register(JacksonFeature.class);

        final ServletHolder sh = new ServletHolder(servletContainer);
        sh.setInitOrder(1);

        final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath(contextPathSeparator);
        context.addServlet(sh, addServlet);
        server.setHandler(context);
        return server;
    }


    /**
     * Start the server with
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        int serverPort = DEFAULT_PORT;
        if (args.length >= 1) {
            try {
                serverPort = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        new AppMain(serverPort);
    }


}
