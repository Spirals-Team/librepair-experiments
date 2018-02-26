package com.weaverplatform.sdk;

import com.weaverplatform.protocol.WriteOperationParser;
import com.weaverplatform.protocol.model.CreateNodeOperation;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;


public class ParserTest {


  @Test
  public void test() throws IOException {
    InputStream stream = getClass().getClassLoader().getResourceAsStream("cbim.json");
    WriteOperationParser parser = new WriteOperationParser();
    assert(parser.parseNext(stream, 1).get(0) instanceof CreateNodeOperation);
    assert(parser.parseNext(stream, 0).isEmpty());
    parser.parseNext(stream, 10);
  }


}
