package server.http.routing.route;

import server.http.util.Result;

/**
 * Created by Attila on 17/10/05.
 */
public class SegmentMatcher implements Matcher {
    private static final char SEGMENT_SEPARATOR = '/';

    private final Matcher innerMatcher;

    public SegmentMatcher(Matcher innerMatcher) {
        this.innerMatcher = innerMatcher;
    }

    @Override
    public Result<String> match(String unmatched) {


    }
}
