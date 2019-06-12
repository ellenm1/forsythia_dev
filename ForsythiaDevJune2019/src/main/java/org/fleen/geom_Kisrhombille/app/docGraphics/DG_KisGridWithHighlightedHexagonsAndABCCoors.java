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

public class DG_KisGridWithHighlightedHexagonsAndABCCoors
extends DocGraphics {
    @Override
    void doGraphics() {
        KPoint[] cp;
        this.initImage(768, 512, 60.0, WHITE);
        Set<KPoint> v0s = this.getV0s(12);
        for (KPoint v0 : v0s) {
            for (KPoint p : cp = this.getClockKPoints(v0)) {
                this.renderPoint(p, 16.0, GREY6);
            }
        }
        for (KPoint v0 : v0s) {
            cp = this.getClockKPoints(v0);
            for (int i = 1; i <= 12; ++i) {
                this.strokeSeg(cp[0], cp[i], 4.0, GREY6);
            }
            this.strokeSeg(cp[1], cp[3], 4.0, GREEN);
            this.strokeSeg(cp[3], cp[5], 4.0, GREEN);
            this.strokeSeg(cp[5], cp[7], 4.0, GREEN);
            this.strokeSeg(cp[7], cp[9], 4.0, GREEN);
            this.strokeSeg(cp[9], cp[11], 4.0, GREEN);
            this.strokeSeg(cp[11], cp[1], 4.0, GREEN);
            this.renderHexagonCoors(v0);
        }
    }

    void renderHexagonCoors(KPoint v) {
        DPoint p = v.getBasicPoint2D();
        AffineTransform graphicstransform = this.graphics.getTransform();
        double[] pt = new double[]{p.x, p.y};
        graphicstransform.transform(pt, 0, pt, 0, 1);
        this.graphics.setTransform(new AffineTransform());
        this.graphics.setPaint(BLACK);
        this.graphics.setFont(new Font("Sans", 0, 18));
        String z = String.valueOf(v.getAnt()) + "," + v.getBat() + "," + v.getDog();
        this.graphics.drawString(z, (float)(pt[0] - 23.0), (float)(pt[1] + 6.0));
        this.graphics.setTransform(graphicstransform);
    }

    public static final void main(String[] a) {
        new org.fleen.geom_Kisrhombille.app.docGraphics.DG_KisGridWithHighlightedHexagonsAndABCCoors();
    }
}

