package simple.http.routing.route;

import simple.http.request.Method;

import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;
import static simple.http.routing.route.NamedMatcher.named;

/**
 * Created by Attila on 17/10/05.
 */
public class Route {
    private final Method method;

    private final List<NamedMatcher> matchers;

    public static Route method(Method method) {
        return new Route(method);
    }

    private Route(Method method) {
        this.method = method;
        this.matchers = new LinkedList<>();
    }

    public Route then(Matcher matcher) {
        return this.then(named(null, requireNonNull(matcher)));
    }

    public final Route then(NamedMatcher matcher) {
        this.matchers.add(requireNonNull(matcher));

        return this;
    }

    public Method getMethod() {
        return method;
    }

    public List<NamedMatcher> getMatchers() {
        return unmodifiableList(matchers);
    }
}
