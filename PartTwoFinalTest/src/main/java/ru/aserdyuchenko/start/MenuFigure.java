package ru.aserdyuchenko.start;

import ru.aserdyuchenko.models.*;
import ru.aserdyuchenko.start.Chess;

public class MenuFigure {
	private Chess chess;
	public Figure figure;
	private String name;
	public int newLocationBoardX;
	public int newLocationBoardY;
	private UserFigure[] figures = new UserFigure[6];
	private int position = 0;

	public MenuFigure(Figure figure, int newLocationBoardX, int newLocationBoardY, Chess chess) {
		this.chess = chess;
		this.figure = figure;
		this.newLocationBoardX = newLocationBoardX;
		this.newLocationBoardY = newLocationBoardY;
	}

	public void fillFigures() {
		this.figures[position++] = new King("King");
		this.figures[position++] = new Queen("Queen");
		this.figures[position++] = new Rook("Rook");
		this.figures[position++] = new Elephant("Elephant");
		this.figures[position++] = new Horse("Horse");
		this.figures[position++] = new Pawn("Pawn");
	}

	public void select(int key) {
		this.figures[key].check(this.figure, this.newLocationBoardX, this.newLocationBoardY, this.chess);
	}

	public void show() {
		for (UserFigure figure : this.figures) {
			if (figure !=null) {
				System.out.println(figure.info());
			}
		}
	}

	private class King extends BaseFigure {
		String nameFigure;
		String name;
		Figure figure;
		int newLocationBoardX;
		int newLocationBoardY;

		public King(String nameFigure) {
			super(nameFigure);
			this.nameFigure = nameFigure;
		}

		public int key() {
			return 0;
		}

		public void check(Figure figure, int newLocationBoardX, int newLocationBoardY, Chess chess) {
			this.name = figure.getName();
			if (checkHowMoveKing(figure, newLocationBoardX, newLocationBoardY) == true) {
				chess.addFigure(new Figure(this.name, newLocationBoardX, newLocationBoardY));
				System.out.print("Move correct.");
			}
		}

		public boolean checkHowMoveKing(Figure figure, int newLocationBoardX, int newLocationBoardY){
			boolean check = false;
			int locationBoardX = figure.getLocationBoardX();
			int locationBoardY = figure.getLocationBoardY();
			this.newLocationBoardX = newLocationBoardX;
			this.newLocationBoardY = newLocationBoardY;

			if (Math.abs(newLocationBoardX - locationBoardX) > 1) {
				System.out.print("Move ancorrect!");
			} else if (Math.abs(newLocationBoardY - locationBoardY) > 1) {
				System.out.print("Move ancorrect!");
			} else {
				check = true;
			}
			return check;
		}

		public String info() {
			return String.format("%s. %s", this.key(), this.nameFigure);
		}
	}

	private class Queen extends BaseFigure {
		String nameFigure;
		String name;
		Figure figure;
		int newLocationBoardX;
		int newLocationBoardY;

		public Queen(String nameFigure) {
			super(nameFigure);
			this.nameFigure = nameFigure;
		}

		public int key() {
			return 1;
		}

		public void check(Figure figure, int newLocationBoardX, int newLocationBoardY, Chess chess) {
			this.name = figure.getName();
			if (checkHowMoveQueen(figure, newLocationBoardX, newLocationBoardY) == true) {
				if (chess.checkIsOnTheRoadAnotherFigure(figure, newLocationBoardX, newLocationBoardY) == true) {
					chess.addFigure(new Figure(this.name, newLocationBoardX, newLocationBoardY));
				System.out.print("Move correct.");
				}
			}
		}

		public String info() {
			return String.format("%s. %s", this.key(), this.nameFigure);
		}

		public boolean checkHowMoveQueen(Figure figure, int newLocationBoardX, int newLocationBoardY){
			boolean check = false;
			int locationBoardX = figure.getLocationBoardX();
			int locationBoardY = figure.getLocationBoardY();
			this.newLocationBoardX = newLocationBoardX;
			this.newLocationBoardY = newLocationBoardY;

			if (newLocationBoardX==locationBoardX) {
				check = true;
			} else if (newLocationBoardY==locationBoardY) {
				check = true;
			} else if (Math.abs(newLocationBoardX - locationBoardX)==Math.abs(newLocationBoardY - locationBoardY)) {
				check = true;
			} else {
				System.out.print("Move ancorrect! Queen can not do this.");
			}
			return check;
		}
	}

	private class Rook extends BaseFigure {
		String nameFigure;
		String name;
		Figure figure;
		int newLocationBoardX;
		int newLocationBoardY;

		public Rook(String nameFigure) {
			super(nameFigure);
			this.nameFigure = nameFigure;
		}

		public int key() {
			return 2;
		}

		public void check(Figure figure, int newLocationBoardX, int newLocationBoardY, Chess chess) {
			if (checkHowMoveRook(figure, newLocationBoardX, newLocationBoardY) == true) {
				if (chess.checkIsOnTheRoadAnotherFigure(figure, newLocationBoardX, newLocationBoardY) == true) {
					chess.addFigure(new Figure(this.name, newLocationBoardX, newLocationBoardY));
					System.out.print("Move correct.");
				}
			}
		}

