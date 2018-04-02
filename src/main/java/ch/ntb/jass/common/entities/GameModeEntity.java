package ch.ntb.jass.common.entities;

public enum GameModeEntity {
	SCHIEBEN(1),
	TRUMPF(2),
	OBENABE(3),
	UNEUFE(4);
	
	//Fields
	private final int id;
	
	//Constructors
	GameModeEntity(int id){
		this.id = id;
	}
	
	//Methods
	/** Creates a new GameModeEntity out of its ID representative.
     * @param id The ID which represents the created game mode.
     * @return A new GameModeEntity.
     */
	public static GameModeEntity getById(int id) {
		switch(id) {
		case 1:
			return SCHIEBEN;
		case 2:
			return TRUMPF;
		case 3:
			return OBENABE;
		case 4:
			return UNEUFE;
		}
		return null;
	}
	
	//Getter and Setters
	public int getId(){
		return id;
	}
	
	
}
