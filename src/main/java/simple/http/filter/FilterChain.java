package simple.http.filter;

import simple.http.request.Request;
import simple.http.response.ResponseBuilder;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Attila on 17/10/05.
 */
public class FilterChain {
    private final List<Filter> filters;

    public FilterChain(List<Filter> filters) {
        this.filters = filters;
    }

    public void walkChain(Request request, ResponseBuilder responseBuilder) {
        if (filters.isEmpty()) {
            return;
        }

        final Iterator<Filter> filterIterator = filters.iterator();

        callNextFilter(request, responseBuilder, filterIterator);
    }

    private void callNextFilter(Request request, ResponseBuilder responseBuilder, Iterator<Filter> filterIterator) {
        if (filterIterator.hasNext()) {
            final Filter filter = filterIterator.next();

            filter.filter(request, responseBuilder, () -> callNextFilter(request, responseBuilder, filterIterator));
        }
    }
}
