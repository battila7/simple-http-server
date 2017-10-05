package server.http.routing.route;

import server.http.util.Result;

/**
 * Created by Attila on 17/10/05.
 */
public enum Any implements Matcher {
    INSTANCE;

    @Override
    public Result<String> match(String unmatched) {
        return Result.success("");
    }
}
