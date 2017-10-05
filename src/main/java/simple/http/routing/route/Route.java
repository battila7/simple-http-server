package simple.http.routing.route;

import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;
import static simple.http.routing.route.NamedMatcher.named;

/**
 * Created by Attila on 17/10/05.
 */
public class Route {
    private final List<NamedMatcher> matchers;

    public static Route route() {
        return new Route();
    }

    private Route() {
        this.matchers = new LinkedList<>();
    }

    public Route then(Matcher matcher) {
        return this.then(named(null, requireNonNull(matcher)));
    }

    public final Route then(NamedMatcher matcher) {
        this.matchers.add(requireNonNull(matcher));

        return this;
    }

    public List<NamedMatcher> getMatchers() {
        return unmodifiableList(matchers);
    }
}
