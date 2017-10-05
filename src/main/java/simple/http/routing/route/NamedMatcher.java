package simple.http.routing.route;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

/**
 * Created by Attila on 17/10/05.
 */
public class NamedMatcher implements Matcher {
    private final String name;

    private final Matcher innerMatcher;

    public static NamedMatcher named(String name, Matcher matcher) {
        return new NamedMatcher(name, requireNonNull(matcher));
    }

    NamedMatcher(Matcher innerMatcher) {
        this(null, innerMatcher);
    }

    NamedMatcher(String name, Matcher innerMatcher) {
        this.name = name;
        this.innerMatcher = innerMatcher;
    }

    @Override
    public MatcherResult match(String unmatched) {
        return innerMatcher.match(unmatched);
    }

    public boolean hasName() {
        return nonNull(name);
    }

    public String getName() {
        return name;
    }
}
