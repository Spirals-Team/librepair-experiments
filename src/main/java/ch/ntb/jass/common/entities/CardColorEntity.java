package ch.ntb.jass.common.entities;

public enum CardColorEntity {
	EICHEL(1),
	ROSE(2),
	SCHILTE(3),
	SCHELLE(4);
	
	//Fields
	private final int id;
	
	//Constructors
	CardColorEntity(int id){
		this.id = id;	
	}
	
	//Methods
	/** Creates a new CardColorEntity out of its ID representative.
     * @param id The ID which represents the created color.
     * @return A new CardColorEntity.
     */
	public static CardColorEntity getById(int id) {
		switch(id) {
		case 1:
			return EICHEL;
		case 2:
			return ROSE;
		case 3:
			return SCHILTE;
		case 4:
			return SCHELLE;
		}
		return null;
	}
	
	//Getter and Setters
	public int getId(){
		return id;
	}
	
}
