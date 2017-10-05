package simple.http.request;

import java.net.Socket;
import java.util.Objects;

/**
 * Created by Attila on 17/10/05.
 */
public class RequestReader {
    private final Socket socket;

    public static RequestReader from(Socket socket) {
        return new RequestReader(Objects.requireNonNull(socket));
    }

    public RequestReader(Socket socket) {
        this.socket = socket;
    }

    public Request parseRequest() throws InvalidRequestException {
        throw new InvalidRequestException("asd");
    }
}
