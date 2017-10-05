package server.http.routing;

import server.http.request.Request;
import server.http.response.ResponseBuilder;

import java.util.Map;

/**
 * Created by Attila on 17/10/05.
 */
@FunctionalInterface
public interface HandlerMethod {
    void handleRequest(Request request, ResponseBuilder responseBuilder, Map<String, String> segments);
}
