package com.weaverplatform.sdk;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.weaverplatform.protocol.weavermodel.ModelDefinition;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author bastbijl, Sysunite 2017
 */
public class WeaverModelTest {

  private InputStream getTestYml() {
    return this.getClass().getClassLoader().getResourceAsStream("model.yml");
  }

  @Test
  public void test() throws IOException {

    ModelDefinition modelDefinition = ModelDefinition.parse(getTestYml());

    String yml = ModelDefinition.write(modelDefinition);
    System.out.println(yml);


    ModelDefinition.parse(new ByteArrayInputStream(yml.getBytes()));

    String original = CharStreams.toString(new InputStreamReader(getTestYml(), Charsets.UTF_8)).trim();
    assertEquals(original, yml);
  }

  @Test
  public void illegalValueTest() {

    assertTrue(ModelDefinition.illegalName("1b"));
    assertTrue(ModelDefinition.illegalName(""));
    assertTrue(ModelDefinition.illegalName(null));
    assertTrue(ModelDefinition.illegalName("$g"));
    assertTrue(ModelDefinition.illegalName("Abcd:Abcd"));
    assertFalse(ModelDefinition.illegalName("b"));
    assertFalse(ModelDefinition.illegalName("b1"));
    assertFalse(ModelDefinition.illegalName("_1"));
    assertFalse(ModelDefinition.illegalName("Abcd_Abcd"));
  }
}
