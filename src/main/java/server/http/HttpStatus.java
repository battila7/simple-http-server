package server.http;

enum HttpStatus {
    OK(200, "OK"),
    BAD_REQUEST(400, "Bad Request"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    IM_A_TEAPOT(418, "I'm a teapot"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int code;

    private final String phrase;

    HttpStatus(int code, String phrase) {
        this.code = code;
        this.phrase = phrase;
    }

    int getCode() {
        return code;
    }

    String getPhrase() {
        return phrase;
    }
}
