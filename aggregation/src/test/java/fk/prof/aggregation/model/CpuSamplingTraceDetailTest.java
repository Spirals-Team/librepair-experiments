package fk.prof.aggregation.model;

import org.junit.Assert;
import org.junit.Test;

public class CpuSamplingTraceDetailTest {
  @Test
  public void testUnclassifiableRootIsChildOfGlobalRoot() {
    CpuSamplingTraceDetail traceDetail = new CpuSamplingTraceDetail();
    CpuSamplingFrameNode existingGlobalRoot = traceDetail.getGlobalRoot();
    CpuSamplingFrameNode existingUnclassifiableRoot = traceDetail.getUnclassifiableRoot();
    CpuSamplingFrameNode addedUnclassifiableRoot = existingGlobalRoot.getOrAddChild(MethodIdLookup.UNCLASSIFIABLE_ROOT_METHOD_ID, MethodIdLookup.DEFAULT_LINE_NUMBER);
    // Verify that the node returned when adding unclassifiable root as child is the same node instance as returned by tracedetail object
    Assert.assertTrue(existingUnclassifiableRoot == addedUnclassifiableRoot);
  }

  //TODO: Tests for increment of samples should be added once serialization is implemented

}
