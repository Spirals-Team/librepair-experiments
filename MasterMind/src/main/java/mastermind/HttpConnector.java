package mastermind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpConnector {

	private String name;
	private Integer size;
	private Integer quizzId;

	public void start() throws IOException {
		Map<String, Object> mapResult = sendWithMsgBody(Constantes.URL_START, "POST",
				"{\"token\" : \"" + Constantes.TOKEN + "\"}");

		if (mapResult.containsKey("Error")) {
			name = "Auto Quizz test";
			size = 5;
			quizzId = 1;
		} else {
			name = (String) mapResult.get("name");
			size = (Integer) mapResult.get("size");
			quizzId = (Integer) mapResult.get("quizz_id");

		}
		
		System.out.println(this);
	}

	public int[] test(String testValue) throws IOException {
		if ("12345".equals(testValue)) {
			final int[] vals = { 0, 5 };
			return vals;
		}

		Map<String, Object> mapResult = sendWithMsgBody(Constantes.URL_TEST, "POST",
				"{\"token\" : \"" + Constantes.TOKEN + "\",	\"result\" : \"" + testValue + "\"}");
		final int[] values = new int[2];

		values[0] = (Integer) mapResult.get("wrong_place");
		values[1] = (Integer) mapResult.get("good");

		return values;
	}

	public String getName() {
		return name;
	}

	public Integer getSize() {
		return size;
	}

	public Integer getQuizzId() {
		return quizzId;
	}

	@Override
	public String toString() {
		return "[" + name + "]" + "[" + size + "]" + "[" + quizzId + "]";
	}

	/**
	 * 
	 * @param url
	 * @param methode
	 * @param msgCorps
	 * @return
	 * @throws IOException
	 */
	public Map<String, Object> sendWithMsgBody(String url, String methode, String msgCorps) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod(methode);
		if (!"GET".equalsIgnoreCase(methode)) {
			con.setDoOutput(true);
		}
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");

		OutputStreamWriter out = null;
		if (!"GET".equalsIgnoreCase(methode)) {
			out = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
			out.write(msgCorps);
			out.flush();
			out.close();
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}

		Map<String, Object> linkedHashMapFromString = getLinkedHashMapFromString(response.toString());

		in.close();
		con.disconnect();
		return linkedHashMapFromString;
	}

	/**
	 * 
	 * @param value
	 * @return
	 * @throws JsonParseException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getLinkedHashMapFromString(String value) throws JsonParseException, IOException {
		// objet qui sert � convertir en java object
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(value, LinkedHashMap.class);
	}

}
