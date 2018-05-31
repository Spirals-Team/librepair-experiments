package com.cmpl.web.backup.writer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CarouselCSVWriterTest {

  @Spy
  private CarouselCSVWriter writer;

  @Test
  public void testGetWriterName() throws Exception {
    Assert.assertEquals("carousels", writer.getWriterName());
  }

}
