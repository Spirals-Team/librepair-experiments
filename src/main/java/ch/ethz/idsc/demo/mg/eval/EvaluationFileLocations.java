// code by mg
package ch.ethz.idsc.demo.mg.eval;

import java.io.File;

import ch.ethz.idsc.owl.bot.util.UserHome;

public enum EvaluationFileLocations {
  ;
  private static final File HANDLABEL_IMAGES = UserHome.Pictures("handlabelimages");
  private static final File EVALUATED_IMAGES = UserHome.Pictures("evaluatedimages");
  private static final File HANDLABEL_CSV = UserHome.Pictures("handlabels");
  private static final File ESTIMATED_CSV = UserHome.Pictures("estimatedlabels");
  private static final File EVALRESULTS = UserHome.Pictures("evalResults");
  private static final File TESTING = UserHome.Pictures("testImages");

  /** @param subfolder name
   * @return directory of the images to be labeled. NOTE: only the images should be in that directory */
  public static File images(String subfolder) {
    warningIfNotDirectory(HANDLABEL_IMAGES);
    return warningIfNotDirectory(new File(HANDLABEL_IMAGES, subfolder));
  }

  /** @param subfolder name
   * @return directory where evaluated images are saved */
  public static File evaluatedImages(String subfolder) {
    warningIfNotDirectory(EVALUATED_IMAGES);
    return warningIfNotDirectory(new File(EVALUATED_IMAGES, subfolder));
  }

  /** @param filename without .csv extension
   * @return file in directory containing the handlabels */
  public static File handlabels(String filename) {
    filename = filename + ".csv";
    return new File(warningIfNotDirectory(HANDLABEL_CSV), filename);
  }

  /** @param filename without .csv extension
   * @return file in directory containing the estimatedlabels */
  public static File estimatedlabels(String filename) {
    filename = filename + ".csv";
    return new File(warningIfNotDirectory(ESTIMATED_CSV), filename);
  }

  /** @param filename without .csv extension
   * @return file in directory containing the estimatedlabels */
  public static File evalResults(String filename) {
    filename = filename + ".csv";
    return new File(warningIfNotDirectory(EVALRESULTS), filename);
  }

  /** @return directory for the GUI screenshots */
  public static File testing() {
    return warningIfNotDirectory(TESTING);
  }

  private static File warningIfNotDirectory(File directory) {
    directory.mkdir();
    if (!directory.isDirectory())
      new RuntimeException("no directory: " + directory).printStackTrace();
    return directory;
  }
}
