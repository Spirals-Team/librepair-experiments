package ch.tbmelabs.tv.core.authorizationserver.test.service.clientdetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.oauth2.provider.ClientDetails;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority.ClientAuthorityAssociation;
import ch.tbmelabs.tv.core.authorizationserver.service.clientdetails.ClientDetailsImpl;

public class ClientDetailsImplTest {
  private static final String CLIENT_REDIRECT_URIS = "uirone" + ClientDetailsImpl.CLIENT_REDIRECT_URI_SPLITTERATOR
      + "uritwo";

  @Mock
  private Client mockClient;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doCallRealMethod().when(mockClient).grantTypesToAssociations(Mockito.anyList());
    doCallRealMethod().when(mockClient).authoritiesToAssociations(Mockito.anyList());
    doCallRealMethod().when(mockClient).scopesToAssociations(Mockito.anyList());

    doReturn(UUID.randomUUID().toString()).when(mockClient).getClientId();
    doReturn(UUID.randomUUID().toString()).when(mockClient).getSecret();
    doReturn(3600).when(mockClient).getAccessTokenValiditySeconds();
    doReturn(7200).when(mockClient).getRefreshTokenValiditySeconds();
    doReturn(CLIENT_REDIRECT_URIS).when(mockClient).getRedirectUri();
  }

  @Test
  public void clientDetailsImplShouldImplementClientDetails() {
    assertThat(ClientDetails.class).isAssignableFrom(ClientDetailsImpl.class);
  }

  @Test
  public void clientDetailsImplShouldHaveAllArgsConstructor() {
    Client client = new Client();

    assertThat(new ClientDetailsImpl(client)).hasFieldOrPropertyWithValue("client", client);
  }

  @Test
  public void clientDetailsImplShouldReturnInformationEquelToUser() {
    ClientDetailsImpl fixture = new ClientDetailsImpl(mockClient);

    assertThat(fixture.getClientId()).isNotNull().isEqualTo(mockClient.getClientId());
    assertThat(fixture.getResourceIds()).isNotNull().isEqualTo(new HashSet<>());
    assertThat(fixture.isSecretRequired()).isNotNull().isEqualTo(mockClient.getIsSecretRequired());
    assertThat(fixture.isAutoApprove(new String())).isNotNull().isEqualTo(mockClient.getIsAutoApprove());
    assertThat(fixture.getClientSecret()).isNotNull().isEqualTo(mockClient.getSecret());
    assertThat(fixture.getAccessTokenValiditySeconds()).isNotNull().isEqualTo(fixture.getAccessTokenValiditySeconds());
    assertThat(fixture.getRefreshTokenValiditySeconds()).isNotNull()
        .isEqualTo(fixture.getRefreshTokenValiditySeconds());
    assertThat(fixture.isScoped()).isNotNull().isEqualTo(!mockClient.getScopes().isEmpty());
    assertThat(fixture.getRegisteredRedirectUri()).isNotNull().isEqualTo(
        new HashSet<>(Arrays.asList(CLIENT_REDIRECT_URIS.split(ClientDetailsImpl.CLIENT_REDIRECT_URI_SPLITTERATOR))));
    assertThat(fixture.getAuthorities()).isNotNull().isEqualTo(mockClient.getGrantedAuthorities().stream()
        .map(ClientAuthorityAssociation::getClientAuthority).collect(Collectors.toList()));
    assertThat(fixture.getAdditionalInformation()).isNotNull().isEqualTo(new HashMap<>());
  }
}