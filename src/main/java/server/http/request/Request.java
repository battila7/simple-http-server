package server.http.request;

import java.util.List;
import java.util.Map;

/**
 * Created by Attila on 17/10/05.
 */
public class Request {
    private final Method method;

    private final String uri;

    private final Map<String, List<String>> queryParameters;

    private final Map<String, String> headers;

    private final byte[] body;

    private Request(Builder builder) {
        this.method = builder.method;
        this.uri = builder.uri;
        this.queryParameters = builder.queryParameters;
        this.headers = builder.headers;
        this.body = builder.body;
    }

    static Builder builder() {
        return new Builder();
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

    public static class Builder {
        private Method method;

        private String uri;

        private Map<String, List<String>> queryParameters;

        private Map<String, String> headers;

        private byte[] body;

        public Builder method(Method method) {
            this.method = method;

            return this;
        }

        public Builder uri(String uri) {
            this.uri = uri;

            return this;
        }

        public Builder queryParameters(Map<String, List<String>> queryParameters) {
            this.queryParameters = queryParameters;

            return this;
        }

        public Builder headers(Map<String, String> headers) {
            this.headers = headers;

            return this;
        }

        public Builder body(byte[] body) {
            this.body = body;

            return this;
        }
    }
}
