package server.http;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;

class RequestHandlerFactory {
    private final ExecutorService executorService;

    private final Path rootDirectory;

    RequestHandlerFactory(ExecutorService executorService, Path rootDirectory) {
        this.executorService = executorService;
        this.rootDirectory = rootDirectory;
    }

    RequestHandler handling(Socket socket) throws IOException {
        return new RequestHandler(socket, rootDirectory, this.executorService::submit);
    }
}
