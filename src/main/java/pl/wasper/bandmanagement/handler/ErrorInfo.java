package pl.wasper.bandmanagement.handler;

public class ErrorInfo {
    public final int status;
    public final String method;
    public final String message;
    public final String url;

    public ErrorInfo(int status, String method, String message, String url) {
        this.status = status;
        this.method = method;
        this.message = message;
        this.url = url;
    }
}
