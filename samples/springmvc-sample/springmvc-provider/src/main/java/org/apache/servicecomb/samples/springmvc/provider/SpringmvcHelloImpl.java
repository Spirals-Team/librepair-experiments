/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.servicecomb.samples.springmvc.provider;

import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.apache.servicecomb.samples.common.schema.models.Person;
import org.apache.servicecomb.samples.common.schema.models.Word;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestSchema(schemaId = "springmvcHello")
@RequestMapping(path = "/springmvchello", produces = MediaType.APPLICATION_JSON)
public class SpringmvcHelloImpl implements Hello {
  @Override
  @RequestMapping(path = "/sayhi", method = RequestMethod.GET)
  public String sayHi(@RequestParam(name = "name", defaultValue = "test") String name) {
    return "Hello " + name;
  }

  @Override
  @RequestMapping(path = "/sayhello", method = RequestMethod.POST)
  public String sayHello(@RequestBody Person person) {
    return "Hello person " + person.getName();
  }

  @Override
  @RequestMapping(path = "/saygg", method = RequestMethod.PUT)
  public String sayGG(@RequestParam(name = "age", defaultValue = "12")int age) {
    return "hello " + age;
  }
  @Override
  @RequestMapping(path = "/justSay", method = RequestMethod.GET)
  public String justSay() {
    return "hello";
  }
  @RequestMapping(path = "/saymap", method = RequestMethod.POST)
  @Override public String sayMap(@RequestBody Map<String, String> map1) {
    return null;
  }
  @RequestMapping(path = "/saymap1", method = RequestMethod.POST)
  @Override public String sayMap1(@RequestBody Map<String, Double> map11) {
    return null;
  }
  @RequestMapping(path = "/saymap2", method = RequestMethod.POST)
  @Override public String sayMap2(@RequestBody Map<String, Word> map12) {
    return null;
  }

  @RequestMapping(path = "/sayPrice", method = RequestMethod.GET)
  @Override public String sayPrice(@RequestParam(name = "price", defaultValue = "12.0") Double price) {
    return null;
  }

  @RequestMapping(path = "/sayGGList", method = RequestMethod.GET)
  @Override public String sayGGList(@RequestParam(name = "age") List<Integer> age) {
    return null;
  }
  @RequestMapping(path = "/sayPersonList", method = RequestMethod.POST)
  @Override public String sayPersonList( @RequestBody List<Person> persons) {
    return null;
  }
}
