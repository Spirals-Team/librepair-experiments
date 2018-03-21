import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class LandingPageTest {
  @Test
  public void testLandingPage() throws Exception {
    HttpResponse response = Unirest.get("http://localhost:8084").asString();
    assertEquals(200, response.getStatus());
    assertFalse(response.getBody().toString().isEmpty());
  }
}
