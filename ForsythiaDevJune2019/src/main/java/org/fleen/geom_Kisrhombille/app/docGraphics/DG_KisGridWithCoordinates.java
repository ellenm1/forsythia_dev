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

public class DG_KisGridWithCoordinates
extends DocGraphics {
    @Override
    void doGraphics() {
        this.initImage(768, 512, 60.0, WHITE);
        Set<KPoint> points = this.strokeGrid(8, 4.0, GREY6);
        for (KPoint p : points) {
            this.renderPoint(p, 16.0, GREY6);
        }
        for (KPoint p : points) {
            this.renderPointCoors(p);
        }
    }

    void renderPointCoors(KPoint v) {
        DPoint p = v.getBasicPoint2D();
        AffineTransform graphicstransform = this.graphics.getTransform();
        double[] pt = new double[]{p.x, p.y};
        graphicstransform.transform(pt, 0, pt, 0, 1);
        this.graphics.setTransform(new AffineTransform());
        this.graphics.setPaint(BLACK);
        this.graphics.setFont(new Font("Sans", 0, 18));
        String z = String.valueOf(v.getAnt()) + "," + v.getBat() + ",";
        this.graphics.drawString(z, (float)(pt[0] - 13.0), (float)(pt[1] - 3.0));
        z = String.valueOf(v.getCat()) + "," + v.getDog();
        this.graphics.drawString(z, (float)(pt[0] - 13.0), (float)(pt[1] + 15.0));
        this.graphics.setTransform(graphicstransform);
    }

    public static final void main(String[] a) {
        new org.fleen.geom_Kisrhombille.app.docGraphics.DG_KisGridWithCoordinates();
    }
}

