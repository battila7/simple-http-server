package server.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import static java.util.concurrent.TimeUnit.SECONDS;

class ExecutorServiceHttpServerImpl implements HttpServer {
    private static final Logger LOGGER = Logger.getLogger(ExecutorServiceHttpServerImpl.class.getName());

    private static final int POOL_SIZE = 5;

    private final ExecutorService executorService;

    private final ListenerWorker listenerWorker;

    private final int port;

    static ExecutorServiceHttpServerImpl of(int port, Path rootDirectory) throws ServerCreationException {
        final ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new ServerCreationException("Could not create socket.", e);
        }

        final ExecutorService executorService = Executors.newFixedThreadPool(POOL_SIZE);

        final RequestHandlerFactory requestHandlerFactory = new RequestHandlerFactory(executorService, rootDirectory);

        final ListenerWorker listenerWorker = new ListenerWorker(serverSocket, requestHandlerFactory);

        return new ExecutorServiceHttpServerImpl(executorService, listenerWorker, port);
    }

    private ExecutorServiceHttpServerImpl(ExecutorService executorService, ListenerWorker listenerWorker, int port) {
        this.executorService = executorService;
        this.listenerWorker = listenerWorker;
        this.port = port;
    }

    @Override
    public void listen() {
        executorService.submit(listenerWorker::listen);

        LOGGER.info(() -> "Listener loop started on port " + port);
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
}
