/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.util.Set;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.app.docGraphics.DocGraphics;

public class DG_KGridKPointsAndCoors
extends DocGraphics {
    @Override
    void doGraphics() {
        this.initImage(768, 256, 30.0, WHITE);
        Set<KPoint> points = this.strokeGrid(8, 4.0, GREY6);
        for (KPoint p : points) {
            this.renderPoint(p, 12.0, GREY6);
        }
        this.renderPoint(new KPoint(-3, -4, -1, 5));
        this.renderPoint(new KPoint(-1, -2, -1, 4));
        this.renderPoint(new KPoint(1, 1, 0, 0));
    }

    private void renderPoint(KPoint p) {
        this.renderPoint(p, 16.0, GREEN);
        DPoint dp = p.getBasicPoint2D();
        AffineTransform graphicstransform = this.graphics.getTransform();
        double[] pt = new double[]{dp.x, dp.y};
        graphicstransform.transform(pt, 0, pt, 0, 1);
        this.graphics.setTransform(new AffineTransform());
        this.graphics.setPaint(BLACK);
        int xoff = 18;
        this.graphics.setFont(new Font("Sans", 0, 25));
        this.graphics.drawString("ant = " + p.getAnt(), (float)(pt[0] + (double)xoff), (float)(pt[1] - 75.0));
        this.graphics.drawString("bat = " + p.getBat(), (float)(pt[0] + (double)xoff), (float)(pt[1] - 50.0));
        this.graphics.drawString("cat = " + p.getCat(), (float)(pt[0] + (double)xoff), (float)(pt[1] - 25.0));
        this.graphics.drawString("dog = " + p.getDog(), (float)(pt[0] + (double)xoff), (float)pt[1]);
        this.graphics.setTransform(graphicstransform);
    }

    public static final void main(String[] a) {
        new org.fleen.geom_Kisrhombille.app.docGraphics.DG_KGridKPointsAndCoors();
    }
}

