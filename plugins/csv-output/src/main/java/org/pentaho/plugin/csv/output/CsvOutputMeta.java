package org.pentaho.plugin.csv.output;


import org.pentaho.beam.app.api.stepmeta.BaseStepMeta;

/**
 * Created by ccaspanello on 5/9/18.
 */
public class CsvOutputMeta extends BaseStepMeta {

  private String path;

  public CsvOutputMeta( String name ) {
    super( name );
  }

  public String getPath() {
    return path;
  }

  public void setPath( String path ) {
    this.path = path;
  }

}
