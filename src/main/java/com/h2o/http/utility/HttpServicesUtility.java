package com.h2o.http.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HttpServicesUtility {

	private static final String RESERVEURL = "http://www.chennaimetrowater.tn.nic.in/reserve.asp";
	
	public static String browseTodayReserveInfo() throws IOException {
		URL obj = new URL(RESERVEURL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		StringBuffer response = new StringBuffer();
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			try {
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
			}finally {
				try {
					in.close();
				}catch(Exception e) {}
			}
		} 
		con.disconnect();
		return response.toString();
	}
	public static String queryReserveForGivenDate(String dateAsString) throws IOException{
		URL obj = new URL(RESERVEURL);
		StringBuffer response = new StringBuffer();
		String urlParameters = "ldate="+dateAsString+"&b1=Submit";
		byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
		int    postDataLength = postData.length;
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
		con.setRequestProperty("Content-Length", Integer.toString( postDataLength ));
		con.setRequestProperty("charset", "utf-8");
		con.setUseCaches( false );
		con.getOutputStream().write(postData);

		int responseCode = con.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			try {
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
			}finally {
				try {
					in.close();
				}catch(Exception e) {}
			}
		}
		con.disconnect();
		return response.toString();
	}
	public static void main(String[] args) {
		try {
//			String htmlContent = HttpServicesUtility.browseTodayReserveInfo();
//			Calendar c = Calendar.getInstance();
//			c.set(2018,2,19,0,0);
			
			LocalDate date = LocalDate.of(2018,3,19);
			String dateAsString = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			System.out.println(dateAsString);
		} catch (Exception e) {			
			e.printStackTrace();
		}

	}

}
