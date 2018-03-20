import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class SignUpLogInLogOutTest {
  @Test
  public void testSignUp() throws Exception {
    HttpResponse response1 = Unirest.post("http://localhost:8084/signUp").field("name","testSignUp").field("password", "testSignUpPass").asString();
    assertEquals(200, response1.getStatus());
    HttpResponse response2 = Unirest.get("http://localhost:8084/dashboard").asString();
    assertEquals(200, response2.getStatus());
    assertFalse(response2.getBody().toString().isEmpty());
  }

  @Test
  public void testLogInLogOut() throws Exception {
    HttpResponse response1 = Unirest.post("http://localhost:8084/signUp").field("name","testLogIn").field("password", "testLogInPass").asString();
    assertEquals(200, response1.getStatus());
    HttpResponse response2 = Unirest.get("http://localhost:8084/logout").asString();
    assertEquals(200, response2.getStatus());
    HttpResponse response3 = Unirest.get("http://localhost:8084/dashboard").asString();
    assertEquals(403, response3.getStatus());
    HttpResponse response4 = Unirest.post("http://localhost:8084/logIn").field("name","testLogIn").field("password", "testLogInPass").asString();
    assertEquals(200, response4.getStatus());
    HttpResponse response5 = Unirest.get("http://localhost:8084/dashboard").asString();
    assertEquals(200, response5.getStatus());
    assertFalse(response2.getBody().toString().isEmpty());
  }
}
