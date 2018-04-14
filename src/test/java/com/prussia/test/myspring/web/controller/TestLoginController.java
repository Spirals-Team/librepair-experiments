package com.prussia.test.myspring.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.prussia.play.spring.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class TestLoginController {
	
	private static final Logger log = LoggerFactory.getLogger(TestLoginController.class);

	@Test
	public void testPostLogin(){
		RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        
        Map<String,String> params = new HashMap<String,String>();
        params.put("username", "prussia");
//        params.put("password", "12345");
        
		JSONObject jsonObj = new JSONObject(params);
        log.info(jsonObj.toString());
        
        HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);

        String url = "http://localhost:8080/api/login";
		/**
		 * o.s.w.s.m.m.a.HttpEntityMethodProcessor
		 * Written [{timestamp=Thu Mar 31 22:34:55 CST 2016, status=400,
		 * error=Bad Request, exception=org.springframework.web.bind.
		 * MissingServletRequestParameterException, message=Required String
		 * parameter 'username' is not present, path=/api/login}] as
		 * "application/json;charset=UTF-8" using
		 * [org.springframework.http.converter.json.
		 * MappingJackson2HttpMessageConverter@75ef3eaa]
		 */
        
        String result =  restTemplate.postForObject(url,formEntity, String.class); 
        
//      Map result = restTemplate.postForObject(url, params, Map.class);
        log.info(result);
	}
	
	
	/**
	 * 
	 * 
		1、restTemplate.postForObject(url, null, String.class, params);
		
		Example: 
		
		 UriTemplate template = new UriTemplate("http://example.com/hotels/{hotel}/bookings/{booking}");
		 Map<String, String> uriVariables = new HashMap<String, String>();
		 uriVariables.put("booking", "42");
		 uriVariables.put("hotel", "1");
		 System.out.println(template.expand(uriVariables));
		 
		will print: 
		http://example.com/hotels/1/bookings/42

	2、postForObject(URI url, Object request, Class<T> responseType)
	
	url中不用加参数
	Create a new resource by POSTing the given object to the URL, and returns the representation found in the response. 
	
	The request parameter can be a HttpEntity in order to add additional HTTP headers to the request.







	 */
}
