package nl.mpi.tg.eg.experiment.client;

public class ServiceLocations_ implements nl.mpi.tg.eg.experiment.client.ServiceLocations {
  
  public java.lang.String dataSubmitUrl() {
    return "http://localhost:8080//ld_screensize-admin/";
  }
  
  public java.lang.String groupServerUrl() {
    return "http://localhost:8080//ld_screensize/";
  }
  
  public java.lang.String registrationUrl() {
    return "";
  }
  
  public java.lang.String staticFilesUrl() {
    return "./static/";
  }
}
