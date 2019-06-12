/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_Kisrhombille.GK;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.KSeg;
import org.fleen.geom_Kisrhombille.app.docGraphics.DGUI;
import org.fleen.geom_Kisrhombille.app.docGraphics.GridHexagon;
import org.fleen.geom_Kisrhombille.app.docGraphics.GridTriangle;

abstract class DocGraphics {
    static final Color BLACK = new Color(0, 0, 0);
    static final Color GREY0 = new Color(32, 32, 32);
    static final Color GREY1 = new Color(64, 64, 64);
    static final Color GREY2 = new Color(96, 96, 96);
    static final Color GREY3 = new Color(128, 128, 128);
    static final Color GREY4 = new Color(160, 160, 160);
    static final Color GREY5 = new Color(192, 192, 192);
    static final Color GREY6 = new Color(224, 224, 224);
    static final Color WHITE = new Color(255, 255, 255);
    static final Color GREEN = new Color(39, 184, 111);
    static final Color BLUE = new Color(36, 140, 192);
    static final Color YELLOW = new Color(223, 159, 2);
    static final Color ORANGE = new Color(225, 75, 0);
    static final Color RED = new Color(192, 19, 21);
    static final Color PURPLE = new Color(178, 67, 133);
    static final double DOTSPAN0 = 8.0;
    static final double DOTSPAN1 = 12.0;
    static final double DOTSPAN2 = 16.0;
    static final float STROKETHICKNESS0 = 2.0f;
    static final float STROKETHICKNESS1 = 3.0f;
    static final float STROKETHICKNESS2 = 4.0f;
    static final float STROKETHICKNESS3 = 6.0f;
    static final double IMAGESCALE0 = 4.0;
    static final double IMAGESCALE1 = 20.0;
    static final double IMAGESCALE2 = 30.0;
    static final double IMAGESCALE3 = 60.0;
    static final double IMAGESCALE4 = 180.0;
    static final int IMAGEWIDTH0 = 96;
    static final int IMAGEHEIGHT0 = 96;
    static final int IMAGEWIDTH1 = 512;
    static final int IMAGEHEIGHT1 = 512;
    static final int IMAGEWIDTH2 = 1000;
    static final int IMAGEHEIGHT2 = 2000;
    static final int IMAGEWIDTH3 = 1600;
    static final int IMAGEHEIGHT3 = 800;
    static final int IMAGEWIDTH4 = 768;
    static final int IMAGEHEIGHT4 = 256;
    static final int IMAGEWIDTH5 = 768;
    static final int IMAGEHEIGHT5 = 512;
    static final int INNEROUTERPOLYGONOFFSET0 = 48;
    private DGUI ui;
    public static final HashMap<RenderingHints.Key, Object> RENDERING_HINTS = new HashMap();
    double imagescale;
    int imagewidth;
    int imageheight;
    BufferedImage image;
    Graphics2D graphics;

