package simple.http.routing.route;

/**
 * Created by Attila on 17/10/05.
 */
@FunctionalInterface
public interface Matcher {
    MatcherResult match(String unmatched);
}
