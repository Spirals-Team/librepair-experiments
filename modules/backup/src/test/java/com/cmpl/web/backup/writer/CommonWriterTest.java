package com.cmpl.web.backup.writer;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.common.repository.BaseRepository;

@RunWith(MockitoJUnitRunner.class)
public class CommonWriterTest {

  private DateTimeFormatter dateFormatter;
  private DataManipulator<EntityTest> dataManipulator;
  private String backupFilePath;

  @Mock
  private BaseRepository<EntityTest> repository;

  @Spy
  private WriterTest writer;

  @Before
  public void setUp() {
    dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault());
    backupFilePath = "somePath";
    dataManipulator = new DataManipulator<>(repository);
    writer = new WriterTest(dateFormatter, dataManipulator, backupFilePath);
    writer = BDDMockito.spy(writer);
  }

  @Test
  public void testGetCSVFileName() throws Exception {
    Assert.assertEquals("test.csv", writer.getCSVFileName());
  }

  @Test
  public void testGetFileHeader() throws Exception {
    List<String> headerToCreate = Arrays.asList("date", "bytes", "locale", "localDate", "strings", "string",
        "booleanValue", "integerValue", "someLong", "id", "creationDate", "creationUser", "modificationDate",
        "modificationUser");
    Assert.assertEquals(headerToCreate, writer.getFileHeader(new EntityTestBuilder().build()));
  }

  @Test
  public void testParseLocalDate() throws Exception {
    EntityTest entity = new EntityTestBuilder().localDate(LocalDateTime.now()).build();
    String parsedDate = dateFormatter.format(entity.getLocalDate());

    Field field = getFieldAccessible(entity, "localDate");
    Assert.assertEquals(parsedDate, writer.parseLocalDateTime(field, entity));

  }

  @Test
  public void testParseByteArray_Null() throws Exception {
    EntityTest entity = new EntityTestBuilder().build();

    Field field = getFieldAccessible(entity, "bytes");
    Assert.assertEquals("", writer.parseByteArray(field, entity));

  }

  @Test
  public void testParseByteArray_Not_Null() throws Exception {
    byte[] bytes = new byte[]{1};
    EntityTest entity = new EntityTestBuilder().bytes(bytes).build();

    Field field = getFieldAccessible(entity, "bytes");
    Assert.assertEquals(new String(bytes, StandardCharsets.UTF_8), writer.parseByteArray(field, entity));
  }

  @Test
  public void testParseLocale() throws Exception {
    EntityTest entity = new EntityTestBuilder().locale(Locale.FRANCE).build();

    Field field = getFieldAccessible(entity, "locale");
    Assert.assertEquals(Locale.FRANCE.getLanguage(), writer.parseLocale(field, entity));
  }

  @Test
  public void testParseDate() throws Exception {
    Date date = new Date();
    EntityTest entity = new EntityTestBuilder().date(date).build();

    Field field = getFieldAccessible(entity, "date");
    Assert.assertEquals(dateFormatter.format(date.toInstant()), writer.parseDate(field, entity));
  }

  @Test
  public void testParseObject_Integer_not_declared() throws Exception {
    EntityTest entity = new EntityTestBuilder().build();

    Field field = getFieldAccessible(entity, "integerValue");
    Assert.assertEquals("0", writer.parseObject(field, entity));

  }

  @Test
  public void testParseObject_Integer_declared() throws Exception {
    EntityTest entity = new EntityTestBuilder().integerValue(123).build();

    Field field = getFieldAccessible(entity, "integerValue");
    Assert.assertEquals("123", writer.parseObject(field, entity));
  }

  @Test
  public void testParseObject_Null() throws Exception {
    EntityTest entity = new EntityTestBuilder().build();

    Field field = getFieldAccessible(entity, "someLong");
    Assert.assertEquals("", writer.parseObject(field, entity));

  }

  @Test
  public void testParseObject_Not_Null() throws Exception {
    EntityTest entity = new EntityTestBuilder().someLong(123456789l).build();

    Field field = getFieldAccessible(entity, "someLong");
    Assert.assertEquals("123456789", writer.parseObject(field, entity));

  }

  @Test
  public void testParseListString() throws Exception {
    List<String> strings = Arrays.asList("test1", "test2");
    EntityTest entity = new EntityTestBuilder().strings(strings).build();

    Field field = getFieldAccessible(entity, "strings");
    Assert.assertEquals("test1;test2", writer.parseListString(field, entity));
  }

  @Test
  public void testParseListString_One_Element() throws Exception {
    List<String> strings = Arrays.asList("test1");
    EntityTest entity = new EntityTestBuilder().strings(strings).build();

    Field field = getFieldAccessible(entity, "strings");
    Assert.assertEquals("test1", writer.parseListString(field, entity));
  }

  @Test
  public void testParseListString_Null() throws Exception {
    EntityTest entity = new EntityTestBuilder().build();

    Field field = getFieldAccessible(entity, "strings");
    Assert.assertEquals("", writer.parseListString(field, entity));
  }

  @Test
  public void testParseObjectValueToString_Field_Not_Accessible() throws Exception {
    EntityTest entity = new EntityTestBuilder().someLong(123456789l).build();
    Field field = getFieldNotAccessible(entity, "someLong");

    Assert.assertEquals("123456789", writer.parseObjectValueToString(field, entity));

  }

  @Test
  public void testParseObjectValueToString_Field_Accessible() throws Exception {
    EntityTest entity = new EntityTestBuilder().someLong(123456789l).build();
    Field field = getFieldAccessible(entity, "someLong");

    Assert.assertEquals("123456789", writer.parseObjectValueToString(field, entity));

  }

  @Test
  public void testParseObjectValueToString_Date() throws Exception {
    String dateString = "24/10/1989";
    Date date = new Date();
    EntityTest entity = new EntityTestBuilder().date(date).build();

    Field field = getFieldAccessible(entity, "date");

    BDDMockito.doReturn(dateString).when(writer)
        .parseDate(BDDMockito.any(Field.class), BDDMockito.any(EntityTest.class));

    Assert.assertEquals(dateString, writer.parseObjectValueToString(field, entity));

  }

  @Test
  public void testParseObjectValueToString_Locale() throws Exception {
    String localString = Locale.FRANCE.getLanguage();
    EntityTest entity = new EntityTestBuilder().locale(Locale.FRANCE).build();

    Field field = getFieldAccessible(entity, "locale");

    BDDMockito.doReturn(localString).when(writer)
        .parseLocale(BDDMockito.any(Field.class), BDDMockito.any(EntityTest.class));

    Assert.assertEquals(localString, writer.parseObjectValueToString(field, entity));

  }

  @Test
  public void testParseObjectValueToString_LocaleDate() throws Exception {
    String localDateString = "24/10/1989";
    EntityTest entity = new EntityTestBuilder().localDate(LocalDateTime.now()).build();

    Field field = getFieldAccessible(entity, "localDate");

    BDDMockito.doReturn(localDateString).when(writer)
        .parseLocalDateTime(BDDMockito.any(Field.class), BDDMockito.any(EntityTest.class));

    Assert.assertEquals(localDateString, writer.parseObjectValueToString(field, entity));

  }

  @Test
  public void testParseObjectValueToString_Byte_Array() throws Exception {
    String arrayString = "sfdfsddfgdggdfgdfgdfg";
    EntityTest entity = new EntityTestBuilder().bytes(new byte[]{1}).build();

    Field field = getFieldAccessible(entity, "bytes");

    BDDMockito.doReturn(arrayString).when(writer)
        .parseByteArray(BDDMockito.any(Field.class), BDDMockito.any(EntityTest.class));

    Assert.assertEquals(arrayString, writer.parseObjectValueToString(field, entity));

  }

  @Test
  public void testParseObjectValueToString_List_String() throws Exception {
    String listString = "test1;test2";
    EntityTest entity = new EntityTestBuilder().strings(Arrays.asList("test1", "test2")).build();

    Field field = getFieldAccessible(entity, "strings");

    BDDMockito.doReturn(listString).when(writer)
        .parseListString(BDDMockito.any(Field.class), BDDMockito.any(EntityTest.class));

    Assert.assertEquals(listString, writer.parseObjectValueToString(field, entity));

  }

  @Test
  public void testParseObjectValueToString_Object() throws Exception {
    String objectString = "123456789";
    EntityTest entity = new EntityTestBuilder().someLong(123456789l).build();

    Field field = getFieldAccessible(entity, "someLong");

    BDDMockito.doReturn(objectString).when(writer)
        .parseObject(BDDMockito.any(Field.class), BDDMockito.any(EntityTest.class));

    Assert.assertEquals(objectString, writer.parseObjectValueToString(field, entity));

  }

  @Test
  public void testParseObjectValueToString_Exception() throws Exception {
    EntityTest entity = new EntityTestBuilder().someLong(123456789l).build();

    Field field = getFieldAccessible(entity, "someLong");

    BDDMockito.doThrow(new Exception()).when(writer)
        .parseObject(BDDMockito.any(Field.class), BDDMockito.any(EntityTest.class));

    Assert.assertEquals("", writer.parseObjectValueToString(field, entity));

  }

  private Field getFieldAccessible(EntityTest entity, String fieldName) throws Exception {
    Field field = getFieldNotAccessible(entity, fieldName);
    field.setAccessible(true);
    return field;
  }

  private Field getFieldNotAccessible(EntityTest entity, String fieldName) throws Exception {
    return entity.getClass().getDeclaredField(fieldName);
  }
}
