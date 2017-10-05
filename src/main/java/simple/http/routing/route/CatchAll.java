package simple.http.routing.route;

import static simple.http.routing.route.MatcherResult.success;

public enum CatchAll implements Matcher {
    INSTANCE;

    @Override
    public MatcherResult match(String unmatched) {
        return success(unmatched, "");
    }

    public static CatchAll catchAll() {
        return INSTANCE;
    }
}
