// code by ynager
package ch.ethz.idsc.owl.bot.se2.glc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

import ch.ethz.idsc.owl.bot.r2.WaypointDistanceCost;
import ch.ethz.idsc.owl.bot.util.RegionRenders;
import ch.ethz.idsc.owl.glc.adapter.RegionConstraints;
import ch.ethz.idsc.owl.glc.adapter.SimpleGlcPlannerCallback;
import ch.ethz.idsc.owl.glc.core.CostFunction;
import ch.ethz.idsc.owl.glc.core.PlannerConstraint;
import ch.ethz.idsc.owl.gui.RenderInterface;
import ch.ethz.idsc.owl.gui.ani.GlcPlannerCallback;
import ch.ethz.idsc.owl.gui.ren.Se2WaypointRender;
import ch.ethz.idsc.owl.gui.win.OwlyAnimationFrame;
import ch.ethz.idsc.owl.math.planar.ConeRegion;
import ch.ethz.idsc.owl.math.region.RegionWithDistance;
import ch.ethz.idsc.owl.math.state.StateTime;
import ch.ethz.idsc.owl.subdiv.curve.BSpline3CurveSubdivision;
import ch.ethz.idsc.owl.subdiv.curve.Se2Geodesic;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.Pretty;
import ch.ethz.idsc.tensor.io.ResourceData;
import ch.ethz.idsc.tensor.red.Nest;

/** demo to simulate dubendorf hangar
 * 
 * the cost function rewards path that are proxy to the waypoints
 * therefore a virtual obstacle region in the center to prevent corner cutting is not required. */
public class GokartWaypoint1Demo extends GokartDemo {
  @Override
  void configure(OwlyAnimationFrame owlyAnimationFrame) {
    final StateTime initial = new StateTime(Tensors.vector(33.6, 41.5, 0.6), RealScalar.ZERO);
    Tensor waypoints = ResourceData.of("/dubilab/waypoints/20180610.csv");
    System.out.println(Pretty.of(waypoints));
    waypoints = Nest.of(new BSpline3CurveSubdivision(Se2Geodesic.INSTANCE)::cyclic, waypoints, 4);
    CostFunction waypointCost = WaypointDistanceCost.linear(waypoints, Tensors.vector(85.33, 85.33), 6.0f, new Dimension(640, 640));
    GokartVecEntity gokartEntity = new GokartVecEntity(initial) {
      @Override
      public RegionWithDistance<Tensor> getGoalRegionWithDistance(Tensor goal) {
        return new ConeRegion(goal, RealScalar.of(Math.PI / 10));
      }
    };
    // set cost function hierarchy
    gokartEntity.setCostVector(Arrays.asList(waypointCost), Arrays.asList(0.0));
    gokartEntity.addTimeCost(1, 0.0);
    // ---
    HelperHangarMap hangarMap = new HelperHangarMap("/dubilab/obstacles/20180423.png", gokartEntity);
    PlannerConstraint plannerConstraint = RegionConstraints.timeInvariant(hangarMap.region);
    // ---
    owlyAnimationFrame.add(gokartEntity);
    owlyAnimationFrame.addBackground(RegionRenders.create(hangarMap.imageRegion));
    owlyAnimationFrame.geometricComponent.setModel2Pixel(HelperHangarMap.MODEL2PIXEL);
    // ---
    RenderInterface renderInterface = new Se2WaypointRender(waypoints, ARROWHEAD, new Color(64, 192, 64, 64));
    owlyAnimationFrame.addBackground(renderInterface);
    GlcPlannerCallback glcPlannerCallback = new SimpleGlcPlannerCallback(gokartEntity);
    GlcWaypointFollowing glcWaypointFollowing = new GlcWaypointFollowing( //
        waypoints, RealScalar.of(2), gokartEntity, plannerConstraint, //
        Arrays.asList(gokartEntity, glcPlannerCallback));
    glcWaypointFollowing.setHorizonDistance(RealScalar.of(8));
    glcWaypointFollowing.startNonBlocking();
    // ---
    owlyAnimationFrame.jFrame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosed(WindowEvent e) {
        System.out.println("window was closed. terminating...");
        glcWaypointFollowing.flagShutdown();
      }
    });
  }

  public static void main(String[] args) {
    new GokartWaypoint1Demo().start().jFrame.setVisible(true);
  }
}