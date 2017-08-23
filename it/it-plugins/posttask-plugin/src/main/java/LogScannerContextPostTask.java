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
import java.util.Map;
import org.sonar.api.ce.posttask.PostProjectAnalysisTask;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

public class LogScannerContextPostTask implements PostProjectAnalysisTask {
  private static final Logger LOG = Loggers.get(LogScannerContextPostTask.class);

  @Override
  public void finished(ProjectAnalysis analysis) {
    for (Map.Entry<String, String> prop : analysis.getScannerContext().getProperties().entrySet()) {
      LOG.info("POSTASKPLUGIN: ScannerProperty {}={}",
        prop.getKey(),
        prop.getValue());
    }
  }
}
