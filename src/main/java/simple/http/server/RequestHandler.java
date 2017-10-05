package simple.http.server;

class RequestHandler {
    /*private static final Logger LOGGER = Logger.getLogger(RequestHandler.class.getName());

    private final Socket socket;

    private final Consumer<Runnable> submitFunction;

    RequestHandler(Socket socket, Consumer<Runnable> submitFunction) {
        this.socket = socket;
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

    private void handleInternal() throws IOException, MalformedRequestException {
        try (OutputStream output = socket.getOutputStream();
             PrintWriter printOutput = new PrintWriter(output);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            final RequestLine requestLine = parseRequestLine(input.readLine());

            final ResponseWriter responseWriter = new ResponseWriter(output, printOutput);

            if (!isMethodAllowed(requestLine)) {
                LOGGER.info("Sending method not allowed response...");

                responseWriter.writeMethodNotAllowed();
            } else if (!resourceExists(requestLine)) {
                LOGGER.info("Sending not found response...");

                responseWriter.writeNotFound();
            } else {
                LOGGER.info("Resource found!");

                responseWriter.writeFile(resolvedPath(requestLine));
            }
        } finally {
            socket.close();

            LOGGER.info("Socket closed, transmission over.");
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

        return rootDirectory.resolve(uri);
    }

    static class RequestLine {
        private final String method;

        private final String uri;

        RequestLine(String method, String uri) {
            this.method = method;
            this.uri = uri;
        }
    }*/
}
