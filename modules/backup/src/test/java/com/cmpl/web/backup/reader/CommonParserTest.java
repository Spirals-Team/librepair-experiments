package com.cmpl.web.backup.reader;

import java.lang.reflect.Field;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.QuoteMode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.backup.writer.DataManipulator;
import com.cmpl.web.core.common.dao.BaseEntity;
import com.cmpl.web.core.page.Page;

@RunWith(MockitoJUnitRunner.class)
public class CommonParserTest {

  private String backupFilePath;
  private DateTimeFormatter dateFormatter;

  @Mock
  private DataManipulator<Page> dataManipulator;

  private PageCSVParser parser;

  @Before
  public void setUp() {
    dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault());
    ;
    backupFilePath = "test";
    parser = new PageCSVParser(dateFormatter, dataManipulator, backupFilePath);
  }

  @Test
  public void testGetCSVFileName() throws Exception {

    String parserName = "pages";

    Assert.assertEquals(parserName + ".csv", parser.getCSVFileName());
  }

  @Test
  public void testBuildCSVFormat() throws Exception {
    Assert.assertEquals(CSVFormat.EXCEL.withHeader().withQuoteMode(QuoteMode.ALL), parser.buildCSVFormat());
  }

  @Test
  public void testSaveEntities() throws Exception {

    List<Page> pages = new ArrayList<Page>();
    parser.saveEntities(pages);
    BDDMockito.verify(dataManipulator, BDDMockito.times(1)).insertData(BDDMockito.eq(pages));
  }

  @Test
  public void testGetFieldsToParse() throws Exception {

    List<Field> fields = new ArrayList<>();
    fields.addAll(Arrays.asList(Page.class.getDeclaredFields()));
    fields.addAll(Arrays.asList(BaseEntity.class.getDeclaredFields()));

    Assert.assertEquals(fields, parser.getFields(Page.class));
  }

}
