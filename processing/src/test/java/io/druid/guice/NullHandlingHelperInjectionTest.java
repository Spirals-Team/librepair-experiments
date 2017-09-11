package io.druid.guice;

import com.google.inject.Injector;
import io.druid.segment.NullHandlingHelper;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class NullHandlingHelperInjectionTest
{
  public static String NULL_HANDLING_CONFIG_STRING = ("druid.null.handling.useDefaultValueForNull");

  @Test
  public void testNullHandlingHelperUseDefaultValues(){
    String prev = System.getProperty(NULL_HANDLING_CONFIG_STRING);
    try {
      System.setProperty(NULL_HANDLING_CONFIG_STRING, "true");
      Injector injector = GuiceInjectors.makeStartupInjector();
      Assert.assertEquals(true, NullHandlingHelper.useDefaultValuesForNull());
    }finally{
      if(prev!= null) {
        System.setProperty(NULL_HANDLING_CONFIG_STRING, prev);
      }
    }
  }

  @Test
  public void testNullHandlingHelperNoDefaultValues(){
    String prev = System.getProperty(NULL_HANDLING_CONFIG_STRING);
    try {
      System.setProperty(NULL_HANDLING_CONFIG_STRING, "false");
      Injector injector = GuiceInjectors.makeStartupInjector();
      Assert.assertEquals(false, NullHandlingHelper.useDefaultValuesForNull());
    }finally{
      if(prev!= null) {
        System.setProperty(NULL_HANDLING_CONFIG_STRING, prev);
      }
    }
  }

}
