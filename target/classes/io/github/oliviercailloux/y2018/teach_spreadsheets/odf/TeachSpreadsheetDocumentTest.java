package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.odftoolkit.simple.SpreadsheetDocument;

import com.hp.hpl.jena.shared.NotFoundException;

public class TeachSpreadsheetDocumentTest {

	@SuppressWarnings("unused")
	@Test
	public void testOpenODSWhenAllExists() {
		String fileName = "Saisie_voeux_dauphine.ods";
		String tableName = "L3_Informatique";
		String cellPosition = "B4";

		try {
			TeachSpreadsheetDocument.openODS(fileName, tableName, cellPosition);
			Assert.assertTrue(true);
		} catch (NullPointerException e) {
			Assert.assertTrue(false);
		} catch (IOException e) {
			Assert.assertTrue(false);
		} catch (Exception e) {
			Assert.assertTrue(false);
		}

	}

	@SuppressWarnings("unused")
	@Test
	public void testOpenODSWhenFileNotFoundInResources() {
		String fileName = "notFound.ods";
		String tableName = "L3_Informatique";
		String cellPosition = "B4";

		try {
			TeachSpreadsheetDocument.openODS(fileName, tableName, cellPosition);
			Assert.assertTrue(false);
		} catch (NotFoundException e) {
			Assert.assertTrue(true);
		} catch (ClassCastException e) {
			Assert.assertTrue(false);
		} catch (NullPointerException e) {
			Assert.assertTrue(false);
		} catch (IOException e) {
			Assert.assertTrue(false);
		} catch (Exception e) {
			Assert.assertTrue(false);
		}

	}

	@SuppressWarnings("unused")
	@Test
	public void testOpenODSWhenTableDoesNotExists() {
		String fileName = "Saisie_voeux_dauphine.ods";
		String tableName = "notExists";
		String cellPosition = "B4";

		try {
			TeachSpreadsheetDocument.openODS(fileName, tableName, cellPosition);
			Assert.assertTrue(false);
		} catch (NotFoundException e) {
			Assert.assertTrue(false);
		} catch (ClassCastException e) {
			Assert.assertTrue(false);
		} catch (NullPointerException e) {
			Assert.assertTrue(true);
		} catch (IOException e) {
			Assert.assertTrue(false);
		} catch (Exception e) {
			Assert.assertTrue(false);
		}

	}

	// TODO
	// Test if the cell doesn't exist
	
	
}
