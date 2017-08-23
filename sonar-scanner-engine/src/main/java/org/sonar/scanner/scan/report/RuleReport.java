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
package org.sonar.scanner.scan.report;

import org.sonar.api.batch.rule.Rule;

import org.apache.commons.lang.builder.ToStringBuilder;

public final class RuleReport {
  private final ReportRuleKey reportRuleKey;
  private final IssueVariation total = new IssueVariation();

  public RuleReport(ReportRuleKey reportRuleKey) {
    this.reportRuleKey = reportRuleKey;
  }

  public IssueVariation getTotal() {
    return total;
  }

  public ReportRuleKey getReportRuleKey() {
    return reportRuleKey;
  }

  public String getSeverity() {
    return reportRuleKey.getSeverity().toString();
  }

  public Rule getRule() {
    return reportRuleKey.getRule();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).
      append("reportRuleKey", reportRuleKey).
      append("total", total).
      toString();
  }
}
