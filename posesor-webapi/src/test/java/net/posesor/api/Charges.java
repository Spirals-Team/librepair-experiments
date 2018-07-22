package net.posesor.api;

import com.jayway.restassured.http.ContentType;
import lombok.experimental.UtilityClass;
import net.posesor.charges.ChargeDocumentDto;
import org.springframework.http.HttpStatus;

import static com.jayway.restassured.RestAssured.given;

@UtilityClass
public class Charges {

    public static class API {

        public static String create(int port, String userName,  ChargeDocumentDto dto) {
            return given()
                    .port(port)
                    .auth().basic(userName, userName)
                    .contentType(ContentType.JSON)
                    .body(dto)
                    .expect().statusCode(HttpStatus.CREATED.value())
                    .post("/api/charges/")
                    .header("location");
        }
    }
}
