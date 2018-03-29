/* amodeus - Copyright (c) 2018, ETH Zurich, Institute for Dynamic Systems and Control */
package ch.ethz.idsc.amodeus.analysis.plot;

import java.awt.Color;
import java.awt.Font;

public enum DiagramSettings {
    ;

    /* package */ static final Font FONT_TITLE = new Font("Dialog", Font.BOLD, 24);
    /* package */ static final Font FONT_AXIS = new Font("Dialog", Font.BOLD, 18);
    /* package */ static final Font FONT_TICK = new Font("Dialog", Font.PLAIN, 14);
    /* package */ static final Color COLOR_BACKGROUND_PAINT = Color.WHITE;
    /* package */ static final Color COLOR_GRIDLINE_PAINT = Color.LIGHT_GRAY;

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 750;

}