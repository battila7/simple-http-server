package simple.http.server;

import simple.http.filter.Filter;
import simple.http.filter.FilterChain;
import simple.http.request.RequestReader;
import simple.http.response.ResponseWriter;
import simple.http.routing.MappingRegistry;
import simple.http.routing.Router;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
        assertConfiguration(configuration);

        final MappingRegistry mappingRegistry = new MappingRegistry();
        final Router router = new Router(mappingRegistry);
        final List<Filter> filters = new ArrayList<>(configuration.getFilters());

        filters.add(router.getFilter());

        final FilterChain filterChain = new FilterChain(filters);

        return ExecutorServiceHttpServerImpl.builder()
                .filterChain(filterChain)
                .router(router)
                .port(configuration.getPort())
                .build();
    }

    private static void assertConfiguration(HttpServerConfiguration configuration) throws ServerCreationException {
        if (configuration.getPort() < MIN_PORT_NUMBER || configuration.getPort() > MAX_PORT_NUMBER) {
            throw new ServerCreationException("Invalid port!");
        }
    }
}
