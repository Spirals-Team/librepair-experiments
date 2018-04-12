/*
# Feature file of the test case made by John Bryan Yepez Herrera
# The test is based on EP-326: "https://mercurio.psl.com.co/jira/browse/EP-326"
*/

package stepDefinitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class EP326Steps {

    private JSONObject topic, newTopic;
    private CloseableHttpClient httpClient = HttpClients.createDefault();

    private String convertResponseToString(HttpResponse response) throws IOException {
        InputStream responseStream = response.getEntity().getContent();
        Scanner scanner = new Scanner(responseStream, "UTF-8");
        String responseString = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return responseString;
    }

    private JSONArray getTopicsByStatus(int status) throws IOException {
        String dirGet = "https://integrador2018apitest.herokuapp.com/topics/findByStatus?status=";
        HttpGet request = new HttpGet(dirGet + status);
        request.addHeader("accept", "application/json");
        HttpResponse httpResponse = httpClient.execute(request);
        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
        String json = convertResponseToString(httpResponse);
        return new JSONArray(json);
    }

    private JSONObject patchTopic(JSONObject patchTopic) throws IOException {
        String dirPatch = "https://integrador2018apitest.herokuapp.com/topics";
        HttpPatch request = new HttpPatch(dirPatch);
        StringEntity entity = new StringEntity(patchTopic.toString());
        request.addHeader("content-type", "application/json");
        request.setEntity(entity);
        HttpResponse response = httpClient.execute(request);
        assertEquals(200, response.getStatusLine().getStatusCode());
        return new JSONObject(convertResponseToString(response));
    }

    private boolean isContained(JSONObject newTopic, JSONArray openTopics) {
        boolean contained = false;
        for(int i=0; i<openTopics.length(); ++i) {
            if(openTopics.getJSONObject(i).get("id").equals(newTopic.get("id"))){
                contained = true;
                break;
            }
        }
        return contained;
    }

    @Given(value = "^the api have already topics with status (\\d+)$")
    public void theApiHaveAlreadyTopicsWithStatus(int arg0) throws Throwable {
        JSONArray topicArray = getTopicsByStatus(arg0);
        assertTrue(topicArray.length() > 0);
        topic = topicArray.getJSONObject(0);
    }

    @When(value = "^I send a patch request to change the status to (\\d+)$")
    public void iSendAPatchRequestToChangeTheStatusTo(int arg0) throws Throwable {
        newTopic = new JSONObject(topic.toString());
        newTopic.put("status",arg0);
        newTopic = patchTopic(newTopic);
    }

    @Then(value = "^The topic must be updated correctly$")
    public void theTopicMustBeUpdatedCorrectly() throws Throwable {
        assertEquals(newTopic.get("id"),topic.get("id"));
    }

    @And(value = "^must be stored in the database as a topic with status (\\d+)$")
    public void mustBeStoredInTheDatabaseAsATopicWithStatus(int arg0) throws Throwable {
        JSONArray topicsArray = getTopicsByStatus(arg0);
        assertTrue(isContained(newTopic, topicsArray));
    }
}
