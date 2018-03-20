import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class UploadConfigTest {
  @Test
  public void testUploadConfig() throws Exception {
    UUID userRandom = UUID.randomUUID();
    HttpResponse response1 = Unirest.post("http://localhost:8084/signUp").field("name", "testSignUp" + userRandom).field("password", "testSignUpPass").asString();
    assertEquals(200, response1.getStatus());
    HttpResponse response2 = Unirest.get("http://localhost:8084/dashboard").asString();
    assertEquals(200, response2.getStatus());
    assertFalse(response2.getBody().toString().isEmpty());
    String config = "{" +
            "  \"products\" : [" +
            "    {" +
            "      \"id\" : \"theProductId\"," +
            "      \"name\" : \"theProductName\"," +
            "      \"quotas\" : [" +
            "        {" +
            "          \"id\" : \"theQuotaId\"," +
            "          \"name\" : \"theQuotaName\"," +
            "          \"type\" : \"numerical\"," +
            "          \"tiers\" : [" +
            "            {" +
            "              \"id\" : \"theTierId\"," +
            "              \"name\" : \"theTierName\"," +
            "              \"max\" : \"5\"," +
            "              \"price\" : \"10\"," +
            "              \"graceExtra\" : \"2\"" +
            "            }" +
            "          ]" +
            "        }" +
            "      ]" +
            "    }" +
            "  ]" +
            "}";
    HttpResponse response3 = Unirest.post("http://localhost:8084/setConfig").header("Content-Type", "multipart/form-data").field("uploaded_file", new ByteArrayInputStream(config.getBytes()),"application/json").asString();
    assertEquals(200, response3.getStatus());
  }
}
