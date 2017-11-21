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

    private final String contextPathSeparator = "/";
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
        final ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages(LoginServices.class.getPackage().getName());
        resourceConfig.register(JacksonFeature.class);
        final ServletContainer servletContainer = new ServletContainer(resourceConfig);
        final ServletHolder sh = new ServletHolder(servletContainer);
        final Server server = new Server(serverPort);
        final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
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
