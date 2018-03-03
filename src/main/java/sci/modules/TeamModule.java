package sci.modules;

import java.util.ArrayList;

import sci.SCI;
import scilib.objects.TeamData;

/**
 * Default module which allows the user to view {@link TeamData} objects.
 * @author Auxiliatrix
 *
 */
public class TeamModule implements Module {

	/*
	 * Creates a TeamAccessModule.
	 */
	public TeamModule() {}
	
	@Override
	public String[] getInvokers() {return new String[]{"team"};}

	@Override
	public String getName() {return "TeamModule";}

	@Override
	public String getHelpDoc() {
		String response = "";
		response += "[Team] Usage:";
		response += "\nThe 'team' command lets you access and view information about teams and groups.";
		response += "\n\tteam\t\tReturns a list of teams.";
		response += "\n\tteam [name]\tReturns information about the given team.";
		response += "\n";
		return response;
	}

	@Override
	public String process(String line, ArrayList<String> tokens) {
		String response = "";
		if( tokens.size() == 0 ) {
			response += "Here is a list of teams:";
			for( String team : SCI.teamAccessMap.keySet() ) {
				response += "\n\t" + team;
			}
		} else {
			String teamName = tokens.get(1);
			TeamData td = SCI.teamAccessMap.get(teamName.toLowerCase());
			if( td == null ) {
				response = "You must enter a valid team name.";
			} else {
				response = td.toString();
			}
		}
		return response;
	}

}
