package ch.ntb.jass.common.entities;

import java.util.Dictionary;

public class ScoreEntity {
	//Dictionary of <Team, Score>
	//Use get((teamId) key) to obtain the score of one team
	public Dictionary<Integer, Integer> scores;
}
