// code by mg
package ch.ethz.idsc.demo.mg.slam;

import java.util.stream.DoubleStream;

import ch.ethz.idsc.retina.util.math.Magnitude;
import ch.ethz.idsc.tensor.Tensor;

/** provides a grid map which is used by the SLAM algorithm */
public class MapProvider {
  private final int numberOfCells;
  private final int mapWidth;
  private final int mapHeight;
  private final double cellDim;
  private final double cellDimInv;
  private final double[] mapArray;
  private final double cornerXLow;
  private final double cornerYLow;
  private final double cornerXHigh;
  private final double cornerYHigh;
  /** tracks max value of values in array */
  private double maxValue;

  public MapProvider(SlamConfig slamConfig) {
    cellDim = Magnitude.METER.toDouble(slamConfig._cellDim);
    cellDimInv = 1 / cellDim;
    mapWidth = slamConfig.mapWidth();
    mapHeight = slamConfig.mapHeight();
    numberOfCells = mapWidth * mapHeight;
    cornerXLow = Magnitude.METER.toDouble(slamConfig._corner.Get(0));
    cornerYLow = Magnitude.METER.toDouble(slamConfig._corner.Get(1));
    Tensor cornerHigh = slamConfig.cornerHigh();
    cornerXHigh = Magnitude.METER.toDouble(cornerHigh.Get(0));
    cornerYHigh = Magnitude.METER.toDouble(cornerHigh.Get(1));
    mapArray = new double[numberOfCells];
    maxValue = 0;
  }

  /** divides the provided maps and saves into targMap */
  public static void divide(MapProvider numerator, MapProvider denominator, MapProvider targetMap) {
    // TODO loop can be done in parallel
    for (int index = 0; index < targetMap.getNumberOfCells(); ++index)
      if (denominator.getValue(index) != 0) {
        double newValue = numerator.getValue(index) / denominator.getValue(index);
        targetMap.setValue(index, newValue);
      }
  }

  // returns coordinates of cell middle point
  public double[] getCellCoord(int cellIndex) {
    if (cellIndex >= numberOfCells) {
      System.out.println("FATAL: should not access that");
      return null;
    }
    int gridPosY = cellIndex / mapWidth;
    int gridPosX = cellIndex - gridPosY * mapWidth;
    // TODO JPH precomputation of more values is possible, e.g.: cornerX + 0.5 * cellDim
    return new double[] { //
        cornerXLow + (gridPosX + 0.5) * cellDim, //
        cornerYLow + (gridPosY + 0.5) * cellDim };
  }

  /** @param posX [m]
   * @param posY [m]
   * @return cellIndex or numberOfCells if position is outside map */
  public int getCellIndex(double posX, double posY) {
    // check if position is inside map
    if (posX <= cornerXLow || posX >= cornerXHigh || posY <= cornerYLow || posY >= cornerYHigh) {
      // unreasonable number to indicate that we dont have this location
      return numberOfCells; // TODO unconventional
    }
    int gridPosX = (int) ((posX - cornerXLow) * cellDimInv);
    int gridPosY = (int) ((posY - cornerYLow) * cellDimInv);
    return gridPosX + mapWidth * gridPosY;
  }

  /** adds value in grid cell corresponding to coordinates
   * 
   * @param worldCoord [m] map position
   * @param value */
  public void addValue(Tensor worldCoord, double value) {
    addValue( //
        worldCoord.Get(0).number().doubleValue(), //
        worldCoord.Get(1).number().doubleValue(), value);
  }

  /** adds value in grid cell corresponding to pose
   * 
   * @param posX in world coordinates
   * @param posY in world coordinates
   * @param value */
  public void addValue(double posX, double posY, double value) {
    int cellIndex = getCellIndex(posX, posY);
    // case of outside map domain
    if (cellIndex == numberOfCells)
      return;
    mapArray[cellIndex] += value;
    if (mapArray[cellIndex] > maxValue)
      maxValue = mapArray[cellIndex];
  }

  public void addValue(int cellIndex, double value) {
    mapArray[cellIndex] += value;
    if (mapArray[cellIndex] > maxValue)
      maxValue = mapArray[cellIndex];
  }

  public void setValue(int cellIndex, double value) {
    if (value > maxValue)
      maxValue = value;
    mapArray[cellIndex] = value;
  }

  private double getValue(int cellIndex) {
    return mapArray[cellIndex];
  }

  /** value of grid cell which correspond to worldCoord
   * 
   * @param worldCoord [m]
   * @return map value */
  public double getValue(Tensor worldCoord) {
    return getValue( //
        worldCoord.Get(0).number().doubleValue(), //
        worldCoord.Get(1).number().doubleValue());
  }

  // gets value of cell in which the coordinates are
  public double getValue(double posX, double posY) {
    int cellIndex = getCellIndex(posX, posY);
    // case of outside map domain
    if (cellIndex == numberOfCells)
      return 0;
    return mapArray[cellIndex];
  }

  // for recorded maps
  public void setMapArray(double[] mapArray) {
    if (this.mapArray.length == mapArray.length) {
      System.arraycopy(mapArray, 0, this.mapArray, 0, this.mapArray.length);
      maxValue = DoubleStream.of(mapArray) //
          .reduce(Math::max).getAsDouble();
    } else
      throw new IllegalArgumentException();
  }

  public double[] getMapArray() {
    return mapArray;
  }

  public int getNumberOfCells() {
    return numberOfCells;
  }

  public int getMapWidth() {
    return mapWidth;
  }

  public int getMapHeight() {
    return mapHeight;
  }

  /** @return maxValue or 1 if maxValue == 0
   * since we divide by that value, we avoid all kinds of problems with that */
  public double getMaxValue() {
    return maxValue == 0 ? 1 : maxValue;
  }
}
