package server.http;

/**
 * Created by Attila on 17/10/05.
 */
public abstract class AbstractAutoConfiguredHttpServer implements HttpServer {


    public abstract void listen();

    public abstract void shutdown();
}