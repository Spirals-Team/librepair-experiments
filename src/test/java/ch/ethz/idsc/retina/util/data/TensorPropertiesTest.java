// code by jph
package ch.ethz.idsc.retina.util.data;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import ch.ethz.idsc.gokart.gui.top.SensorsConfig;
import ch.ethz.idsc.gokart.offline.api.GokartLogConfig;
import ch.ethz.idsc.owl.bot.util.UserHome;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.StringScalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

// TODO JAN more tests!
public class TensorPropertiesTest extends TestCase {
  public void testListSize1() throws Exception {
    ParamContainer ori = new ParamContainer();
    ori.string = "some string, no new line please";
    ori.maxTor = Scalars.fromString("3.13[m*s^2]");
    List<String> list = TensorProperties.strings(ori);
    assertEquals(list.size(), 2);
  }

  public void testListSize2() throws Exception {
    ParamContainer ori = new ParamContainer();
    ori.shape = Tensors.fromString("{1,2,3}");
    ori.abc = RealScalar.ONE;
    ori.maxTor = Scalars.fromString("3.13[m*s^2]");
    List<String> list = TensorProperties.strings(ori);
    assertEquals(list.size(), 3);
  }

  public void testBoolean() throws Exception {
    ParamContainer ori = new ParamContainer();
    ori.status = true;
    assertEquals(TensorProperties.strings(ori).size(), 1);
    Properties properties = TensorProperties.extract(ori);
    assertEquals(properties.getProperty("status"), "true");
    properties.setProperty("status", "corrupt");
    TensorProperties.insert(properties, ori);
    assertNull(ori.status);
    assertEquals(TensorProperties.strings(ori).size(), 0);
    // ---
    properties.setProperty("status", "true");
    TensorProperties.insert(properties, ori);
    assertTrue(ori.status);
    assertEquals(TensorProperties.strings(ori).size(), 1);
    // ---
    properties.setProperty("status", "false");
    TensorProperties.insert(properties, ori);
    assertFalse(ori.status);
    assertEquals(TensorProperties.strings(ori).size(), 1);
  }

  public void testStore() throws Exception {
    ParamContainer ori = new ParamContainer();
    ori.string = "some string, no new line please";
    assertEquals(TensorProperties.strings(ori).size(), 1);
    ori.maxTor = Scalars.fromString("3.13[m*s^2]");
    ori.shape = Tensors.fromString("{1,2,3}");
    assertEquals(TensorProperties.strings(ori).size(), 3);
    ori.abc = RealScalar.ONE;
    assertEquals(TensorProperties.strings(ori).size(), 4);
    Properties properties = TensorProperties.extract(ori);
    {
      ParamContainer pc = new ParamContainer();
      TensorProperties.insert(properties, pc);
      assertEquals(ori.string, pc.string);
      assertEquals(ori.maxTor, pc.maxTor);
      assertEquals(ori.shape, pc.shape);
      assertEquals(ori.abc, pc.abc);
    }
    {
      ParamContainer pc = TensorProperties.newInstance(properties, ParamContainer.class);
      assertEquals(ori.string, pc.string);
      assertEquals(ori.maxTor, pc.maxTor);
      assertEquals(ori.shape, pc.shape);
      assertEquals(ori.abc, pc.abc);
    }
  }

  public void testInsert() {
    Properties properties = new Properties();
    properties.setProperty("maxTor", "123[m]");
    properties.setProperty("shape", "{3   [s*kg],8*I}");
    ParamContainer pc = new ParamContainer();
    Field[] fields = ParamContainer.class.getFields();
    for (Field field : fields)
      if (!Modifier.isStatic(field.getModifiers()))
        try {
          // System.out.println(field.getName());
          Class<?> cls = field.getType();
          final String string = properties.getProperty(field.getName());
          if (Objects.nonNull(string)) {
            if (cls.equals(Tensor.class))
              field.set(pc, Tensors.fromString(string));
            else //
            if (cls.equals(Scalar.class))
              field.set(pc, Scalars.fromString(string));
            else//
            if (cls.equals(String.class))
              field.set(pc, string);
          }
        } catch (Exception exception) {
          exception.printStackTrace();
        }
    assertTrue(pc.maxTor instanceof Quantity);
    assertFalse(pc.shape.stream().anyMatch(scalar -> scalar instanceof StringScalar));
    assertEquals(pc.shape.length(), 2);
  }

  public void testUsername() {
    String name = System.getProperty("user.name");
    assertFalse(name.isEmpty());
  }

  public void testManifest() throws IOException {
    File file = UserHome.file("TensorProperties_testfile.properties");
    assertFalse(file.exists());
    TensorProperties.manifest(file, SensorsConfig.GLOBAL);
    assertTrue(file.exists());
    SensorsConfig sc = TensorProperties.retrieve(file, new SensorsConfig());
    assertEquals(sc.urg04lx, SensorsConfig.GLOBAL.urg04lx);
    assertEquals(sc.vlp16Height, SensorsConfig.GLOBAL.vlp16Height);
    file.delete();
  }

  public void testRetrieveFail() {
    GokartLogConfig sc = TensorProperties.retrieve(new File("asd"), new GokartLogConfig());
    assertTrue(sc != null);
  }
}
