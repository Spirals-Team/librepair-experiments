/**
 * 
 */
package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.util.Objects;
import java.util.Scanner;

import org.odftoolkit.simple.SpreadsheetDocument;

/**
 * <b>This class allows to print data by using a SpreadsheetShower object.</b>
 * @author tuannamdavaux
 * @see io.github.oliviercailloux.y2018.teach_spreadsheets.odf.SpreadsheetReader
 */
public class SpreadsheetShower {
	/**
	 * SpreadsheetReader object for showing cell values
	 */
	private SpreadsheetReader spreadsheetReader = null;

	public SpreadsheetShower(SpreadsheetReader spreadsheetReader) {
		this.spreadsheetReader = Objects.requireNonNull(spreadsheetReader);
	}
	
	/**
	 * This method asks the user to enter cell position in standard input then print its value.
	 * And it will ask again until the user types End.
	 */
	public void showCellsValue() {
		try(Scanner sc = new Scanner(System.in)){
		String position = "";
		String cellValue = "";
		boolean typeEnd = false;
		System.out.println("Ce programme peut lire les cellules de la feuille :\nEntrez une position par exemple A1 / Tapez Fin pour terminer la saisie.");
		do {
			System.out.println(cellValue);
			position = sc.nextLine();
			if(position.equals("Fin")) {
				System.out.println("End");
				typeEnd = !typeEnd;
			}else cellValue = spreadsheetReader.getCellValue(position);
		} while (!typeEnd);
		}
	}
	
	/**
	 * Same method plus : 
	 * This method asks the user to specify a file.
	 */
	public void showCellsValueWithSpecifiedFile() throws Exception {
		try(Scanner sc = new Scanner(System.in)){
			String position = "";
			String cellValue = "";
			boolean typeEnd = false;
			System.out.println("Veuillez préciser le fichier d'entrée avec le chemin complet:");
			spreadsheetReader.setDocument(SpreadsheetDocument.loadDocument(sc.nextLine()));
			System.out.println("Ce programme peut lire les cellules de la feuille :\nEntrez une position par exemple A1 / Tapez Fin pour terminer la saisie.");
			do {
				System.out.println(cellValue);
				position = sc.nextLine();
				if(position.equals("Fin")) {
					System.out.println("End");
					typeEnd = !typeEnd;
				}else cellValue = spreadsheetReader.getCellValue(position);
			} while (!typeEnd);
			}
	}

}
