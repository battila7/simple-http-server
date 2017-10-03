package server.http;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

class ResponseWriter {
    private static final int COPY_BUFFER_SIZE = 8192;
    private static final String DEFAULT_CONTENT_TYPE = "text/html";
    private static final String CRLF = "\r\n";
    private static final String STATUS_LINE_TEMPLATE = "HTTP/1.1 %d %s" + CRLF;
    private static final String CONTENT_TYPE_HEADER_TEMPLATE = "Content-Type: %s" + CRLF;
    private static final String CONTENT_LENGTH_HEADER_TEMPLATE = "Content-Length: %d" + CRLF;

    private static final Map<String, String> contentTypeMap = new HashMap<>();

    static {
        contentTypeMap.put(".uu", "application/octet-stream");
        contentTypeMap.put(".exe", "application/octet-stream");
        contentTypeMap.put(".ps", "application/postscript");
        contentTypeMap.put(".zip", "application/zip");
        contentTypeMap.put(".sh", "application/x-shar");
        contentTypeMap.put(".tar", "application/x-tar");
        contentTypeMap.put(".snd", "audio/basic");
        contentTypeMap.put(".au", "audio/basic");
        contentTypeMap.put(".wav", "audio/x-wav");
        contentTypeMap.put(".gif", "image/gif");
        contentTypeMap.put(".jpg", "image/jpeg");
        contentTypeMap.put(".jpeg", "image/jpeg");
        contentTypeMap.put(".htm", "text/html");
        contentTypeMap.put(".html", "text/html");
        contentTypeMap.put(".text", "text/plain");
        contentTypeMap.put(".c", "text/plain");
        contentTypeMap.put(".cc", "text/plain");
        contentTypeMap.put(".c++", "text/plain");
        contentTypeMap.put(".h", "text/plain");
        contentTypeMap.put(".pl", "text/plain");
        contentTypeMap.put(".txt", "text/plain");
        contentTypeMap.put(".java", "text/plain");
        contentTypeMap.put(".xml", "application/xml");
    }

    private final OutputStream output;

    private final PrintWriter printOutput;

    ResponseWriter(OutputStream output, PrintWriter printOutput) {
        this.output = output;
        this.printOutput = printOutput;
    }

    void writeFile(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            writeImATeapot();
        } else {
            writeFileOK(path);
        }
    }

    void writeNotFound() {
        printOutput.print(statusLine(HttpStatus.NOT_FOUND));
        printOutput.flush();
    }

    void writeMethodNotAllowed() {
        printOutput.print(statusLine(HttpStatus.METHOD_NOT_ALLOWED));
        printOutput.flush();
    }

    private void writeImATeapot() {
        printOutput.print(statusLine(HttpStatus.IM_A_TEAPOT));
        printOutput.flush();
    }

    private void writeFileOK(Path path) throws IOException {
        printOutput.print(statusLine(HttpStatus.OK));
        printOutput.print(contentType(path));
        printOutput.print(contentLength(path));
        printOutput.print(CRLF);

        printOutput.flush();

        printContents(path);
    }

    private String contentLength(Path path) {
        final long length = path.toFile().length();

        return String.format(CONTENT_LENGTH_HEADER_TEMPLATE, length);
    }

    private String contentType(Path path) {
        final String httpContentType = contentTypeMap.getOrDefault(getExtension(path), DEFAULT_CONTENT_TYPE);

        return String.format(CONTENT_TYPE_HEADER_TEMPLATE, httpContentType);
    }

    private String statusLine(HttpStatus status) {
        return String.format(STATUS_LINE_TEMPLATE, status.getCode(), status.getPhrase());
    }

    private void printContents(Path path) throws IOException {
        final byte[] buffer = new byte[COPY_BUFFER_SIZE];
        int readCount;

        final FileInputStream inputStream = new FileInputStream(path.toFile());

        while ((readCount = inputStream.read(buffer)) > 0) {
            output.write(buffer, 0, readCount);
        }
    }

    private String getExtension(Path path) {
        final int indexOfDot = path.toString().lastIndexOf('.');

        return path.toString().substring(indexOfDot);
    }
}
