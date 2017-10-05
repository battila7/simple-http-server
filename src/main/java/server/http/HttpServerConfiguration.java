package server.http;

import server.http.routing.Controller;
import server.http.filter.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Attila on 17/10/05.
 */
public class HttpServerConfiguration {
    private final int port;

    private final String rootDirectory;

    private final List<Filter> filters;

    private final List<Controller> controllers;

    public static Builder builder() {
        return new Builder();
    }

    private HttpServerConfiguration(Builder builder) {
        this.port = builder.port;
        this.rootDirectory = builder.rootDirectory;
        this.filters = builder.filters;
        this.controllers = builder.controllers;
    }

    public int getPort() {
        return port;
    }

    public String getRootDirectory() {
        return rootDirectory;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public List<Controller> getControllers() {
        return controllers;
    }

    public static class Builder {
        private int port;

        private String rootDirectory;

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

        public Builder rootDirectory(String rootDirectory) {
            this.rootDirectory = rootDirectory;

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
