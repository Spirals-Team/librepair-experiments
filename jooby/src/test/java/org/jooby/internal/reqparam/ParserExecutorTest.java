package org.jooby.internal.reqparam;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jooby.Parser;
import org.jooby.Upload;
import org.jooby.internal.StatusCodeProvider;
import org.jooby.internal.UploadParamReferenceImpl;
import org.jooby.internal.parser.ParserExecutor;
import org.jooby.test.MockUnit;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import com.typesafe.config.ConfigFactory;

public class ParserExecutorTest {

  @Test
  public void ifupload() throws Exception {
    new MockUnit(Injector.class, Upload.class)
        .run(unit -> {
          Set<Parser> parsers = Sets
              .newHashSet((Parser) (type, ctx) -> ctx.ifupload(up -> unit.get(Upload.class)));
          Object converted = new ParserExecutor(unit.get(Injector.class), parsers,
              new StatusCodeProvider(ConfigFactory.empty()))
                  .convert(TypeLiteral.get(Upload.class),
                      new UploadParamReferenceImpl("x", Lists.newArrayList()));
          assertEquals(unit.get(Upload.class), converted);
        });
  }

  @Test
  public void params() throws Exception {
    new MockUnit(Injector.class)
        .run(unit -> {
          Set<Parser> parsers = Sets.newHashSet((Parser) (type, ctx) -> ctx.params(up -> "p"));
          Object converted = new ParserExecutor(unit.get(Injector.class), parsers,
              new StatusCodeProvider(ConfigFactory.empty()))
                  .convert(TypeLiteral.get(Map.class), new HashMap<>());
          assertEquals("p", converted);
        });
  }

  @Test
  public void ctxStr() throws Exception {
    new MockUnit(Injector.class, Upload.class)
        .run(unit -> {
          Set<Parser> parsers = Sets
              .newHashSet((Parser) (type, ctx) -> ctx.toString());
          Object converted = new ParserExecutor(unit.get(Injector.class), parsers,
              new StatusCodeProvider(ConfigFactory.empty()))
                  .convert(TypeLiteral.get(Upload.class),
                      new UploadParamReferenceImpl("x", Lists.newArrayList()));
          assertEquals(parsers.toString(), converted);
        });
  }

}
