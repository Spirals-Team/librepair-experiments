package com.pufose.server;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@Import(WebSecurityConfig.class)
public class RestControlerIT {

	
	@LocalServerPort
	private int port;
	
	@Autowired
	private IMongoRepository gridRepo;

	private String url;
	
	
	@Before
	public void setup() {
		url="http://localhost:" + port;
		gridRepo.deleteAll();
		
		
	}
	
	@Test
	public void testGetAllNamesWhenNoGridExists() throws Exception {
		given().
		when().
				get(url + "/api").
		then().
				statusCode(200).
				assertThat().
				body(is("[]"));
	}
	@Test
	public void testGetAllNamesWithExistingOneGrid() throws Exception {
	gridRepo.save(new DatabaseGrid(1));
	
	given().
	when().
	get(url + "/api").
	then().
	statusCode(200).
	assertThat().
	body(
	is("[\"1\"]")
	);
	}
	
	@Test
	public void testGetAllNamesWithExistingMoreGrids() throws Exception {
	gridRepo.save(new DatabaseGrid(1));
	gridRepo.save(new DatabaseGrid(2));
	given().
	when().
	get(url + "/api").
	then().
	statusCode(200).
	assertThat().
	body(
	is("[\"1\",\"2\"]")
	);
	}
	
	@Test
	public void testGetOneGridWhenItExists() throws Exception {
		gridRepo.save(new DatabaseGrid(1));
		given().
		when().
		get(url + "/api/grid1").
		then().
		statusCode(200).
		assertThat().
		body(
		is("{\"n\":0,\"matrix\":[],\"id\":1}")
		);
	}
	
	@Test
	public void testGetOneGridWhenItDoesNotExists() throws Exception {
		given().
		when().
		get(url + "/api/grid1").
		then().
		statusCode(200).
		assertThat().
		body(
		is("null")
		);
	}
	
	@Test
	public void testGetPathWhenItsEmpty() {
		gridRepo.save(new DatabaseGrid(new int[][] {{0,0},{0,0}},1));
		given().
		when().
		get(url + "/api/path0_0TO0_1IN1").
		then().
		statusCode(200).
		assertThat().
		body(
		is("[]")
		);
	}
	@Test
	public void testGetPathWhenItsNotEmpty() {
		gridRepo.save(new DatabaseGrid(new int[][] {{1,1},{0,0}},1));
		given().
		when().
		get(url + "/api/path0_0TO0_1IN1").
		then().
		statusCode(200).
		assertThat().
		body(
		is("[\"0_0\",\"0_1\"]")
		);
	}
	
	
		
	
	

	
}
