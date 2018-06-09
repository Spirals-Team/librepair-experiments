// code by jph
package ch.ethz.idsc.demo.jph;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Optional;

import ch.ethz.idsc.gokart.core.pos.LocalizationConfig;
import ch.ethz.idsc.gokart.core.slam.PredefinedMap;
import ch.ethz.idsc.owl.bot.se2.Se2PointsVsRegions;
import ch.ethz.idsc.owl.bot.se2.glc.CarEntity;
import ch.ethz.idsc.owl.bot.se2.glc.CarFlows;
import ch.ethz.idsc.owl.bot.util.FlowsInterface;
import ch.ethz.idsc.owl.bot.util.RegionRenders;
import ch.ethz.idsc.owl.bot.util.UserHome;
import ch.ethz.idsc.owl.glc.adapter.GlcTrajectories;
import ch.ethz.idsc.owl.glc.core.GlcNode;
import ch.ethz.idsc.owl.glc.core.TrajectoryPlanner;
import ch.ethz.idsc.owl.gui.ani.GlcPlannerCallback;
import ch.ethz.idsc.owl.gui.win.OwlyAnimationFrame;
import ch.ethz.idsc.owl.math.region.ImageRegion;
import ch.ethz.idsc.owl.math.region.Region;
import ch.ethz.idsc.owl.math.state.SimpleTrajectoryRegionQuery;
import ch.ethz.idsc.owl.math.state.TrajectoryRegionQuery;
import ch.ethz.idsc.owl.math.state.TrajectorySample;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.img.MeanFilter;
import ch.ethz.idsc.tensor.io.Export;
import ch.ethz.idsc.tensor.io.ImageFormat;
import ch.ethz.idsc.tensor.qty.Degree;

public class DubendorfPlannerDemo {
  public final OwlyAnimationFrame start() {
    OwlyAnimationFrame owlyAnimationFrame = new OwlyAnimationFrame();
    configure(owlyAnimationFrame);
    owlyAnimationFrame.configCoordinateOffset(50, 700);
    owlyAnimationFrame.jFrame.setBounds(100, 50, 1200, 800);
    owlyAnimationFrame.jFrame.setVisible(true);
    return owlyAnimationFrame;
  }

  static TrajectoryRegionQuery createCarQuery(Region<Tensor> region) {
    Region<Tensor> se2PointsVsRegion = Se2PointsVsRegions.line(Tensors.vector(0.2, 0.1, 0, -0.1), region);
    return SimpleTrajectoryRegionQuery.timeInvariant(se2PointsVsRegion);
  }

  void configure(OwlyAnimationFrame owlyAnimationFrame) {
    FlowsInterface carFlows = CarFlows.forward(RealScalar.of(3.0), Degree.of(25));
    CarEntity carEntity = null; // FIXME
    // new CarEntity( //
    // new StateTime(Tensors.vector(35.600, 42.933, 0.942), RealScalar.ZERO), //
    // Tensors.vector(2, 2, Degree.of(10).number()), //
    // carFlows, //
    // RimoSinusIonModel.standard().footprint());
    PredefinedMap predefinedMap = LocalizationConfig.getPredefinedMap();
    BufferedImage bufferedImage = predefinedMap.getImage();
    Tensor tensor = ImageFormat.from(bufferedImage);
    Dimensions.of(tensor);
    tensor = MeanFilter.of(tensor, 7); // document 7 pixels == approx. half width of gokart
    Tensor range = predefinedMap.range();
    ImageRegion imageRegion = new ImageRegion(tensor, range, true);
    TrajectoryRegionQuery trq = createCarQuery(imageRegion);
    // carEntity.obstacleQuery = trq;
    owlyAnimationFrame.add(carEntity);
    // owlyAnimationFrame.setObstacleQuery(trq);
    owlyAnimationFrame.addBackground(RegionRenders.create(imageRegion));
    owlyAnimationFrame.geometricComponent.setModel2Pixel(predefinedMap.getModel2Pixel());
    Tensor total = Tensors.empty();
    // owlyAnimationFrame.trajectoryPlannerCallbackExtra =
    new GlcPlannerCallback() {
      @Override
      public void expandResult(List<TrajectorySample> head, TrajectoryPlanner trajectoryPlanner) {
        System.out.println("HERE !!!");
        Optional<GlcNode> optional = trajectoryPlanner.getBest();
        if (optional.isPresent()) {
          final GlcNode node = optional.get();
          // draw detailed trajectory from root to goal/furthestgo
          List<TrajectorySample> trajectory = //
              GlcTrajectories.detailedTrajectoryTo(trajectoryPlanner.getStateIntegrator(), node);
          for (TrajectorySample ts : trajectory) {
            total.append(ts.stateTime().state());
            System.out.println(ts.stateTime().state());
          }
          try {
            Export.of(UserHome.file("trajectory_" + System.nanoTime() + ".csv"), total);
            System.out.println("exported trajectory");
          } catch (Exception exception) {
            exception.printStackTrace();
          }
        }
      }
    };
  }

  public static void main(String[] args) {
    new DubendorfPlannerDemo().start();
  }
}
