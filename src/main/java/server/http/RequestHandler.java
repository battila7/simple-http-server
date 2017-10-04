package server.http;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Level.WARNING;

class RequestHandler {
    private static final Logger LOGGER = Logger.getLogger(RequestHandler.class.getName());

    private static final String GET_METHOD = "GET";

    private final Socket socket;

    private final Path rootDirectory;

    private final Consumer<Runnable> submitFunction;

    RequestHandler(Socket socket, Path rootDirectory, Consumer<Runnable> submitFunction) throws IOException {
        this.socket = socket;
        this.rootDirectory = rootDirectory;
        this.submitFunction = submitFunction;
    }

    void handle() {
        submitFunction.accept(() -> {
            try {
                handleInternal();
            } catch (Exception e) {
                LOGGER.log(WARNING, "Exception encountered while processing request.", e);
            }
        });
    }

    private void handleInternal() throws IOException {
        try (OutputStream output = socket.getOutputStream();
             PrintWriter printOutput = new PrintWriter(output);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            final ResponseWriter responseWriter = new ResponseWriter(output, printOutput);

            respond(input.readLine(), responseWriter);

        } finally {
            socket.close();

            LOGGER.info("Socket closed, transmission over.");
        }
    }

    private void respond(final String line, final ResponseWriter responseWriter){
        try {
            final RequestLine requestLine = parseRequestLine(line);
            ProcessChain.of(requestLine, responseWriter)
                    .process(this::checkMethodAllowed)
                    .process(this::checkResourceExists)
                    .process(this::checkReadAllowed)
                    .process(this::writeOk);
        } catch (MalformedRequestException e) {
            responseWriter.writeBadRequest();
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        } catch (Exception e) {
            responseWriter.writeInternalServerError();
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private boolean writeOk(final RequestLine requestLine, final ResponseWriter responseWriter) {
        LOGGER.info("Resource found!");
        try {
            responseWriter.writeFile(resolvedPath(requestLine));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return false;
    }

    private boolean checkReadAllowed(final RequestLine requestLine, final ResponseWriter responseWriter){

        if(!resolvedPath(requestLine).toFile().canRead()){
            LOGGER.info("Read file is not allowed");
            responseWriter.writeForbidden();
            return false;
        }
        return true;
    }

    private boolean checkResourceExists(final RequestLine requestLine, final ResponseWriter responseWriter){
        if (!resourceExists(requestLine)) {
            LOGGER.info("Sending not found response...");
            responseWriter.writeNotFound();
            return false;
        }
        return true;
    }

    private boolean checkMethodAllowed(final RequestLine requestLine, final ResponseWriter responseWriter){
        if (!isMethodAllowed(requestLine)) {
            LOGGER.info("Sending method not allowed response...");
            responseWriter.writeMethodNotAllowed();
            return false;
        }
        return true;
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

    private boolean isMethodAllowed(RequestLine requestLine) {
        return GET_METHOD.equals(requestLine.method);
    }

    private boolean resourceExists(RequestLine requestLine) {
        return Files.exists(resolvedPath(requestLine));
    }

    private Path resolvedPath(RequestLine requestLine) {
        String uri = requestLine.uri;

        if (uri.startsWith("/")) {
            uri = uri.substring(1);
        }

        // avoiding reaching any file (especially /etc/shadow) by typing a bunch of '../' and then the absolute path
        uri = uri.replaceAll("\\.{2}", ".");

        return rootDirectory.resolve(uri);
    }

    static class RequestLine {
        private final String method;

        private final String uri;

        RequestLine(String method, String uri) {
            this.method = method;
            this.uri = uri;
        }
    }
}
