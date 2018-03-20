package examples;

import com.objectia.Twostep;
import com.objectia.twostep.exception.*;
import com.objectia.twostep.model.Tax;

public class Example {

    public static void main(String[] args) {

        try {
            String res = Tax.locateIp("8.8.8.8");
            System.out.println(res);
        } catch (BadRequestException ex) {
            System.err.println("Bad request error: " + ex.getMessage());
        } catch (UnauthorizedException ex) {
            System.err.println("Unauthorized error: " + ex.getMessage());
        } catch (RequestFailedException ex) {
            System.err.println("Request failed error: " + ex.getMessage());
        } catch (NotFoundException ex) {
            System.err.println("Not found error: " + ex.getMessage());
        } catch (ConnectionException ex) {
            System.err.println("Connection error: " + ex.getMessage());
        } catch (APIException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }

}