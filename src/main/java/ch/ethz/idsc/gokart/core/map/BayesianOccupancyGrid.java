// code by ynager
package ch.ethz.idsc.gokart.core.map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import ch.ethz.idsc.gokart.core.pos.GokartPoseHelper;
import ch.ethz.idsc.gokart.gui.top.SensorsConfig;
import ch.ethz.idsc.owl.gui.RenderInterface;
import ch.ethz.idsc.owl.gui.win.AffineTransforms;
import ch.ethz.idsc.owl.gui.win.GeometricLayer;
import ch.ethz.idsc.owl.math.RadiusXY;
import ch.ethz.idsc.owl.math.map.Se2Utils;
import ch.ethz.idsc.owl.math.region.Region;
import ch.ethz.idsc.retina.util.math.Bresenham;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.VectorQ;
import ch.ethz.idsc.tensor.mat.DiagonalMatrix;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.sca.Ceiling;
import ch.ethz.idsc.tensor.sca.Floor;
import ch.ethz.idsc.tensor.sca.Sign;

/** all pixels have the same amount of weight or clearance radius attached
 * 
 * the cascade of affine transformation is
 * lidar2cell == grid2gcell * world2grid * gokart2world * lidar2gokart */
public class BayesianOccupancyGrid implements Region<Tensor>, RenderInterface {
  private static final short MASK_OCCUPIED = 0x00;
  private static final short MASK_UNKNOWN = 0xDD;
  @SuppressWarnings("unused")
  private static final short MASK_EMPTY = 0xFF;
  private static final Tensor LIDAR2GOKART = SensorsConfig.GLOBAL.vlp16Gokart(); // from lidar frame to gokart frame
  // ---
  /** prior */
  private static final double P_M = MappingConfig.GLOBAL.getP_M(); // prior
  private static final double L_M_INV = StaticHelper.pToLogOdd(1 - P_M);
  /** inv sensor model p(m|z) */
  private static final double P_M_HIT = MappingConfig.GLOBAL.getP_M_HIT();
  private static final double P_M_PASS = MappingConfig.GLOBAL.getP_M_PASS();
  /** cells with p(m|z_1:t) > probThreshold are considered occupied */
  private static final double P_THRESH = MappingConfig.GLOBAL.getP_THRESH();
  private static final double L_THRESH = StaticHelper.pToLogOdd(P_THRESH);
  private static final double[] PREDEFINED_P = { 1 - P_M_HIT, P_M_HIT, P_M_PASS };
  /** forgetting factor for previous classifications */
  private static final double lambda = MappingConfig.GLOBAL.getLambda();

  /** @param lbounds vector of length 2
   * @param range effective size of grid in coordinate space
   * @param cellDim non-negative dimension of cell in [m]
   * @return instance of BayesianOccupancyGrid with grid dimensions ceil'ed to fit a whole number
   * of cells per dimension */
  public static BayesianOccupancyGrid of(Tensor lbounds, Tensor range, Scalar cellDim) {
    Tensor sizeCeil = Ceiling.of(range.divide(Sign.requirePositive(cellDim)));
    Tensor rangeCeil = sizeCeil.multiply(cellDim);
    return new BayesianOccupancyGrid(lbounds, rangeCeil, //
        new Dimension(sizeCeil.Get(0).number().intValue(), sizeCeil.Get(1).number().intValue()));
  }

  // ---
  private Tensor lbounds;
  private final Scalar cellDim; // [m] per cell
  private final Tensor cellDimHalfVec;
  private final Scalar cellDimInv; // cells per [m]
  private final Tensor gridSize; // grid size in pixels
  private final int dimx;
  private final int dimy;
  // ---
  /** from gokart frame to world frame */
  private Tensor gokart2world = null;
  private final GeometricLayer lidar2cellLayer;
  private final GeometricLayer world2cellLayer;
  @SuppressWarnings("unused")
  private double poseQuality;
  // ---
  /** array containing current log odds of each cell */
  private double[] logOdds;
  /** maximum likelihood obstacle map */
  private final BufferedImage obstacleImage;
  private final byte[] imagePixels;
  private final Graphics2D imageGraphics;
  /** set of occupied cells */
  private final Set<Tensor> hset = new HashSet<>();
  // ---
  private Scalar obsDilationRadius;
  private final Tensor scaling;
  private final double lFactor;

