package simple.http.routing;

public class HandlerMethodExecutionException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Could not execute handler method (%s).";

    public HandlerMethodExecutionException(HandlerMethod handlerMethod, Throwable cause) {
        super(String.format(MESSAGE_TEMPLATE, handlerMethod.toString()), cause);
    }
}
