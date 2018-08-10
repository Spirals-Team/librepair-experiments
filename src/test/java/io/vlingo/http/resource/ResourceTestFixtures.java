// Copyright © 2012-2018 Vaughn Vernon. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.http.resource;

import static io.vlingo.common.serialization.JsonSerialization.serialized;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;

import io.vlingo.actors.World;
import io.vlingo.http.sample.user.ContactData;
import io.vlingo.http.sample.user.NameData;
import io.vlingo.http.sample.user.UserData;
import io.vlingo.http.sample.user.model.UserRepository;
import io.vlingo.wire.message.ByteBufferAllocator;
import io.vlingo.wire.message.Converters;

public abstract class ResourceTestFixtures {
  protected Action actionPostUser;
  protected Action actionPatchUserContact;
  protected Action actionPatchUserName;
  protected Action actionGetUser;
  protected Action actionGetUsers;

  protected Resource<?> resource;
  protected Class<? extends ResourceHandler> resourceHandlerClass;
  protected Resources resources;
  protected Dispatcher dispatcher;
  protected World world;
  
  protected final UserData johnDoeUserData =
          UserData.from(
                  NameData.from("John", "Doe"),
                  ContactData.from("john.doe@vlingo.io", "+1 212-555-1212"));

  protected final String johnDoeUserSerialized = serialized(johnDoeUserData);

  protected final UserData janeDoeUserData =
          UserData.from(
                  NameData.from("Jane", "Doe"),
                  ContactData.from("jane.doe@vlingo.io", "+1 212-555-1212"));

  protected final String janeDoeUserSerialized = serialized(janeDoeUserData);

  protected final String postJohnDoeUserMessage =
          "POST /users HTTP/1.1\nHost: vlingo.io\nContent-Length: " + johnDoeUserSerialized.length() + "\n\n" + johnDoeUserSerialized;

  protected final String postJaneDoeUserMessage =
          "POST /users HTTP/1.1\nHost: vlingo.io\nContent-Length: " + janeDoeUserSerialized.length() + "\n\n" + janeDoeUserSerialized;

  private final ByteBuffer buffer = ByteBufferAllocator.allocate(65535);
  
  private int uniqueId = 1;

  protected ByteBuffer toByteBuffer(final String requestContent) {
    buffer.clear();
    buffer.put(Converters.textToBytes(requestContent));
    buffer.flip();
    return buffer;
  }

  protected String createdResponse(final String body) {
    return "HTTP/1.1 201 CREATED\nContent-Length: " + body.length() + "\n\n" + body;
  }

  protected String postRequest(final String body) {
    return "POST /users HTTP/1.1\nHost: vlingo.io\nContent-Length: " + body.length() + "\n\n" + body;
  }

  protected String janeDoeCreated() {
    return createdResponse(janeDoeUserSerialized);
  }

  protected String uniqueJaneDoe() {
    final UserData unique =
            UserData.from(
                    "" + uniqueId,
                    NameData.from("Jane", "Doe"),
                    ContactData.from("jane.doe@vlingo.io", "+1 212-555-1212"));

    ++uniqueId;
    
    final String serialized = serialized(unique);
    
    return serialized;
  }

  protected String uniqueJaneDoePostCreated() {
    return createdResponse(uniqueJaneDoe());
  }

  protected String uniqueJaneDoePostRequest() {
    return postRequest(uniqueJaneDoe());
  }

  protected String uniqueJohnDoe() {
    String id = "" + uniqueId;
    if (id.length() == 1) id = "00" + id;
    if (id.length() == 2) id = "0" + id;
    final UserData unique =
            UserData.from(
                    id, //"" + uniqueId,
                    NameData.from("John", "Doe"),
                    ContactData.from("john.doe@vlingo.io", "+1 212-555-1212"));

    ++uniqueId;
    
    final String serialized = serialized(unique);

    return serialized;
  }

  protected String johnDoeCreated() {
    return createdResponse(johnDoeUserSerialized);
  }

  protected String uniqueJohnDoePostCreated() {
    return createdResponse(uniqueJohnDoe());
  }

  protected String uniqueJohnDoePostRequest() {
    return postRequest(uniqueJohnDoe());
  }

  @Before
  public void setUp() throws Exception {
    world = World.start("resource-test");
    
    actionPostUser = new Action(0, "POST", "/users", "register(body:io.vlingo.http.sample.user.UserData userData)", null, true);
    actionPatchUserContact = new Action(1, "PATCH", "/users/{userId}/contact", "changeContact(String userId, body:io.vlingo.http.sample.user.ContactData contactData)", null, true);
    actionPatchUserName = new Action(2, "PATCH", "/users/{userId}/name", "changeName(String userId, body:io.vlingo.http.sample.user.NameData nameData)", null, true);
    actionGetUser = new Action(3, "GET", "/users/{userId}", "queryUser(String userId)", null, true);
    actionGetUsers = new Action(4, "GET", "/users", "queryUsers()", null, true);

    final List<Action> actions =
            Arrays.asList(
                    actionPostUser,
                    actionPatchUserContact,
                    actionPatchUserName,
                    actionGetUser,
                    actionGetUsers);

    resourceHandlerClass = Resource.newResourceHandlerClassFor("io.vlingo.http.sample.user.UserResource");
    
    resource = Resource.newResourceFor("user", resourceHandlerClass, 5, actions);
    
    resource.allocateHandlerPool(world.stage());
    
    final Map<String,Resource<?>> oneResource = new HashMap<>(1);
    
    oneResource.put(resource.name, resource);
    
    resources = new Resources(oneResource);
    
    dispatcher = new TestDispatcher(resources);
  }

  @After
  public void tearDown() {
    world.terminate();
    
    UserRepository.reset();
  }
}
