package org.pentaho.beam.app.api.plugin;

import org.pentaho.beam.app.api.plugin.Steps;

import java.util.Properties;

/**
 * Created by ccaspanello on 5/10/18.
 */
public interface Plugin {

  String getName();
  Steps steps();
  Properties properties();

}