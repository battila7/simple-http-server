package simple.http.request;

public class MalformedRequestException extends Exception {
    private static final String MESSAGE = "Unable to process malformed request!";

    MalformedRequestException() {
        super(MESSAGE);
    }
}
