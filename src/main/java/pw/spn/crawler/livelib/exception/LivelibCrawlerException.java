package pw.spn.crawler.livelib.exception;

public class LivelibCrawlerException extends RuntimeException {
    private static final long serialVersionUID = -217211638086560871L;

    public LivelibCrawlerException(Throwable cause) {
        super(cause);
    }

    public LivelibCrawlerException(String message, Throwable cause) {
        super(message, cause);
    }
}
