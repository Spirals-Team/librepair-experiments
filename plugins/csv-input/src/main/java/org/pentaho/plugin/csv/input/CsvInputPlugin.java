package org.pentaho.plugin.csv.input;

import org.pentaho.beam.app.api.plugin.Plugin;
import org.pentaho.beam.app.api.plugin.Steps;

import java.util.Properties;

/**
 * Created by ccaspanello on 5/10/18.
 */
public class CsvInputPlugin implements Plugin {

  @Override
  public String getName() {
    return "CsvInputPlugin";
  }

  @Override
  public Steps steps() {
    Steps steps = new Steps();
    steps.getSteps().put( CsvInputMeta.class, CsvInput.class );
    return steps;
  }

  @Override
  public Properties properties() {
    return null;
  }
}
