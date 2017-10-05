package simple.http.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.function.Consumer;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

class ListenerWorker {
    private static final Logger LOGGER = Logger.getLogger(ListenerWorker.class.getName());

    private static final int SOCKET_TIMEOUT = 1000;

    private final ServerSocket serverSocket;

    private final Consumer<Socket> connectionHandler;

    ListenerWorker(ServerSocket serverSocket, Consumer<Socket> connectionHandler) {
        this.serverSocket = serverSocket;
        this.connectionHandler = connectionHandler;
    }

    void listen() {
        try {
            serverSocket.setSoTimeout(SOCKET_TIMEOUT);

            while (true) {
                final Socket socket;

                try {
                    socket = serverSocket.accept();
                } catch (SocketTimeoutException e) {
                    if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedException();
                    } else {
                        continue;
                    }
                }

                LOGGER.info("Received a new connection.");

                connectionHandler.accept(socket);
            }
        } catch (Exception e) {
            LOGGER.log(SEVERE, "Exception encountered in listener loop!", e);
        }
    }
}
