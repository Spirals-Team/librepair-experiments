package anton.potemkin.spring.message;

/**
 * Created by Anton Potemkin on 26/05/2018.
 */
public class Message {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void init() {
        System.out.println("Init bean");
    }

    public void destroy() {
        System.out.println("destroy");
    }
}
