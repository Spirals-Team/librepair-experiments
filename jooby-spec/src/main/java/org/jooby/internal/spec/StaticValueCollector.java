/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jooby.internal.spec;

import java.util.HashMap;
import java.util.Map;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class StaticValueCollector extends VoidVisitorAdapter<Context> {

  private Map<String, Object> values = new HashMap<>();

  public Map<String, Object> accept(final Node node, final Context ctx) {
    root(node).accept(this, ctx);
    return values;
  }

  @Override
  public void visit(final VariableDeclarator n, final Context ctx) {
    String id = n.getId().toStringWithoutComments();
    values.put(id, n.getInit().accept(new LiteralCollector(), ctx));
  }

  private Node root(final Node n) {
    Node prev = n;
    Node it = n;
    while (it != null) {
      prev = it;
      it = it.getParentNode();
    }
    return prev;
  }

}
