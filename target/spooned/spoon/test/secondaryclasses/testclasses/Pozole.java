package spoon.test.secondaryclasses.testclasses;


public class Pozole {
    private static final java.lang.Object CONFLICT_HOOK = new javax.swing.JFrame(new java.awt.GraphicsConfiguration() {
        @java.lang.Override
        public java.awt.GraphicsDevice getDevice() {
            return null;
        }

        @java.lang.Override
        public java.awt.image.ColorModel getColorModel() {
            return null;
        }

        @java.lang.Override
        public java.awt.image.ColorModel getColorModel(int i) {
            return new java.awt.image.ColorModel(i) {
                @java.lang.Override
                public int getRed(int i) {
                    return i;
                }

                @java.lang.Override
                public int getGreen(int i) {
                    return 0;
                }

                @java.lang.Override
                public int getBlue(int i) {
                    return 0;
                }

                @java.lang.Override
                public int getAlpha(int i) {
                    return 0;
                }
            };
        }

        @java.lang.Override
        public java.awt.geom.AffineTransform getDefaultTransform() {
            return null;
        }

        @java.lang.Override
        public java.awt.geom.AffineTransform getNormalizingTransform() {
            return null;
        }

        @java.lang.Override
        public java.awt.Rectangle getBounds() {
            return null;
        }
    });
}

