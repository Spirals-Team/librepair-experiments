package edu.stanford.futuredata.macrobase.analysis.summary;

public interface Explanation {
    String prettyPrint();
    double numOutliers();
    double numTotal();
}
