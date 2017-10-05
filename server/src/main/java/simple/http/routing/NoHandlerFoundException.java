package simple.http.routing;

public class NoHandlerFoundException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "No handler found for URI \"%s\"";

    private final String uri;

    public NoHandlerFoundException(String uri) {
        super(String.format(MESSAGE_TEMPLATE, uri));

        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }
}
