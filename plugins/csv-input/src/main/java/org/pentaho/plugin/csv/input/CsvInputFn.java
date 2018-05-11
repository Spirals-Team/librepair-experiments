package org.pentaho.plugin.csv.input;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.Row;
import org.apache.beam.sdk.values.RowType;

/**
 * Created by ccaspanello on 5/9/18.
 */
public class CsvInputFn extends DoFn<String, Row> {

  private RowType rowType;

  public CsvInputFn( RowType rowType){
    this.rowType = rowType;
  }

  @ProcessElement
  public void processElement(ProcessContext c) {

    String line = c.element();
    if(line.contains( rowType.getFieldName( 0 ) )){
      return;
    }

    Row row =
      Row
        .withRowType( rowType )
        .addValues( line.split(",(?=([^\"]|\"[^\"]*\")*$)") )
        .build();

    c.output(row);
  }
}
