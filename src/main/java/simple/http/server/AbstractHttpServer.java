package simple.http.server;

import simple.http.filter.FilterChain;
import simple.http.routing.Router;

import java.net.ServerSocket;

/**
 * Created by Attila on 17/10/05.
 */
public abstract class AbstractHttpServer implements HttpServer {
    private final FilterChain filterChain;

    private final Router router;

    private final ServerSocket serverSocket;

    private final int port;

    public AbstractHttpServer(FilterChain filterChain, Router router, ServerSocket serverSocket, int port) {
        this.filterChain = filterChain;
        this.router = router;
        this.serverSocket = serverSocket;
        this.port = port;
    }

    protected FilterChain getFilterChain() {
        return filterChain;
    }

    protected Router getRouter() {
        return router;
    }

    protected ServerSocket getServerSocket() {
        return serverSocket;
    }

    protected int getPort() {
        return port;
    }

    public abstract void listen();

    public abstract void shutdown();
}