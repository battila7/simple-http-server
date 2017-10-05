package simple.http.server;

import simple.http.filter.FilterChain;
import simple.http.request.MalformedRequestException;
import simple.http.request.Request;
import simple.http.request.RequestReader;
import simple.http.response.Response;
import simple.http.response.ResponseWriter;
import simple.http.routing.Router;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.logging.Level.WARNING;

class ExecutorServiceHttpServerImpl extends AbstractHttpServer {
    private static final Logger LOGGER = Logger.getLogger(ExecutorServiceHttpServerImpl.class.getName());

    private static final int POOL_SIZE = 5;

    private final ExecutorService executorService;

    private final ListenerWorker listenerWorker;

    static Builder builder() {
        return new Builder();
    }

    private static ExecutorServiceHttpServerImpl of(Builder builder) throws ServerCreationException {
        final ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(builder.port);
        } catch (IOException e) {
            throw new ServerCreationException("Could not create socket.", e);
        }

        return new ExecutorServiceHttpServerImpl(builder.serverSocket(serverSocket));
    }

    ExecutorServiceHttpServerImpl(Builder builder) {
        super(builder.filterChain, builder.router, builder.serverSocket, builder.port);

        this.executorService = Executors.newFixedThreadPool(POOL_SIZE);

        this.listenerWorker = new ListenerWorker(builder.serverSocket, this::handleRequest);
    }

    @Override
    public void listen() {
        executorService.submit(listenerWorker::listen);

        LOGGER.info("Listener loop started on port " + this.getPort());
    }

    @Override
    public void shutdown() {
        // Copied from https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html
        executorService.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!executorService.awaitTermination(60, SECONDS)) {
                executorService.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!executorService.awaitTermination(60, SECONDS))
                    LOGGER.severe("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            executorService.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }

    private void handleRequest(Socket socket) {
        executorService.submit(() -> {
            try {
                handleRequestInternal(socket);
            } catch (Exception e) {
                LOGGER.log(WARNING, "Exception encountered while processing request.", e);
            }
        });
    }

    private void handleRequestInternal(Socket socket) throws MalformedRequestException, IOException {
        try {
            final RequestReader reader = RequestReader.from(socket);
            final Request request = reader.parseRequest();

            final Response.Builder responseBuilder = Response.builder();

            getFilterChain().walkChain(request, responseBuilder);

            final ResponseWriter writer = ResponseWriter.from(socket, responseBuilder.build());
            writer.respond();
        } finally {
            socket.close();

            LOGGER.info("Transmission over, socket closed");
        }
    }

    static class Builder {
        private FilterChain filterChain;

        private Router router;

        private int port;

        private ServerSocket serverSocket;

        Builder filterChain(FilterChain filterChain) {
            this.filterChain = filterChain;

            return  this;
        }

        Builder router(Router router) {
            this.router = router;

            return this;
        }

        Builder port(int port) {
            this.port = port;

            return this;
        }

        Builder serverSocket(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;

            return this;
        }

        ExecutorServiceHttpServerImpl build() throws ServerCreationException {
            return ExecutorServiceHttpServerImpl.of(this);
        }
    }
}
