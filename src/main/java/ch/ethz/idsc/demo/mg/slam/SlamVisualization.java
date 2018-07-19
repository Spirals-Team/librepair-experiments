// code by mg
package ch.ethz.idsc.demo.mg.slam;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import ch.ethz.idsc.demo.mg.util.VisualizationUtil;

/** similar to pipelineVisualization. Provides a live update of SlamMapFrame */
// TODO probably create abstract visualization class and then extend Slamvisualization and PipelineVisualization?
class SlamVisualization {
  private final JFrame jFrame = new JFrame();
  private final BufferedImage[] bufferedImage = new BufferedImage[3];
  private final int desiredWidth = 600; // [pixel]
  private final double scaling;
  private final JComponent jComponent = new JComponent() {
    @Override
    protected void paintComponent(Graphics graphics) {
      graphics.drawString("Occurrencce map", 50, 13);
      graphics.drawImage(VisualizationUtil.scaleImage(bufferedImage[0], scaling), 50, 20, null);
      graphics.drawString("Detected Waypoints", 670, 13);
      graphics.drawImage(VisualizationUtil.scaleImage(bufferedImage[1], scaling), 670, 20, null);
    }
  };

  public SlamVisualization(SlamConfig slamConfig) {
    double mapWidth = slamConfig.dimX.divide(slamConfig.cellDim).number().doubleValue();
    double mapHeight = slamConfig.dimY.divide(slamConfig.cellDim).number().doubleValue();
    scaling = desiredWidth / mapWidth;
    bufferedImage[0] = new BufferedImage((int) mapWidth, (int) mapHeight, BufferedImage.TYPE_BYTE_INDEXED);
    bufferedImage[1] = new BufferedImage((int) mapWidth, (int) mapHeight, BufferedImage.TYPE_BYTE_INDEXED);
    bufferedImage[2] = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_INDEXED);
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    jFrame.setContentPane(jComponent);
    jFrame.setBounds(100, 100, 1320, 700);
    jFrame.setVisible(true);
  }

  public void setFrames(BufferedImage[] bufferedImages) {
    for (int i = 0; i < bufferedImages.length; i++) {
      bufferedImage[i] = bufferedImages[i];
    }
    jComponent.repaint();
  }
}
