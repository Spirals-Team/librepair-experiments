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
package org.sonar.server.computation.task.projectanalysis.step;

import com.google.common.annotations.VisibleForTesting;
import java.util.List;
import org.apache.commons.lang.math.NumberUtils;
import org.sonar.db.DbClient;
import org.sonar.db.DbSession;
import org.sonar.db.MyBatis;
import org.sonar.db.measure.custom.CustomMeasureDto;
import org.sonar.server.computation.task.projectanalysis.component.Component;
import org.sonar.server.computation.task.projectanalysis.component.ComponentVisitor;
import org.sonar.server.computation.task.projectanalysis.component.CrawlerDepthLimit;
import org.sonar.server.computation.task.projectanalysis.component.DepthTraversalTypeAwareCrawler;
import org.sonar.server.computation.task.projectanalysis.component.TreeRootHolder;
import org.sonar.server.computation.task.projectanalysis.component.TypeAwareVisitorAdapter;
import org.sonar.server.computation.task.projectanalysis.measure.Measure;
import org.sonar.server.computation.task.projectanalysis.measure.MeasureRepository;
import org.sonar.server.computation.task.projectanalysis.metric.Metric;
import org.sonar.server.computation.task.projectanalysis.metric.MetricRepository;
import org.sonar.server.computation.task.step.ComputationStep;

public class CustomMeasuresCopyStep implements ComputationStep {

  private final TreeRootHolder treeRootHolder;
  private final DbClient dbClient;
  private final MetricRepository metricRepository;
  private final MeasureRepository measureRepository;

  public CustomMeasuresCopyStep(TreeRootHolder treeRootHolder, DbClient dbClient,
    MetricRepository metricRepository, MeasureRepository measureRepository) {
    this.treeRootHolder = treeRootHolder;
    this.dbClient = dbClient;
    this.metricRepository = metricRepository;
    this.measureRepository = measureRepository;
  }

  @Override
  public void execute() {
    new DepthTraversalTypeAwareCrawler(
        new TypeAwareVisitorAdapter(CrawlerDepthLimit.LEAVES, ComponentVisitor.Order.PRE_ORDER) {
      @Override
      public void visitAny(Component component) {
        copy(component);
      }
    }).visit(treeRootHolder.getRoot());
  }

  private void copy(Component component) {
    for (CustomMeasureDto dto : loadCustomMeasures(component)) {
      Metric metric = metricRepository.getById(dto.getMetricId());
      // else metric is not found and an exception is raised
      Measure measure = dtoToMeasure(dto, metric);
      measureRepository.add(component, metric, measure);
    }
  }

  private List<CustomMeasureDto> loadCustomMeasures(Component component) {
    DbSession session = dbClient.openSession(false);
    try {
      return dbClient.customMeasureDao().selectByComponentUuid(session, component.getUuid());
    } finally {
      MyBatis.closeQuietly(session);
    }
  }

  @VisibleForTesting
  static Measure dtoToMeasure(CustomMeasureDto dto, Metric metric) {
    switch (metric.getType()) {
      case INT:
      case RATING:
        return Measure.newMeasureBuilder().create((int) dto.getValue());
      case MILLISEC:
      case WORK_DUR:
        return Measure.newMeasureBuilder().create((long) dto.getValue());
      case FLOAT:
      case PERCENT:
        return Measure.newMeasureBuilder().create(dto.getValue(), metric.getDecimalScale());
      case BOOL:
        return Measure.newMeasureBuilder().create(NumberUtils.compare(dto.getValue(), 1.0) == 0);
      case LEVEL:
        return Measure.newMeasureBuilder().create(Measure.Level.valueOf(dto.getTextValue()));
      case STRING:
      case DISTRIB:
      case DATA:
        return Measure.newMeasureBuilder().create(dto.getTextValue());
      default:
        throw new IllegalArgumentException(String.format("Custom measures do not support the metric type [%s]", metric.getType()));
    }
  }

  @Override
  public String getDescription() {
    return "Copy custom measures";
  }
}
