package com.weaverplatform.sdk;

import com.weaverplatform.protocol.SortedWriteOperationParser;
import com.weaverplatform.protocol.WriteOperationParser;
import com.weaverplatform.protocol.model.CreateNodeOperation;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;


public class ParserTest {


  @Test
  public void test() throws IOException {
    InputStream stream = getClass().getClassLoader().getResourceAsStream("cbim.json");
    WriteOperationParser parser = new WriteOperationParser();
    assert(parser.parseNext(stream, 1).get(0) instanceof CreateNodeOperation);
    assert(parser.parseNext(stream, 0).isEmpty());
    parser.parseNext(stream, 10);
  }
  @Test
  public void testStartChars() throws IOException {
    InputStream stream = getClass().getClassLoader().getResourceAsStream("cbim.json");
    Set<Character> res = SortedWriteOperationParser.startChars(stream);
    for(Character c : res) {
      System.out.println(c);
    }
  }


}
