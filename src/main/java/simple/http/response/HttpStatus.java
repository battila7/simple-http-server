package simple.http.response;

public enum HttpStatus {
    OK(200, "OK"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    IM_A_TEAPOT(418, "I'm a teapot");

    private final int code;

    private final String phrase;

    HttpStatus(int code, String phrase) {
        this.code = code;
        this.phrase = phrase;
    }

    public int getCode() {
        return code;
    }

    public String getPhrase() {
        return phrase;
    }
}
