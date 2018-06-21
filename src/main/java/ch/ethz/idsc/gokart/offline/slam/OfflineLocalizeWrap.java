// code by jph
package ch.ethz.idsc.gokart.offline.slam;

import java.nio.ByteBuffer;
import java.util.Objects;

import ch.ethz.idsc.gokart.core.pos.LocalizationConfig;
import ch.ethz.idsc.gokart.gui.GokartLcmChannel;
import ch.ethz.idsc.gokart.gui.GokartStatusEvent;
import ch.ethz.idsc.gokart.gui.top.ChassisGeometry;
import ch.ethz.idsc.gokart.lcm.autobox.LinmotLcmServer;
import ch.ethz.idsc.gokart.lcm.autobox.RimoLcmServer;
import ch.ethz.idsc.gokart.offline.api.OfflineTableSupplier;
import ch.ethz.idsc.retina.dev.davis.data.DavisImuFrame;
import ch.ethz.idsc.retina.dev.lidar.LidarAngularFiringCollector;
import ch.ethz.idsc.retina.dev.lidar.LidarRotationProvider;
import ch.ethz.idsc.retina.dev.lidar.LidarSpacialProvider;
import ch.ethz.idsc.retina.dev.lidar.VelodyneDecoder;
import ch.ethz.idsc.retina.dev.lidar.VelodyneModel;
import ch.ethz.idsc.retina.dev.lidar.vlp16.Vlp16Decoder;
import ch.ethz.idsc.retina.dev.linmot.LinmotGetEvent;
import ch.ethz.idsc.retina.dev.rimo.RimoGetEvent;
import ch.ethz.idsc.retina.dev.rimo.RimoPutEvent;
import ch.ethz.idsc.retina.dev.rimo.RimoPutHelper;
import ch.ethz.idsc.retina.dev.rimo.RimoPutTire;
import ch.ethz.idsc.retina.dev.steer.SteerConfig;
import ch.ethz.idsc.retina.lcm.davis.DavisImuFramePublisher;
import ch.ethz.idsc.retina.lcm.lidar.VelodyneLcmChannels;
import ch.ethz.idsc.retina.util.math.Magnitude;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.io.TableBuilder;
import ch.ethz.idsc.tensor.sca.Round;

public class OfflineLocalizeWrap implements OfflineTableSupplier, LocalizationResultListener {
  private static final String CHANNEL_LIDAR = //
      VelodyneLcmChannels.ray(VelodyneModel.VLP16, GokartLcmChannel.VLP16_CENTER);
  private static final String CHANNEL_IMU = //
      DavisImuFramePublisher.channel(GokartLcmChannel.DAVIS_OVERVIEW);
  // ---
  private final VelodyneDecoder velodyneDecoder = new Vlp16Decoder();
  private final OfflineLocalize offlineLocalize;
  private DavisImuFrame davisImuFrame;
  private RimoGetEvent rimoGetEvent;
  private RimoPutEvent rimoPutEvent;
  private LinmotGetEvent linmotGetEvent;
  private GokartStatusEvent gokartStatusEvent;
  private final TableBuilder tableBuilder = new TableBuilder();

  public OfflineLocalizeWrap(OfflineLocalize offlineLocalize) {
    LidarAngularFiringCollector lidarAngularFiringCollector = new LidarAngularFiringCollector(2304, 2);
    // LidarSpacialProvider lidarSpacialProvider = SensorsConfig.GLOBAL.planarEmulatorVlp16_p01deg();
    LidarSpacialProvider lidarSpacialProvider = LocalizationConfig.GLOBAL.planarEmulatorVlp16();
    lidarSpacialProvider.addListener(lidarAngularFiringCollector);
    LidarRotationProvider lidarRotationProvider = new LidarRotationProvider();
    lidarRotationProvider.addListener(lidarAngularFiringCollector);
    velodyneDecoder.addRayListener(lidarSpacialProvider);
    velodyneDecoder.addRayListener(lidarRotationProvider);
    this.offlineLocalize = offlineLocalize;
    lidarAngularFiringCollector.addListener(offlineLocalize);
    offlineLocalize.addListener(this);
  }

  @Override // from OfflineLogListener
  public void event(Scalar time, String channel, ByteBuffer byteBuffer) {
    if (channel.equals(CHANNEL_LIDAR)) {
      offlineLocalize.setTime(time);
      velodyneDecoder.lasers(byteBuffer);
    } else //
    if (channel.equals(CHANNEL_IMU)) {
      davisImuFrame = new DavisImuFrame(byteBuffer);
      offlineLocalize.imuFrame(davisImuFrame);
    } else //
    if (channel.equals(LinmotLcmServer.CHANNEL_GET))
      linmotGetEvent = new LinmotGetEvent(byteBuffer);
    else //
    if (channel.equals(RimoLcmServer.CHANNEL_GET))
      rimoGetEvent = new RimoGetEvent(byteBuffer);
    else //
    if (channel.equals(RimoLcmServer.CHANNEL_PUT))
      rimoPutEvent = RimoPutHelper.from(byteBuffer);
    else //
    if (channel.equals(GokartLcmChannel.STATUS))
      gokartStatusEvent = new GokartStatusEvent(byteBuffer);
  }

  @Override // from LocalizationResultListener
  public void localizationCallback(LocalizationResult localizationResult) {
    if (Objects.isNull(davisImuFrame) || //
        Objects.isNull(linmotGetEvent) || //
        Objects.isNull(rimoGetEvent) || //
        Objects.isNull(rimoPutEvent) || //
        Objects.isNull(gokartStatusEvent))
      return;
    System.out.println(localizationResult.time + " " + localizationResult.ratio);
    Tensor rates = rimoGetEvent.getAngularRate_Y_pair();
    Scalar speed = ChassisGeometry.GLOBAL.odometryTangentSpeed(rimoGetEvent);
    Scalar rate = ChassisGeometry.GLOBAL.odometryTurningRate(rimoGetEvent);
    tableBuilder.appendRow( //
        localizationResult.time.map(Magnitude.SECOND), //
        rimoPutEvent.getTorque_Y_pair().map(RimoPutTire.MAGNITUDE_ARMS), //
        rates.map(Magnitude.ANGULAR_RATE), //
        speed.map(Magnitude.VELOCITY), //
        rate.map(Magnitude.ANGULAR_RATE), //
        davisImuFrame.gyroImageFrame().Get(1).map(Magnitude.ANGULAR_RATE), //
        SteerConfig.GLOBAL.getAngleFromSCE(gokartStatusEvent), //
        linmotGetEvent.getActualPosition().map(Magnitude.METER).map(Round._6), //
        localizationResult.pose_xyt.extract(0, 2).map(Round._3), //
        localizationResult.pose_xyt.Get(2).map(Round._6), //
        localizationResult.ratio //
    );
  }

  @Override // from OfflineTableSupplier
  public Tensor getTable() {
    return tableBuilder.toTable();
  }
}
