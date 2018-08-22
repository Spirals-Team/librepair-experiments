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
package com.alipay.sofa.rpc.registry.mesh.model;

/**
 * @author bystander
 * @version $Id: MeshEndpoint.java, v 0.1 2018年04月20日 3:08 PM bystander Exp $
 */
public class MeshEndpoint {

    public final static String PUBLISH     = "/services/publish";

    public final static String UN_PUBLISH  = "/services/unpublish";

    public final static String SUBCRIBE    = "/services/subscribe";

    public final static String UN_SUBCRIBE = "/services/unsubscribe";

    public final static String CONFIGS     = "/configs/application";

}