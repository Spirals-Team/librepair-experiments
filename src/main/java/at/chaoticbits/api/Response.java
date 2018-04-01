package at.chaoticbits.api;


/**
 * Illustrates a HttpResponse containing
 * the Http Status Code and the response body
 */
public class Response {


    private int status;

    private String body;



    public Response(int status, String body) {
        this.status = status;
        this.body = body;
    }


    public int getStatus() {
        return status;
    }


    public String getBody() {
        return body;
    }

}
