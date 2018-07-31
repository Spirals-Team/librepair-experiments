package tech.spring.structure.auth.evaluator;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class StructureSecurityExpressionEvaluatorTest {

    @Test(expected = UnsupportedOperationException.class)
    public void testEmptyChain() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, IOException, ServletException {
        StructureSecurityExpressionEvaluator structureSecurityExpressionEvaluator = new StructureSecurityExpressionEvaluator();

        Field field = StructureSecurityExpressionEvaluator.class.getDeclaredField("EMPTY_CHAIN");
        field.setAccessible(true);
        FilterChain filterChain = (FilterChain) field.get(structureSecurityExpressionEvaluator);

        filterChain.doFilter(new MockHttpServletRequest(), new MockHttpServletResponse());
    }

}
