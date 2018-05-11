package org.pentaho.beam.app.api.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.pentaho.beam.app.api.plugin.StepRegistry;
import org.pentaho.beam.app.api.step.Step;
import org.pentaho.beam.app.api.stepmeta.StepMeta;

import java.lang.reflect.Type;

/**
 * StepMeta Adapter
 * <p>
 * Since StepMeta is an interface we need a way to inject a value so we know how to serialize/deserialize it to the
 * proper object.
 * <p>
 * Created by ccaspanello on 1/29/18.
 */
public class StepMetaAdapter implements JsonSerializer<StepMeta>, JsonDeserializer<StepMeta> {

  private static final String TYPE = "type";
  private static final String step = "step";
  private final StepRegistry stepRegistry;

  public StepMetaAdapter( StepRegistry stepRegistry ) {
    this.stepRegistry = stepRegistry;
  }

  @Override
  public StepMeta deserialize( JsonElement jsonElement, Type typeOf, JsonDeserializationContext context )
    throws JsonParseException {
    JsonObject jsonObject = jsonElement.getAsJsonObject();
    JsonPrimitive prim = (JsonPrimitive) jsonObject.get( TYPE );
    String className = prim.getAsString();
    Class clazz = getObjectClass( className );
    return context.deserialize( jsonObject.get( step ), clazz );
  }

  @Override
  public JsonElement serialize( StepMeta stepMeta, Type typeOfSrc, JsonSerializationContext context ) {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty( TYPE, stepMeta.getClass().getName() );
    jsonObject.add( step, context.serialize( stepMeta ) );
    return jsonObject;
  }

  private Class getObjectClass( String className ) {
//    try {
      return stepRegistry.getStepMeta(className);
      //return Class.forName( className );
//    } catch ( ClassNotFoundException e ) {
//      throw new JsonParseException( e.getMessage() );
//    }
  }
}
