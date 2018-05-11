package org.pentaho.plugin.csv.output;

import org.pentaho.beam.app.api.plugin.Plugin;
import org.pentaho.beam.app.api.plugin.Steps;

import java.util.Properties;

/**
 * Created by ccaspanello on 5/10/18.
 */
public class CsvOutputPlugin implements Plugin {

  @Override
  public String getName() {
    return "CsvInputPlugin";
  }

  @Override
  public Steps steps() {
    Steps steps = new Steps();
    steps.getSteps().put( CsvOutputMeta.class, CsvOutput.class );
    return steps;
  }

  @Override
  public Properties properties() {
    return null;
  }
}
