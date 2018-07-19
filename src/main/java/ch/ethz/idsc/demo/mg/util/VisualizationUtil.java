// code by mg
package ch.ethz.idsc.demo.mg.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.List;

import ch.ethz.idsc.demo.mg.pipeline.ImageBlob;

// provides static functions for visualization
public enum VisualizationUtil {
  ;
  /** scales a bufferedImage. if scaled width/height is smaller than 1, it is set to 1
   * 
   * @param unscaled original bufferedImage
   * @param scale scaling factor
   * @return scaled bufferedImage */
  public static BufferedImage scaleImage(BufferedImage unscaled, double scale) {
    int newWidth = (int) (unscaled.getWidth() * scale >= 1 ? unscaled.getWidth() * scale : 1);
    int newHeight = (int) (unscaled.getHeight() * scale >= 1 ? unscaled.getHeight() * scale : 1);
    BufferedImage scaled = new BufferedImage(newWidth, newHeight, unscaled.getType());
    AffineTransform scaleInstance = AffineTransform.getScaleInstance(scale, scale);
    AffineTransformOp scaleOp = new AffineTransformOp(scaleInstance, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    scaleOp.filter(unscaled, scaled);
    return scaled;
  }

  /** flips the bufferedImage along the horizontal axis
   * 
   * @param bufferedImage
   * @return flipped bufferedImage */
  public static BufferedImage flipHorizontal(BufferedImage bufferedImage) {
    AffineTransform affineTransform = AffineTransform.getScaleInstance(1, -1);
    affineTransform.translate(0, -bufferedImage.getHeight());
    AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    return affineTransformOp.filter(bufferedImage, null);
  }

  /** draws an ellipse representing a ImageBlob object onto a Graphics2D object
   * 
   * @param graphics object to be drawn onto
   * @param blob ImageBlob to be drawn
   * @param color desired color */
  public static void drawImageBlob(Graphics2D graphics, ImageBlob blob, Color color) {
    AffineTransform old = graphics.getTransform();
    double rotAngle = blob.getRotAngle();
    float[] semiAxes = blob.getStandardDeviation();
    float leftCornerX = blob.getPos()[0] - semiAxes[0];
    float leftCornerY = blob.getPos()[1] - semiAxes[1];
    // draw ellipse with first eigenvalue aligned with x axis
    Ellipse2D ellipse = new Ellipse2D.Float(leftCornerX, leftCornerY, 2 * semiAxes[0], 2 * semiAxes[1]);
    // rotate around blob pos by rotAngle
    graphics.rotate(rotAngle, blob.getPos()[0], blob.getPos()[1]);
    graphics.setColor(color);
    graphics.draw(ellipse);
    graphics.setTransform(old);
  }

  /** draw ellipses for image based on list of blobs for the image.
   * 
   * @param graphics
   * @param blobs */
  public static void drawEllipsesOnImage(Graphics2D graphics, List<ImageBlob> blobs) {
    blobs.forEach(blob -> drawImageBlob(graphics, blob, Color.WHITE));
  }
}
