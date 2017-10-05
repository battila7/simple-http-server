package simple.http.request;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by Attila on 17/10/05.
 */
public class RequestReader {
    private static final String EMPTY_LINE = "";
    private static final char HEADER_SEPARATOR = ':';
    private static final int BODY_BUFFER_SIZE = 1024;

    private final Socket socket;

    public static RequestReader from(Socket socket) {
        return new RequestReader(Objects.requireNonNull(socket));
    }

    public RequestReader(Socket socket) {
        this.socket = socket;
    }

    public Request parseRequest() throws MalformedRequestException, IOException {
        try (InputStream inputStream = socket.getInputStream();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            final Request.Builder builder = Request.builder();

            final RequestLine requestLine = parseRequestLine(bufferedReader.readLine());

            builder.method(Method.valueOf(requestLine.method))
                .uri(requestLine.uri);

            // TODO: Replace with actual parsing
            builder.queryParameters(Collections.emptyMap());

            builder.headers(parseHeaders(bufferedReader));

            builder.body(readBody(bufferedReader));

            return builder.build();
        }
    }

    private RequestLine parseRequestLine(String line) throws MalformedRequestException {
        if (Objects.isNull(line)) {
            throw new MalformedRequestException();
        }

        final String[] lineParts = line.split(" ");

        if (lineParts.length != 3) {
            throw new MalformedRequestException();
        }

        return new RequestLine(lineParts[0], lineParts[1]);
    }

    private Map<String, String> parseHeaders(BufferedReader input) throws IOException {
        final Map<String, String> headers = new HashMap<>();

        while (true) {
            final String line = input.readLine();

            if (line.equals(EMPTY_LINE)) {
                return headers;
            }

            final int separatorPosition = line.indexOf(HEADER_SEPARATOR);

            headers.put(line.substring(0, separatorPosition), line.substring(separatorPosition + 2));
        }
    }

    private byte[] readBody(BufferedReader input) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        final char[] buffer = new char[BODY_BUFFER_SIZE];

        int length;

        while (input.ready() && (length = input.read(buffer)) > 0) {
            result.write(toBytes(buffer), 0, length);
        }

        return result.toByteArray();
    }

    private byte[] toBytes(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);

        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
                byteBuffer.position(), byteBuffer.limit());

        Arrays.fill(charBuffer.array(), '\u0000');
        Arrays.fill(byteBuffer.array(), (byte) 0);

        return bytes;
    }

    private static class RequestLine {
        private final String method;

        private final String uri;

        RequestLine(String method, String uri) {
            this.method = method;
            this.uri = uri;
        }
    }
}
