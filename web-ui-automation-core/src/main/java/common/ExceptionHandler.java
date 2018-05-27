package common;

import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.Assert;

final class ExceptionHandler
{
    public static <T, R> R apply(Function<T, R> executeFunction, Function<T, String> exceptionMessageFunction, T arg)
    {
        R result = null;
        
        try
        {
            result = executeFunction.apply(arg);
        }
        catch(RuntimeException e)
        {
            errorMessage(exceptionMessageFunction.apply(arg));
        }
        
        return result;
    }
    
    public static <T> void apply(Consumer<T> executeFunction, Function<T, String> exceptionMessageFunction, T arg)
    {
        try
        {
            executeFunction.accept(arg);
        }
        catch(RuntimeException e)
        {
            errorMessage(exceptionMessageFunction.apply(arg));
        }
    }
    
    public static <T> String getNotContainsMessage(T containmentValue, T actualValue)
    {
        return getMessage(StringCollection.Error.NOT_CONTAINS_HEADER, StringCollection.Identifier.ACTUAL_VALUE + actualValue.toString() + System.lineSeparator() + StringCollection.Identifier.CONTAINMENT_VALUE + containmentValue);
    }
    
    public static <T> String getTimeoutMessage(T arg)
    {
        return getMessage(StringCollection.Error.TIMEOUT_HEADER, arg.toString());
    }
    
    public static <T> String getUnexpectedTagNameMessage(T arg)
    {
        return getMessage(StringCollection.Error.UNEXPECTED_TAG_NAME_HEADER, arg.toString());
    }
    
    public static <T> String getNoSuchElementMessage(T arg)
    {
        return getMessage(StringCollection.Error.NO_SUCH_ELEMENT_HEADER, arg.toString());
    }
    
    public static String getMessage(String header, String content)
    {
        return System.lineSeparator() + header + content;
    }
    
    public static void errorMessage(String errorMessage)
    {
        Assert.fail(errorMessage);
    }
}
