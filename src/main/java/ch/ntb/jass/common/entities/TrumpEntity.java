package ch.ntb.jass.common.entities;

public enum TrumpEntity {
	SCHIEBEN(1, GameModeEntity.SCHIEBEN, null),
	EICHEL(2, GameModeEntity.TRUMPF, CardColorEntity.EICHEL),
	ROSE(3, GameModeEntity.TRUMPF, CardColorEntity.ROSE),
	SCHILTE(4, GameModeEntity.TRUMPF, CardColorEntity.SCHILTE),
	SCHELLE(5, GameModeEntity.TRUMPF, CardColorEntity.SCHELLE),
	OBENABE(6, GameModeEntity.OBENABE, null),
	UNEUFE(7, GameModeEntity.UNEUFE, null);
	
	//Fields
	private final int id;
	//TODO Maybe use private final fields instead?
	//TODO Maybe not necessary anyways?
	public GameModeEntity gameMode;
	public CardColorEntity trumpColor;
	
	//Constructors
	TrumpEntity(int id, GameModeEntity gameMode, CardColorEntity trumpColor){
		this.id = id;
		this.gameMode = gameMode;
		this.trumpColor = trumpColor;
	}
	
	//Methods
	/** Creates a new TrumpEntity out of its ID representative.
     * @param id The ID which represents the created trump.
     * @return A new TrumpEntity.
     */
	public static TrumpEntity getById(int id) {
		switch(id) {
		case 1:
			return SCHIEBEN;
		case 2:
			return EICHEL;
		case 3:
			return ROSE;
		case 4:
			return SCHILTE;
		case 5:
			return SCHELLE;
		case 6:
			return OBENABE;
		case 7:
			return UNEUFE;
		}
		return null;
	}
	
	//Getters and Setters
	public int getId() {
		return id;
	}

}
