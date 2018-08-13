// code by mg
package ch.ethz.idsc.demo.mg.eval;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ch.ethz.idsc.demo.mg.pipeline.ImageBlob;
import ch.ethz.idsc.demo.mg.pipeline.PipelineConfig;
import ch.ethz.idsc.retina.dev.davis._240c.DavisDvsEvent;

// this class saves the estimatedFeatures at timestamps when hand-labeled ground truth is available and saves
// finally to a CSV file for further analysis with TrackingEvaluator
// TODO if no features are hand-labeled at a certain instant, we do not collect the estimatedFeatures at that instant.
public class TrackingCollector {
  private final String imagePrefix;
  private final String estimatedLabelFileName;
  private final File estimatedLabelFile;
  private final List<List<ImageBlob>> estimatedFeatures;
  private final int numberOfLabelInstants;
  private final int[] timeStamps; // timestamps for which hand-labeled features are available
  private int currentLabelInstant = 0;

  public TrackingCollector(PipelineConfig pipelineConfig) {
    imagePrefix = pipelineConfig.davisConfig.logFileName();
    numberOfLabelInstants = EvaluationFileLocations.images(imagePrefix).list().length;
    timeStamps = EvalUtil.getTimestampsFromImages(numberOfLabelInstants, imagePrefix);
    estimatedLabelFileName = pipelineConfig.estimatedLabelFileName.toString();
    estimatedLabelFile = EvaluationFileLocations.estimatedlabels(estimatedLabelFileName);
    // set up empty list of estimated features
    estimatedFeatures = new ArrayList<>(numberOfLabelInstants);
    for (int i = 0; i < timeStamps.length; i++)
      estimatedFeatures.add(new ArrayList<>());
  }

  public boolean isGroundTruthAvailable(DavisDvsEvent davisDvsEvent) {
    return currentLabelInstant <= numberOfLabelInstants - 1 //
        && davisDvsEvent.time == timeStamps[currentLabelInstant];
  }

  public void setEstimatedFeatures(List<ImageBlob> estimatedFeaturesInstant) {
    System.out.println("Estimated features are collected. Instant nr " + (currentLabelInstant + 1));
    estimatedFeatures.set(currentLabelInstant, estimatedFeaturesInstant);
    // counter
    currentLabelInstant++;
    if (currentLabelInstant == numberOfLabelInstants) {
      EvalUtil.saveToCSV(estimatedLabelFile, estimatedFeatures, timeStamps);
      System.out.println("Estimated labels saved to " + estimatedLabelFileName);
    }
  }
}
