package examples;

import com.objectia.Twostep;
import com.objectia.twostep.exception.APIConnectionException;
import com.objectia.twostep.exception.APIException;
import com.objectia.twostep.exception.TwostepException;
import com.objectia.twostep.model.User;
import com.objectia.twostep.model.Users;

public class Example {

    public static void main(String[] args) {

        Twostep.clientId = System.getenv("TWOSTEP_CLIENT_ID");
        Twostep.clientSecret = System.getenv("TWOSTEP_CLIENT_SECRET");
        
        try {
            User user = Users.createUser("jdoe@example.com", "+12125551234", 1);
            // ...
        } catch (APIException ex) {
            System.err.println("Error: " + ex.getMessage());
        } catch (APIConnectionException ex) {
            System.err.println("Error: " + ex.getMessage());
        } catch (TwostepException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }
}