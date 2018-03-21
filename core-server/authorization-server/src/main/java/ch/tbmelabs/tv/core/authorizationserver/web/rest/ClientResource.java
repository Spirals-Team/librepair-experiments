package ch.tbmelabs.tv.core.authorizationserver.web.rest;

import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientCRUDRepository;
import ch.tbmelabs.tv.shared.constants.security.SecurityRole;

@RestController
@RequestMapping({ "/rest/api/clients" })
@PreAuthorize("hasRole('" + SecurityRole.SERVER_ADMIN + "')")
public class ClientResource {
  @Autowired
  private ClientCRUDRepository clientRespository;

  @PostMapping({ "", "/" })
  public Client safeClient(@RequestBody(required = true) Client client) {
    return clientRespository.save(client);
  }

  @GetMapping({ "", "/" })
  @SuppressWarnings("unchecked")
  public List<Client> getAllClients() {
    return IteratorUtils.toList(clientRespository.findAll().iterator());
  }

  @PutMapping({ "", "/" })
  public Client updateClient(@RequestBody(required = true) Client client) {
    return clientRespository.save(client);
  }

  @DeleteMapping({ "", "/" })
  public void deleteClient(@RequestBody(required = true) Client client) {
    clientRespository.delete(client);
  }
}