package simple.http.routing;

import simple.http.request.Request;
import simple.http.response.Response;

import java.util.Map;

/**
 * Created by Attila on 17/10/05.
 */
@FunctionalInterface
public interface HandlerMethod {
    void handleRequest(Request request, Response.Builder responseBuilder, Map<String, String> segments) throws Exception;
}
