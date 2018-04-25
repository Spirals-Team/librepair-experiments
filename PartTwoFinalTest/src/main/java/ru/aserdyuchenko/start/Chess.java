package ru.aserdyuchenko.start;

import ru.aserdyuchenko.models.*;
import java.util.*;

public class Chess {
	Figure figure = new Figure();

	public Figure[][] locationBoard = new Figure[8][8];
	String name;
	int locationBoardX;
	int locationBoardY;
	int newLocationBoardX;
	int newLocationBoardY;
	boolean check = false;

/**
 * This metod show board. This nead, if you want to see all figures on board on display.
 */
	public void showBoard() {
		System.out.println();
		for (int locationBoardX = 0; locationBoardX < 8; locationBoardX++) {
   			for (int locationBoardY = 0; locationBoardY < 8; locationBoardY++) {
				if (locationBoard[locationBoardX][locationBoardY] != null) {
        			System.out.print("[" + locationBoard[locationBoardX][locationBoardY].getName()+ "]" + "\t");
				} else {
					System.out.print("[ ]" + "\t");
				}
    		}
    		System.out.println();
		}
		System.out.println();
	}

/**
 * This metod creat new figure.
 */
	public Figure addFigure(Figure figure) {
		this.locationBoard[figure.getLocationBoardX()][figure.getLocationBoardY()] = figure;
		return figure;
	}

