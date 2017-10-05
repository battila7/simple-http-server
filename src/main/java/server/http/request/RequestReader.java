package server.http.request;

/**
 * Created by Attila on 17/10/05.
 */
public class RequestReader {
    public static RequestReader from() {
        return new RequestReader();
    }

    RequestReader() {

    }

    public Request parseRequest() throws InvalidRequestException {
        throw new InvalidRequestException("asd");
    }
}