  /** @param lbounds vector of length 2
   * @param range effective size of grid in coordinate space of the form {value, value}
   * @param dimension of grid in cell space */
  private BayesianOccupancyGrid(Tensor lbounds, Tensor range, Dimension dimension) {
    VectorQ.requireLength(range, 2);
    System.out.print("Grid range: " + range + "\n");
    System.out.print("Grid size: " + dimension + "\n");
    this.lbounds = VectorQ.requireLength(lbounds, 2);
    gridSize = Tensors.vector(dimension.width, dimension.height).unmodifiable();
    dimx = dimension.width;
    dimy = dimension.height;
    cellDim = RadiusXY.requireSame(range).divide(gridSize.Get(0));
    cellDimInv = cellDim.reciprocal();
    cellDimHalfVec = Tensors.of(cellDim, cellDim).divide(RealScalar.of(2)).unmodifiable();
    scaling = DiagonalMatrix.of(cellDim, cellDim, RealScalar.ONE).unmodifiable();
    // ---
    obstacleImage = new BufferedImage(dimx, dimy, BufferedImage.TYPE_BYTE_GRAY);
    WritableRaster writableRaster = obstacleImage.getRaster();
    DataBufferByte dataBufferByte = (DataBufferByte) writableRaster.getDataBuffer();
    imagePixels = dataBufferByte.getData();
    imageGraphics = obstacleImage.createGraphics();
    genObstacleMap();
    obsDilationRadius = cellDim.divide(RealScalar.of(2));
    Scalar ratio = MappingConfig.GLOBAL.minObsHeight.divide(SensorsConfig.GLOBAL.vlp16Height);
    lFactor = RealScalar.ONE.subtract(ratio).number().doubleValue();
    // ---
    // PREDEFINED_P
    logOdds = new double[dimx * dimy];
    Arrays.fill(logOdds, StaticHelper.pToLogOdd(P_M));
    // ---
    Tensor grid2cell = DiagonalMatrix.of(cellDimInv, cellDimInv, RealScalar.ONE);
    Tensor world2grid = getWorld2grid();
    //  ---
    lidar2cellLayer = GeometricLayer.of(grid2cell); // grid 2 cell
    lidar2cellLayer.pushMatrix(world2grid); // world to grid
    lidar2cellLayer.pushMatrix(IdentityMatrix.of(3)); // placeholder gokart2world
    lidar2cellLayer.pushMatrix(LIDAR2GOKART); // lidar to gokart
    // ---
    world2cellLayer = GeometricLayer.of(grid2cell);
    world2cellLayer.pushMatrix(world2grid);
  }

  /** @return matrix */
  private Tensor getWorld2grid() {
    return Se2Utils.toSE2Matrix(lbounds.negate().append(RealScalar.ZERO));
  }

