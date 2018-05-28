/*
 * Copyright (c) 2018, PostgreSQL Global Development Group
 * See the LICENSE file in the project root for more information.
 */

package org.postgresql.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/*
 * See https://www.postgresql.org/docs/current/static/libpq-pgservice.html.
 */
public class PGServiceFile {
  public static Map load(String service) throws Exception {
    String filename = findPath();
    PGServiceFile file = new PGServiceFile();
    BufferedReader in = new BufferedReader(new FileReader(filename));
    file.parse(in);
    return file.getService(service);
  }

  private static String findPath() {
    String filename = System.getProperty("org.postgresql.pgservicefile");
    if (filename == null) {
      filename = System.getenv().get("PGSERVICEFILE");
    }
    if (filename == null) {
      filename = System.getProperty("user.home") + File.separator + ".pgservices.conf";
    }
    return filename;
  }

  private Map<String, Map<String, String>> sections;

  public PGServiceFile() {
    sections = new HashMap();
  }

  public void parse(BufferedReader in) throws Exception {
    String line = null;
    int lineNum = 0;
    String sectionName = null;
    Map<String, String> section = null;

    while ((line = in.readLine()) != null) {
      lineNum++;
      line = line.replaceAll("^\\s+", "");
      if (line.isEmpty() || line.startsWith("#")) {
        continue;
      } else if (line.startsWith("[")) {
        if (!line.endsWith("]")) {
          String msg = MessageFormat.format("Error in service file line {0}: missing ].", lineNum);
          throw new Exception(msg);
        }
        sectionName = line.substring(1, line.length() - 1);
        section = new HashMap();
        sections.put(sectionName, section);
      } else if (section == null) {
        String msg = MessageFormat.format("Error in service file line {0}: not in section.", lineNum);
        throw new Exception(msg);
      } else {
        String[] segment = line.split("=");
        if (segment.length != 2) {
          String msg = MessageFormat.format("Error in service file line {0}: bad syntax.", lineNum);
          throw new Exception(msg);
        }
        section.put(segment[0], segment[1]);
      }
    }
  }

  public Map getService(String name) {
    return sections.get(name);
  }
}
