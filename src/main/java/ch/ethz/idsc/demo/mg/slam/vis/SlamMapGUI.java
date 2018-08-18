// code by mg
package ch.ethz.idsc.demo.mg.slam.vis;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import ch.ethz.idsc.demo.mg.slam.SlamConfig;
import ch.ethz.idsc.retina.util.img.BufferedImageResize;

/** GUI to display SLAM algorithm */
/* package */ class SlamMapGUI {
  private final JFrame jFrame = new JFrame();
  private final BufferedImage[] bufferedImage = new BufferedImage[3];
  private final double scaling;
  private final int frameWidth;
  private final JComponent jComponent = new JComponent() {
    @Override
    protected void paintComponent(Graphics graphics) {
      graphics.drawString("Occurrencce map", 50, 13);
      graphics.drawImage(BufferedImageResize.of(bufferedImage[0], scaling), 50, 20, null);
      graphics.drawString("Detected Waypoints", 670, 13);
      graphics.drawImage(BufferedImageResize.of(bufferedImage[1], scaling), 670, 20, null);
    }
  };

  SlamMapGUI(SlamConfig slamConfig) {
    frameWidth = slamConfig.frameWidth.number().intValue();
    int mapWidth = slamConfig.mapWidth();
    int mapHeight = slamConfig.mapHeight();
    scaling = frameWidth / (double) mapWidth;
    bufferedImage[0] = new BufferedImage(mapWidth, mapHeight, BufferedImage.TYPE_BYTE_INDEXED);
    bufferedImage[1] = new BufferedImage(mapWidth, mapHeight, BufferedImage.TYPE_BYTE_INDEXED);
    bufferedImage[2] = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_INDEXED);
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    jFrame.setContentPane(jComponent);
    jFrame.setBounds(100, 100, 1320, 700);
    jFrame.setVisible(true);
  }

  public void setFrames(BufferedImage[] bufferedImages) {
    for (int i = 0; i < bufferedImages.length; i++)
      bufferedImage[i] = bufferedImages[i];
    jComponent.repaint();
  }
}
