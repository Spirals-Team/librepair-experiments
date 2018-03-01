package org.camunda.bpm.spring.boot.starter.configuration.impl;

import java.util.Map;

import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.camunda.bpm.spring.boot.starter.property.GenericProperties;
import org.camunda.bpm.spring.boot.starter.configuration.Ordering;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.util.CollectionUtils;

@Order(Ordering.DEFAULT_ORDER - 1)
public class GenericPropertiesConfiguration extends AbstractCamundaConfiguration {

  @Autowired
public ApplicationContext applicationContext;

  @Override
  public void preInit(SpringProcessEngineConfiguration springProcessEngineConfiguration) {
    GenericProperties genericProperties = camundaBpmProperties.getGenericProperties();
    final Map<String, Object> properties = genericProperties.getProperties();
    if (!CollectionUtils.isEmpty(properties)) {
      // RelaxedDataBinder relaxedDataBinder = new RelaxedDataBinder(springProcessEngineConfiguration);
      // relaxedDataBinder.setIgnoreInvalidFields(genericProperties.isIgnoreInvalidFields());
      // relaxedDataBinder.setIgnoreUnknownFields(genericProperties.isIgnoreUnknownFields());
      // relaxedDataBinder.bind(getPropertyValues(properties));
      Binder binder = Binder.get(applicationContext.getEnvironment());
      binder.bind("GenericProperties", Bindable.of(GenericProperties.class));
      logger.debug("properties bound to configuration: {}", genericProperties);
    }
  }

  protected PropertyValues getPropertyValues(Map<String, Object> genericProperties) {
    return new MutablePropertyValues(genericProperties);
  }

}
