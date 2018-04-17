package parser;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UnknownFormatConversionException;
import java.util.stream.Collectors;


public class JsonConverter<T> implements Converter<T> {


    public T from(String JsonString){
        InputStream stream = new ByteArrayInputStream(JsonString.getBytes(StandardCharsets.UTF_8));
        return from(stream);
    }

    public T from(InputStream JsonString){
            T parsed = null;
        String result = new BufferedReader(new InputStreamReader(JsonString)).lines()
                .parallel().collect(Collectors.joining("\n"));
        try{
            parsed = (T) JsonReader.jsonToJava(result);
        }
        catch (Exception e){
            throw new UnknownFormatConversionException("The message that was attempted to parse" +
                    " appears to be corrupted or an incorrect class was specified");
        }
        return parsed;
    }
    public InputStream to(T javaObject){
        Map<String,Object> args = new HashMap<>();
        args.put(JsonWriter.PRETTY_PRINT,true);

       String converted = JsonWriter.objectToJson(javaObject,args);

       InputStream stream = new ByteArrayInputStream(converted.getBytes(StandardCharsets.UTF_8));

       return stream;
    }

}