	public void findOldLocationByNameAndRemove(String name) {
		for (int locationBoardX = 0; locationBoardX < 8; locationBoardX++) {
   			for (int locationBoardY = 0; locationBoardY < 8; locationBoardY++) {
				if (locationBoard[locationBoardX][locationBoardY] != null && locationBoard[locationBoardX][locationBoardY].getName().equals(name)) {
					locationBoard[locationBoardX][locationBoardY] = null;
					break;
				}
    		}
		}
	}

/**
 * This metod check: "is new location empty?".
 */
	public boolean checkNewLocationIsEmpty(int newLocationBoardX, int newLocationBoardY) {

		boolean check = false;
		if (locationBoard[newLocationBoardX][newLocationBoardY] == null) {
			check = true;
		} else check = false;
	return check;
	}



/**
 * This metod move figure.
 */
	public void moveFigure(int key, Figure figure, int newLocationBoardX, int newLocationBoardY, Chess chess) {
		this.name = figure.getName();
		findOldLocationByNameAndRemove(this.name);
		if (checkNewLocationIsEmpty(newLocationBoardX, newLocationBoardY) == true) {
			MenuFigure menu = new MenuFigure(figure, newLocationBoardX, newLocationBoardY, chess);
			menu.fillFigures();
			menu.select(key);
		} else System.out.print("Location is not empty!");
	}

/**
 * This metod check: "Is on the road another figure by horisontal?".
 */
	public boolean checkIsOnTheRoadAnotherFigureByHorizontal(Figure figure, int newLocationBoardX, int newLocationBoardY){
		boolean check = true;
		int locationBoardX = figure.getLocationBoardX();
		int locationBoardY = figure.getLocationBoardY();
		this.newLocationBoardX = newLocationBoardX;
		this.newLocationBoardY = newLocationBoardY;
		Chess chess = new Chess();
		if (newLocationBoardX == locationBoardX) {
			if (newLocationBoardY > locationBoardY) {
				for (int index = (locationBoardY+1); index < newLocationBoardY; index++) {
					if (checkNewLocationIsEmpty(locationBoardX, index)==false) {
						System.out.print("Move ancorrect! Figure on the road.");
						check = false;
						break;
					}
				}
			} else if (newLocationBoardY < locationBoardY) {
				for (int index = newLocationBoardY; index < locationBoardY; index++) {
					if (checkNewLocationIsEmpty(locationBoardX, index)==false) {
						System.out.print("Move ancorrect! Figure on the road.");
						check = false;
						break;
					}
				}
			}
		}
	return check;
	}

/**
 * This metod check: "Is on the road another figure by vertical?".
 */
	public boolean checkIsOnTheRoadAnotherFigureByVertical(Figure figure, int newLocationBoardX, int newLocationBoardY){
		boolean check = true;
		int locationBoardX = figure.getLocationBoardX();
		int locationBoardY = figure.getLocationBoardY();
		this.newLocationBoardX = newLocationBoardX;
		this.newLocationBoardY = newLocationBoardY;
		Chess chess = new Chess();
		if (newLocationBoardY == locationBoardY) {
			if (newLocationBoardX > locationBoardX) {
				for (int index = locationBoardX; index < newLocationBoardX; index++) {
					if (checkNewLocationIsEmpty(index, locationBoardY)==false) {
						System.out.print("Move ancorrect! Figure on the road.");
						check = false;
						break;
					}
				}
			} else if (newLocationBoardX < locationBoardX) {
				for (int index = newLocationBoardX; index < locationBoardX; index++) {
					if (checkNewLocationIsEmpty(index, locationBoardY)==false) {
						System.out.print("Move ancorrect! Figure on the road.");
						check = false;
						break;
					}
				}
			}
		}
		return check;
	}

/**
 * This metod check: "Is on the road another figure by diagonal?".
 */
	public boolean checkIsOnTheRoadAnotherFigureByDiagonal(Figure figure, int newLocationBoardX, int newLocationBoardY){
		boolean check = true;
		int locationBoardX = figure.getLocationBoardX();
		int locationBoardY = figure.getLocationBoardY();
		this.newLocationBoardX = newLocationBoardX;
		this.newLocationBoardY = newLocationBoardY;
		Chess chess = new Chess();
		if ((newLocationBoardX > locationBoardX) && (newLocationBoardY > locationBoardY)) {
			for (int index = locationBoardX; index < newLocationBoardX; index++ ) {
				if (checkNewLocationIsEmpty(index, locationBoardY)==false) {
					System.out.print("Move ancorrect! Figure on the road.");
					check = false;
					break;
				}
			locationBoardY++;
			}
		} else if ((newLocationBoardX > locationBoardX) && (newLocationBoardY < locationBoardY)) {
			for (int index = locationBoardX; index < newLocationBoardX; index++ ) {
				if (checkNewLocationIsEmpty(index, locationBoardY)==false) {
					System.out.print("Move ancorrect! Figure on the road.");
					check = false;
					break;
				}
			locationBoardY++;
			}
		} else if ((newLocationBoardX < locationBoardX) && (newLocationBoardY > locationBoardY)) {
			for (int index = locationBoardX; index < newLocationBoardX; index++ ) {
				if (checkNewLocationIsEmpty(index, locationBoardY)==false) {
					System.out.print("Move ancorrect! Figure on the road.");
					check = false;
					break;
				}
				locationBoardY++;
			}
		} else if ((newLocationBoardX < locationBoardX) && (newLocationBoardY < locationBoardY)) {
			for (int index = locationBoardX; index < newLocationBoardX; index++ ) {
				if (checkNewLocationIsEmpty(index, locationBoardY)==false) {
					System.out.print("Move ancorrect! Figure on the road.");
					check = false;
					break;
				}
				locationBoardY++;
			}
		}
		return check;
	}

/**
 * This metod directs, what road we must check(horisontal, vertical or diagonal). This nead to check: "Is on the road anoter figure?".
 */
	public boolean checkIsOnTheRoadAnotherFigure(Figure figure, int newLocationBoardX, int newLocationBoardY){
		int locationBoardX = figure.getLocationBoardX();
		int locationBoardY = figure.getLocationBoardY();
		this.newLocationBoardX = newLocationBoardX;
		this.newLocationBoardY = newLocationBoardY;
		Chess chess = new Chess();
		if (newLocationBoardX == locationBoardX) {
			if (checkIsOnTheRoadAnotherFigureByHorizontal(figure, newLocationBoardX, newLocationBoardY) == true){
					check = true;
			}
		} else if (newLocationBoardY == locationBoardY) {
			if (checkIsOnTheRoadAnotherFigureByVertical(figure, newLocationBoardX, newLocationBoardY)){
					check = true;
			}
		} else if (Math.abs(newLocationBoardX - locationBoardX)==Math.abs(newLocationBoardY - locationBoardY)) {
			if (checkIsOnTheRoadAnotherFigureByDiagonal(figure, newLocationBoardX, newLocationBoardY)){
					check = true;
			}
		}
		return check;
	}
}
