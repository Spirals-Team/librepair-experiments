package com.weaverplatform.protocol;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.weaverplatform.protocol.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.weaverplatform.protocol.model.WriteOperation.WriteOperationAction.*;

/**
 * @author Mohamad Alamili
 */
public class WriteOperationParser {

  private static final Gson gson;

  static {
    WriteOperationDeserializer deserializer = new WriteOperationDeserializer();

    deserializer.registerAction(CREATE_NODE,                CreateNodeOperation.class);
    deserializer.registerAction(CREATE_ATTRIBUTE,           CreateAttributeOperation.class);
    deserializer.registerAction(CREATE_RELATION,            CreateRelationOperation.class);
    deserializer.registerAction(REMOVE_NODE,                RemoveNodeOperation.class);
    deserializer.registerAction(REMOVE_NODE_UNRECOVERABLE,  RemoveNodeUnrecoverableOperation.class);
    deserializer.registerAction(REMOVE_ATTRIBUTE,           RemoveAttributeOperation.class);
    deserializer.registerAction(REMOVE_RELATION,            RemoveRelationOperation.class);

    gson = new GsonBuilder().registerTypeAdapter(WriteOperation.class, deserializer).create();
  }

  public static List<WriteOperation> parse(String writeOperationsJson) {
    return gson.fromJson(writeOperationsJson, new TypeToken<List<WriteOperation>>(){}.getType());
  }

  private JsonReader reader = null;

  public List<WriteOperation> parseNext(InputStream stream, int chunkSize) throws IOException {
    List<WriteOperation> list = new ArrayList<>();

    if(reader == null) {
      reader = new JsonReader(new InputStreamReader(stream, "UTF-8"));
      reader.beginArray();
    }

    int count = 0;
    while(reader.hasNext() && count++ < chunkSize) {
      list.add(gson.fromJson(reader, new TypeToken<WriteOperation>(){}.getType()));
    }
    return list;
  }

  public static List<WriteOperation> parse(String writeOperationsJson, String creator) {
    List<WriteOperation> operations = parse(writeOperationsJson);

    // Set creator in bulk
    if (creator != null){
      operations.forEach(operation -> operation.setUser(creator));
    }

    return operations;
  }

  static class WriteOperationDeserializer implements JsonDeserializer<WriteOperation> {
    Map<String, Class<? extends WriteOperation>> actionRegistry = new HashMap<>();

    void registerAction(WriteOperation.WriteOperationAction action, Class<? extends WriteOperation> javaType) {
      actionRegistry.put(action.getValue(), javaType);
    }

    @Override
    public WriteOperation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
      JsonObject jsonObject = json.getAsJsonObject();
      String action = jsonObject.get("action").getAsString();

      if(!actionRegistry.containsKey(action)) {
        throw new WeaverError(WeaverError.WRITE_OPERATION_NOT_EXISTS, "This action was not found: " + action);
      }

      Class<? extends WriteOperation> dataType = actionRegistry.get(action);
      return context.deserialize(jsonObject, dataType);
    }
  }
}

