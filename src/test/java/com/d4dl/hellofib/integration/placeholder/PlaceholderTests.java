package com.d4dl.hellofib.integration.placeholder;

import com.d4dl.hellofib.placeholder.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import java.net.URISyntaxException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Tests for the placeholder jpa tier
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlaceholderTests {

    @LocalServerPort
    private String port;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void whenGetPosts() throws URISyntaxException {
        ResponseEntity<List<Post>> response = restTemplate.exchange("http://localhost:" + port + "/placeholder-posts", HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>() {});
        for (Post post : response.getBody()) {
            assertThat(post.getId()).isNotNull();
        }
        assertThat(response.getStatusCodeValue()).isLessThan(299);
    }

    @Test
    public void testVerbs() {
    }
}
