package nl.mpi.tg.eg.experiment.client;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	/root/workspace/MPI-ExperimentGroup/ExperimentTemplate/384776966/gwt-cordova/src/main/resources/nl/mpi/tg/eg/experiment/client/Version.properties'.
 */
public interface Version extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "@application.buildVersion@".
   * 
   * @return translated "@application.buildVersion@"
   */
  @DefaultMessage("@application.buildVersion@")
  @Key("buildVersion")
  String buildVersion();

  /**
   * Translated "@application.buildDate@".
   * 
   * @return translated "@application.buildDate@"
   */
  @DefaultMessage("@application.buildDate@")
  @Key("compileDate")
  String compileDate();

  /**
   * Translated "@application.lastCommitDate@".
   * 
   * @return translated "@application.lastCommitDate@"
   */
  @DefaultMessage("@application.lastCommitDate@")
  @Key("lastCommitDate")
  String lastCommitDate();

  /**
   * Translated "@application.majorVersion@".
   * 
   * @return translated "@application.majorVersion@"
   */
  @DefaultMessage("@application.majorVersion@")
  @Key("majorVersion")
  String majorVersion();

  /**
   * Translated "@application.minorVersion@".
   * 
   * @return translated "@application.minorVersion@"
   */
  @DefaultMessage("@application.minorVersion@")
  @Key("minorVersion")
  String minorVersion();

  /**
   * Translated "@project.version@".
   * 
   * @return translated "@project.version@"
   */
  @DefaultMessage("@project.version@")
  @Key("projectVersion")
  String projectVersion();
}
