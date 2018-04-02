package ch.ntb.jass.common.entities;


public enum CardValueEntity {
	SECHS(1),
	SIEBEN(2),
	ACHT(3),
	NEUN(4),
	ZEHN(5),
	UNDER(6),
	OBER(7),
	KOENIG(8),
	ASS(9);
	
	//Fields
	private final int id;
	
	//Constructors
	CardValueEntity(int id){
		this.id = id;
	}
	//Methods
	/** Creates a new CardValueEntity out of its ID representative.
     * @param id The ID which represents the created value.
     * @return A new CardValueEntity.
     */
	public static CardValueEntity getById(int id) {
		switch(id) {
			case 0:
				return SECHS;
			case 1:
				return SIEBEN;
			case 2:
				return ACHT;
			case 3:
				return NEUN;
			case 4:
				return ZEHN;
			case 5:
				return UNDER;
			case 6:
				return OBER;
			case 7:
				return KOENIG;
			case 8:
				return ASS;
		}
		return null;
	}
	
	//Getter and Setters
	public int getId() {
		return id;
	}
}
