package org.iths.mimicTests.sprint1;

import java.util.HashMap;
import java.util.Random;

public class MockTest {

	private HashMap<String, String> storedAnswers;

	public MockTest() {
		storedAnswers = new HashMap<String, String>();
	}

	public void storeQuestionAndAnswer(String key, String value) {
		storedAnswers.put(key, value);
	}

	public String checkQuestion(String question) {
		if(storedAnswers.isEmpty()) {
			return "?";
		}else {
			if(storedAnswers.containsKey(question)) {
				return storedAnswers.get(question);
			}else {
				Random generator = new Random();
				Object[] values = storedAnswers.values().toArray();
				return (String)values[generator.nextInt(values.length)]; //helloo
			}

		}

	}

}	

