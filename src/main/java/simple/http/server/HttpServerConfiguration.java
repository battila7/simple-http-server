package simple.http.server;

import simple.http.filter.Filter;
import simple.http.routing.Controller;

import java.util.ArrayList;
import java.util.List;

public class HttpServerConfiguration {
    private final int port;

    private final List<Filter> filters;

    private final List<Controller> controllers;

    public static Builder builder() {
        return new Builder();
    }

    private HttpServerConfiguration(Builder builder) {
        this.port = builder.port;
        this.filters = builder.filters;
        this.controllers = builder.controllers;
    }

    public int getPort() {
        return port;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public List<Controller> getControllers() {
        return controllers;
    }

    public static class Builder {
        private int port;

        private final List<Filter> filters;

        private final List<Controller> controllers;

        private Builder() {
            this.filters = new ArrayList<>();
            this.controllers = new ArrayList<>();
        }

        public Builder port(int port) {
            this.port = port;

            return this;
        }

        public Builder filter(Filter filter) {
            this.filters.add(filter);

            return this;
        }

        public Builder controller(Controller controller) {
            this.controllers.add(controller);

            return this;
        }

        public HttpServerConfiguration build() {
            return new HttpServerConfiguration(this);
        }
    }
}
