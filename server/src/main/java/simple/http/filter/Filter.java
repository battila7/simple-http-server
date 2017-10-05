package simple.http.filter;

import simple.http.request.Request;
import simple.http.response.Response;

public interface Filter {
    void filter(Request request, Response.Builder responseBuilder, Runnable nextFilter);
}
