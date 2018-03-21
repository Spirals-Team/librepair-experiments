package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.MappedSuperclass;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ch.tbmelabs.tv.core.authorizationserver.domain.NicelyDocumentedJDBCResource;

public class NicelyDocumentedJDBCResourceTest {
  @Mock
  private NicelyDocumentedJDBCResource fixture;

  @Before
  public void beforeClassSetUp() {
    initMocks(this);

    doCallRealMethod().when(fixture).onCreate();
    doCallRealMethod().when(fixture).onUpdate();
  }

  @Test
  public void nicelyDocumentedJDBCResourceShouldBeAnnotated() {
    assertThat(NicelyDocumentedJDBCResource.class).hasAnnotation(MappedSuperclass.class);
  }

  @Test
  public void nicelyDocumentedJDBCResourceShouldExtendSerializable() {
    assertThat(Serializable.class).isAssignableFrom(NicelyDocumentedJDBCResource.class);
  }

  @Test
  public void onCreateShouldSetCreateAndLastUpdatedToCurrentTime() {
    fixture.onCreate();

    assertThat(fixture.created).isNotNull();
    assertThat(fixture.lastUpdated).isNotNull();
  }

  @Test
  public void onUpdateShouldSetLastUpdatedToCurrentTime() {
    Date created = new Date();
    fixture.created = created;

    fixture.onUpdate();

    assertThat(fixture.created).isNotNull().isEqualTo(created);
    assertThat(fixture.lastUpdated).isNotNull();
  }
}