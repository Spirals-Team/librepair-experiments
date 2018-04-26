package org.iths.mimicTests.sprint1;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.iths.mimicTests.Store;
import org.junit.Test;

public class TestStore {
	
	
	@Test
	public void TestStore111ValidValues() {
		Store mock = new Store();
		mock.learnResponse("1+1", "2");
		String answer = mock.getResponse("1+1");
		assertEquals("2",answer);
	}
	
	@Test
	public void TestStore112ValidNullRequest() {
		Store mock = new Store();
		mock.learnResponse("1+1", null);
		String answer = mock.getResponse("1+1");
		assertEquals(null,answer);
	}
	
	@Test
	public void TestStore113NullRequestLearnResponse() {
		boolean thrown = false;
		Store mock = new Store();
		try {
		mock.learnResponse(null, "2");
		}catch(NullPointerException e) {
			thrown = true;
		}
		assertTrue(thrown);
	}

	@Test
	public void TestStore116UnlearnAll() {
		Store mock = new Store();
		mock.learnResponse("1+1", "2");
		String response = mock.getResponse("1+1");
		assertEquals("2",response);
		mock.unlearnAll();
		response = mock.getResponse("1+1");
		assertNull(response);
	}
	
	@Test
	public void TestStore117MultiLineValues() {
		Store mock = new Store();
		String multLine = "This is the first line, \n"
				+ "this is the second line.";
		mock.learnResponse("Two lines", multLine);
		String answer = mock.getResponse("Two lines");
		System.out.println(multLine);
		assertEquals(multLine,answer);
	}
	
	@Test
	public void TestStore118NonUSCharacters() {
		Store mock = new Store();
		HashMap<String, String> charachterList = new HashMap();
		charachterList.put("�", "check");
		charachterList.put("�", "check");
		charachterList.put("�", "check");
		Iterator it = charachterList.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        String key = (String) pair.getKey();
	        String value = (String) pair.getValue();
	        mock.learnResponse(key, value);
	        String response = mock.getResponse(key);
	        assertEquals("check",response);
	        System.out.println(key);
	        it.remove();
	    }
	}

}
