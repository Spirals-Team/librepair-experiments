package ch.ntb.jass.common.entities;

public enum WeisTypeEntity {
	STOECK(1),
	DREIBLATT(2),
	VIERBLATT(3),
	FUENFBLATT(4),
	VIERGLEICHE(5),
	VIERNELL(6),
	SECHSBLATT(7),
	VIERBAUERN(8),
	SIEBENBLATT(9),
	ACHTBLATT(10),
	NEUNBLATT(11);
	
	//Fields
	private final int id;
	
	//Constructors
	WeisTypeEntity(int id){
		this.id = id;
	}
		
	//Methods
	/** Creates a new WeisTypeEntity out of its ID representative.
     * @param id The ID which represents the created weis.
     * @return A new WeisTypeEntity.
     */
	public static WeisTypeEntity getById(int id) {
		switch(id) {
		case 1:
			return STOECK;
		case 2:
			return DREIBLATT;
		case 3:
			return VIERBLATT;
		case 4:
			return FUENFBLATT;
		case 5:
			return VIERGLEICHE;
		case 6:
			return VIERNELL;
		case 7:
			return SECHSBLATT;
		case 8:
			return VIERBAUERN;
		case 9:
			return SIEBENBLATT;
		case 10:
			return ACHTBLATT;
		case 11:
			return NEUNBLATT;
		}
		return null;
	}
		
	//Getter and Setters
	public int getId() {
		return id;
	}
}
