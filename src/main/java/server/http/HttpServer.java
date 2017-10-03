package server.http;

public interface HttpServer {
    void listen();

    void shutdown();
}
