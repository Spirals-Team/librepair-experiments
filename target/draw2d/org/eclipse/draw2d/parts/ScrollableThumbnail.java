package org.eclipse.draw2d.parts;


public final class ScrollableThumbnail extends org.eclipse.draw2d.parts.Thumbnail {
    private class ClickScrollerAndDragTransferrer extends org.eclipse.draw2d.MouseMotionListener.Stub implements org.eclipse.draw2d.MouseListener {
        private boolean dragTransfer;

        public void mouseDoubleClicked(org.eclipse.draw2d.MouseEvent me) {
        }

        public void mouseDragged(org.eclipse.draw2d.MouseEvent me) {
            if (dragTransfer)
                syncher.mouseDragged(me);

        }

        public void mousePressed(org.eclipse.draw2d.MouseEvent me) {
            if (!(org.eclipse.draw2d.parts.ScrollableThumbnail.this.getClientArea().contains(me.getLocation())))
                return;

            org.eclipse.draw2d.geometry.Dimension selectorCenter = selector.getBounds().getSize().scale(0.5F);
            org.eclipse.draw2d.geometry.Point scrollPoint = me.getLocation().getTranslated(getLocation().getNegated()).translate(selectorCenter.negate()).scale((1.0F / (getViewportScaleX())), (1.0F / (getViewportScaleY()))).translate(viewport.getHorizontalRangeModel().getMinimum(), viewport.getVerticalRangeModel().getMinimum());
            viewport.setViewLocation(scrollPoint);
            syncher.mousePressed(me);
            dragTransfer = true;
        }

        public void mouseReleased(org.eclipse.draw2d.MouseEvent me) {
            syncher.mouseReleased(me);
            dragTransfer = false;
        }
    }

    private class ScrollSynchronizer extends org.eclipse.draw2d.MouseMotionListener.Stub implements org.eclipse.draw2d.MouseListener {
        private org.eclipse.draw2d.geometry.Point startLocation;

        private org.eclipse.draw2d.geometry.Point viewLocation;

        public void mouseDoubleClicked(org.eclipse.draw2d.MouseEvent me) {
        }

        public void mouseDragged(org.eclipse.draw2d.MouseEvent me) {
            if ((startLocation) != null) {
                org.eclipse.draw2d.geometry.Dimension d = me.getLocation().getDifference(startLocation);
                d.scale((1.0F / (getViewportScaleX())), (1.0F / (getViewportScaleY())));
                viewport.setViewLocation(viewLocation.getTranslated(d));
                me.consume();
            }
        }

        public void mousePressed(org.eclipse.draw2d.MouseEvent me) {
            startLocation = me.getLocation();
            viewLocation = viewport.getViewLocation();
            me.consume();
        }

        public void mouseReleased(org.eclipse.draw2d.MouseEvent me) {
        }
    }

    private class SelectorFigure extends org.eclipse.draw2d.Figure {
        {
            org.eclipse.swt.widgets.Display display = org.eclipse.swt.widgets.Display.getCurrent();
            org.eclipse.swt.graphics.PaletteData pData = new org.eclipse.swt.graphics.PaletteData(255, 65280, 16711680);
            org.eclipse.swt.graphics.RGB rgb = ColorConstants.menuBackgroundSelected.getRGB();
            int fillColor = pData.getPixel(rgb);
            org.eclipse.swt.graphics.ImageData iData = new org.eclipse.swt.graphics.ImageData(1, 1, 24, pData);
            iData.setPixel(0, 0, fillColor);
            iData.setAlpha(0, 0, 55);
            image = new org.eclipse.swt.graphics.Image(display, iData);
        }

        private org.eclipse.draw2d.geometry.Rectangle iBounds = new org.eclipse.draw2d.geometry.Rectangle(0, 0, 1, 1);

        private org.eclipse.swt.graphics.Image image;

        protected void dispose() {
            image.dispose();
        }

        public void paintFigure(org.eclipse.draw2d.Graphics g) {
            org.eclipse.draw2d.geometry.Rectangle bounds = getBounds().getCopy();
            if (((bounds.width) < 5) || ((bounds.height) < 5))
                return;

            org.eclipse.draw2d.geometry.Dimension thumbnailSize = new org.eclipse.draw2d.geometry.Dimension(getThumbnailImage());
            org.eclipse.draw2d.geometry.Dimension size = getSize().getExpanded(1, 1);
            if (size.contains(thumbnailSize))
                return;

            (bounds.height)--;
            (bounds.width)--;
            g.drawImage(image, iBounds, bounds);
            g.setForegroundColor(ColorConstants.menuBackgroundSelected);
            g.drawRectangle(bounds);
        }
    }

    private org.eclipse.draw2d.FigureListener figureListener = new org.eclipse.draw2d.FigureListener() {
        public void figureMoved(org.eclipse.draw2d.IFigure source) {
            reconfigureSelectorBounds();
        }
    };

