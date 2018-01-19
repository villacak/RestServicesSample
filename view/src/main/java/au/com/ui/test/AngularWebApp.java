package au.com.ui.test;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class AngularWebApp {

    private static final int DEFAULT_PORT = 9080;

    private final String WEB_APP_DIR_LOCATION = "src/app/";
    private final String ADD_SERVLET = "/*";

    private int serverPort;

    public AngularWebApp(final int serverPort) throws Exception {
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
        final Server server = new Server(serverPort);
        final ResourceConfig resourceConfig = new ResourceConfig();
        final ServletContainer servletContainer = new ServletContainer(resourceConfig);


        final ServletHolder sh = new ServletHolder(servletContainer);
        sh.setInitOrder(1);

        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath(ADD_SERVLET);
//    webapp.setDescriptor(WEB_APP_DIR_LOCATION + "/weba") ;
        webapp.setResourceBase(WEB_APP_DIR_LOCATION);


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

        new AngularWebApp(serverPort);
    }
}
