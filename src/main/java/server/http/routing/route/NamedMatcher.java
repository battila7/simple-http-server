package server.http.routing.route;

import server.http.util.Result;

import static java.util.Objects.isNull;

/**
 * Created by Attila on 17/10/05.
 */
class NamedMatcher implements Matcher {
    private final String name;

    private final Matcher innerMatcher;

    NamedMatcher(Matcher innerMatcher) {
        this(null, innerMatcher);
    }

    NamedMatcher(String name, Matcher innerMatcher) {
        this.name = name;
        this.innerMatcher = innerMatcher;
    }

    @Override
    public Result<String> match(String unmatched) {
        return innerMatcher.match(unmatched);
    }

    public boolean hasName() {
        return isNull(name);
    }

    public String getName() {
        return name;
    }
}
