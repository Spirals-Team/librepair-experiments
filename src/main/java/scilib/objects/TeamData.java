package scilib.objects;

import java.util.HashMap;

/**
 * Object which stores data about a team's statistics.
 * @author Auxiliatrix
 *
 */
public class TeamData {
	
	private HashMap<String, Double> data;
	
	public String teamNumber;
	
	/**
	 * Creates a TeamData object with a given team name.
	 * @param teamNumber String identification of a team
	 */
	public TeamData(String teamNumber) {
		data = new HashMap<String, Double>();

		this.teamNumber = teamNumber;
	}
	
	/**
	 * Gets the data for a given statistic.
	 * @param dataType - String parameter of the data being retrieved
	 * @return value of the data for the given String parameter. If the parameter is invalid, return null.
	 */
	public Double get(String dataType) {
		return data.keySet().contains(dataType) ? data.get(dataType) : null;
	}
	
	/**
	 * Sets data for a given statistic for this team.
	 * @param dataType - String parameter of the data being added
	 * @param value - Double value of the data for the given String parameter
	 */
	public void put(String dataType, double value) {
		data.put(dataType, value);
	}
	
	@Override
	public String toString() {
		String response = "";
		response += "Team " + teamNumber + "\n";
		for( String key : data.keySet() ) {
			response += "\t" + key + ": " + data.get(key) + "\n";
		}
		return response;
	}
	
}
