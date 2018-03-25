package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Incidence;

public class IncidenceUtils {
	
	private static String[] usernames = {"user1","sensor2","company3","user4"};
	private static int[] usertypes = {1,2,3,4};
	private static String[] name = {"Incendio","Terremoto","Inundacion","Temperatura"};
	private static String[] description = {"Descripcion1","Descripcion2","Descripcion3","Descripcion4"};
	private static String[] location = {"43;5","82:74","115:20","20:63"};
	private static String[] info = {"ExtraInfo1","ExtraInfo2","ExtraInfo3","ExtraInfo4"};
	private static int[] state = {0,1,2,3};
	private static int[] expiration = {20181003,20180625,20180101,20180906};
	private static String[] operator = {"operator1","operator2","operator3","operator4"};
	
	
	public static Incidence randomInci(int i) {
		Incidence inci = new Incidence();
		Random r = new Random();
		inci.setInciId("id"+i);
		inci.setUsername(usernames[r.nextInt(4)]);
		inci.setUsertype(usertypes[r.nextInt(4)]);
		inci.setInciName(name[r.nextInt(4)]);
		inci.setInciDescription(description[r.nextInt(4)]);
		inci.setInciLocation(location[r.nextInt(4)]);
		inci.setInciInfo(info[r.nextInt(4)]);
		inci.setState(state[r.nextInt(4)]);
		inci.setExpiration(expiration[r.nextInt(4)]);
		inci.setOperatorId(operator[r.nextInt(4)]);
		return inci;
	}
	
	public static String getStateString(int i) {
		switch(i) {
			case 0:
				return "Open";
			case 1:
				return "In process";
			case 2:
				return "Closed";
			case 3:
				return "Cancelled";
			default:
				return "";
		}
	}

	public static List<Incidence> filterByOperator(List<Incidence> incis, String operatorId) {
		List<Incidence> result = new ArrayList<Incidence>();
		for(Incidence i : incis) {
			if(i.getOperatorId().equals(operatorId))
				result.add(i);
		}
		return result;
	}

}
