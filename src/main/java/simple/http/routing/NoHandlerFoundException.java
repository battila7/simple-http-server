package simple.http.routing;

/**
 * Created by Attila on 2017. 10. 05..
 */
public class NoHandlerFoundException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "No handler found for URI \"%s\"";

    private final String uri;

    public NoHandlerFoundException(String uri, String message) {
        super(String.format(MESSAGE_TEMPLATE, uri));

        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }
}
