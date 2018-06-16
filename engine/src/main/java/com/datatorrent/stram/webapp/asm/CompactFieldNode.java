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
package com.datatorrent.stram.webapp.asm;

import java.util.List;

/**
 * <p>CompactFieldNode class.</p>
 *
 * @since 3.2.0
 */
public class CompactFieldNode
{
  private String name;
  private String desc;
  private String signature;

  private List<CompactAnnotationNode> visibleAnnotations;
  private FieldSignatureVisitor fieldSignatureNode;

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getDescription()
  {
    return desc;
  }

  public void setDescription(String desc)
  {
    this.desc = desc;
  }

  public String getSignature()
  {
    return signature;
  }

  public void setSignature(String signature)
  {
    this.signature = signature;
  }

  public List<CompactAnnotationNode> getVisibleAnnotations()
  {
    return visibleAnnotations;
  }

  public void setVisibleAnnotations(List<CompactAnnotationNode> visibleAnnotations)
  {
    this.visibleAnnotations = visibleAnnotations;
  }

  public FieldSignatureVisitor getFieldSignatureNode()
  {
    return fieldSignatureNode;
  }

  public void setFieldSignatureNode(FieldSignatureVisitor fieldSignatureNode)
  {
    this.fieldSignatureNode = fieldSignatureNode;
  }

}
