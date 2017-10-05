package simple.http.filter;

import simple.http.request.Request;
import simple.http.response.ResponseBuilder;

/**
 * Created by Attila on 17/10/05.
 */
public interface Filter {
    void filter(Request request, ResponseBuilder responseBuilder, Runnable nextFilter);
}