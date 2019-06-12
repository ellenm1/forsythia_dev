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
import java.util.Set;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.GD;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.app.docGraphics.DocGraphics;

public class DG_KGrid_KPolygon_NewKGridParamsLabelled
extends DocGraphics {
    static final Color FILL = new Color(255, 255, 0, 64);
    private static final double TWISTARCRADIUS = 0.9;
    private static final double TWISTARCSTART = 1.8849555921538759;
    private static final double TWISTARCEND = 6.911503837897546;
    private static final double SEGARCINCREMENT = 0.1308996938995747;
    private static final double HEADLENGTH = 0.5;
    private static final double HEADSPAN = 0.25;
    DPoint pend = null;
    double dend;
    private static final double FISHBRACKETOFFSET = 20.0;
    private static final double FISHBRACKETSPAN0 = 60.0;
    private static final double FISHBRACKETSPAN1 = 60.0;
    static final double ARROWOFFSET0 = 60.0;
    static final double ARROWSHAFTLENGTH = 60.0;
    static final double ARROWHEADLENGTH = 30.0;
    static final double ARROWHEADWIDTH = 7.0;

    @Override
    void doGraphics() {
        this.initImage(768, 512, 60.0, WHITE);
        Set<KPoint> points = this.strokeGrid(8, 4.0, GREY6);
        for (KPoint p : points) {
            this.renderPoint(p, 12.0, GREY6);
        }
        KPolygon a = new KPolygon(new KPoint(-1, -1, 0, 0), new KPoint(-1, 0, 1, 0), new KPoint(0, 1, 1, 0), new KPoint(1, 1, 0, 0), new KPoint(1, 0, -1, 0), new KPoint(0, -1, -1, 0));
        this.fillPolygon(a, FILL);
        this.renderPolygon(a, 4.0, 12.0, GREEN);
        this.renderPolygonPointIndex((KPoint)a.get(0), 0, 12.0f, 7.0f);
        this.renderPolygonPointIndex((KPoint)a.get(1), 1, 6.0f, 25.0f);
        this.renderPolygonPointIndex((KPoint)a.get(2), 2, -15.0f, 25.0f);
        this.renderPolygonPointIndex((KPoint)a.get(3), 3, -25.0f, 7.0f);
        this.renderPolygonPointIndex((KPoint)a.get(4), 4, -15.0f, -7.0f);
        this.renderPolygonPointIndex((KPoint)a.get(5), 5, 6.0f, -7.0f);
        this.renderOrigin((KPoint)a.get(0));
        this.renderNorth((KPoint)a.get(0), (KPoint)a.get(1));
        this.renderFish((KPoint)a.get(0), (KPoint)a.get(1));
        this.renderTwist();
        this.renderGrid();
    }

    private void renderGrid() {
        AffineTransform graphicstransform = this.graphics.getTransform();
        this.graphics.setTransform(new AffineTransform());
        this.graphics.setPaint(GREY4);
        this.graphics.setFont(new Font("Sans", 0, 40));
        this.graphics.drawString("grid0", 10.0f, 44.0f);
        this.graphics.setTransform(graphicstransform);
    }

    private void renderTwist() {
        DPoint p = new KPoint(0, 0, 0, 0).getBasicPoint2D();
        this.renderTwistArc(p, 0.9, 1.8849555921538759, 6.911503837897546);
        this.renderTwistArcArrowhead();
        this.renderTwistLabel("twist");
    }

    private void renderTwistLabel(String text) {
        AffineTransform graphicstransform = this.graphics.getTransform();
        double[] pt = new double[]{this.pend.x, this.pend.y};
        graphicstransform.transform(pt, 0, pt, 0, 1);
        this.graphics.setTransform(new AffineTransform());
        this.graphics.setPaint(PURPLE);
        this.graphics.setFont(new Font("Sans", 0, 18));
        this.graphics.drawString(text, (float)(pt[0] - 53.0), (float)(pt[1] + 44.0));
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

    private void renderFish(KPoint p0, KPoint p1) {
        DPoint dp0 = p0.getBasicPoint2D();
        DPoint dp1 = p1.getBasicPoint2D();
        AffineTransform graphicstransform = this.graphics.getTransform();
        double[] pt0 = new double[]{dp0.x, dp0.y};
        double[] pt1 = new double[]{dp1.x, dp1.y};
        graphicstransform.transform(pt0, 0, pt0, 0, 1);
        graphicstransform.transform(pt1, 0, pt1, 0, 1);
        this.graphics.setTransform(new AffineTransform());
        this.graphics.setPaint(PURPLE);
        this.graphics.setFont(new Font("Sans", 0, 25));
        this.graphics.drawString("fish x M", (float)(pt0[0] - 85.0), (float)(pt0[1] - 123.0));
        this.renderBracket(pt0, pt1);
        this.graphics.setTransform(graphicstransform);
    }

    private void renderBracket(double[] pt0, double[] pt1) {
        double dforeward = GD.getDirection_PointPoint(pt0[0], pt0[1], pt1[0], pt1[1]);
        double dbackward = GD.normalizeDirection(dforeward - 3.141592653589793);
        double dout = GD.normalizeDirection(dforeward + 1.5707963267948966);
        double[] b0p0 = GD.getPoint_PointDirectionInterval(pt0[0], pt0[1], dout, 20.0);
        double[] b0p1 = GD.getPoint_PointDirectionInterval(b0p0[0], b0p0[1], dout, 60.0);
        double[] b0p2 = GD.getPoint_PointDirectionInterval(b0p1[0], b0p1[1], dforeward, 60.0);
        double[] b1p0 = GD.getPoint_PointDirectionInterval(pt1[0], pt1[1], dout, 20.0);
        double[] b1p1 = GD.getPoint_PointDirectionInterval(b1p0[0], b1p0[1], dout, 60.0);
        double[] b1p2 = GD.getPoint_PointDirectionInterval(b1p1[0], b1p1[1], dbackward, 60.0);
        this.graphics.setStroke(this.createStroke(4.0 * this.imagescale));
        Path2D.Double path = new Path2D.Double();
        ((Path2D)path).moveTo(b0p0[0], b0p0[1]);
        ((Path2D)path).lineTo(b0p1[0], b0p1[1]);
        ((Path2D)path).lineTo(b0p2[0], b0p2[1]);
        this.graphics.draw(path);
        path = new Path2D.Double();
        ((Path2D)path).moveTo(b1p0[0], b1p0[1]);
        ((Path2D)path).lineTo(b1p1[0], b1p1[1]);
        ((Path2D)path).lineTo(b1p2[0], b1p2[1]);
        this.graphics.draw(path);
    }

    private void renderNorth(KPoint p0, KPoint p1) {
        DPoint dp0 = p0.getBasicPoint2D();
        DPoint dp1 = p1.getBasicPoint2D();
        AffineTransform graphicstransform = this.graphics.getTransform();
        double[] pt0 = new double[]{dp0.x, dp0.y};
        double[] pt1 = new double[]{dp1.x, dp1.y};
        graphicstransform.transform(pt0, 0, pt0, 0, 1);
        graphicstransform.transform(pt1, 0, pt1, 0, 1);
        this.graphics.setTransform(new AffineTransform());
        this.graphics.setPaint(BLUE);
        this.graphics.setFont(new Font("Sans", 0, 25));
        this.graphics.drawString("north", (float)(pt0[0] + 85.0), (float)(pt0[1] - 75.0));
        this.renderNorthArrow(pt0, pt1);
        this.graphics.setTransform(graphicstransform);
    }

    private void renderNorthArrow(double[] pt0, double[] pt1) {
        double[] arrd = pt0;
        arrd[0] = arrd[0] + 15.0;
        double[] arrd2 = pt0;
        arrd2[1] = arrd2[1] + 15.0;
        double[] arrd3 = pt1;
        arrd3[0] = arrd3[0] + 15.0;
        double[] arrd4 = pt1;
        arrd4[1] = arrd4[1] + 15.0;
        double da = GD.getDirection_PointPoint(pt0[0], pt0[1], pt1[0], pt1[1]);
        double dright = GD.normalizeDirection(da + 1.5707963267948966);
        double dleft = GD.normalizeDirection(da - 1.5707963267948966);
        double[] p0 = GD.getPoint_PointDirectionInterval(pt0[0], pt0[1], da, 60.0);
        double[] p1 = GD.getPoint_PointDirectionInterval(p0[0], p0[1], da, 60.0);
        double[] p2 = GD.getPoint_PointDirectionInterval(p1[0], p1[1], da, 30.0);
        double[] pleft = GD.getPoint_PointDirectionInterval(p1[0], p1[1], dleft, 7.0);
        double[] pright = GD.getPoint_PointDirectionInterval(p1[0], p1[1], dright, 7.0);
        Path2D.Double path = new Path2D.Double();
        ((Path2D)path).moveTo(p0[0], p0[1]);
        ((Path2D)path).lineTo(p1[0], p1[1]);
        this.graphics.setPaint(BLUE);
        this.graphics.setStroke(this.createStroke(4.0 * this.imagescale));
        this.graphics.draw(path);
        path = new Path2D.Double();
        ((Path2D)path).moveTo(p2[0], p2[1]);
        ((Path2D)path).lineTo(pleft[0], pleft[1]);
        ((Path2D)path).lineTo(pright[0], pright[1]);
        path.closePath();
        this.graphics.fill(path);
    }

    private void renderOrigin(KPoint p) {
        DPoint dp = p.getBasicPoint2D();
        AffineTransform graphicstransform = this.graphics.getTransform();
        double[] pt = new double[]{dp.x, dp.y};
        graphicstransform.transform(pt, 0, pt, 0, 1);
        this.graphics.setTransform(new AffineTransform());
        this.graphics.setPaint(RED);
        this.graphics.setFont(new Font("Sans", 0, 25));
        this.graphics.drawString("origin", (float)(pt[0] - 80.0), (float)(pt[1] + 28.0));
        this.graphics.setTransform(graphicstransform);
    }

    private void renderPolygonPointIndex(KPoint p, int index, float offsetx, float offsety) {
        DPoint dp = p.getBasicPoint2D();
        AffineTransform graphicstransform = this.graphics.getTransform();
        double[] pt = new double[]{dp.x, dp.y};
        graphicstransform.transform(pt, 0, pt, 0, 1);
        this.graphics.setTransform(new AffineTransform());
        this.graphics.setPaint(GREY0);
        this.graphics.setFont(new Font("Sans", 0, 22));
        this.graphics.drawString(String.valueOf(index), (float)(pt[0] + (double)offsetx), (float)(pt[1] + (double)offsety));
        this.graphics.setTransform(graphicstransform);
    }

    public static final void main(String[] a) {
        new org.fleen.geom_Kisrhombille.app.docGraphics.DG_KGrid_KPolygon_NewKGridParamsLabelled();
    }
}

