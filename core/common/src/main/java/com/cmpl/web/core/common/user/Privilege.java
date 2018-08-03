package com.cmpl.web.core.common.user;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.plugin.core.Plugin;

@Qualifier("privileges")
public interface Privilege extends Plugin<String> {

  String namespace();

  String feature();

  String right();

  String privilege();
}
