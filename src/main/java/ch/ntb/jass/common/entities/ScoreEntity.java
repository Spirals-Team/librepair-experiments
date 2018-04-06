package ch.ntb.jass.common.entities;

import java.util.Map;

public class ScoreEntity {
	//Dictionary of <Team, Score>
	//Use get((teamId) key) to obtain the score of one team
	public Map<Integer, Integer> scores;
}
