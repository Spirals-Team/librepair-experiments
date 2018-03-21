package ch.tbmelabs.tv.core.authorizationserver.service.clientdetails;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientCRUDRepository;

@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {
  private static final Logger LOGGER = LogManager.getLogger(ClientDetailsServiceImpl.class);

  @Autowired
  private ClientCRUDRepository clientRepository;

  @Override
  public ClientDetailsImpl loadClientByClientId(String clientId) {
    LOGGER.debug("Loading client details for client id \"" + clientId + "\"");

    return new ClientDetailsImpl(clientRepository.findByClientId(clientId));
  }
}