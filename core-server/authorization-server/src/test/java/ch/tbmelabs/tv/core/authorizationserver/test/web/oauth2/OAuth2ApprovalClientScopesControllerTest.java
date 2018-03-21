package ch.tbmelabs.tv.core.authorizationserver.test.web.oauth2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.Scope;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientscope.ClientScopeAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientScopeAssociationCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.web.oauth2.OAuth2ApprovalClientScopesController;

public class OAuth2ApprovalClientScopesControllerTest {
  private static final String CLIENT_SCOPE_NAME = "TEST";

  @Mock
  private ClientScopeAssociation mockAssociation;

  @Mock
  private ClientCRUDRepository clientRepositoryFixture;

  @Mock
  private ClientScopeAssociationCRUDRepository clientScopeAssociationRepository;

  @Spy
  @InjectMocks
  private OAuth2ApprovalClientScopesController fixture;

  @Before
  public void beforeClassSetUp() {
    initMocks(this);

    doReturn(new Scope(CLIENT_SCOPE_NAME)).when(mockAssociation).getClientScope();

    doReturn(new Client()).when(clientRepositoryFixture).findByClientId(Mockito.anyString());
    doReturn(Arrays.asList(mockAssociation)).when(clientScopeAssociationRepository)
        .findByClient(Mockito.any(Client.class));
  }

  @Test
  public void oAuth2ApprovalClientScopesControllerShouldBeAnnotated() {
    assertThat(OAuth2ApprovalClientScopesController.class).hasAnnotation(RestController.class);
  }

  @Test
  public void oAuth2ApprovalClientScopesControllerShouldHavePublicConstructor() {
    assertThat(new OAuth2ApprovalClientScopesController()).isNotNull();
  }

  @Test
  public void getAccessConfirmationShouldReturnScopeNamesOnly() throws Exception {
    Method getAccessConfirmation = OAuth2ApprovalClientScopesController.class.getDeclaredMethod("getAccessConfirmation",
        new Class<?>[] { String.class });
    assertThat(getAccessConfirmation.getDeclaredAnnotation(RequestMapping.class).value()).isNotEmpty()
        .containsExactly("/oauth/confirm_access_scopes");

    assertThat(fixture.getAccessConfirmation(RandomStringUtils.random(11))).containsExactly(CLIENT_SCOPE_NAME);
  }
}