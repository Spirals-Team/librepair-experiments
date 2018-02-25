package org.tests.model.history;

import io.ebean.BaseTestCase;
import io.ebean.Ebean;
import io.ebean.annotation.IgnorePlatform;
import io.ebean.annotation.Platform;
import org.junit.Test;

import java.sql.Timestamp;

import static org.assertj.core.api.StrictAssertions.assertThat;

public class TestHistoryExclude extends BaseTestCase {

  private HeLink link;

  private void prepare() {
    if (link == null) {
      HeDoc docA = new HeDoc("doca");
      HeDoc docB = new HeDoc("docb");
      docA.save();
      docB.save();

      link = new HeLink("some", "link");
      link.getDocs().add(docA);
      link.getDocs().add(docB);
      link.save();
    }
  }

  @Test
  public void testLazyLoad() {

    prepare();

    HeLink linkFound = Ebean.find(HeLink.class, link.getId());
    linkFound.getDocs().size();
  }

  @IgnorePlatform(Platform.ORACLE)
  @Test
  public void testAsOfThenLazy() {

    prepare();

    HeLink linkFound = Ebean.find(HeLink.class)
      .asOf(new Timestamp(System.currentTimeMillis()))
      .setId(link.getId())
      .findOne();

    assertThat(linkFound.getDocs().size()).isEqualTo(2);
  }
}