    private org.eclipse.draw2d.KeyListener keyListener = new org.eclipse.draw2d.KeyListener.Stub() {
        public void keyPressed(org.eclipse.draw2d.KeyEvent ke) {
            int moveX = (viewport.getClientArea().width) / 4;
            int moveY = (viewport.getClientArea().height) / 4;
            if (((ke.keycode) == (org.eclipse.swt.SWT.HOME)) || (isMirrored() ? (ke.keycode) == (org.eclipse.swt.SWT.ARROW_RIGHT) : (ke.keycode) == (org.eclipse.swt.SWT.ARROW_LEFT)))
                viewport.setViewLocation(viewport.getViewLocation().translate((-moveX), 0));
            else
                if (((ke.keycode) == (org.eclipse.swt.SWT.END)) || (isMirrored() ? (ke.keycode) == (org.eclipse.swt.SWT.ARROW_LEFT) : (ke.keycode) == (org.eclipse.swt.SWT.ARROW_RIGHT)))
                    viewport.setViewLocation(viewport.getViewLocation().translate(moveX, 0));
                else
                    if (((ke.keycode) == (org.eclipse.swt.SWT.ARROW_UP)) || ((ke.keycode) == (org.eclipse.swt.SWT.PAGE_UP)))
                        viewport.setViewLocation(viewport.getViewLocation().translate(0, (-moveY)));
                    else
                        if (((ke.keycode) == (org.eclipse.swt.SWT.ARROW_DOWN)) || ((ke.keycode) == (org.eclipse.swt.SWT.PAGE_DOWN)))
                            viewport.setViewLocation(viewport.getViewLocation().translate(0, moveY));




        }
    };

    private java.beans.PropertyChangeListener propListener = new java.beans.PropertyChangeListener() {
        public void propertyChange(java.beans.PropertyChangeEvent evt) {
            reconfigureSelectorBounds();
        }
    };

    private org.eclipse.draw2d.parts.ScrollableThumbnail.SelectorFigure selector;

    private org.eclipse.draw2d.parts.ScrollableThumbnail.ScrollSynchronizer syncher;

    private org.eclipse.draw2d.Viewport viewport;

    public ScrollableThumbnail() {
        super();
        initialize();
    }

    public ScrollableThumbnail(org.eclipse.draw2d.Viewport port) {
        super();
        setViewport(port);
        initialize();
    }

    public void deactivate() {
        viewport.removePropertyChangeListener(Viewport.PROPERTY_VIEW_LOCATION, propListener);
        viewport.removeFigureListener(figureListener);
        remove(selector);
        selector.dispose();
        deactivate();
    }

    private double getViewportScaleX() {
        return ((double) (targetSize.width)) / (viewport.getContents().getBounds().width);
    }

    private double getViewportScaleY() {
        return ((double) (targetSize.height)) / (viewport.getContents().getBounds().height);
    }

    private void initialize() {
        selector = new org.eclipse.draw2d.parts.ScrollableThumbnail.SelectorFigure();
        selector.addMouseListener((syncher = new org.eclipse.draw2d.parts.ScrollableThumbnail.ScrollSynchronizer()));
        selector.addMouseMotionListener(syncher);
        selector.setFocusTraversable(true);
        selector.addKeyListener(keyListener);
        add(selector);
        org.eclipse.draw2d.parts.ScrollableThumbnail.ClickScrollerAndDragTransferrer transferrer = new org.eclipse.draw2d.parts.ScrollableThumbnail.ClickScrollerAndDragTransferrer();
        addMouseListener(transferrer);
        addMouseMotionListener(transferrer);
    }

    private void reconfigureSelectorBounds() {
        org.eclipse.draw2d.geometry.Rectangle rect = new org.eclipse.draw2d.geometry.Rectangle();
        org.eclipse.draw2d.geometry.Point offset = viewport.getViewLocation();
        offset.x -= viewport.getHorizontalRangeModel().getMinimum();
        offset.y -= viewport.getVerticalRangeModel().getMinimum();
        rect.setLocation(offset);
        rect.setSize(viewport.getClientArea().getSize());
        rect.scale(getViewportScaleX(), getViewportScaleY());
        rect.translate(getClientArea().getLocation());
        selector.setBounds(rect);
    }

    protected void setScales(float scaleX, float scaleY) {
        if ((scaleX == (getScaleX())) && (scaleY == (getScaleY())))
            return;

        setScales(scaleX, scaleY);
        reconfigureSelectorBounds();
    }

    public void setViewport(org.eclipse.draw2d.Viewport port) {
        port.addPropertyChangeListener(Viewport.PROPERTY_VIEW_LOCATION, propListener);
        port.addFigureListener(figureListener);
        viewport = port;
    }
}

