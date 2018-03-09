package ru.aserdyuchenko.start;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import ru.aserdyuchenko.models.*;
import org.apache.commons.io.output.ByteArrayOutputStream;
import java.io.PrintStream;

public class ChessTest {
	@Test
	public void whenMoveKingCorrect() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Chess chess = new Chess();
		chess.addFigure(new Figure("King", 0, 5));
		chess.moveFigure(0, (new Figure("King", 0, 5)), 1, 6, chess);
		assertThat(out.toString(), is("Move correct."));
	}

	@Test
	public void whenMoveKingAncorrect() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Chess chess = new Chess();
		chess.addFigure(new Figure("King", 0, 5));
		chess.moveFigure(0, (new Figure("King", 0, 5)), 7, 7, chess);
		assertThat(out.toString(), is("Move ancorrect!"));
	}

	@Test
	public void whenMoveQueenCorrect() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Chess chess = new Chess();
		chess.addFigure(new Figure("Queen", 0, 0));
		chess.moveFigure(1, (new Figure("Queen", 0, 0)), 0, 5, chess);
		assertThat(out.toString(), is("Move correct."));
	}

	@Test
	public void whenMoveQueenAncorrect() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Chess chess = new Chess();
		chess.addFigure(new Figure("Queen", 0, 5));
		chess.moveFigure(1, (new Figure("Queen", 0, 5)), 2, 6, chess);
		assertThat(out.toString(), is("Move ancorrect! Queen can not do this."));
	}

	@Test
	public void whenMoveQueenAndOnTheRoadIsAnotherFigure() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Chess chess = new Chess();
		chess.addFigure(new Figure("Queen", 0, 0));
		chess.addFigure(new Figure("Pawn", 5, 5));
		chess.moveFigure(1, (new Figure("Queen", 0, 0)), 7, 7, chess);
		assertThat(out.toString(), is("Move ancorrect! Figure on the road."));
	}

	@Test
	public void whenMoveRookCorrect() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Chess chess = new Chess();
		chess.addFigure(new Figure("Rook", 0, 5));
		chess.moveFigure(2, (new Figure("Rook", 0, 5)), 7, 5, chess);
		assertThat(out.toString(), is("Move correct."));
	}

	@Test
	public void whenMoveRookAncorrect() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Chess chess = new Chess();
		chess.addFigure(new Figure("Rook", 0, 5));
		chess.moveFigure(2, (new Figure("Rook", 0, 5)), 1, 6, chess);
		assertThat(out.toString(), is("Move ancorrect!"));
	}

	@Test
	public void whenMoveRookAndOnTheRoadIsAnotherFigure() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Chess chess = new Chess();
		chess.addFigure(new Figure("Rook", 0, 5));
		chess.addFigure(new Figure("Pawn", 5, 5));
		chess.moveFigure(2, (new Figure("Rook", 0, 5)), 7, 5, chess);
		assertThat(out.toString(), is("Move ancorrect! Figure on the road."));
	}

	@Test
	public void whenMoveElephantCorrect() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Chess chess = new Chess();
		chess.addFigure(new Figure("Elephant", 0, 5));
		chess.moveFigure(3, (new Figure("Elephant", 0, 5)), 2, 7, chess);
		assertThat(out.toString(), is("Move correct."));
	}

	@Test
	public void whenMoveElephantAncorrect() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Chess chess = new Chess();
		chess.addFigure(new Figure("Elephant", 0, 5));
		chess.moveFigure(3, (new Figure("Elephant", 0, 5)), 7, 5, chess);
		assertThat(out.toString(), is("Move ancorrect!"));
	}

	@Test
	public void whenMoveElephantAndOnTheRoadIsAnotherFigure() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Chess chess = new Chess();
		chess.addFigure(new Figure("Elephant", 0, 0));
		chess.addFigure(new Figure("Pawn", 5, 5));
		chess.moveFigure(3, (new Figure("Elephant", 0, 0)), 7, 7, chess);
		assertThat(out.toString(), is("Move ancorrect! Figure on the road."));
	}

	@Test
	public void whenMoveHorseCorrect() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Chess chess = new Chess();
		chess.addFigure(new Figure("Horse", 0, 5));
		chess.moveFigure(4, (new Figure("Horse", 0, 5)), 1, 7, chess);
		assertThat(out.toString(), is("Move correct."));
	}

	@Test
	public void whenMoveHorseAncorrect() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Chess chess = new Chess();
		chess.addFigure(new Figure("Horse", 0, 5));
		chess.moveFigure(4, (new Figure("Horse", 0, 5)), 1, 5, chess);
		assertThat(out.toString(), is("Move ancorrect!"));
	}

	@Test
	public void whenMovePawnCorrect() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Chess chess = new Chess();
		chess.addFigure(new Figure("Pawn", 5, 5));
		chess.moveFigure(5, (new Figure("Pawn", 5, 5)), 4, 5, chess);
		assertThat(out.toString(), is("Move correct."));
	}

	@Test
	public void whenMovePawneAncorrect() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Chess chess = new Chess();
		chess.addFigure(new Figure("Pawn", 0, 5));
		chess.moveFigure(5, (new Figure("Pawn", 0, 5)), 1, 5, chess);
		assertThat(out.toString(), is("Move ancorrect!"));
	}

	@Test
	public void whenMoveFigureButLocationIsNotEmpty() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Chess chess = new Chess();
		chess.addFigure(new Figure("Pawn", 5, 5));
		chess.addFigure(new Figure("Queen", 0, 5));
		chess.moveFigure(1, (new Figure("Queen", 0, 5)), 5, 5, chess);
		assertThat(out.toString(), is("Location is not empty!"));
	}
}
