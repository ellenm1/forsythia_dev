/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.GD;
import org.fleen.geom_Kisrhombille.KGrid;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.app.docGraphics.DocGraphics;

public class DG_KGridDefinitionOnAPlaneWithParamsLabelled
extends DocGraphics {
    private static final double originx = 0.0;
    private static final double originy = 0.0;
    private static final double NORTH = 0.0;
    private static final double FISH = 1.0;
    private static final boolean TWIST = true;
    private static final double IMAGEXOFFSET = 0.0;
    private static final double IMAGEYOFFSET = 0.0;
    private static final int CGRIDLABELSOFFSETXX = -250;
    private static final int CGRIDLABELSOFFSETXY = 12;
    private static final int CGRIDLABELSOFFSETYX = -12;
    private static final int CGRIDLABELSOFFSETYY = 220;
    private static final int ORIGINLABELOFFSETX = -24;
    private static final int ORIGINLABELOFFSETY = 26;
    private static final int NORTHLABELOFFSETX = 8;
    private static final int NORTHLABELOFFSETY = 20;
    private static final String NORTHLABELTEXT = "north=0";
    private static final KPoint FISHPOINT0 = new KPoint(1, 0, -1, 3);
    private static final KPoint FISHPOINT1 = new KPoint(1, 0, -1, 4);
    private static final int FISHLABELOFFSETX = -37;
    private static final int FISHLABELOFFSETY = 40;
    private static final double TWISTARCRADIUS = 1.5;
    private static final double TWISTARCSTART = 2.199114857512855;
    private static final double TWISTARCEND = 5.026548245743669;
    private static final String TWISTLABELTEXT = "twist=clockwise";
    private static final int TWISTLABELOFFSETX = -160;
    private static final int TWISTLABELOFFSETY = 1;
    private static final double NORTHSHAFTOFFSET = 0.5;
    private static final double SHAFTLENGTH = 0.6;
    private static final double HEADLENGTH = 0.5;
    private static final double HEADSPAN = 0.25;
    private static final double SEGARCINCREMENT = 0.1308996938995747;
    DPoint pend = null;
    double dend;

    @Override
    void doGraphics() {
        KGrid g = new KGrid(0.0, 0.0, 0.0, true, 1.0);
        this.initImage(512, 512, 60.0, WHITE);
        AffineTransform t = this.graphics.getTransform();
        t.translate(0.0, 0.0);
        this.graphics.setTransform(t);
        this.renderCGrid();
        this.renderKGrid(g);
        this.renderOrigin(g);
        this.renderNorth(g);
        this.renderFish(g);
        this.renderTwist(g);
    }

    private void renderCGrid() {
        this.graphics.setStroke(this.createStroke(3.0));
        this.graphics.setPaint(YELLOW);
        this.graphics.drawLine(-100, 0, 100, 0);
        this.graphics.drawLine(0, -100, 0, 100);
        this.renderCGridLabels();
    }

    private void renderCGridLabels() {
        AffineTransform graphicstransform = this.graphics.getTransform();
        double[] p = new double[]{0.0, 0.0};
        graphicstransform.transform(p, 0, p, 0, 1);
        this.graphics.setTransform(new AffineTransform());
        this.graphics.setPaint(YELLOW);
        this.graphics.setFont(new Font("Sans", 0, 12));
        this.graphics.drawString("x", (float)(p[0] + -250.0), (float)(p[1] + 12.0));
        this.graphics.drawString("y", (float)(p[0] + -12.0), (float)(p[1] + 220.0));
        this.graphics.setTransform(graphicstransform);
    }

    private void renderKGrid(KGrid g) {
        Set<KPoint> gp = this.renderKGrid(g, 8, 2.0, GREY6);
        for (KPoint p : gp) {
            this.renderKGridPoint(g, p);
        }
    }

    Set<KPoint> renderKGrid(KGrid grid, int range, double thickness, Color color) {
        HashSet<KPoint> points = new HashSet<KPoint>();
        Set<KPoint> v0s = this.getV0s(range);
        for (KPoint p : v0s) {
            points.addAll(Arrays.asList(this.getClockKPoints(p)));
            this.strokeClock(grid, p, thickness, color);
        }
        return points;
    }

    void strokeClock(KGrid grid, KPoint v, double thickness, Color color) {
        KPoint[] cp = this.getClockKPoints(v);
        for (int i = 1; i < cp.length; ++i) {
            int j = i + 1;
            if (j == cp.length) {
                j = 1;
            }
            this.strokeSeg(grid, cp[i], cp[j], thickness, color);
            this.strokeSeg(grid, cp[0], cp[i], thickness, color);
        }
    }

    void strokeSeg(KGrid grid, KPoint v0, KPoint v1, double thickness, Color color) {
        DPoint p0 = new DPoint(grid.getPoint2D(v0));
        DPoint p1 = new DPoint(grid.getPoint2D(v1));
        this.strokeSeg(p0, p1, thickness, color);
    }

    private void renderKGridPoint(KGrid grid, KPoint p) {
        DPoint dp = new DPoint(grid.getPoint2D(p));
        this.renderPoint(dp, 8.0, GREY4);
        AffineTransform graphicstransform = this.graphics.getTransform();
        double[] pt = new double[]{dp.x, dp.y};
        graphicstransform.transform(pt, 0, pt, 0, 1);
        this.graphics.setTransform(new AffineTransform());
        this.graphics.setPaint(GREY3);
        this.graphics.setFont(new Font("Sans", 0, 10));
        String s = String.valueOf(p.getAnt()) + " " + p.getBat();
        this.graphics.drawString(s, (float)(pt[0] + 4.0), (float)(pt[1] - 15.0));
        s = String.valueOf(p.getCat()) + " " + p.getDog();
        this.graphics.drawString(s, (float)(pt[0] + 4.0), (float)(pt[1] - 3.0));
        this.graphics.setTransform(graphicstransform);
    }

    private void renderOrigin(KGrid grid) {
        KPoint origin = new KPoint(0, 0, 0, 0);
        DPoint p = new DPoint(grid.getPoint2D(origin));
        this.renderPoint(p, 12.0, RED);
        AffineTransform graphicstransform = this.graphics.getTransform();
        double[] pt = new double[]{p.x, p.y};
        graphicstransform.transform(pt, 0, pt, 0, 1);
        this.graphics.setTransform(new AffineTransform());
        this.graphics.setPaint(RED);
        this.graphics.setFont(new Font("Sans", 0, 18));
        String s = "origin=(" + p.x + "," + p.y + ")";
        this.graphics.drawString(s, (float)(pt[0] + -24.0), (float)(pt[1] + 26.0));
        this.graphics.setTransform(graphicstransform);
    }

    private void renderTwist(KGrid g) {
        DPoint p = new DPoint(g.getPoint2D(new KPoint(0, 0, 0, 0)));
        this.renderTwistArc(p, 1.5, 2.199114857512855, 5.026548245743669);
        this.renderTwistArcArrowhead();
        this.renderTwistLabel(TWISTLABELTEXT);
    }

    private void renderTwistLabel(String text) {
        AffineTransform graphicstransform = this.graphics.getTransform();
        double[] pt = new double[]{this.pend.x, this.pend.y};
        graphicstransform.transform(pt, 0, pt, 0, 1);
        this.graphics.setTransform(new AffineTransform());
        this.graphics.setPaint(PURPLE);
        this.graphics.setFont(new Font("Sans", 0, 18));
        this.graphics.drawString(text, (float)(pt[0] + -160.0), (float)(pt[1] + 1.0));
        this.graphics.setTransform(graphicstransform);
    }

    private void renderTwistArc(DPoint center, double radius, double start, double end) {
        Path2D.Double path = new Path2D.Double();
        double arclength = Math.abs(GD.getAngle_2Directions(start, end));
        int segcount = (int)(arclength / 0.1308996938995747) + 1;
        double d = start;
        double[] p = GD.getPoint_PointDirectionInterval(center.x, center.y, d, radius);
        ArrayList<DPoint> points = new ArrayList<DPoint>();
        points.add(new DPoint(p));
        ((Path2D)path).moveTo(p[0], p[1]);
        for (int i = 0; i < segcount; ++i) {
            d = GD.normalizeDirection(d + 0.1308996938995747);
            p = GD.getPoint_PointDirectionInterval(center.x, center.y, d, radius);
            points.add(new DPoint(p));
            ((Path2D)path).lineTo(p[0], p[1]);
        }
        this.graphics.setPaint(PURPLE);
        this.graphics.setStroke(this.createStroke(4.0));
        this.graphics.draw(path);
        this.pend = (DPoint)points.get(points.size() - 1);
        this.dend = ((DPoint)points.get(points.size() - 2)).getDirection(this.pend);
    }

    private void renderTwistArcArrowhead() {
        double dleft = GD.normalizeDirection(this.dend - 1.5707963267948966);
        double dright = GD.normalizeDirection(this.dend + 1.5707963267948966);
        DPoint pleft = this.pend.getPoint(dleft, 0.125);
        DPoint pright = this.pend.getPoint(dright, 0.125);
        DPoint ptip = this.pend.getPoint(this.dend, 0.5);
        Path2D.Double path = new Path2D.Double();
        ((Path2D)path).moveTo(pleft.x, pleft.y);
        ((Path2D)path).lineTo(ptip.x, ptip.y);
        ((Path2D)path).lineTo(pright.x, pright.y);
        path.closePath();
        this.graphics.setPaint(PURPLE);
        this.graphics.fill(path);
    }

    private void renderFish(KGrid g) {
        DPoint p0d = new DPoint(g.getPoint2D(FISHPOINT0));
        DPoint p1d = new DPoint(g.getPoint2D(FISHPOINT1));
        this.strokeSeg(p0d, p1d, 4.0, GREEN);
        this.renderPoint(p0d, 12.0, GREEN);
        this.renderPoint(p1d, 12.0, GREEN);
        this.renderFishLabel(p0d, "fish=1.0");
    }

    private void renderFishLabel(DPoint p, String labeltext) {
        AffineTransform graphicstransform = this.graphics.getTransform();
        double[] pt = new double[]{p.x, p.y};
        graphicstransform.transform(pt, 0, pt, 0, 1);
        this.graphics.setTransform(new AffineTransform());
        this.graphics.setPaint(GREEN);
        this.graphics.setFont(new Font("Sans", 0, 18));
        this.graphics.drawString(labeltext, (float)(pt[0] + -37.0), (float)(pt[1] + 40.0));
        this.graphics.setTransform(graphicstransform);
    }

    private void renderNorth(KGrid grid) {
        KPoint p0 = new KPoint(0, 0, 0, 0);
        KPoint p1 = new KPoint(0, 0, 0, 2);
        DPoint p0d = new DPoint(grid.getPoint2D(p0));
        DPoint p1d = new DPoint(grid.getPoint2D(p1));
        double dir = p0d.getDirection(p1d);
        DPoint a0 = p0d.getPoint(dir, 0.5);
        DPoint a1 = a0.getPoint(dir, 0.6);
        this.renderNorthArrow(a0, a1);
        this.renderNorthLabel(a1);
    }

    private void renderNorthArrow(DPoint p0, DPoint p1) {
        this.strokeSeg(p0, p1, 4.0, BLUE);
        double d = p0.getDirection(p1);
        double dleft = GD.normalizeDirection(d - 1.5707963267948966);
        double dright = GD.normalizeDirection(d + 1.5707963267948966);
        DPoint pleft = p1.getPoint(dleft, 0.125);
        DPoint pright = p1.getPoint(dright, 0.125);
        DPoint pend = p1.getPoint(d, 0.5);
        Path2D.Double path = new Path2D.Double();
        ((Path2D)path).moveTo(pleft.x, pleft.y);
        ((Path2D)path).lineTo(pend.x, pend.y);
        ((Path2D)path).lineTo(pright.x, pright.y);
        path.closePath();
        this.graphics.setPaint(BLUE);
        this.graphics.fill(path);
    }

    private void renderNorthLabel(DPoint p) {
        AffineTransform graphicstransform = this.graphics.getTransform();
        double[] pt = new double[]{p.x, p.y};
        graphicstransform.transform(pt, 0, pt, 0, 1);
        this.graphics.setTransform(new AffineTransform());
        this.graphics.setPaint(BLUE);
        this.graphics.setFont(new Font("Sans", 0, 18));
        this.graphics.drawString(NORTHLABELTEXT, (float)(pt[0] + 8.0), (float)(pt[1] + 20.0));
        this.graphics.setTransform(graphicstransform);
    }

    public static final void main(String[] a) {
        new org.fleen.geom_Kisrhombille.app.docGraphics.DG_KGridDefinitionOnAPlaneWithParamsLabelled();
    }
}

