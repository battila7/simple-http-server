package server.http;

import java.util.function.BiPredicate;

class ProcessChain {
    private final RequestHandler.RequestLine requestLine;
    private final ResponseWriter responseWriter;
    private boolean isNotDone;

    private ProcessChain(final RequestHandler.RequestLine requestLine, final ResponseWriter responseWriter) {
        this.requestLine = requestLine;
        this.responseWriter = responseWriter;
        this.isNotDone = true;
    }
    
    static ProcessChain of(final RequestHandler.RequestLine requestLine, final ResponseWriter responseWriter){
        return new ProcessChain(requestLine, responseWriter);
    }
    
    ProcessChain process(BiPredicate<RequestHandler.RequestLine, ResponseWriter> process){
        if(isNotDone){
            isNotDone = process.test(requestLine, responseWriter);
        }
        return this;
    }
}
