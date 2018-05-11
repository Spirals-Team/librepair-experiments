package org.pentaho.beam.app.api.hop;

import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.Row;
import org.jgrapht.graph.DefaultEdge;
import org.pentaho.beam.app.api.step.Step;

/**
 * Hop
 *
 * Wraps the DefaultEdge to define the to/from steps.  This wrapper will cast the source/target to Steps which by
 * default are not accessible.
 *
 * Created by ccaspanello on 1/29/18.
 */
public class Hop extends DefaultEdge {

    private HopMeta hopMeta;
    private PCollection<Row> data;

    public Hop(HopMeta hopMeta){
        this.hopMeta = hopMeta;
    }

    //<editor-fold desc="Getters & Setters">
    public Step incomingStep() {
        return (Step) getSource();
    }

    public Step outgoingStep() {
        return (Step) getTarget();
    }

    public PCollection<Row> getData() {
        return data;
    }

    public void setData( PCollection <Row> data ) {
        this.data = data;
    }

    public HopMeta getHopMeta() {
        return hopMeta;
    }
    //</editor-fold>

}
