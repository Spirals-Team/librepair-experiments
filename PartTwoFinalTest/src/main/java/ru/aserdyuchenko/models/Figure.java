package ru.aserdyuchenko.models;

public class Figure {
	public String name;
	public int locationBoardX;
	public int locationBoardY;
	public int[][] locationBoard = new int[8][8];

	public Figure() {
	}

	public Figure(String name, int locationBoardX, int locationBoardY) {
		this.name = name;
		this.locationBoardX = locationBoardX;
		this.locationBoardY = locationBoardY;
	}

	public String getName() {
		return this.name = name;
	}

	public String setName(String name) {
		return this.name;
	}

	public int getLocationBoardX() {
		return this.locationBoardX = locationBoardX;
	}

	public int setLocationBoardX(int locationBoardX) {
		return this.locationBoardX;
	}

	public int getLocationBoardY() {
		return this.locationBoardY = locationBoardY;
	}

	public int setLocationBoardY(int locationBoardY) {
		return this.locationBoardY;
	}

	public int getLocationBoard() {
		return this.locationBoard[locationBoardX][locationBoardY] = locationBoard[locationBoardX][locationBoardY];
	}

	public int setLocationBoard(int locationBoardX, int locationBoardY) {
		return this.locationBoard[locationBoardX][locationBoardY];
	}
}
