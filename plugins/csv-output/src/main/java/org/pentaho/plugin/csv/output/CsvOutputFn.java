package org.pentaho.plugin.csv.output;

import org.apache.beam.sdk.repackaged.org.apache.commons.lang3.StringUtils;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.Row;
import org.apache.beam.sdk.values.RowType;

import java.util.List;

/**
 * Created by ccaspanello on 5/9/18.
 */
public class CsvOutputFn extends DoFn<Row, String> {

  private RowType rowType;

  public CsvOutputFn( RowType rowType ) {
    this.rowType = rowType;
  }

  @ProcessElement
  public void processElement( ProcessContext c ) {
    Row row = c.element();
    List<Object> values = row.getValues();
    c.output( StringUtils.join( values, "," ) );
  }
}
