package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Incidence;

public class IncidenceUtils {
	
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

	public static List<Incidence> filterByState(List<Incidence>incis){
		List<Incidence> result = new ArrayList<Incidence>();
		for(Incidence i : incis) {
			if(i.getState()!=2)
				result.add(i);
		}
		return result;
	}
}
