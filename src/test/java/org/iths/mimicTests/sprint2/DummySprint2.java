package org.iths.mimicTests.sprint2;

import java.util.HashMap;
import java.util.Scanner;

import org.iths.mimicTests.RequestResponse;

public class DummySprint2 {

	private HashMap<String, RequestResponse> list = new HashMap();

	public DummySprint2() {

	}

	/**
	 * Mock for sending getRequest to the Mimic System. Only takes url in 3 formats 
	 * with different functions:
	 *
	 * 1. localhost:8080/XXX?storeRequest=YYY 
	 * Will directly store a question XXX with response YYY.
	 * 
	 * 2. localhost:8080/XXX?learn 
	 * Will prompt you to give a response to XXX and if request 
	 * already exists, ask you if you want to replace it. 
	 * 
	 * 3. localhost:8080/XXX
	 * Ask the system a question XXX and if the system has 
	 * a response its gives a response. Otherwise asks the user 
	 * if they want to store a response.
	 *  
	 * @param request String - in above specified format
	 * @return String - with response from request
	 */
	public String sendGetRequest(String getRequest) {
		if(getRequest.contains("?storeRequest=")) {

			String request = getRequest.replace("localhost:8080/", "");
			int index = request.indexOf("?");
			request = request.substring(0, index);

			index = getRequest.indexOf("=");
			String response = getRequest.substring(index, getRequest.length());
			response = response.replace("=", "");

			list.put(request, new RequestResponse(request, response));

			return list.get(request).getResponse();						
		}else if(getRequest.contains("?learn")) {

			boolean cont = true;

			String request = getRequest.replace("localhost:8080/", "");

			int index = request.indexOf("?");
			request = request.substring(0, index);
			Scanner sc = new Scanner(System.in);
			System.out.print("Please input answer to the question: ");
			String response = sc.nextLine();

			if(list.containsKey(request)) {
				System.out.print("Request already have an answer: "+list.get(request).getResponse()+".");
				System.out.print(" Do you want to replace it with: "+response+"? Enter Yes/No:");
				String reply = sc.nextLine();
				if(reply.equalsIgnoreCase("Yes")) {
					list.replace(request, new RequestResponse(request, response));
					System.out.println("New request stored");
					sc.close();
				}else{
					System.out.println("No changes has been made");
					cont = false;
					sc.close();
				}
			}else{
				if(cont) {
					list.put(request, new RequestResponse(request, response));
					System.out.println("New request stored");
					sc.close();
				}
			}

			return list.get(request).getResponse();	
		}else if (getRequest.contains("localhost:8080/") && !getRequest.contains("?storeRequest=") && !getRequest.contains("?learn=")){
			String request = getRequest.replace("localhost:8080/", "");
			if(!list.containsKey(request)) {
				return "No answer to this question";
			}else {
				return list.get(request).getResponse();
			}
			
		}else {
			return "Invalid request";
		}
	}

}
