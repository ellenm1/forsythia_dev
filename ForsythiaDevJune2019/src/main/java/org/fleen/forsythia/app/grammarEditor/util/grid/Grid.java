/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.util.grid;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.PrintStream;
import java.io.Serializable;
import javax.swing.JPanel;
import org.fleen.forsythia.app.grammarEditor.util.grid.CursorCircle;
import org.fleen.forsythia.app.grammarEditor.util.grid.CursorSquare;
import org.fleen.forsythia.app.grammarEditor.util.grid.CursorX;
import org.fleen.forsythia.app.grammarEditor.util.grid.GeometryCache;
import org.fleen.forsythia.app.grammarEditor.util.grid.GridRenderer;
import org.fleen.forsythia.app.grammarEditor.util.grid.GridViewDef;
import org.fleen.forsythia.app.grammarEditor.util.grid.UIGridUtil;
import org.fleen.geom_Kisrhombille.GK;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;

public abstract class Grid
extends JPanel {
    private static final long serialVersionUID = -9047391457848367846L;
    public GridRenderer gridrenderer = new GridRenderer();
    static final int CURSORMODE_AMBIGUOUS = -1;
    static final int CURSORMODE_CIRCLE = 0;
    static final int CURSORMODE_SQUARE = 1;
    static final int CURSORMODE_X = 2;
    private int cursormode = -1;
    private static final double VIEWSCALE_DEFAULT = 64.0;
    private static final double VIEWSCALE_MIN = 8.0;
    private static final double VIEWCENTERX_DEFAULT = 0.0;
    private static final double VIEWCENTERY_DEFAULT = 0.0;
    private Point2D.Double ptemp0 = new Point2D.Double();
    public double viewscale = 64.0;
    public double viewcenterx = 0.0;
    public double viewcentery = 0.0;
    private static final int MINDRAGDIST = 10;
    private int mousedownx;
    private int mousedowny;
    private static final long MOUSEMOVESAMPLEPERIOD = 50L;
    private long lastsample = -1L;
    private double[] latestsamplepoint;
    private KPoint latestsamplevertex;
    GeometryCache geometrycache = null;

    public Grid() {
        this.setDoubleBuffered(true);
        this.addMouseListener(new ML0());
        this.addMouseMotionListener(new ML0());
        this.setFocusable(true);
        this.initGeometryCache();
    }

    public void setCursorCircle() {
        if (this.cursormode != 0) {
            this.cursormode = 0;
            CursorCircle.setCursor(this);
        }
    }

    public void setCursorSquare() {
        if (this.cursormode != 1) {
            this.cursormode = 1;
            CursorSquare.setCursor(this);
        }
    }

    public void setCursorX() {
        if (this.cursormode != 2) {
            this.cursormode = 2;
            CursorX.setCursor(this);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int w = this.getWidth();
        int h = this.getHeight();
        if (w <= 0 || h <= 0) {
            return;
        }
        BufferedImage i = new BufferedImage(w, h, 2);
        Graphics2D g2 = i.createGraphics();
        try {
            this.gridrenderer.render(g2, w, h, this.viewscale, this.viewcenterx, this.viewcentery);
            this.paintOverlay(g2, w, h, this.viewscale, this.viewcenterx, this.viewcentery);
            ((Graphics2D)g).drawImage(i, null, null);
        }
        catch (Exception x) {
            System.out.println("grid paint failed");
            x.printStackTrace();
        }
    }

    public void paintOverlay(Graphics2D g, GridViewDef d) {
        this.paintOverlay(g, d.w, d.h, d.scale, d.centerx, d.centery);
    }

    protected abstract void paintOverlay(Graphics2D var1, int var2, int var3, double var4, double var6, double var8);

    public void setViewScaleDefault() {
        this.gridrenderer.invalidateTileImage();
        this.viewscale = 64.0;
    }

    public void setViewCenterDefault() {
        this.viewcenterx = 0.0;
        this.viewcentery = 0.0;
    }

    public void setTransformDefaults() {
        this.setViewScaleDefault();
        this.setViewCenterDefault();
    }

    private void adjustViewScale(double factor) {
        this.clearGeometryCache();
        this.gridrenderer.invalidateTileImage();
        double vsm = this.getViewScaleMax();
        this.viewscale *= factor;
        if (this.viewscale < 8.0) {
            this.viewscale = 8.0;
        }
        if (this.viewscale > vsm) {
            this.viewscale = vsm;
        }
    }

    private void adjustViewCenter(double dx, double dy) {
        this.clearGeometryCache();
        this.viewcenterx += dx;
        this.viewcentery += dy;
    }

    private double getViewScaleMax() {
        return Math.max(this.getWidth(), this.getHeight());
    }

    public double[] getViewPoint(double x, double y) {
        this.ptemp0.setLocation(x, y);
        this.getGridToViewTransform().transform(this.ptemp0, this.ptemp0);
        return new double[]{this.ptemp0.x, this.ptemp0.y};
    }

    public AffineTransform getGridToViewTransform() {
        AffineTransform t = new AffineTransform();
        t.translate(this.getWidth() / 2, this.getHeight() / 2);
        t.scale(this.viewscale, - this.viewscale);
        t.translate(- this.viewcenterx, - this.viewcentery);
        return t;
    }

    public double[] getGridPoint(double x, double y) {
        this.ptemp0.setLocation(x, y);
        this.getViewToGridTransform().transform(this.ptemp0, this.ptemp0);
        return new double[]{this.ptemp0.x, this.ptemp0.y};
    }

    public AffineTransform getViewToGridTransform() {
        AffineTransform t = null;
        try {
            t = this.getGridToViewTransform().createInverse();
        }
        catch (Exception x) {
            x.printStackTrace();
        }
        return t;
    }

    public void centerAndFit() {
        double vsm;
        double whratiopbounds;
        double viewheight;
        this.gridrenderer.invalidateTileImage();
        KPolygon polygon = this.getHostPolygon();
        if (polygon == null || polygon.isEmpty()) {
            this.setTransformDefaults();
            return;
        }
        Rectangle2D.Double polygonbounds = UIGridUtil.getPolygonBounds2D(polygon);
        double viewwidth = this.getWidth();
        double whratioview = viewwidth / (viewheight = (double)this.getHeight());
        this.viewscale = whratioview > (whratiopbounds = polygonbounds.width / polygonbounds.height) ? (viewheight - 72.0) / polygonbounds.height : (viewwidth - 72.0) / polygonbounds.width;
        if (this.viewscale < 8.0) {
            this.viewscale = 8.0;
        }
        if (this.viewscale > (vsm = this.getViewScaleMax())) {
            this.viewscale = vsm;
        }
        this.viewcenterx = polygonbounds.getCenterX();
        this.viewcentery = polygonbounds.getCenterY();
    }

    protected abstract KPolygon getHostPolygon();

    private void doMouseTouch(int x, int y) {
        this.mouseTouched(this.getViewPoint(this.latestsamplepoint[0], this.latestsamplepoint[1]), this.latestsamplevertex);
    }

    private void doMouseZoom(double dy) {
        double z = 1.0 + dy / (double)this.getHeight();
        this.adjustViewScale(z * z);
        this.repaint();
    }

    private void doMousePan(int dx, int dy) {
        this.adjustViewCenter((double)(- dx) / this.viewscale, (double)dy / this.viewscale);
        this.repaint();
    }

    public double[] getLatestSamplePoint() {
        return this.latestsamplepoint;
    }

    public KPoint getLatestSampleVertex() {
        return this.latestsamplevertex;
    }

    private void doConditionalSampleForMouseMoved(int x, int y) {
        long t = System.currentTimeMillis();
        if (t - this.lastsample < 50L) {
            return;
        }
        this.lastsample = t;
        this.latestsamplepoint = this.getGridPoint(x, y);
        this.latestsamplevertex = GK.getStandardVertex(this.latestsamplepoint[0], this.latestsamplepoint[1], 22.0 / this.viewscale);
        if (this.latestsamplevertex == null) {
            this.mouseMovedFarFromVertex(this.latestsamplepoint);
        } else {
            this.mouseMovedCloseToVertex(this.latestsamplevertex);
        }
    }

    protected abstract void mouseTouched(double[] var1, KPoint var2);

    protected abstract void mouseMovedCloseToVertex(KPoint var1);

    protected abstract void mouseMovedFarFromVertex(double[] var1);

    private void initGeometryCache() {
        this.geometrycache = new GeometryCache(this);
    }

    public void clearGeometryCache() {
        this.geometrycache.clear();
    }

    public GeometryCache getGeometryCache() {
        return this.geometrycache;
    }

    static /* synthetic */ void access$0(Grid grid, int n) {
        grid.mousedownx = n;
    }

    static /* synthetic */ void access$1(Grid grid, int n) {
        grid.mousedowny = n;
    }

    private class ML0
    extends MouseAdapter
    implements Serializable {
        private static final long serialVersionUID = 4153888926854789467L;

        private ML0() {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            Grid.this.requestFocusInWindow();
            Grid.access$0(Grid.this, e.getX());
            Grid.access$1(Grid.this, e.getY());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int b = e.getButton();
            int x = e.getX();
            int y = e.getY();
            if (b == 1 && !e.isConsumed()) {
                if (e.getClickCount() == 2) {
                    e.consume();
                    Grid.this.centerAndFit();
                    Grid.this.repaint();
                    return;
                }
                int dx = x - Grid.this.mousedownx;
                int dy = y - Grid.this.mousedowny;
                boolean shift = e.isShiftDown();
                if (Math.abs(dx) < 10 && Math.abs(dy) < 10 && !shift) {
                    Grid.this.doMouseTouch(x, y);
                } else if (shift) {
                    Grid.this.doMouseZoom(dy);
                } else {
                    Grid.this.doMousePan(dx, dy);
                }
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            Grid.this.doConditionalSampleForMouseMoved(e.getX(), e.getY());
        }
    }

}

