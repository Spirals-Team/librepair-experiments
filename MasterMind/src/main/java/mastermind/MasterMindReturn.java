package mastermind;

public class MasterMindReturn {
	private int wellPlaced;
	private int badlyPlaced;

	// Constructor
	public MasterMindReturn(int wellPlaced, int badlyPlaced) {
		this.wellPlaced = wellPlaced;
		this.badlyPlaced = badlyPlaced;
	}

	// getters and setters
	public int getWellPlaced() {
		return badlyPlaced;
	}

	public void setWellPlaced(int wellPlaced) {
		badlyPlaced = wellPlaced;
	}

	public int getBadlyPlaced() {
		return wellPlaced;
	}

	public void setBadlyPlaced(int badlyPlaced) {
		wellPlaced = badlyPlaced;
	}

}
