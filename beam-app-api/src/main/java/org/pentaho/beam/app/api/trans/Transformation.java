package org.pentaho.beam.app.api.trans;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;
import org.pentaho.beam.app.api.hop.Hop;
import org.pentaho.beam.app.api.hop.HopMeta;
import org.pentaho.beam.app.api.plugin.StepRegistry;
import org.pentaho.beam.app.api.step.Step;
import org.pentaho.beam.app.api.stepmeta.StepMeta;
import org.pentaho.beam.app.api.transmeta.TransContext;
import org.pentaho.beam.app.api.transmeta.TransMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Transformation
 * <p>
 * Materialized version of TransMeta data; logical steps wired with hops.
 * <p>
 * Created by ccaspanello on 1/29/18.
 */
public class Transformation implements Serializable {

  private static final Logger LOG = LoggerFactory.getLogger( Transformation.class );

  private final TransContext transContext;
  private final String name;
  private TransMeta transMeta;

  public Transformation( TransContext transContext, TransMeta transMeta ) {
    this.name = transMeta.getName();
    this.transContext = transContext;
    this.transMeta = transMeta;
  }

  /**
   * Execute transformation
   */
  public Result execute() {
    List<Step> executionPlan = createExecutionPlan();
    executePlan( executionPlan );
    transContext.getPipeline().run().waitUntilFinish();
    return new Result( executionPlan );
  }

  private List<Step> createExecutionPlan() {
    DirectedGraph<Step, Hop> graph = createGraph( transMeta, transContext.getStepRegistry() );
    LOG.warn( "STEP ORDER" );
    LOG.warn( "=============================" );
    List<Step> executionPlan = new ArrayList<>();
    TopologicalOrderIterator<Step, Hop> orderIterator = new TopologicalOrderIterator<>( graph );
    while ( orderIterator.hasNext() ) {
      Step step = orderIterator.next();
      LOG.warn( "Step -> {}", step.getStepMeta().getName() );
      Set<Hop> incoming = graph.incomingEdgesOf( step );
      Set<Hop> outgoing = graph.outgoingEdgesOf( step );

      LOG.warn( "   - Incoming: {}", incoming.size() );
      LOG.warn( "   - Outgoing: {}", outgoing.size() );

      step.getIncoming().addAll( incoming );
      step.getOutgoing().addAll( outgoing );

      executionPlan.add( step );
    }
    return executionPlan;
  }

  private void executePlan( List<Step> executionPlan ) {
    LOG.warn( "RUNNING STEPS" );
    LOG.warn( "=============================" );
    for ( Step step : executionPlan ) {
      LOG.warn( "***** -> {}", step.getStepMeta().getName() );
      step.setPipeline( transContext.getPipeline() );
      step.setStepRegistry( transContext.getStepRegistry() );
      step.apply();
    }
  }

  private DirectedGraph<Step, Hop> createGraph( TransMeta transMeta, StepRegistry stepRegistry ) {
    DirectedGraph<Step, Hop> graph = new DefaultDirectedGraph<>( Hop.class );

    // Create and Map Steps
    Map<String, Step> map = new HashMap<>();
    for ( StepMeta stepMeta : transMeta.getSteps() ) {
      map.put( stepMeta.getName(), stepRegistry.createStep( stepMeta ) );
    }

    for ( StepMeta stepMeta : transMeta.getSteps() ) {
      graph.addVertex( map.get( stepMeta.getName() ) );
    }

    for ( HopMeta hopMeta : transMeta.getHops() ) {
      Step incoming = map.get( hopMeta.getIncoming() );
      Step outgoing = map.get( hopMeta.getOutgoing() );
      graph.addEdge( incoming, outgoing, new Hop( hopMeta ) );
    }
    return graph;
  }

  //<editor-fold desc="Getters & Setters">
  public String getName() {
    return name;
  }
  //</editor-fold>

}
