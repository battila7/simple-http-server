package simple.http.request;

import java.util.List;
import java.util.Map;

public class Request {
    private final Method method;

    private final String uri;

    private final Map<String, List<String>> queryParameters;

    private final Map<String, String> headers;

    private final byte[] body;

    static Builder builder() {
        return new Builder();
    }

    private Request(Builder builder) {
        this.method = builder.method;
        this.uri = builder.uri;
        this.queryParameters = builder.queryParameters;
        this.headers = builder.headers;
        this.body = builder.body;
    }

    public Method getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public Map<String, List<String>> getQueryParameters() {
        return queryParameters;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

    static final class Builder {
        private Method method;

        private String uri;

        private Map<String, List<String>> queryParameters;

        private Map<String, String> headers;

        private byte[] body;

        private Builder() {
            /*
             * Prevent instantiation.
             */
        }

        Builder method(Method method) {
            this.method = method;

            return this;
        }

        Builder uri(String uri) {
            this.uri = uri;

            return this;
        }

        Builder queryParameters(Map<String, List<String>> queryParameters) {
            this.queryParameters = queryParameters;

            return this;
        }

        Builder headers(Map<String, String> headers) {
            this.headers = headers;

            return this;
        }

        Builder body(byte[] body) {
            this.body = body;

            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }
}