  /** process a new lidar observation and update the occupancy map
   * 
   * @param pos 2D position of new lidar observation in gokart coordinates
   * @param type of observation either 0, or 1 */
  public void processObservation(Tensor pos, int type) {
    if (Objects.nonNull(gokart2world)) {
      Tensor cell = lidarToCell(pos);
      int pix = cell.Get(0).number().intValue();
      if (0 <= pix && pix < dimx) {
        int piy = cell.Get(1).number().intValue();
        if (0 <= piy && piy < dimy) {
          int idx = piy * dimx + pix;
          double logOddPrev = logOdds[idx];
          updateCellLogOdd(pix, piy, PREDEFINED_P[type]);
          double logOdd = logOdds[idx];
          // Max likelihood estimation
          synchronized (hset) {
            if (L_THRESH < logOdd && logOddPrev <= L_THRESH)
              hset.add(cell);
            else //
            if (logOdd < L_THRESH && L_THRESH <= logOddPrev)
              hset.remove(cell);
          }
          // ---
          if (type == 0 && lFactor != 1.0) {
            Tensor pos0 = pos.multiply(DoubleScalar.of(lFactor));
            Tensor cell0 = lidarToCell(pos0);
            List<Point> line = Bresenham.line( //
                cell0.Get(0).number().intValue(), //
                cell0.Get(1).number().intValue(), //
                pix, piy);
            line.remove(line.size() - 1);
            for (Point p : line) {
              idx = p.y * dimx + p.x;
              logOddPrev = logOdds[idx];
              updateCellLogOdd(p.x, p.y, PREDEFINED_P[2]);
              logOdd = logOdds[idx];
              // Max likelihood estimation
              synchronized (hset) {
                if (L_THRESH < logOdd && logOddPrev <= L_THRESH)
                  hset.add(cell);
                else //
                if (logOdd < L_THRESH && L_THRESH <= logOddPrev)
                  hset.remove(cell);
              }
            }
          }
        }
      }
    } else {
      System.err.println("Observation not processed - no pose received");
    }
  }

  /** set vehicle pose w.r.t world frame
   * 
   * @param pose vector of the form {px, py, heading}
   * @param quality */
  public void setPose(Tensor pose, Scalar quality) {
    poseQuality = quality.number().doubleValue();
    gokart2world = GokartPoseHelper.toSE2Matrix(pose);
    lidar2cellLayer.popMatrix();
    lidar2cellLayer.popMatrix();
    lidar2cellLayer.pushMatrix(gokart2world);
    lidar2cellLayer.pushMatrix(LIDAR2GOKART);
  }

  /** clears current obstacle image and redraws all known obstacles */
  public void genObstacleMap() {
    imageGraphics.setColor(new Color(MASK_UNKNOWN, MASK_UNKNOWN, MASK_UNKNOWN));
    imageGraphics.fillRect(0, 0, obstacleImage.getWidth(), obstacleImage.getHeight());
    synchronized (hset) {
      for (Tensor cell : hset)
        drawSphere(cell, obsDilationRadius, MASK_OCCUPIED);
    }
  }

  /** cells within this radius of an occupied cell will also be labeled as occupied.
   * If not set or below cellDim, only the occupied cell is labeled as an obstacle
   * @param radius */
  public void setObstacleRadius(Scalar radius) {
    System.out.println("Radius: " + radius);
    obsDilationRadius = radius;
  }

  /** Updates the grid lbounds. Grid range and size remain unchanged.
   * Overlapping segment is copied.
   * 
   * @param lbounds */
  public void setNewlBound(Tensor lbounds) {
    if (Objects.nonNull(gokart2world)) {
      this.lbounds = VectorQ.requireLength(lbounds, 2);
      // ---
      lidar2cellLayer.popMatrix();
      lidar2cellLayer.popMatrix();
      lidar2cellLayer.popMatrix();
      lidar2cellLayer.pushMatrix(getWorld2grid()); // updated world to grid
      double[] logOddsNew = new double[dimx * dimy];
      Arrays.fill(logOddsNew, StaticHelper.pToLogOdd(P_M));
      double threshold = L_THRESH;
      synchronized (hset) {
        hset.clear();
        Tensor trans = lidarToCell(toPos(Tensors.vector(0, 0))); // calculate translation
        final int ofsx = trans.Get(0).number().intValue();
        final int ofsy = trans.Get(1).number().intValue();
        // ---
        for (int i = 0; i < dimx; i++)
          for (int j = 0; j < dimy; j++) {
            double logOdd = logOdds[j * dimx + i];
            int pix = i + ofsx;
            if (0 <= pix && pix < dimx) {
              int piy = j + ofsy;
              if (0 <= piy && piy < dimy) {
                logOddsNew[piy * dimx + pix] = logOdd;
                if (logOdd > threshold)
                  hset.add(Tensors.vector(pix, piy));
              }
            }
          }
      }
      logOdds = logOddsNew;
      lidar2cellLayer.pushMatrix(gokart2world); // gokart to world
      lidar2cellLayer.pushMatrix(LIDAR2GOKART); // lidar to gokart
    }
  }

