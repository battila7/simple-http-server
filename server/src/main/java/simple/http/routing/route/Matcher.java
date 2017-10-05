package simple.http.routing.route;

@FunctionalInterface
public interface Matcher {
    MatcherResult match(String unmatched);
}
