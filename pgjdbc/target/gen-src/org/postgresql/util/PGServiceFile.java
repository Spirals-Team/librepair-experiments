/*
 * Copyright (c) 2018, PostgreSQL Global Development Group
 * See the LICENSE file in the project root for more information.
 */

package org.postgresql.util;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
 * See https://www.postgresql.org/docs/current/static/libpq-pgservice.html.
 */
public class PGServiceFile {
  public static Map load(String service) throws Exception {
    String filename = findPath();
    PGServiceFile file = new PGServiceFile();
    Scanner in = new Scanner(new FileInputStream(filename), StandardCharsets.UTF_8.name());
    try {
      file.parse(in);
    } finally {
      in.close();
    }
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

  public void parse(Scanner in) throws Exception {
    String line = null;
    int lineNum = 0;
    String sectionName = null;
    Map<String, String> section = null;

    while (in.hasNextLine()) {
      line = in.nextLine();
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
        String[] segment = line.split("=", 2);
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
