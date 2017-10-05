package server.http.filter;

import server.http.request.Request;
import server.http.response.ResponseBuilder;

/**
 * Created by Attila on 17/10/05.
 */
public interface Filter {
    void filter(Request request, ResponseBuilder responseBuilder, Runnable nextFilter);
}
