/*
 * Copyright (C) 2016 Max Planck Institute for Psycholinguistics, Nijmegen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package nl.mpi.tg.eg.experiment.client.model;

import nl.mpi.tg.eg.experiment.client.exception.MetadataFieldException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @since Aug 9, 2016 5:24:26 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class MetadataFieldTest {

    private static final String LIST = "|male|female|other";
    private static final String STRING = ".{3,}";
    private static final String EMAIL = "^[^@]+@[^@]+$";
    private static final String CHECKBOX = "true|false";
    private static final String MULTILINE = "[\\S\\s]{3,}";

    public MetadataFieldTest() {
    }

    /**
     * Test of isCheckBox method, of class MetadataField.
     */
    @Test
    public void testIsCheckBox() {
        System.out.println("isCheckBox");
        assertEquals(true, new MetadataField("", "", CHECKBOX, CHECKBOX, "").isCheckBox());
        assertEquals(false, new MetadataField("", "", EMAIL, EMAIL, "").isCheckBox());
        assertEquals(false, new MetadataField("", "", STRING, STRING, "").isCheckBox());
        assertEquals(false, new MetadataField("", "", LIST, LIST, "").isCheckBox());
        assertEquals(false, new MetadataField("", "", MULTILINE, MULTILINE, "").isCheckBox());
    }

    /**
     * Test of isMultiLine method, of class MetadataField.
     */
    @Test
    public void testIsMultiLine() {
        System.out.println("isMultiLine");
        assertEquals(false, new MetadataField("", "", CHECKBOX, CHECKBOX, "").isMultiLine());
        assertEquals(false, new MetadataField("", "", EMAIL, EMAIL, "").isMultiLine());
        assertEquals(false, new MetadataField("", "", STRING, STRING, "").isMultiLine());
        assertEquals(false, new MetadataField("", "", LIST, LIST, "").isMultiLine());
        assertEquals(true, new MetadataField("", "", MULTILINE, MULTILINE, "").isMultiLine());
    }

    /**
     * Test of isListBox method, of class MetadataField.
     */
    @Test
    public void testIsListBox() {
        System.out.println("isListBox");
        assertEquals(true, new MetadataField("", "", CHECKBOX, CHECKBOX, "").isListBox());
        assertEquals(false, new MetadataField("", "", EMAIL, EMAIL, "").isListBox());
        assertEquals(false, new MetadataField("", "", STRING, STRING, "").isListBox());
        assertEquals(true, new MetadataField("", "", LIST, LIST, "").isListBox());
        assertEquals(false, new MetadataField("", "", MULTILINE, MULTILINE, "").isListBox());
    }

    /**
     * Test of getListValues method, of class MetadataField.
     */
    @Test
    public void testGetListValues() {
        System.out.println("getListValues");
        MetadataField instance = new MetadataField("", "", LIST, LIST, "");
        String[] expResult = new String[]{"", "male", "female", "other"};
        String[] result = instance.getListValues();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of validateValue method, of class MetadataField.
     */
    @Test
    public void testValidateValue() throws Exception {
        System.out.println("validateValue");
        new MetadataField("", "", CHECKBOX, CHECKBOX, "").validateValue("true");
        new MetadataField("", "", EMAIL, EMAIL, "").validateValue("test@test");
        new MetadataField("", "", STRING, STRING, "").validateValue("test");
        try {
            new MetadataField("", "", STRING, STRING, "").validateValue("test\ntest");
            fail("Failed to prevent linebreaks in field");
        } catch (MetadataFieldException exception) {

        }
        new MetadataField("", "", LIST, LIST, "").validateValue("male");
        new MetadataField("", "", MULTILINE, MULTILINE, "").validateValue("male\nasldj");
    }
}
