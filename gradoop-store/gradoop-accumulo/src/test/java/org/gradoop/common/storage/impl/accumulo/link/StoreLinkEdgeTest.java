/**
 * Copyright © 2014 - 2018 Leipzig University (Database Research Group)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradoop.common.storage.impl.accumulo.link;

import org.gradoop.common.GradoopTestUtils;
import org.gradoop.common.model.api.entities.EPGMElement;
import org.gradoop.common.model.impl.id.GradoopIdSet;
import org.gradoop.common.model.impl.pojo.Edge;
import org.gradoop.common.model.impl.pojo.Element;
import org.gradoop.common.model.impl.pojo.Vertex;
import org.gradoop.AccumuloStoreTestBase;
import org.gradoop.common.storage.impl.accumulo.predicate.AccumuloRelativePredicate;
import org.gradoop.common.storage.impl.accumulo.predicate.calculate.AND;
import org.gradoop.common.storage.impl.accumulo.predicate.units.LabelEquals;
import org.gradoop.common.storage.impl.accumulo.predicate.units.PropLargerThan;
import org.gradoop.common.storage.iterator.ClosableIterator;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * accumulo graph store link edge test
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StoreLinkEdgeTest extends AccumuloStoreTestBase {

  private static final String TEST01 = "link_edge_01";
  private static final String TEST02 = "link_edge_02";
  private static final String TEST03 = "link_edge_03";
  private static final String TEST04 = "link_edge_04";

  /**
   * pick single vertex, get all its link edge and link table, assert them equals
   *
   * @throws Throwable if error
   */
  @Test
  public void test01_singleVertexLinkQuery() throws Throwable {
    doTest(TEST01, (loader, store) -> {
      List<Vertex> vertices = sample(new ArrayList<>(loader.getVertices()), 10);
      for (Vertex sample : vertices) {
        // edge out only
        List<Edge> linkEdges = loader.getEdges().stream()
          .filter(it -> Objects.equals(it.getSourceId().toString(), sample.getId().toString()))
          .collect(Collectors.toList());
        AccumuloRelativePredicate<Edge> predicate = new AccumuloRelativePredicate<>(
          GradoopIdSet.fromExisting(sample.getId()),
          false,
          true,
          null);
        try (ClosableIterator<Edge> iterator = store.getRelativeEdgeSpace(predicate)
        ) {
          List<Edge> rows = iterator.readRemains();
          GradoopTestUtils.validateEPGMElementCollections(linkEdges, rows);
        }

        // edge in only
        linkEdges = loader.getEdges().stream()
          .filter(it -> Objects.equals(it.getTargetId().toString(), sample.getId().toString()))
          .collect(Collectors.toList());
        predicate = new AccumuloRelativePredicate<>(
          GradoopIdSet.fromExisting(sample.getId()),
          true,
          false,
          null);
        try (ClosableIterator<Edge> iterator = store.getRelativeEdgeSpace(predicate)) {
          List<Edge> rows = iterator.readRemains();
          GradoopTestUtils.validateEPGMElementCollections(linkEdges, rows);
        }

        // edge in & out
        linkEdges = loader.getEdges().stream()
          .filter(it -> Objects.equals(it.getSourceId().toString(), sample.getId().toString()) ||
            Objects.equals(it.getTargetId().toString(), sample.getId().toString()))
          .collect(Collectors.toList());
        predicate = new AccumuloRelativePredicate<>(
          GradoopIdSet.fromExisting(sample.getId()),
          true,
          true,
          null);
        try (ClosableIterator<Edge> iterator = store.getRelativeEdgeSpace(predicate)) {
          List<Edge> rows = iterator.readRemains();
          GradoopTestUtils.validateEPGMElementCollections(linkEdges, rows);
        }
      }
    });
  }

  /**
   * pick 3 vertices, get all their link edges and link table, assert them equals
   *
   * @throws Throwable if error
   */
  @Test
  public void test02_multiVertexLinkQuery() throws Throwable {
    doTest(TEST02, (loader, store) -> {
      List<Vertex> vertices = sample(new ArrayList<>(loader.getVertices()), 10);
      for (int i = 0; i < 10; i++) {
        List<Vertex> sample = sample(vertices, 3);
        Set<String> sampleIds = sample.stream()
          .map(it -> it.getId().toString())
          .collect(Collectors.toSet());

        List<Edge> linkEdges = loader.getEdges().stream()
          .filter(it -> sampleIds.contains(it.getTargetId().toString()))
          .collect(Collectors.toList());

        AccumuloRelativePredicate<Edge> predicate = new AccumuloRelativePredicate<>(
          GradoopIdSet.fromExisting(sample.stream()
            .map(Element::getId)
            .collect(Collectors.toList())),
          true,
          false,
          null);

        try (ClosableIterator<Edge> iterator = store.getRelativeEdgeSpace(predicate)) {
          List<Edge> rows = iterator.readRemains();
          GradoopTestUtils.validateEPGMElementCollections(linkEdges, rows);
        }
      }
    });
  }

  /**
   * find all link edge by single vertex and property filter
   *
   * @throws Throwable if error
   */
  @Test
  public void test03_singleVertexLinkWithFilterQuery() throws Throwable {
    doTest(TEST03, (loader, store) -> {
      List<Vertex> vertices = loader.getVertices().stream()
        .filter(it -> it.getLabel().equals("Person"))
        .collect(Collectors.toList());
      for (Vertex sample : vertices) {
        //do test from sample vertex
        List<Edge> edges = loader.getEdges().stream()
          .filter(it -> Objects.equals(it.getSourceId().toString(), sample.getId().toString()))
          .filter(it -> Objects.equals(it.getLabel(), "knows") &&
            it.getProperties() != null &&
            it.getProperties().get("since").getInt() >= 2014)
          .collect(Collectors.toList());

        List<Edge> query = store.getRelativeEdgeSpace(new AccumuloRelativePredicate<>(
          GradoopIdSet.fromExisting(sample.getId()),
          false,
          true,
          AND.create(
            new LabelEquals<>("knows"),
            new PropLargerThan<>("since", 2014, true)
          ))).readRemainsAndClose();
        GradoopTestUtils.validateEPGMElementCollections(query, edges);
      }
    });
  }

  /**
   * find all link edge by a set of vertices and property filter
   *
   * @throws Throwable if error
   */
  @Test
  public void test04_multiVertexLinkWithFilterQuery() throws Throwable {
    doTest(TEST04, (loader, store) -> {
      List<Vertex> vertices = loader.getVertices().stream()
        .filter(it -> it.getLabel().equals("Person"))
        .collect(Collectors.toList());
      for (int i = 0; i < 10; i++) {
        List<Vertex> sample = sample(vertices, 3);
        Set<String> sampleIds = sample.stream()
          .map(it -> it.getId().toString())
          .collect(Collectors.toSet());

        List<Edge> edges = loader.getEdges().stream()
          .filter(it -> sampleIds.contains(it.getSourceId().toString()) ||
            sampleIds.contains(it.getTargetId().toString()))
          .filter(it -> Objects.equals(it.getLabel(), "knows") &&
            it.getProperties() != null &&
            it.getProperties().get("since").getInt() >= 2014)
          .collect(Collectors.toList());

        AccumuloRelativePredicate<Edge> predicate = new AccumuloRelativePredicate<>(
          GradoopIdSet.fromExisting(sample.stream()
            .map(EPGMElement::getId)
            .collect(Collectors.toList())),
          true,
          true,
          AND.create(
            new LabelEquals<>("knows"),
            new PropLargerThan<>("since", 2014, true)
          ));
        List<Edge> query = store.getRelativeEdgeSpace(predicate).readRemainsAndClose();
        GradoopTestUtils.validateEPGMElementCollections(edges, query);
      }
    });
  }

}
