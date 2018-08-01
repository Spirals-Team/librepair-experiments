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
		return wellPlaced;
	}

	public void setWellPlaced(int wellPlaced) {
		this.wellPlaced = wellPlaced;
	}

	public int getBadlyPlaced() {
		return badlyPlaced;
	}

	public void setBadlyPlaced(int badlyPlaced) {
		this.badlyPlaced = badlyPlaced;
	}

}