  /** Update the log odds of a cell using the probability of occupation given a new observation.
   * l_t = l_{t-1} + log[ p(m|z_t) / (1 - p(m|z_t)) ] + log[ (1-p(m)) / p(m) ]
   * @param idx of cell to be updated
   * @param p_m_z probability in [0,1] that Cell is occupied given the current observation z */
  private void updateCellLogOdd(int pix, int piy, double p_m_z) {
    int idx = piy * dimx + pix;
    double logOddDelta = StaticHelper.pToLogOdd(p_m_z) + L_M_INV;
    logOdds[idx] = lambda * logOdds[idx] + logOddDelta;
    if (Double.isInfinite(logOdds[idx]))
      throw new ArithmeticException("Overflow");
  }

  /** function is used as key
   * 
   * @param pos vector of the form {px, py, ...}; only the first two entries are considered
   * @return */
  private Tensor lidarToCell(Tensor pos) {
    Point2D point2D = lidar2cellLayer.toPoint2D(pos);
    Tensor point = Tensors.vector(point2D.getX(), point2D.getY());
    return Floor.of(point); // TODO investigate if class with 2 int's is an attractive replacement as key type
  }

  /** Remark: values in the open interval (-1, 0) are now incorrectly ceil'ed to 0.
   * however, the consequences are negligible
   * 
   * @param pos
   * @return */
  private Point worldToCell(Tensor pos) {
    Point2D point2D = world2cellLayer.toPoint2D(pos);
    return new Point( //
        (int) point2D.getX(), //
        (int) point2D.getY());
  }

  private Tensor toPos(Tensor cell) {
    return cell.multiply(cellDim).add(cellDimHalfVec);
  }

  private int cellToIdx(Tensor cell) {
    return cell.Get(1).multiply(gridSize.Get(0)).add(cell.Get(0)).number().intValue();
  }

  private void drawCell(Tensor cell, byte grayScale) {
    imagePixels[cellToIdx(cell)] = grayScale;
  }

  private void drawSphere(Tensor cell, Scalar radius, short grayScale) {
    if (Scalars.lessEquals(obsDilationRadius, cellDim))
      drawCell(cell, (byte) grayScale);
    else {
      Tensor pos = toPos(cell);
      Scalar radiusScaled = radius.multiply(cellDimInv);
      double dim = radiusScaled.number().doubleValue();
      Ellipse2D sphere = new Ellipse2D.Double( //
          pos.Get(0).multiply(cellDimInv).subtract(radiusScaled).number().doubleValue(), //
          pos.Get(1).multiply(cellDimInv).subtract(radiusScaled).number().doubleValue(), //
          2 * dim, 2 * dim);
      imageGraphics.setColor(new Color(grayScale, grayScale, grayScale));
      imageGraphics.fill(sphere);
    }
  }

  @Override // from Region<Tensor>
  public boolean isMember(Tensor state) {
    Point cell = worldToCell(state);
    int pix = cell.x;
    if (0 <= pix && pix < dimx) {
      int piy = cell.y;
      if (0 <= piy && piy < dimy)
        return imagePixels[piy * dimx + pix] == MASK_OCCUPIED;
    }
    return true;
  }

  @Override // from RenderInterface
  public void render(GeometricLayer geometricLayer, Graphics2D graphics) {
    Tensor model2pixel = geometricLayer.getMatrix();
    Tensor translate = IdentityMatrix.of(3);
    translate.set(lbounds.get(0).multiply(cellDimInv), 0, 2);
    translate.set(lbounds.get(1).multiply(cellDimInv), 1, 2);
    Tensor matrix = model2pixel.dot(scaling).dot(translate);
    graphics.drawImage(obstacleImage, AffineTransforms.toAffineTransform(matrix), null);
  }
}
