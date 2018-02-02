package com.orasi.api.restServices;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.orasi.api.APIBaseTest;
import com.orasi.api.restServices.Headers.HeaderType;
import com.orasi.api.restServices.exceptions.RestException;
import com.orasi.api.restServices.helpers.PostRequest;
import com.orasi.api.restServices.helpers.PostsResponse;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestRestResponse extends APIBaseTest {
    public final static String basePostsUrl = "https://jsonplaceholder.typicode.com/posts";
    private RestResponse response;

    @Features("API")
    @Stories("RestServices")
    @Title("createRestResponse")
    @Test
    public void createRestResponse() {
        RestService rest = new RestService();
        response = rest.sendGetRequest(basePostsUrl + "/1");
        Assert.assertNotNull(response);
    }

    @Features("API")
    @Stories("RestServices")
    @Title("getStatusCode")
    @Test
    public void getStatusCode() {
        RestService rest = new RestService();
        response = rest.sendGetRequest(basePostsUrl + "/1");
        Assert.assertTrue(response.getStatusCode() == ResponseCodes.OK);
    }

    @Features("API")
    @Stories("RestServices")
    @Title("getResponse")
    @Test
    public void getResponse() {
        RestService rest = new RestService();
        response = rest.sendGetRequest(basePostsUrl + "/1");
        Assert.assertTrue(!response.getResponse().isEmpty());
    }

    @Features("API")
    @Stories("RestServices")
    @Title("getResponseAsXMLNode")
    @Test
    public void getResponseAsXMLNode() {
        RestService rest = new RestService();
        response = rest.sendGetRequest(basePostsUrl + "/1");
        Assert.assertTrue(!response.getResponseAsXML().isEmpty());
    }

    @Features("API")
    @Stories("RestServices")
    @Title("getResponseAsXMLArray")
    @Test
    public void getResponseAsXMLArray() {
        RestService rest = new RestService();
        response = rest.sendGetRequest(basePostsUrl);
        Assert.assertTrue(!response.getResponseAsXML().isEmpty());
    }

    @Features("API")
    @Stories("RestServices")
    @Title("getResponseFormat")
    @Test
    public void getResponseFormat() {
        RestService rest = new RestService();
        response = rest.sendGetRequest(basePostsUrl + "/1");
        Assert.assertTrue(response.getResponseFormat().equals("json"));
    }

    @Features("API")
    @Stories("RestServices")
    @Title("getMethod")
    @Test
    public void getMethod() {
        RestService rest = new RestService();
        response = rest.sendGetRequest(basePostsUrl + "/1");
        Assert.assertTrue(response.getMethod().equals("GET"));
    }

    @Features("API")
    @Stories("RestServices")
    @Title("getRequest")
    @Test
    public void getRequest() {
        RestService rest = new RestService();
        response = rest.sendGetRequest(basePostsUrl + "/1");
        Assert.assertNotNull(response.getRequest());
    }

    @Features("API")
    @Stories("RestServices")
    @Title("getRequestBody")
    @Test
    public void getRequestBody() {
        PostRequest request = new PostRequest();
        request.setUserId(1);
        request.setTitle("blah");
        request.setBody("blah");
        RestService rest = new RestService();
        response = rest.sendPostRequest(basePostsUrl + "/1", HeaderType.JSON, RestService.getJsonFromObject(request));
        Assert.assertTrue(StringUtils.isNotEmpty(response.getRequestBody()));
    }

    @Features("API")
    @Stories("RestServices")
    @Title("getURL")
    @Test
    public void getURL() {
        RestService rest = new RestService();
        response = rest.sendGetRequest(basePostsUrl + "/1");
        Assert.assertTrue(response.getURL().equals("https://jsonplaceholder.typicode.com/posts/1"));
    }

    @Features("API")
    @Stories("RestServices")
    @Title("getHeaders")
    @Test
    public void getHeaders() {
        RestService rest = new RestService();
        response = rest.sendGetRequest(basePostsUrl + "/1");
        Assert.assertTrue(response.getHeaders().length > 0);
    }

    @Features("API")
    @Stories("RestServices")
    @Title("getHeaders")
    @Test
    public void getHeadersNotFound() {
        RestService rest = new RestService();
        response = rest.sendGetRequest(basePostsUrl + "/1");
        Assert.assertNull(response.getHeader("blah"));
    }

    @Features("API")
    @Stories("RestServices")
    @Title("getHeader")
    @Test
    public void getHeader() {
        RestService rest = new RestService();
        response = rest.sendGetRequest(basePostsUrl + "/1");
        Assert.assertTrue(response.getHeader("Content-Type").equals("application/json; charset=utf-8"));
    }

    @Features("API")
    @Stories("RestServices")
    @Title("mapJSONToObject")
    @Test
    public void mapJSONToObject() {
        RestService rest = new RestService();
        response = rest.sendGetRequest(basePostsUrl + "/1");
        PostsResponse post = response.mapJSONToObject(PostsResponse.class);
        Assert.assertNotNull(post);
    }

    @Features("API")
    @Stories("RestServices")
    @Title("mapJSONToObjectInvalidJson")
    @Test(expectedExceptions = RestException.class)
    public void mapJSONToObjectInvalidJson() {
        RestService rest = new RestService();
        response = rest.sendGetRequest(basePostsUrl + "/1");
        response.mapJSONToObject("{{blah:1}", PostsResponse.class);
    }

    @Features("API")
    @Stories("RestServices")
    @Title("mapJSONToObjectIncorrectJson")
    @Test(expectedExceptions = RestException.class)
    public void mapJSONToObjectIncorrectJson() {
        RestService rest = new RestService();
        response = rest.sendGetRequest(basePostsUrl + "/1");
        response.mapJSONToObject("{\"userId\":1,\"id\":1,\"blah\":1}", PostsResponse.class);
    }

    @Features("API")
    @Stories("RestServices")
    @Title("mapJSONToTree")
    @Test
    public void mapJSONToTree() {
        RestService rest = new RestService();
        response = rest.sendGetRequest(basePostsUrl + "/1");
        response.mapJSONToTree();
    }

    @Features("API")
    @Stories("RestServices")
    @Title("mapJSONToTree")
    @Test(expectedExceptions = RestException.class)
    public void mapJSONToTreeInvalidJson() {
        RestService rest = new RestService();
        response = rest.sendGetRequest(basePostsUrl + "/1");
        response.mapJSONToTree("{{blah:1}");
    }
}
