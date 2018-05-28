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

/*
 * See https://www.postgresql.org/docs/current/static/libpq-pgservice.html.
 */
public class PGServiceFile {
  public static HashMap load(String service) throws Exception {
    String filename = findPath();
    PGServiceFile file = new PGServiceFile();
    BufferedReader in = new BufferedReader(new FileReader(filename));
    file.parse(in);
    return file.getService(service);
  }

  private static String findPath() {
    String filename = System.getProperty("postgres.pgservicefile");
    if (filename == null) {
      filename = System.getenv().get("PGSERVICEFILE");
    }
    if (filename == null) {
      filename = System.getProperty("user.home") + File.separator + ".pgservices.conf";
    }
    return filename;
  }

  private HashMap<String, HashMap<String, String>> sections;

  public PGServiceFile() {
    sections = new HashMap();
  }

  public void parse(BufferedReader in) throws Exception {
    String line = null;
    Integer lineno = 0;
    String section_name = null;
    HashMap<String, String> section = null;

    while ((line = in.readLine()) != null) {
      lineno++;
      line = line.replaceAll("\\s+", "");
      if (line.isEmpty() || line.startsWith("#")) {
        continue;
      } else if (line.startsWith("[")) {
        if (!line.endsWith("]")) {
          String msg = MessageFormat.format("Error in service file line {0}: missing ].", lineno);
          throw new Exception(msg);
        }
        section_name = line.substring(1, line.length() - 1);
        section = new HashMap();
        sections.put(section_name, section);
      } else if (section == null) {
        String msg = MessageFormat.format("Error in service file line {0}: not in section.", lineno);
        throw new Exception(msg);
      } else {
        String[] segment = line.split("=");
        if (segment.length != 2) {
          String msg = MessageFormat.format("Error in service file line {0}: bad syntax.", lineno);
          throw new Exception(msg);
        }
        section.put(segment[0], segment[1]);
      }
    }
  }

  public HashMap getService(String name) {
    return sections.get(name);
  }
}
