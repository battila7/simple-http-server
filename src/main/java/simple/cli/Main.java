package simple.cli;

import simple.http.server.HttpServer;
import simple.http.server.HttpServerConfiguration;
import simple.http.server.HttpServerFactory;
import simple.http.server.ServerCreationException;

public class Main {
    private static final String PORT_PROPERTY = "port";
    private static final String ROOT_DIRECTORY_PROPERTY = "rootDirectory";

    private static final String DEFAULT_PORT = "9090";
    private static final String DEFAULT_ROOT_DIRECTORY = ".";

    public static void main(String[] args) {
        final int port = Integer.parseInt((String) System.getProperties().getOrDefault(PORT_PROPERTY, DEFAULT_PORT));
        final String rootDirectory = (String) System.getProperties().getOrDefault(ROOT_DIRECTORY_PROPERTY, DEFAULT_ROOT_DIRECTORY);

        try {
            final HttpServerConfiguration configuration = HttpServerConfiguration.builder()
                    .port(port)
                    .build();

            final HttpServer server = HttpServerFactory.makeServer(configuration);

            server.listen();
        } catch (ServerCreationException e) {
            System.out.println("Could not create simple!");

            e.printStackTrace();
        }
    }
}
