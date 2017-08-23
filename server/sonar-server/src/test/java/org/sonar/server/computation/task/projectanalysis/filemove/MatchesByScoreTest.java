/*
 * SonarQube
 * Copyright (C) 2009-2017 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.server.computation.task.projectanalysis.filemove;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Test;

import static com.google.common.collect.ImmutableSet.of;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.sonar.server.computation.task.projectanalysis.filemove.FileMoveDetectionStep.MIN_REQUIRED_SCORE;

public class MatchesByScoreTest {

  private static final List<Match> NO_MATCH = null;

  @Test
  public void creates_returns_always_the_same_instance_of_maxScore_is_less_than_min_required_score() {
    Set<String> doesNotMatterDbFileKeys = emptySet();
    Map<String, FileSimilarity.File> doesNotMatterReportFiles = Collections.emptyMap();
    int[][] doesNotMatterScores = new int[0][0];

    ScoreMatrix scoreMatrix1 = new ScoreMatrix(doesNotMatterDbFileKeys, doesNotMatterReportFiles, doesNotMatterScores, MIN_REQUIRED_SCORE - 1);
    MatchesByScore matchesByScore = MatchesByScore.create(scoreMatrix1);

    assertThat(matchesByScore.getSize()).isEqualTo(0);
    assertThat(matchesByScore).isEmpty();

    ScoreMatrix scoreMatrix2 = new ScoreMatrix(doesNotMatterDbFileKeys, doesNotMatterReportFiles, doesNotMatterScores, MIN_REQUIRED_SCORE - 5);
    assertThat(MatchesByScore.create(scoreMatrix2)).isSameAs(matchesByScore);
  }

  @Test
  public void creates_supports_score_with_same_value_as_min_required_score() {
    int maxScore = 92;
    int[][] scores = {
      {maxScore},
      {8},
      {85},
    };
    MatchesByScore matchesByScore = MatchesByScore.create(new ScoreMatrix(
      of("A", "B", "C"), ImmutableMap.of("1", fileOf("1")), scores, maxScore));

    assertThat(matchesByScore.getSize()).isEqualTo(2);
    assertThat(Lists.newArrayList(matchesByScore)).isEqualTo(Arrays.asList(
      ImmutableList.of(new Match("A", "1")), // 92
      NO_MATCH,
      NO_MATCH,
      NO_MATCH,
      NO_MATCH,
      NO_MATCH,
      NO_MATCH,
      ImmutableList.of(new Match("C", "1")) // 85
    ));
  }

  private static FileSimilarity.File fileOf(String key) {
    return new FileSimilarity.File("path of " + key, emptyList());
  }
}
