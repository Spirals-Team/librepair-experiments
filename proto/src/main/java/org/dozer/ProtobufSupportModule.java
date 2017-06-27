/*
 * Copyright 2005-2017 Dozer Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dozer;

import java.util.Collection;
import java.util.Collections;
import org.dozer.builder.BeanBuilderCreationStrategy;
import org.dozer.builder.ByProtobufBuilder;
import org.dozer.classmap.generator.BeanFieldsDetector;
import org.dozer.classmap.generator.ProtobufBeanFieldsDetector;
import org.dozer.config.BeanContainer;
import org.dozer.factory.DestBeanCreator;
import org.dozer.propertydescriptor.PropertyDescriptorCreationStrategy;
import org.dozer.propertydescriptor.PropertyDescriptorFactory;
import org.dozer.propertydescriptor.ProtoFieldPropertyDescriptorCreationStrategy;

/**
 * @author Dmitry Spikhalskiy
 */
public class ProtobufSupportModule implements DozerModule {

  private BeanContainer beanContainer;
  private DestBeanCreator destBeanCreator;
  private PropertyDescriptorFactory propertyDescriptorFactory;

  @Override
  public void init(BeanContainer beanContainer, DestBeanCreator destBeanCreator, PropertyDescriptorFactory propertyDescriptorFactory)  {
    this.beanContainer = beanContainer;
    this.destBeanCreator = destBeanCreator;
    this.propertyDescriptorFactory = propertyDescriptorFactory;
  }

  public void init() {
  }

  @Override
  public Collection<BeanBuilderCreationStrategy> getBeanBuilderCreationStrategies() {
    return Collections.singleton(new ByProtobufBuilder());
  }

  @Override
  public Collection<BeanFieldsDetector> getBeanFieldsDetectors() {
    return Collections.singleton(new ProtobufBeanFieldsDetector());
  }

  @Override
  public Collection<PropertyDescriptorCreationStrategy> getPropertyDescriptorCreationStrategies() {
    return Collections.singleton(new ProtoFieldPropertyDescriptorCreationStrategy(beanContainer, destBeanCreator, propertyDescriptorFactory));
  }
}
