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
import java.util.Set;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_2D.GD;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.app.docGraphics.DocGraphics;

public class DG_KGrid_KPolygonWithLabelledKGridInsideIt
extends DocGraphics {
    static final Color FILL0 = new Color(220, 255, 220);
    static final double ARROWOFFSET0 = 80.0;
    static final double ARROWSHAFTLENGTH = 40.0;
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
        this.fillPolygon(a, FILL0);
        this.strokePolygon(a, 4.0, GREEN);
        Path2D.Double clip = a.getDefaultPolygon2D().getPath2D();
        this.graphics.setClip(clip);
        points = this.strokeGrid(2, 4.0, GREEN);
        Shape bigclip = clip.createTransformedShape(AffineTransform.getScaleInstance(1.2, 1.2));
        this.graphics.setClip(bigclip);
        for (KPoint p : points) {
            this.renderPoint(p, 12.0, GREEN);
        }
        this.graphics.setClip(null);
        this.renderGrid0Label();
        this.renderGrid1Label();
    }

    private void renderGrid0Label() {
        AffineTransform graphicstransform = this.graphics.getTransform();
        this.graphics.setTransform(new AffineTransform());
        this.graphics.setPaint(GREY3);
        this.graphics.setFont(new Font("Sans", 0, 40));
        this.graphics.drawString("grid0", 10.0f, 44.0f);
        this.renderNorthArrow(new double[]{140.0, 170.0}, new double[]{140.0, 160.0});
        this.graphics.setTransform(graphicstransform);
    }

    private void renderGrid1Label() {
        AffineTransform graphicstransform = this.graphics.getTransform();
        this.graphics.setTransform(new AffineTransform());
        this.graphics.setPaint(GREY0);
        this.graphics.setFont(new Font("Sans", 0, 40));
        this.graphics.drawString("grid1", 270.0f, 200.0f);
        this.renderNorthArrow(new double[]{200.0, 250.0}, new double[]{200.0 + GD.SQRT3, 247.0});
        this.graphics.setTransform(graphicstransform);
    }

    private void renderNorthArrow(double[] pt0, double[] pt1) {
        double da = GD.getDirection_PointPoint(pt0[0], pt0[1], pt1[0], pt1[1]);
        double dright = GD.normalizeDirection(da + 1.5707963267948966);
        double dleft = GD.normalizeDirection(da - 1.5707963267948966);
        double[] p0 = GD.getPoint_PointDirectionInterval(pt0[0], pt0[1], da, 80.0);
        double[] p1 = GD.getPoint_PointDirectionInterval(p0[0], p0[1], da, 40.0);
        double[] p2 = GD.getPoint_PointDirectionInterval(p1[0], p1[1], da, 30.0);
        double[] pleft = GD.getPoint_PointDirectionInterval(p1[0], p1[1], dleft, 7.0);
        double[] pright = GD.getPoint_PointDirectionInterval(p1[0], p1[1], dright, 7.0);
        Path2D.Double path = new Path2D.Double();
        ((Path2D)path).moveTo(p0[0], p0[1]);
        ((Path2D)path).lineTo(p1[0], p1[1]);
        this.graphics.setStroke(this.createStroke(4.0 * this.imagescale));
        this.graphics.draw(path);
        path = new Path2D.Double();
        ((Path2D)path).moveTo(p2[0], p2[1]);
        ((Path2D)path).lineTo(pleft[0], pleft[1]);
        ((Path2D)path).lineTo(pright[0], pright[1]);
        path.closePath();
        this.graphics.fill(path);
    }

    public static final void main(String[] a) {
        new org.fleen.geom_Kisrhombille.app.docGraphics.DG_KGrid_KPolygonWithLabelledKGridInsideIt();
    }
}

