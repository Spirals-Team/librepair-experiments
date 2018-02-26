package com.weaverplatform.protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.weaverplatform.protocol.model.SuperOperation;
import com.weaverplatform.protocol.model.WriteOperation;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author Mohamad Alamili
 */
public class SortedWriteOperationParser {

  private static final Gson gson;

  static {
    gson = new GsonBuilder().create();
  }



  private JsonReader reader = null;

  public List<WriteOperation> parseNext(InputStream stream, int chunkSize) throws IOException {
    List<WriteOperation> list = new ArrayList<>();

    if(reader == null) {
      reader = new JsonReader(new InputStreamReader(stream, "UTF-8"));
      reader.beginArray();
    }

    while(reader.hasNext() && list.size() < chunkSize) {
      list.add(gson.fromJson(reader, new TypeToken<WriteOperation>(){}.getType()));
    }
    return list;
  }

  public static Character sortChar(String id) {
    if(id == null || id.trim().isEmpty()) {
      return Character.MIN_VALUE;
    }
    if(id.indexOf(":") > -1 && !id.endsWith(":")) {
      return id.charAt(id.indexOf(":")+1);
    } else {
      return id.charAt(0);
    }
  }

  public static Set<Character> startChars(InputStream stream) throws IOException {
    TreeSet<Character> set = new TreeSet<>();
    JsonReader reader = new JsonReader(new InputStreamReader(stream, "UTF-8"));
    reader.beginArray();

    while(reader.hasNext()) {
      String id = sortKey(gson.fromJson(reader, SuperOperation.class));
      set.add(sortChar(id));
    }
    return set;
  }

  public static String sortKey(SuperOperation operation) {
    if(operation.getAction().equals("create-attribute") || operation.getAction().equals("create-relation")) {
      return operation.getSourceId()+operation.getId();
    }
    return operation.getId();
  }



  public TreeSet<SuperOperation> parseNextSorted(InputStream stream, int chunkSize, Character filter) {
    TreeSet<SuperOperation> set = new TreeSet<>(new Comparator<SuperOperation>() {
      @Override
      public int compare(SuperOperation o1, SuperOperation o2) {
        return sortKey(o1).compareTo(sortKey(o2));
      }
    });

    try {
      if (reader == null) {
        reader = new JsonReader(new InputStreamReader(stream, "UTF-8"));
        reader.beginArray();
      }

      while (reader.hasNext() && set.size() < chunkSize) {
        SuperOperation operation = gson.fromJson(reader, SuperOperation.class);
        if (filter == null || filter.equals(sortChar(sortKey(operation)))) {
          set.add(operation);
        }
      }
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return set;
  }
}

