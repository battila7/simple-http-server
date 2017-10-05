package simple.cli;

import simple.http.filter.Filter;
import simple.http.request.Request;
import simple.http.response.Response;

public class ContentLengthSetterFilter implements Filter {
    @Override
    public void filter(Request request, Response.Builder responseBuilder, Runnable nextFilter) {
        nextFilter.run();

        if (responseBuilder.getOutputStream().size() > 0) {
            responseBuilder.header("Content-Length", Integer.toString(responseBuilder.getOutputStream().size()));
        }
    }
}
