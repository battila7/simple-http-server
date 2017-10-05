package server.http.routing;

import server.http.request.Request;
import server.http.response.ResponseBuilder;

/**
 * Created by Attila on 17/10/05.
 */
public class Router {


    public class Filter implements server.http.filter.Filter {
        @Override
        public void filter(Request request, ResponseBuilder responseBuilder, Runnable nextFilter) {
            nextFilter.run();
        }
    }
}
