package simple.http.response;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

public class Response {
    private final Status status;

    private final Map<String, String> headers;

    private final byte[] body;

    public static Builder builder() {
        return new Builder();
    }

    private Response(Builder builder) {
        this.status = builder.status;
        this.headers = unmodifiableMap(builder.headers);
        this.body = builder.byteArrayOutputStream.toByteArray();
    }

    public Status getStatus() {
        return status;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

    public static final class Builder {
        private Status status;

        private Map<String, String> headers;

        private ByteArrayOutputStream byteArrayOutputStream;

        private Builder() {
            this.headers = new HashMap<>();
            this.byteArrayOutputStream = new ByteArrayOutputStream(0);
        }

        public Builder status(Status status) {
            this.status = status;

            return this;
        }

        public Builder header(String key, String value) {
            headers.put(key, value);

            return this;
        }

        public Status getStatus() {
            return status;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public ByteArrayOutputStream getOutputStream() {
            return byteArrayOutputStream;
        }

        public Response build() {
            return new Response(this);
        }
    }
}
