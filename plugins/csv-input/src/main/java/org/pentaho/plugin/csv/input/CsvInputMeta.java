package org.pentaho.plugin.csv.input;


import org.pentaho.beam.app.api.stepmeta.BaseStepMeta;

/**
 * Created by ccaspanello on 5/9/18.
 */
public class CsvInputMeta extends BaseStepMeta {

  private String path;

  public CsvInputMeta( String name ) {
    super( name );
  }

  public String getPath() {
    return path;
  }

  public void setPath( String path ) {
    this.path = path;
  }
}
