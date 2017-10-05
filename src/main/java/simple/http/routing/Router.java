package simple.http.routing;

import simple.http.request.Request;
import simple.http.response.Response;
import simple.http.routing.route.MatcherResult;
import simple.http.routing.route.NamedMatcher;
import simple.http.routing.route.Route;

import java.util.*;

public class Router {
    private static final String EMPTY_ROUTE = "";

    private final MappingRegistry mappingRegistry;

    private final Filter filter;

    public Router(MappingRegistry mappingRegistry) {
        this.mappingRegistry = mappingRegistry;

        this.filter = new Filter();
    }

    public simple.http.filter.Filter getFilter() {
        return filter;
    }

    private boolean route(Request request, Response.Builder responseBuilder) {
        for (final Mapping mapping : mappingRegistry.getMappings()) {
            final Optional<Map<String, String>> matchResult = matchRoute(request, mapping.getRoute());

            if (matchResult.isPresent()) {
                try {
                    mapping.getHandlerMethod().handleRequest(request, responseBuilder, matchResult.get());
                } catch (Exception e) {
                    throw new HandlerMethodExecutionException(mapping.getHandlerMethod(), e);
                }

                return true;
            }
        }

        return false;
    }

    private Optional<Map<String, String>> matchRoute(Request request, Route route) {
        if (request.getMethod() != route.getMethod()) {
            return Optional.empty();
        }

        return matchUri(request.getUri(), route.getMatchers());
    }

    private Optional<Map<String, String>> matchUri(String uri, List<NamedMatcher> matchers) {
        final Iterator<NamedMatcher> matcherIterator = matchers.iterator();
        final Map<String, String> segments = new HashMap<>();

        String remaining = uri;

        while (matcherIterator.hasNext()) {
            final NamedMatcher matcher = matcherIterator.next();
            final MatcherResult result = matcher.match(remaining);

            if (result.isFailure()) {
                return Optional.empty();
            }

            if (matcher.hasName()) {
                segments.put(matcher.getName(), result.getMatched());
            }

            remaining = result.getRemaining();
        }

        if (!EMPTY_ROUTE.equals(remaining)) {
            return Optional.empty();
        }

        return Optional.of(segments);
    }

    private class Filter implements simple.http.filter.Filter {
        @Override
        public void filter(Request request, Response.Builder responseBuilder, Runnable nextFilter) {
            if (!Router.this.route(request, responseBuilder)) {
                throw new NoHandlerFoundException(request.getUri());
            }

            nextFilter.run();
        }
    }
}
