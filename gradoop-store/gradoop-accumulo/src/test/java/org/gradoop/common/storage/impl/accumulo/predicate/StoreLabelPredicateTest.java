package org.gradoop.common.storage.impl.accumulo.predicate;

import org.gradoop.common.model.impl.pojo.Edge;
import org.gradoop.common.model.impl.pojo.Vertex;
import org.gradoop.AccumuloStoreTestBase;
import org.gradoop.common.storage.impl.accumulo.predicate.units.LabelEquals;
import org.gradoop.common.storage.impl.accumulo.predicate.units.LabelReg;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.gradoop.common.GradoopTestUtils.validateEPGMElementCollections;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StoreLabelPredicateTest extends AccumuloStoreTestBase {

  private static final String TEST01 = "label_predicate_01";
  private static final String TEST02 = "label_predicate_02";
  private static final String TEST03 = "label_predicate_03";
  private static final String TEST04 = "label_predicate_04";

  /**
   * find all edge that's with label 'hasInterest' or 'hasMember'
   *
   * @throws Throwable if error
   */
  @Test
  public void test01_vertexLabelEquals() throws Throwable {
    doTest(TEST01, (loader, store) -> {
      List<Vertex> inputVertex = loader.getVertices().stream()
        .filter(it ->
          Objects.equals(it.getLabel(), "Person") ||
            Objects.equals(it.getLabel(), "Tag"))
        .collect(Collectors.toList());

      //vertex label query
      List<Vertex> queryResult = store
        .getVertexSpace(new LabelEquals<Vertex>("Person", "Tag").all())
        .readRemainsAndClose();

      validateEPGMElementCollections(inputVertex, queryResult);
    });
  }

  /**
   * find all edge that's with label 'hasInterest' or 'hasMember'
   *
   * @throws Throwable if error
   */
  @Test
  public void test02_edgeLabelEquals() throws Throwable {
    doTest(TEST02, (loader, store) -> {
      List<Edge> inputEdges = loader.getEdges().stream()
        .filter(it ->
          Objects.equals(it.getLabel(), "hasInterest") ||
            Objects.equals(it.getLabel(), "hasMember"))
        .collect(Collectors.toList());

      //edge label query
      List<Edge> queryResult = store
        .getEdgeSpace(new LabelEquals<Edge>("hasInterest", "hasMember").all())
        .readRemainsAndClose();
      validateEPGMElementCollections(inputEdges, queryResult);
    });
  }

  /**
   * find all edge that's with label 'hasInterest' or 'hasMember'
   *
   * @throws Throwable if error
   */
  @Test
  public void test03_vertexLabelRegex() throws Throwable {
    doTest(TEST03, (loader, store) -> {
      List<Vertex> inputVertex = loader.getVertices().stream()
        .filter(it -> Pattern.compile("[Pers|Ta].*+").matcher(it.getLabel()).matches())
        .collect(Collectors.toList());

      //vertex label regex query
      List<Vertex> queryResult = store
        .getVertexSpace(new LabelReg<Vertex>(Pattern.compile("[Pers|Ta].*+")).all())
        .readRemains();

      validateEPGMElementCollections(inputVertex, queryResult);
    });
  }

  /**
   * find all edge that's with label 'hasInterest' or 'hasMember'
   *
   * @throws Throwable if error
   */
  @Test
  public void test04_edgeLabelRegex() throws Throwable {
    doTest(TEST04, (loader, store) -> {
      //vertex label query
      List<Edge> inputVertex = loader.getEdges().stream()
        .filter(it -> Pattern.compile("has.*+").matcher(it.getLabel()).matches())
        .collect(Collectors.toList());

      //edge label regex query
      List<Edge> queryResult = store
        .getEdgeSpace(new LabelReg<Edge>(Pattern.compile("has.*+")).all())
        .readRemainsAndClose();

      validateEPGMElementCollections(inputVertex, queryResult);
    });
  }

}
