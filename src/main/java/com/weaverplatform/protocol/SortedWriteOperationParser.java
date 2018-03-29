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

import static com.weaverplatform.util.SortUtil.compareChar;
import static com.weaverplatform.util.SortUtil.compareReverseIndex;

/**
 * @author Mohamad Alamili
 */
public class SortedWriteOperationParser {

  private static final Gson gson = new GsonBuilder().create();

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
//    if(id.endsWith(":")) {
//      if(id.length() == 1) {
//        return ':';
//      } else {
//        return id.charAt(id.length()-2);
//      }
//    }
    return id.charAt(id.length()-1); // use last character
  }

  public static Set<Character> startChars(InputStream stream) throws IOException {
    TreeSet<Character> set = new TreeSet<>(new Comparator<Character>() {
      @Override
      public int compare(Character o1, Character o2) {
        return compareChar(o1, o2);
      }
    });
    JsonReader reader = new JsonReader(new InputStreamReader(stream, "UTF-8"));
    reader.beginArray();

    while(reader.hasNext()) {
      String id = sortKey(gson.fromJson(reader, SuperOperation.class));
      Character sc = sortChar(id);
      set.add(sc);
    }
    return set;
  }

  public static String keyFilter(String key) {
    if(key.equals("rdf:type")) {
      return "a";
    }
    return key;
  }

  public static String sortKey(SuperOperation operation) {
    if(operation.getAction().equals("create-attribute")) {
      return operation.getId() + keyFilter(operation.getKey()) + operation.getSourceId();
    }
    if(operation.getAction().equals("create-relation")) {
      return operation.getTargetId() + keyFilter(operation.getKey()) + operation.getSourceId();
    }
    return operation.getId();
  }



  public TreeSet<SuperOperation> parseNextSorted(InputStream stream, int chunkSize, Character filter) {
    TreeSet<SuperOperation> set = new TreeSet<>(new Comparator<SuperOperation>() {
      @Override
      public int compare(SuperOperation o1, SuperOperation o2) {
        String key1 = sortKey(o1);
        String key2 = sortKey(o2);
        return compareReverseIndex(key1, key2, 0);
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