		public boolean checkHowMoveRook(Figure figure, int newLocationBoardX, int newLocationBoardY){
			boolean check = false;
			int locationBoardX = figure.getLocationBoardX();
			int locationBoardY = figure.getLocationBoardY();
			this.newLocationBoardX = newLocationBoardX;
			this.newLocationBoardY = newLocationBoardY;

			if (newLocationBoardX==locationBoardX) {
				check = true;
			} else if (newLocationBoardY==locationBoardY) {
				check = true;
			} else {
				System.out.print("Move ancorrect!");
			}
			return check;
		}

		public String info() {
			return String.format("%s. %s", this.key(), this.nameFigure);
		}
	}

	private class Elephant extends BaseFigure {
		String nameFigure;
		String name;
		Figure figure;
		int newLocationBoardX;
		int newLocationBoardY;

		public Elephant(String nameFigure) {
			super(nameFigure);
			this.nameFigure = nameFigure;
		}

		public int key() {
			return 3;
		}

		public void check(Figure figure, int newLocationBoardX, int newLocationBoardY, Chess chess) {
			if (checkHowMoveElephant(figure, newLocationBoardX, newLocationBoardY) == true) {
				if (chess.checkIsOnTheRoadAnotherFigure(figure, newLocationBoardX, newLocationBoardY) == true) {
					chess.addFigure(new Figure(this.name, newLocationBoardX, newLocationBoardY));
					System.out.print("Move correct.");
				}
			}
		}

		public boolean checkHowMoveElephant(Figure figure, int newLocationBoardX, int newLocationBoardY){
			boolean check = false;
			int locationBoardX = figure.getLocationBoardX();
			int locationBoardY = figure.getLocationBoardY();
			this.newLocationBoardX = newLocationBoardX;
			this.newLocationBoardY = newLocationBoardY;

			if (Math.abs(newLocationBoardX - locationBoardX)==Math.abs(newLocationBoardY - locationBoardY)) {
				check = true;
			} else {
				System.out.print("Move ancorrect!");
			}
			return check;
		}

		public String info() {
			return String.format("%s. %s", this.key(), this.nameFigure);
		}
	}

	private class Horse extends BaseFigure {
		String nameFigure;
		String name;
		Figure figure;
		int newLocationBoardX;
		int newLocationBoardY;

		public Horse(String nameFigure) {
			super(nameFigure);
			this.nameFigure = nameFigure;
		}

		public int key() {
			return 4;
		}

		public void check(Figure figure, int newLocationBoardX, int newLocationBoardY, Chess chess) {
			if (checkHowMoveHorse(figure, newLocationBoardX, newLocationBoardY) == true) {
				chess.addFigure(new Figure(this.name, newLocationBoardX, newLocationBoardY));
				System.out.print("Move correct.");
			}
		}

		public boolean checkHowMoveHorse(Figure figure, int newLocationBoardX, int newLocationBoardY){
			boolean check = false;
			int locationBoardX = figure.getLocationBoardX();
			int locationBoardY = figure.getLocationBoardY();
			this.newLocationBoardX = newLocationBoardX;
			this.newLocationBoardY = newLocationBoardY;

			if (Math.abs(newLocationBoardX - locationBoardX)==2 && Math.abs(newLocationBoardY - locationBoardY)==1) {
				check = true;
			} else if (Math.abs(newLocationBoardY - locationBoardY)==2 && Math.abs(newLocationBoardX - locationBoardX)==1) {
				check = true;
			} else {
				System.out.print("Move ancorrect!");
			}
			return check;
		}

		public String info() {
			return String.format("%s. %s", this.key(), this.nameFigure);
		}
	}

	private class Pawn extends BaseFigure {
		String nameFigure;
		String name;
		Figure figure;
		int newLocationBoardX;
		int newLocationBoardY;

		public Pawn(String nameFigure) {
			super(nameFigure);
			this.nameFigure = nameFigure;
		}

		public int key() {
			return 5;
		}

		public void check(Figure figure, int newLocationBoardX, int newLocationBoardY, Chess chess) {
			if (checkHowMovePawn(figure, newLocationBoardX, newLocationBoardY) == true) {
				chess.addFigure(new Figure(this.name, newLocationBoardX, newLocationBoardY));
				System.out.print("Move correct.");
			}
		}

		public boolean checkHowMovePawn(Figure figure, int newLocationBoardX, int newLocationBoardY){
			boolean check = false;
			int locationBoardX = figure.getLocationBoardX();
			int locationBoardY = figure.getLocationBoardY();
			this.newLocationBoardX = newLocationBoardX;
			this.newLocationBoardY = newLocationBoardY;

			if (((locationBoardX - newLocationBoardX)==1) && (locationBoardY == newLocationBoardY)) {
				check = true;
			} else {
				System.out.print("Move ancorrect!");
			}
			return check;
		}

		public String info() {
			return String.format("%s. %s", this.key(), this.nameFigure);
		}
	}
}
