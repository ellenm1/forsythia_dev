/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.AffineTransform;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.GD;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.app.docGraphics.DocGraphics;

public class DG_UnitTriangle
extends DocGraphics {
    @Override
    void doGraphics() {
        this.initImage(768, 256, 180.0, WHITE);
        AffineTransform t = this.graphics.getTransform();
        t.translate(-1.3, 0.45);
        t.rotate(-0.5235987755982988);
        this.graphics.setTransform(t);
        KPoint v0 = new KPoint(0, 0, 0, 0);
        KPoint v1 = new KPoint(0, 0, 0, 4);
        KPoint v2 = new KPoint(0, 0, 0, 5);
        KPolygon kp = new KPolygon(v0, v1, v2);
        this.renderPolygon(kp, 6.0, 16.0, ORANGE);
        DPoint p0 = v0.getBasicPoint2D();
        DPoint p1 = v1.getBasicPoint2D();
        DPoint p2 = v2.getBasicPoint2D();
        DPoint phawk = new DPoint(GD.getPoint_Between2Points(p0.x, p0.y, p1.x, p1.y, 0.5));
        DPoint pfish = new DPoint(GD.getPoint_Between2Points(p1.x, p1.y, p2.x, p2.y, 0.5));
        DPoint pgoat = new DPoint(GD.getPoint_Between2Points(p2.x, p2.y, p0.x, p0.y, 0.5));
        this.renderLabel(p0, "P12", -56, 0);
        this.renderLabel(p1, "P6", 17, -7);
        this.renderLabel(p2, "P4", -3, 33);
        this.renderLabel(pfish, "F", 15, 18);
        this.renderLabel(pgoat, "G", -21, 28);
        this.renderLabel(phawk, "H", 0, -12);
    }

    void renderLabel(DPoint p, String s, int xoff, int yoff) {
        AffineTransform oldgt = this.graphics.getTransform();
        double[] pt = new double[]{p.x, p.y};
        oldgt.transform(pt, 0, pt, 0, 1);
        this.graphics.setTransform(new AffineTransform());
        this.graphics.setPaint(BLACK);
        this.graphics.setFont(new Font("Sans", 0, 22));
        this.graphics.drawString(s, (float)(pt[0] + (double)xoff), (float)(pt[1] + (double)yoff));
        this.graphics.setTransform(oldgt);
    }

    public static final void main(String[] a) {
        new org.fleen.geom_Kisrhombille.app.docGraphics.DG_UnitTriangle();
    }
}

