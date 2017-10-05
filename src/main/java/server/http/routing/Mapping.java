package server.http.routing;

import server.http.routing.route.Route;

/**
 * Created by Attila on 17/10/05.
 */
public class Mapping {
    private final Route route;

    private final HandlerMethod handlerMethod;

    public static Builder mapping(Route route) {
        return new Builder(route);
    }

    private Mapping(Builder builder) {
        this.route = builder.route;
        this.handlerMethod = builder.handlerMethod;
    }

    public Route getRoute() {
        return route;
    }

    public HandlerMethod getHandlerMethod() {
        return handlerMethod;
    }

    public static class Builder {
        private final Route route;

        private HandlerMethod handlerMethod;

        private Builder(Route route) {
            this.route = route;
        }

        public Builder to(HandlerMethod handlerMethod) {
            this.handlerMethod = handlerMethod;

            return this;
        }

        public Mapping build() {
            return new Mapping(this);
        }
    }
}
