package simple.cli;

import simple.http.server.HttpServer;
import simple.http.server.HttpServerConfiguration;
import simple.http.server.HttpServerFactory;
import simple.http.server.ServerCreationException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static final String PORT_PROPERTY = "port";

    private static final String DEFAULT_PORT = "9090";

    public static void main(String[] args) {
        final int port = Integer.parseInt((String) System.getProperties().getOrDefault(PORT_PROPERTY, DEFAULT_PORT));

        try {
            final HttpServerConfiguration configuration = HttpServerConfiguration.builder()
                    .port(port)
                    .filter(new ContentLengthSetterFilter())
                    .controller(new HelloController())
                    .build();

            final HttpServer server = HttpServerFactory.makeServer(configuration);

            server.listen();
        } catch (ServerCreationException e) {
            LOGGER.log(Level.SEVERE, "Could not create server.", e);
        }
    }
}
