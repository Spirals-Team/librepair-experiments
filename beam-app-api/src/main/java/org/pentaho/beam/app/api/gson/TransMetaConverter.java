package org.pentaho.beam.app.api.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.apache.beam.sdk.repackaged.org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.commons.io.FileUtils;
import org.pentaho.beam.app.api.plugin.StepRegistry;
import org.pentaho.beam.app.api.stepmeta.StepMeta;
import org.pentaho.beam.app.api.transmeta.TransMeta;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * TransMeta Converter
 * <p>
 * Coverts a TransMeta Object to and from JSON strings / files.
 * <p>
 * Created by ccaspanello on 1/29/18.
 */
public class TransMetaConverter {

  private Gson gson;

  public TransMetaConverter( StepRegistry stepRegistry ) {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter( StepMeta.class, new StepMetaAdapter(stepRegistry) );
    builder.setPrettyPrinting();
    this.gson = builder.create();
  }

  public String toJson( TransMeta transMeta ) {
    return gson.toJson( transMeta );
  }

  public TransMeta fromJson( String transMeta ) {
    return gson.fromJson( transMeta, TransMeta.class );
  }

  public void toJson( TransMeta transMeta, File file ) {
    try {
      FileUtils.writeStringToFile( file, toJson( transMeta ) );
    } catch ( IOException e ) {
      throw new RuntimeException( "Unable to write json file.", e );
    }
  }

  public TransMeta fromJson( File file ) {
    try {
      String json = FileUtils.readFileToString( file, Charset.forName("UTF-8") );
      return fromJson(json);
    } catch ( IOException e ) {
      throw new RuntimeException( "Unable to read json file.", e );
    }
  }

  public TransMeta fromJson( File file, Map<String, String> parameters ) {
    try {
      String json = FileUtils.readFileToString( file, Charset.forName("UTF-8") );
      StrSubstitutor substitutor = new StrSubstitutor( parameters );
      String newJson = substitutor.replace(json);
      return fromJson(newJson);
    } catch ( IOException e ) {
      throw new RuntimeException( "Unable to read json file with parameters.", e );
    }
  }
}
