package server.http.routing.route;

import server.http.util.Result;

/**
 * Created by Attila on 17/10/05.
 */
@FunctionalInterface
public interface Matcher {
    Result<String> match(String unmatched);
}
