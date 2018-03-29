package com.weaverplatform;

import org.apache.commons.cli.*;

/**
 * @author bastbijl, Sysunite 2017
 */
public class CliOptions {

  private CommandLineParser parser = new DefaultParser();
  private CommandLine cmd;

  public CliOptions(String[] args) {
    try {
      cmd = parser.parse(getOptions(), args);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static Options getOptions() {
    Options options = new Options();
    options.addOption("z", "zip", false, "zip the payload before downloading or uploading");
    return options;
  }

  public static void printUsage() {

    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp(
    "\n<url> <username> <password> [<project> history|snaphot|restore|project-create|project-ready|project-wipe <filename>]" +
    "\ne.g. $ weaver-cli http://localhost:9487 admin admin projectA restore < operations.json" +
    "\n" +
    "\nargs:"
    , getOptions());

    System.exit(0);
  }

  public boolean zip() {
    return cmd.hasOption("z");
  }
}
