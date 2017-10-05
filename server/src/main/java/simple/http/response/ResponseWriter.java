package simple.http.response;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

public class ResponseWriter {
    private static final String CRLF = "\r\n";
    private static final String STATUS_LINE_TEMPLATE = "HTTP/1.1 %d %s" + CRLF;
    private static final String HEADER_LINE_TEMPLATE = "%s: %s";

    private final Socket socket;

    private final Response response;

    public static ResponseWriter from(Socket socket, Response responseBuilder) {
        return new ResponseWriter(requireNonNull(socket), requireNonNull(responseBuilder));
    }

    private ResponseWriter(Socket socket, Response response) {
        this.socket = socket;
        this.response = response;
    }

    public void respond() throws IOException {
        try (OutputStream outputStream = socket.getOutputStream();
             PrintWriter printWriter = new PrintWriter(outputStream)) {
            printWriter.print(statusLine(response.getStatus()));
            printWriter.flush();

            printWriter.print(headers(response.getHeaders()));
            printWriter.flush();

            outputStream.write(response.getBody());
            outputStream.flush();
        }
    }

    private String statusLine(Status status) {
        return String.format(STATUS_LINE_TEMPLATE, status.getCode(), status.getPhrase());
    }

    private String headers(Map<String, String> headers) {
        return headers.entrySet().stream()
                .map(entry -> String.format(HEADER_LINE_TEMPLATE, entry.getKey(), entry.getValue()))
                .collect(joining(CRLF, "", CRLF)) + CRLF;
    }
}
