package org.eclipse.internal.cpacep.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class StatusBar extends Canvas {
    private static final int DEFAULT_WIDTH = 160;
    private static final int DEFAULT_HEIGHT = 18;

    int fColorBarWidth = 0;

    public int getfColorBarWidth() {
	return fColorBarWidth;
    }

    public void setfColorBarWidth(int fColorBarWidth) {
	this.fColorBarWidth = fColorBarWidth;
	redraw();
    }

    Color fOKColor;
    Color fFailureColor;
    Color fUnknownColor;

    private boolean fSuccess;
    private boolean fError;
    private boolean fUnknown = false;

    public StatusBar(Composite parent) {
	super(parent, SWT.NONE);
	addControlListener(new ControlAdapter() {
	    @Override
	    public void controlResized(ControlEvent e) {
	    }
	});
	addPaintListener(new PaintListener() {
	    public void paintControl(PaintEvent e) {
		paint(e);
	    }
	});
	addDisposeListener(new DisposeListener() {
	    public void widgetDisposed(DisposeEvent e) {
		fFailureColor.dispose();
		fOKColor.dispose();
		fUnknownColor.dispose();
	    }
	});
	Display display = parent.getDisplay();
	fFailureColor = new Color(display, 159, 63, 63);
	fOKColor = new Color(display, 95, 191, 95);
	fUnknownColor = new Color(display, 120, 120, 120);
    }

    public void reset() {
	fError = false;
	fUnknown = false;
	fColorBarWidth = 0;
	redraw();
    }

    private void setStatusColor(GC gc) {
	if (fUnknown)
	    gc.setBackground(fUnknownColor);
	else if (fError)
	    gc.setBackground(fFailureColor);
	else
	    gc.setBackground(fOKColor);
    }

    public void stopped() {
	fUnknown = true;
	redraw();
    }

    private void drawBevelRect(GC gc, int x, int y, int w, int h, Color topleft, Color bottomright) {
	gc.setForeground(topleft);
	gc.drawLine(x, y, x + w - 1, y);
	gc.drawLine(x, y, x, y + h - 1);

	gc.setForeground(bottomright);
	gc.drawLine(x + w, y, x + w, y + h);
	gc.drawLine(x, y + h, x + w, y + h);
    }

    void paint(PaintEvent event) {
	GC gc = event.gc;
	Display disp = getDisplay();

	Rectangle rect = getClientArea();
	gc.fillRectangle(rect);
	drawBevelRect(gc, rect.x, rect.y, rect.width - 1, rect.height - 1,
		disp.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW),
		disp.getSystemColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));

	setStatusColor(gc);
	fColorBarWidth = Math.min(rect.width - 2, fColorBarWidth);
	gc.fillRectangle(1, 1, fColorBarWidth, rect.height - 2);
    }

    @Override
    public Point computeSize(int wHint, int hHint, boolean changed) {
	checkWidget();
	Point size = new Point(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	if (wHint != SWT.DEFAULT)
	    size.x = wHint;
	if (hHint != SWT.DEFAULT)
	    size.y = hHint;
	return size;
    }

    public void refresh(boolean hasErrors) {
	fError = hasErrors;
	redraw();
    }

    public void setStatus(String status, Display display) {
	if (status.equals("TRUE")) {
	    success(display);
	} else if (status.equals("FALSE")) {
	    failure(display);
	} else if (status.equals("UNKNOWN")) {
	    unknown(display);
	}else {
	    clear(display);
	}
    }

    public void success(Display display) {
	this.setBackground(new Color(display, 0, 153, 0));
    }

    public void failure(Display display) {
	this.setBackground(new Color(display, 255, 0, 0));
    }

    public void unknown(Display display) {
	this.setBackground(new Color(display, 255, 254, 0));
    }
    
    public void clear(Display display) {
	this.setBackground(new Color(display, 255,255,255));
    }

}
