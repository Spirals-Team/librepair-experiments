package nl.mpi.tg.eg.experiment.client;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	/root/workspace/MPI-ExperimentGroup/ExperimentTemplate/384776966/gwt-cordova/src/main/resources/nl/mpi/tg/eg/experiment/client/ServiceLocations.properties'.
 */
public interface ServiceLocations extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "@experiment.destinationServerUrl@/@experiment.configuration.name@-admin/".
   * 
   * @return translated "@experiment.destinationServerUrl@/@experiment.configuration.name@-admin/"
   */
  @DefaultMessage("@experiment.destinationServerUrl@/@experiment.configuration.name@-admin/")
  @Key("dataSubmitUrl")
  String dataSubmitUrl();

  /**
   * Translated "@experiment.destinationServerUrl@/@experiment.configuration.name@/".
   * 
   * @return translated "@experiment.destinationServerUrl@/@experiment.configuration.name@/"
   */
  @DefaultMessage("@experiment.destinationServerUrl@/@experiment.configuration.name@/")
  @Key("groupServerUrl")
  String groupServerUrl();

  /**
   * Translated "@experiment.synQuizRegistrationUrl@".
   * 
   * @return translated "@experiment.synQuizRegistrationUrl@"
   */
  @DefaultMessage("@experiment.synQuizRegistrationUrl@")
  @Key("registrationUrl")
  String registrationUrl();

  /**
   * Translated "./static/".
   * 
   * @return translated "./static/"
   */
  @DefaultMessage("./static/")
  @Key("staticFilesUrl")
  String staticFilesUrl();
}
