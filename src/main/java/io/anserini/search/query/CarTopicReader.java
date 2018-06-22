/**
 * Anserini: An information retrieval toolkit built on Lucene
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.anserini.search.query;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class CarTopicReader extends TopicReader {

  public CarTopicReader(Path topicFile) {
    super(topicFile);
  }

  /**
   * Read topics in the topic file of TREC CAR Track 2018
   * @return SortedMap where keys are query/topic IDs and values are title portions of the topics
   * @throws IOException
   */
  @Override
  public SortedMap<String, Map<String, String>> read(BufferedReader bRdr) throws IOException {
    SortedMap<String, Map<String, String>> map = new TreeMap<>();

    String line;
    while ((line = bRdr.readLine()) != null) {
      Map<String,String> fields = new HashMap<>();
      line = line.trim();
      if (line.startsWith("enwiki:")) {
        String id = line;
        String title = java.net.URLDecoder.decode(line.substring(7).replace("%20", " ")
            .replace("%2", " ").replace("/", " "), "utf-8");
        fields.put("title", title);
        map.put(id, fields);
      }
      else if (line.length() != 0) {
        String title = line;
        String id = "enwiki:" + java.net.URLEncoder.encode(line, "utf-8")
            .replace("+", "%20").replace("%28", "(")
            .replace("%29", ")").replace("%27", "'")
            .replace("%2C", ",");
        fields.put("title", title);
        map.put(id, fields);
      }
    }
    return map;
  }
}
