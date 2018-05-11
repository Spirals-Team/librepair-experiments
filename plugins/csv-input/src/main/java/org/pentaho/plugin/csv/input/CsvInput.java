package org.pentaho.plugin.csv.input;

import org.apache.beam.sdk.extensions.sql.RowSqlType;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.Row;
import org.apache.beam.sdk.values.RowType;
import org.pentaho.beam.app.api.step.BaseStep;

/**
 * Created by ccaspanello on 5/9/18.
 */
public class CsvInput extends BaseStep<CsvInputMeta> {

  public CsvInput( CsvInputMeta meta ) {
    super( meta );
  }

  @Override
  public void apply() {
    RowType rowType =
      RowSqlType
        .builder()
        .withIntegerField( "movieId" )
        .withVarcharField( "title" )
        .withVarcharField( "genres" )
        .build();

    String path = getStepMeta().getPath();

    PCollection<String> rows = getPipeline()
      .apply( this.getStepMeta().getName(), TextIO.read().from( path ) );

    PCollection<Row> data = rows.apply( ParDo.of( new CsvInputFn( rowType ) ) );

    getOutgoing().stream().forEach( h -> h.setData( data ) );
  }

}
