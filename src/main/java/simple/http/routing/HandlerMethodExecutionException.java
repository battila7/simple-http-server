package simple.http.routing;

/**
 * Created by Attila on 2017. 10. 05..
 */
public class HandlerMethodExecutionException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Could not execute handler method (%s).";

    public HandlerMethodExecutionException(HandlerMethod handlerMethod, Throwable cause) {
        super(String.format(MESSAGE_TEMPLATE, handlerMethod.toString()), cause);
    }
}
