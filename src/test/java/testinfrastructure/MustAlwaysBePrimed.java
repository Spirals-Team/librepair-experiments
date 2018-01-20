package testinfrastructure;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

public class MustAlwaysBePrimed implements Answer<Object> {

    static MustAlwaysBePrimed MUST_ALWAYS_BE_PRIMED = new MustAlwaysBePrimed();

    private MustAlwaysBePrimed() {
        // Singleton
    }

    @Override
    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
        if (isVoidMethod(invocationOnMock)) {
            return null;
        }

        if (!invocationIsInsideClassUnderTest()) {
            return null;
        }

        String className = invocationOnMock.getMock().getClass().getSimpleName();
        String methodName = invocationOnMock.getMethod().getName();
        String arguments = stream(invocationOnMock.getArguments()).map(Object::toString).collect(joining(",","[","]"));
        String message = String.format("Method '%s' on mock '%s' was not primed for arguments '%s'!", methodName, className, arguments);
        throw new AssertionError(message);
    }

    private boolean invocationIsInsideClassUnderTest() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return stream(stackTrace)
                .map(StackTraceElement::getClassName)
                .filter(className -> className.endsWith("Test"))
                .findFirst()
                .map(testClassName -> invocationIsInsideClassUnderTest(stackTrace, testClassName))
                .orElse(false);
    }

    private boolean invocationIsInsideClassUnderTest(StackTraceElement[] stackTrace, String testClassName) {
        String classUnderTestName = testClassName.substring(0, testClassName.length() - 4);
        return stream(stackTrace)
                .map(StackTraceElement::getClassName)
                .anyMatch(className -> className.equals(classUnderTestName));
    }

    private boolean isVoidMethod(InvocationOnMock invocationOnMock) {
        return invocationOnMock.getMethod().getReturnType().equals(void.class);
    }
}
