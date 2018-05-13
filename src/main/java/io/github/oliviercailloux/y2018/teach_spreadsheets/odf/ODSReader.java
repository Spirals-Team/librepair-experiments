package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.util.Objects;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.style.StyleTypeDefinitions.CellBordersType;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements SpreadsheetReader : it allows to read a Spreadsheet
 * document, get the value of a cell, check if a cell is a diagonal border or
 * not. To use these methods, you'll need to precise a {@link Table}, the sheet
 * where the Reader will read.
 * 
 * @author Victor CHEN (Kantoki), Louis Fontaine
 * @version Version : 2.1 Last update : 08/05/2018
 */
public class ODSReader implements SpreadsheetReader {

	private final static Logger LOGGER = LoggerFactory.getLogger(ODSReader.class);

	private SpreadsheetDocument document;

	private Table sheet;

	public ODSReader(SpreadsheetDocument spreadsheetDocument) {
		this.document = Objects.requireNonNull(spreadsheetDocument);
		this.sheet = Objects.requireNonNull(spreadsheetDocument.getTableList().get(0));

		LOGGER.info("ODSReader initialized without actual sheet");
	}

	public ODSReader(SpreadsheetDocument spreadsheetDocument, String sheet) {
		this.document = Objects.requireNonNull(spreadsheetDocument);
		this.sheet = Objects.requireNonNull(spreadsheetDocument.getTableByName(sheet));

		LOGGER.info("ODSReader initialized with actual sheet");

	}

	public Table getSheet() {
		return sheet;
	}

	@Override
	public void setSheet(Table sheet) {
		this.sheet = sheet;
	}

	public void setSheet(String sheet) {
		this.sheet = Objects.requireNonNull(document.getTableByName(sheet));
	}

	public SpreadsheetDocument getDocument() {
		return document;
	}

	@Override
	public void setDocument(SpreadsheetDocument document) {
		this.document = Objects.requireNonNull(document);
	}

	@Override
	public String getCellValue(String cellPosition) {
		Cell cell = sheet.getCellByPosition(cellPosition);
		boolean isDiagonalBorder = isDiagonalBorder(sheet.getTableName(), cellPosition);
		if (cell == null) {
			return "";
		}
		if (isDiagonalBorder) {
			return null;
		}
		return cell.getDisplayText();
	}

	@SuppressWarnings("unused")
	@Override
	public boolean isDiagonalBorder(String yearOfStudy, String cellPosition) {
		Table table = this.getDocument().getSheetByName(yearOfStudy);
		/*
		 * There is a problem with ODFTookit, their function getBorder return NULL if
		 * the border doesn't exists, but if there is a border, It doesn't return the
		 * description but a NumberFormatException, so the catch fix it
		 */
		try {
			table.getCellByPosition(cellPosition).getBorder(CellBordersType.DIAGONALBLTR);
		} catch (NullPointerException e) {
			return false;
		} catch (NumberFormatException z) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unused")
	@Override
	public boolean isDiagonalBorder(Cell cell) {
		/*
		 * There is a problem with ODFTookit, their function getBorder return NULL if
		 * the border doesn't exists, but if there is a border, It doesn't return the
		 * description but a NumberFormatException, so the catch fix it
		 */
		try {
			cell.getBorder(CellBordersType.DIAGONALBLTR);
		} catch (NullPointerException e) {
			return false;
		} catch (NumberFormatException z) {
			return true;
		}
		return false;
	}

}