    static {
        RENDERING_HINTS.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        RENDERING_HINTS.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        RENDERING_HINTS.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DEFAULT);
        RENDERING_HINTS.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        RENDERING_HINTS.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        RENDERING_HINTS.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        RENDERING_HINTS.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
    }

    DocGraphics() {
        this.init();
    }

    void init() {
        this.initUI();
        this.doGraphics();
        this.ui.repaint();
    }

    void regenerate() {
        this.doGraphics();
        this.ui.repaint();
    }

    abstract void doGraphics();

    List<KSeg> getSegs(int count, int v0range, int lengthrange) {
        ArrayList<KSeg> segs = new ArrayList<KSeg>();
        for (int i = 0; i < count; ++i) {
            KSeg s = this.getRandomSeg(v0range, lengthrange);
            segs.add(s);
        }
        return segs;
    }

    boolean segsIntersect(List<KSeg> segs) {
        for (KSeg s0 : segs) {
            for (KSeg s1 : segs) {
                if (s0.equals(s1) || !s0.intersects(s1)) continue;
                return true;
            }
        }
        return false;
    }

    void renderPolygon(KPolygon polygon, double strokethickness, double dotspan, Color color) {
        int s = polygon.size();
        for (int i0 = 0; i0 < s; ++i0) {
            int i1 = i0 + 1;
            if (i1 == s) {
                i1 = 0;
            }
            KPoint p0 = (KPoint)polygon.get(i0);
            this.renderPoint(p0, dotspan, color);
            KPoint p1 = (KPoint)polygon.get(i1);
            this.strokeSeg(p0, p1, strokethickness, color);
        }
    }

    void strokePolygon(KPolygon polygon, double strokethickness, Color color) {
        DPolygon p = polygon.getDefaultPolygon2D();
        this.strokePolygon(p, strokethickness, color);
    }

    void strokePolygon(DPolygon polygon, double strokethickness, Color color) {
        Path2D.Double path = new Path2D.Double();
        int s = polygon.size();
        DPoint p = (DPoint)polygon.get(0);
        ((Path2D)path).moveTo(p.x, p.y);
        for (int i = 1; i < s; ++i) {
            p = (DPoint)polygon.get(i);
            ((Path2D)path).lineTo(p.x, p.y);
        }
        path.closePath();
        this.graphics.setStroke(this.createStroke(strokethickness));
        this.graphics.setPaint(color);
        this.graphics.draw(path);
    }

    void fillPolygon(DPolygon polygon, Color c) {
        Path2D.Double path = new Path2D.Double();
        int s = polygon.size();
        DPoint p = (DPoint)polygon.get(0);
        ((Path2D)path).moveTo(p.x, p.y);
        for (int i = 1; i < s; ++i) {
            p = (DPoint)polygon.get(i);
            ((Path2D)path).lineTo(p.x, p.y);
        }
        path.closePath();
        this.graphics.setPaint(c);
        this.graphics.fill(path);
    }

    void fillPolygon(KPolygon polygon, Color c) {
        Path2D.Double path = new Path2D.Double();
        int s = polygon.size();
        DPoint p = ((KPoint)polygon.get(0)).getBasicPoint2D();
        ((Path2D)path).moveTo(p.x, p.y);
        for (int i = 1; i < s; ++i) {
            p = ((KPoint)polygon.get(i)).getBasicPoint2D();
            ((Path2D)path).lineTo(p.x, p.y);
        }
        path.closePath();
        this.graphics.setPaint(c);
        this.graphics.fill(path);
    }

    KPoint getRandomPoint(int range) {
        Random r = new Random();
        boolean valid = false;
        int a = 0;
        int b = 0;
        int c = 0;
        int count = 0;
        while (!valid) {
            if (++count == 1000) {
                throw new IllegalArgumentException("infinite loop");
            }
            a = r.nextInt(range * 2) - range;
            b = r.nextInt(range * 2) - range;
            c = r.nextInt(range * 2) - range;
            boolean bl = valid = c == b - a;
        }
        int d = r.nextInt(6);
        return new KPoint(a, b, c, d);
    }

    KSeg getRandomSeg(int v0range, int lengthrange) {
        KPoint v0 = this.getRandomPoint(v0range);
        int[] b = GK.getLiberties(v0.getDog());
        Random r = new Random();
        int dir = b[r.nextInt(b.length)];
        int length = r.nextInt(lengthrange) + 1;
        KPoint v1 = GK.getVertex_Transitionswise(v0, dir, length);
        return new KSeg(v0, v1);
    }

    Stroke createStroke(double thickness) {
        BasicStroke s = new BasicStroke((float)(thickness / this.imagescale), 2, 1, 0.0f, null, 0.0f);
        return s;
    }

    void strokeSeg(KPoint v0, KPoint v1, double thickness, Color color) {
        DPoint p0 = v0.getBasicPoint2D();
        DPoint p1 = v1.getBasicPoint2D();
        this.strokeSeg(p0, p1, thickness, color);
    }

    void strokeSeg(DPoint p0, DPoint p1, double thickness, Color color) {
        Path2D.Double path = new Path2D.Double();
        ((Path2D)path).moveTo(p0.x, p0.y);
        ((Path2D)path).lineTo(p1.x, p1.y);
        this.graphics.setStroke(this.createStroke(thickness));
        this.graphics.setPaint(color);
        this.graphics.draw(path);
    }

    void renderSeg(KPoint v0, KPoint v1, double strokethickness, double dotspan, Color color) {
        this.strokeSeg(v0, v1, strokethickness, color);
        this.renderPoint(v0, dotspan, color);
        this.renderPoint(v1, dotspan, color);
    }

    void renderSeg(KSeg s, double strokethickness, double dotspan, Color color) {
        this.renderSeg(s.getVertex0(), s.getVertex1(), strokethickness, dotspan, color);
    }

    void strokeClock(KPoint v, double thickness, Color color) {
        KPoint[] cp = this.getClockKPoints(v);
        for (int i = 1; i < cp.length; ++i) {
            int j = i + 1;
            if (j == cp.length) {
                j = 1;
            }
            this.strokeSeg(cp[i], cp[j], thickness, color);
            this.strokeSeg(cp[0], cp[i], thickness, color);
        }
    }

    Set<KPoint> strokeGrid(int range, double thickness, Color color) {
        HashSet<KPoint> points = new HashSet<KPoint>();
        Set<KPoint> v0s = this.getV0s(range);
        for (KPoint p : v0s) {
            points.addAll(Arrays.asList(this.getClockKPoints(p)));
            this.strokeClock(p, thickness, color);
        }
        return points;
    }

    Set<KPoint> getV0s(int range) {
        HashSet<KPoint> points = new HashSet<KPoint>();
        for (int ant = - range; ant < range; ++ant) {
            for (int bat = - range; bat < range; ++bat) {
                for (int cat = - range; cat < range; ++cat) {
                    boolean valid;
                    boolean bl = valid = cat == bat - ant;
                    if (!valid) continue;
                    KPoint p = new KPoint(ant, bat, cat, 0);
                    points.add(p);
                }
            }
        }
        return points;
    }

    List<GridTriangle> getGridTriangles(int range) {
        ArrayList<GridTriangle> triangles = new ArrayList<GridTriangle>();
        Set<KPoint> v0s = this.getV0s(range);
        for (KPoint p : v0s) {
            KPoint[] cp = this.getClockKPoints(p);
            triangles.add(new GridTriangle(cp[0].getBasicPoint2D(), cp[1].getBasicPoint2D(), cp[2].getBasicPoint2D(), 0));
            triangles.add(new GridTriangle(cp[0].getBasicPoint2D(), cp[2].getBasicPoint2D(), cp[3].getBasicPoint2D(), 1));
            triangles.add(new GridTriangle(cp[0].getBasicPoint2D(), cp[3].getBasicPoint2D(), cp[4].getBasicPoint2D(), 2));
            triangles.add(new GridTriangle(cp[0].getBasicPoint2D(), cp[4].getBasicPoint2D(), cp[5].getBasicPoint2D(), 3));
            triangles.add(new GridTriangle(cp[0].getBasicPoint2D(), cp[5].getBasicPoint2D(), cp[6].getBasicPoint2D(), 4));
            triangles.add(new GridTriangle(cp[0].getBasicPoint2D(), cp[6].getBasicPoint2D(), cp[7].getBasicPoint2D(), 5));
            triangles.add(new GridTriangle(cp[0].getBasicPoint2D(), cp[7].getBasicPoint2D(), cp[8].getBasicPoint2D(), 6));
            triangles.add(new GridTriangle(cp[0].getBasicPoint2D(), cp[8].getBasicPoint2D(), cp[9].getBasicPoint2D(), 7));
            triangles.add(new GridTriangle(cp[0].getBasicPoint2D(), cp[9].getBasicPoint2D(), cp[10].getBasicPoint2D(), 8));
            triangles.add(new GridTriangle(cp[0].getBasicPoint2D(), cp[10].getBasicPoint2D(), cp[11].getBasicPoint2D(), 9));
            triangles.add(new GridTriangle(cp[0].getBasicPoint2D(), cp[11].getBasicPoint2D(), cp[12].getBasicPoint2D(), 10));
            triangles.add(new GridTriangle(cp[0].getBasicPoint2D(), cp[12].getBasicPoint2D(), cp[1].getBasicPoint2D(), 11));
        }
        return triangles;
    }

    List<GridHexagon> getGridHexagons(int range) {
        ArrayList<GridHexagon> hexagons = new ArrayList<GridHexagon>();
        Set<KPoint> v0s = this.getV0s(range);
        for (KPoint p : v0s) {
            KPoint[] cp = this.getClockKPoints(p);
            hexagons.add(new GridHexagon(cp[1].getBasicPoint2D(), cp[3].getBasicPoint2D(), cp[5].getBasicPoint2D(), cp[7].getBasicPoint2D(), cp[9].getBasicPoint2D(), cp[11].getBasicPoint2D(), p));
        }
        return hexagons;
    }

    KPoint[] getClockKPoints(KPoint v) {
        int a = v.getAnt();
        int b = v.getBat();
        int c = v.getCat();
        KPoint[] w = new KPoint[]{new KPoint(a, b, c, 0), new KPoint(a, b, c, 2), new KPoint(a, b, c, 3), new KPoint(a, b, c, 4), new KPoint(a, b, c, 5), new KPoint(a + 1, b, c - 1, 2), new KPoint(a + 1, b, c - 1, 1), new KPoint(a, b - 1, c - 1, 4), new KPoint(a, b - 1, c - 1, 3), new KPoint(a, b - 1, c - 1, 2), new KPoint(a - 1, b - 1, c, 5), new KPoint(a - 1, b - 1, c, 4), new KPoint(a, b, c, 1)};
        return w;
    }

    void renderPoint(KPoint v, double dotspan, Color color) {
        DPoint p = v.getBasicPoint2D();
        this.renderPoint(p, dotspan, color);
    }

    void renderPoint(DPoint p, double dotspan, Color color) {
        Ellipse2D.Double dot = new Ellipse2D.Double(p.x - (dotspan /= this.imagescale) / 2.0, p.y - dotspan / 2.0, dotspan, dotspan);
        this.graphics.setPaint(color);
        this.graphics.fill(dot);
    }

    void renderPointCoors(KPoint v, int fontsize, Color color) {
        DPoint p = v.getBasicPoint2D();
        AffineTransform graphicstransform = this.graphics.getTransform();
        double[] pt = new double[]{p.x, p.y};
        graphicstransform.transform(pt, 0, pt, 0, 1);
        this.graphics.setTransform(new AffineTransform());
        this.graphics.setPaint(color);
        this.graphics.setFont(new Font("Sans", 0, fontsize));
        String z = String.valueOf(v.getAnt()) + "," + v.getBat() + ",";
        this.graphics.drawString(z, (float)(pt[0] - 11.0), (float)(pt[1] + 1.0));
        z = String.valueOf(v.getCat()) + "," + v.getDog();
        this.graphics.drawString(z, (float)(pt[0] - 11.0), (float)(pt[1] + 9.0));
        this.graphics.setTransform(graphicstransform);
    }

    private void initUI() {
        this.ui = new DGUI(this);
        this.ui.setBackground(Color.black);
    }

    void export() {
    }

    void initImage(int width, int height, double scale, Color fill) {
        this.imagewidth = width;
        this.imageheight = height;
        this.imagescale = scale;
        this.image = new BufferedImage(this.imagewidth, this.imageheight, 2);
        this.graphics = this.image.createGraphics();
        this.graphics.setRenderingHints(RENDERING_HINTS);
        this.graphics.setPaint(fill);
        this.graphics.fillRect(0, 0, this.imagewidth, this.imageheight);
        this.graphics.setTransform(this.getTransform());
    }

    private AffineTransform getTransform() {
        AffineTransform t = new AffineTransform();
        t.translate(this.imagewidth / 2, this.imageheight / 2);
        t.scale(this.imagescale, - this.imagescale);
        return t;
    }
}

