package simple.cli;

import simple.http.request.Request;
import simple.http.response.Response;
import simple.http.routing.Controller;
import simple.http.routing.MappingRegistry;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static simple.http.request.Method.GET;
import static simple.http.response.Status.OK;
import static simple.http.routing.Mapping.mapping;
import static simple.http.routing.route.CatchAll.catchAll;
import static simple.http.routing.route.Route.method;

public class HelloController implements Controller {
    @Override
    public void registerHandlers(MappingRegistry mappingRegistry) {
        mappingRegistry.register(mapping(method(GET).then(catchAll())).to(this::hello).build());
    }

    private void hello(Request request, Response.Builder responseBuilder, Map<String, String> segments) throws IOException {
        responseBuilder.status(OK);

        PrintWriter writer = new PrintWriter(responseBuilder.getOutputStream());

        writer.print("Hello World!");

        writer.flush();
    }
}

