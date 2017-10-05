package server.http;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class HttpServerFactory {
    private static final int MIN_PORT_NUMBER = 1;
    private static final int MAX_PORT_NUMBER = 65535;

    private static final Path currentPath = Paths.get(".");

    private HttpServerFactory() {
        /*
         * Prevent instantiation.
         */
    }

    public static HttpServer makeServer(HttpServerConfiguration configuration) throws ServerCreationException {
        if (configuration.getPort() < MIN_PORT_NUMBER || configuration.getPort() > MAX_PORT_NUMBER) {
            throw new ServerCreationException("Invalid port!");
        }

        if (Files.notExists(currentPath.resolve(configuration.getRootDirectory()))) {
            throw new ServerCreationException("Invalid directory!");
        }

        return ExecutorServiceHttpServerImpl.of(port, currentPath.resolve(rootDirectory));
    }
}
