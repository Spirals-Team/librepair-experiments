/*
    Step definition file for the test EP-325, which steps are available in JIRA:
                                    https://mercurio.psl.com.co/jira/browse/EP-325
    Made by Jenny Marcela Zapata Pulgarín
*/

package stepDefinitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;


public class EP325Steps {

    CloseableHttpClient httpClient= HttpClients.createDefault();
    int responseCode;

    @Given("^The API has a list of groups with status=(\\d+) already saved$")
    public void the_API_has_a_list_of_groups_with_status_already_saved(int arg1) throws Throwable {
        JSONArray list = getRequest(arg1);
        assertTrue(list.length()>0);
    }

    @When("^I send a get request by status equals to (\\d+)$")
    public void i_send_a_get_request_by_status_equals_to(int arg1) throws Throwable {
        getRequest(arg1);
    }

    @Then("^I should get a successful response$")
    public void i_should_get_a_successful_response() throws Throwable {
        assertEquals(200, responseCode);
    }

    @And("^The groups have the correct attributes for the status (\\d+)$")
    public void the_groups_have_the_correct_attributes_for_the_status(int arg1) throws Throwable {
        JSONArray groups = getRequest(arg1);
        int i;
        if (arg1 == 0){
            for(i=0; i < groups.length(); i++){
                JSONObject group = groups.getJSONObject(i);
                assertEquals(0, group.getInt("status"));
                assertTrue(group.get("chat").equals(null));
                assertFalse(group.get("createdAt").equals(null));
                assertTrue(group.get("openedAt").equals(null));
                assertTrue(group.get("closedAt").equals(null));
            }
        }else if(arg1 == 1){
            for(i=0; i < groups.length(); i++){
                JSONObject group = groups.getJSONObject(i);
                assertEquals(1, group.getInt("status"));
                assertFalse(group.get("chat").equals(null));
                assertFalse(group.get("createdAt").equals(null));
                assertFalse(group.get("openedAt").equals(null));
                assertTrue(group.get("closedAt").equals(null));
            }
        }else{
            for(i=0; i < groups.length(); i++){
                JSONObject group = groups.getJSONObject(i);
                assertEquals(2, group.getInt("status"));
                assertFalse(group.get("createdAt").equals(null));
                assertFalse(group.get("closedAt").equals(null));
            }
        }
    }

    public JSONArray getRequest(int status) throws IOException {
        HttpGet request = new HttpGet("https://integrador2018apitest.herokuapp.com/topics/findByStatus?status=" + status);
        request.addHeader("accept", "application/json");
        HttpResponse httpResponse = httpClient.execute(request);
        responseCode = httpResponse.getStatusLine().getStatusCode();
        String jsonResponse = convertResponseToString(httpResponse);
        JSONArray jsonArray = new JSONArray(jsonResponse);
        return jsonArray;
    }

    private String convertResponseToString(HttpResponse response) throws IOException{
        InputStream responseStream = response.getEntity().getContent();
        Scanner scanner = new Scanner(responseStream, "UTF-8");
        String responseString = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return responseString;
    }
}
