package org.apache.samoa.moa.classifiers.rules.core.splitcriteria;

/*
 * #%L
 * SAMOA
 * %%
 * Copyright (C) 2014 - 2015 Apache Software Foundation
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

/*
 *    SDRSplitCriterionAMRules.java
 *    Copyright (C) 2014 - 2015 Apache Software Foundation
 *    @author A. Bifet, J. Duarte, J. Gama
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *    
 *    
 */

import org.apache.samoa.moa.classifiers.core.splitcriteria.SDRSplitCriterion;
import org.apache.samoa.moa.classifiers.core.splitcriteria.SplitCriterion;

public class SDRSplitCriterionAMRules extends SDRSplitCriterion implements SplitCriterion {

  private static final long serialVersionUID = 1L;

  @Override
  public double getMeritOfSplit(double[] preSplitDist, double[][] postSplitDists) {
    double SDR = 0.0;
    double N = preSplitDist[0];
    int count = 0;

    for (int i = 0; i < postSplitDists.length; i++)
    {
      double Ni = postSplitDists[i][0];
      if (Ni >= 0.05 * preSplitDist[0]) {
        count = count + 1;
      }
    }
    if (count == postSplitDists.length) {
      SDR = computeSD(preSplitDist);

      for (int i = 0; i < postSplitDists.length; i++)
      {
        double Ni = postSplitDists[i][0];
        SDR -= (Ni / N) * computeSD(postSplitDists[i]);

      }
    }
    return SDR;
  }

  @Override
  public double getRangeOfMerit(double[] preSplitDist) {
    return 1;
  }

  public static double[] computeBranchSplitMerits(double[][] postSplitDists) {
    double[] SDR = new double[postSplitDists.length];
    double N = 0;

    for (int i = 0; i < postSplitDists.length; i++)
    {
      double Ni = postSplitDists[i][0];
      N += Ni;
    }
    for (int i = 0; i < postSplitDists.length; i++)
    {
      double Ni = postSplitDists[i][0];
      SDR[i] = (Ni / N) * computeSD(postSplitDists[i]);
    }
    return SDR;

  }

}
