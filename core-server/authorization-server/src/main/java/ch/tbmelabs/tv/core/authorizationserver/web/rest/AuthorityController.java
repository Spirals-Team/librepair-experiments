package ch.tbmelabs.tv.core.authorizationserver.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.tbmelabs.tv.core.authorizationserver.domain.Authority;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.AuthorityCRUDRepository;
import ch.tbmelabs.tv.shared.constants.security.SecurityRole;

@RestController
@PreAuthorize("hasRole('" + SecurityRole.SERVER_ADMIN + "')")
@RequestMapping({ "${spring.data.rest.base-path}/authorities" })
public class AuthorityController {
  @Autowired
  private AuthorityCRUDRepository authorityRepository;

  @GetMapping
  public Page<Authority> getAllAuthorities(Pageable pageable) {
    return authorityRepository.findAll(pageable);
  }
}