/*
 * SonarQube Findbugs Plugin
 * Copyright (C) 2012 SonarSource
 * sonarqube@googlegroups.com
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
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.findbugs;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class FindbugsXmlReportParserTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private List<FindbugsXmlReportParser.XmlBugInstance> violations;

  @Before
  public void init() {
    File findbugsXmlReport = getFile("/org/sonar/plugins/findbugs/findbugsReport.xml");
    FindbugsXmlReportParser xmlParser = new FindbugsXmlReportParser(findbugsXmlReport);
    violations = xmlParser.getBugInstances();
  }

  @Test
  public void createFindbugsXmlReportParserWithUnexistedReportFile() {
    File xmlReport = new File("doesntExist.xml");
    thrown.expect(IllegalStateException.class);
    thrown.expectMessage("The findbugs XML report can't be found at '" + xmlReport.getAbsolutePath() + "'");
    new FindbugsXmlReportParser(xmlReport);
  }

  @Test
  public void testGetViolations() {
    assertThat(violations.size()).isEqualTo(2);

    FindbugsXmlReportParser.XmlBugInstance fbViolation = violations.get(0);
    assertThat(fbViolation.getType()).isEqualTo("AM_CREATES_EMPTY_ZIP_FILE_ENTRY");
    assertThat(fbViolation.getLongMessage()).isEqualTo("Empty zip file entry created in org.sonar.commons.ZipUtils._zip(String, File, ZipOutputStream)");

    FindbugsXmlReportParser.XmlSourceLineAnnotation sourceLine = fbViolation.getPrimarySourceLine();
    assertThat(sourceLine.getStart()).isEqualTo(107);
    assertThat(sourceLine.getEnd()).isEqualTo(107);
    assertThat(sourceLine.getClassName()).isEqualTo("org.sonar.commons.ZipUtils");
  }

  @Test
  public void testGetSonarJavaFileKey() {
    FindbugsXmlReportParser.XmlSourceLineAnnotation sourceLine = new FindbugsXmlReportParser.XmlSourceLineAnnotation();
    sourceLine.className = "org.sonar.batch.Sensor";
    assertThat(sourceLine.getSonarJavaFileKey()).isEqualTo("org.sonar.batch.Sensor");
    sourceLine.className = "Sensor";
    assertThat(sourceLine.getSonarJavaFileKey()).isEqualTo("Sensor");
    sourceLine.className = "org.sonar.batch.Sensor$1";
    assertThat(sourceLine.getSonarJavaFileKey()).isEqualTo("org.sonar.batch.Sensor");
  }

  private final File getFile(String filename) {
    try {
      return new File(getClass().getResource(filename).toURI());
    } catch (URISyntaxException e) {
      throw new IllegalStateException("Unable to open file " + filename, e);
    }
  }
}
