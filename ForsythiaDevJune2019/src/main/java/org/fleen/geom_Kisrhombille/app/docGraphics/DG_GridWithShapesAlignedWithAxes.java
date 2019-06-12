/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.util.Set;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.app.docGraphics.DocGraphics;

public class DG_GridWithShapesAlignedWithAxes
extends DocGraphics {
    private static final Color TRANSFILL = new Color(255, 255, 0, 64);

    @Override
    void doGraphics() {
        this.initImage(768, 256, 30.0, WHITE);
        Set<KPoint> points = this.strokeGrid(8, 4.0, GREY6);
        for (KPoint p : points) {
            this.renderPoint(p, 12.0, GREY6);
        }
        KPolygon p0 = new KPolygon(new KPoint(-4, -3, 1, 5), new KPoint(-3, -2, 1, 5), new KPoint(-2, -2, 0, 0), new KPoint(-2, -2, 0, 5), new KPoint(-1, -2, -1, 0), new KPoint(-3, -4, -1, 5));
        this.renderPolygon(p0);
        KPolygon p1 = new KPolygon(new KPoint(-2, -1, 1, 0), new KPoint(0, 1, 1, 0), new KPoint(0, -1, -1, 0));
        this.renderPolygon(p1);
        KPolygon p2 = new KPolygon(new KPoint(1, 1, 0, 2), new KPoint(1, 1, 0, 4), new KPoint(2, 1, -1, 2), new KPoint(1, 0, -1, 4), new KPoint(1, 0, -1, 2), new KPoint(0, 0, 0, 4));
        this.renderPolygon(p2);
        KPolygon p3 = new KPolygon(new KPoint(6, 2, -4, 0), new KPoint(2, 2, 0, 0), new KPoint(2, 6, 4, 0));
        this.renderPolygon(p3);
        KPolygon p4 = new KPolygon(new KPoint(2, 1, -1, 4), new KPoint(2, 1, -1, 5), new KPoint(2, 1, -1, 0));
        this.renderPolygon(p4);
    }

    private void renderPolygon(KPolygon p) {
        this.fillPolygon(p, TRANSFILL);
        this.strokePolygon(p, 4.0, GREEN);
    }

    public static final void main(String[] a) {
        new org.fleen.geom_Kisrhombille.app.docGraphics.DG_GridWithShapesAlignedWithAxes();
    }
}

