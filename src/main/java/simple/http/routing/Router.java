package simple.http.routing;

import simple.http.request.Request;
import simple.http.response.ResponseBuilder;
import simple.http.routing.route.MatcherResult;
import simple.http.routing.route.NamedMatcher;
import simple.http.routing.route.Route;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Attila on 17/10/05.
 */
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

    private boolean route(Request request, ResponseBuilder responseBuilder) {
        for (final Mapping mapping : mappingRegistry.getMappings()) {
            final Optional<Map<String, String>> matchResult = tryMatch(request.getUri(), mapping.getRoute());

            if (matchResult.isPresent()) {
                mapping.getHandlerMethod().handleRequest(request, responseBuilder, matchResult.get());

                return true;
            }
        }

        return false;
    }

    private Optional<Map<String, String>> tryMatch(String uri, Route route) {
        final Iterator<NamedMatcher> matcherIterator = route.getMatchers().iterator();
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
        public void filter(Request request, ResponseBuilder responseBuilder, Runnable nextFilter) {
            if (!Router.this.route(request, responseBuilder)) {
                // throw fail
            }

            nextFilter.run();
        }
    }
}
