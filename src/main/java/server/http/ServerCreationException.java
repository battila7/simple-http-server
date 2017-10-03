package server.http;

public class ServerCreationException extends Exception {
    public ServerCreationException(String message) {
        super(message);
    }

    public ServerCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
