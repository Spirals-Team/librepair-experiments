package io.mincongh.rest;

import io.mincongh.rest.dto.User;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Default implementation of user endpoint.
 *
 * @author Mincong Huang
 */
public class UserEndpointImpl implements UserEndpoint {

  private AtomicInteger id = new AtomicInteger(0);

  private Map<Integer, User> users = new HashMap<>();

  @Override
  public User createUser(String name, int age) {
    User u = new User(id.getAndIncrement(), name, age);
    users.put(u.getId(), u);
    return u;
  }

  @Override
  public User getUser(int id) {
    if (users.containsKey(id)) {
      return users.get(id);
    }
    throw new UserNotFoundException("Cannot find user (id=" + id + ")");
  }
}
