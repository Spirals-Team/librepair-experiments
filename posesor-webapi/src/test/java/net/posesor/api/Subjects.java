package net.posesor.api;

import com.jayway.restassured.http.ContentType;
import lombok.experimental.UtilityClass;
import net.posesor.WebSocketClient;
import net.posesor.subjects.SubjectDto;
import net.posesor.subjects.SubjectsNotifier;

import java.util.Iterator;

import static com.jayway.restassured.RestAssured.given;

@UtilityClass
public class Subjects {

    public static class API {

        public static Iterator createdEvent(int port, String userName) {
            return new WebSocketClient()
                    .connect(port, userName, SubjectsNotifier.QUEUE, SubjectsNotifier.SubjectCreatedDto.class)
                    .blockingIterable().iterator();
        }

        public static String[] suggest(int port, String userName, String token) {

            return given()
                    .port(port)
                    .auth().basic(userName, userName)
                    .contentType(ContentType.JSON)
                    .get("/api/subjects/suggest/" + token)
                    .as(String[].class);
        }
    }
}
