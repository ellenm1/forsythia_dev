/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.util.Set;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.app.docGraphics.DocGraphics;

public class DG_SomePolygons
extends DocGraphics {
    @Override
    void doGraphics() {
        this.initImage(512, 512, 20.0, WHITE);
        this.strokeGrid(8, 3.0, GREY6);
        KPolygon p0 = new KPolygon(new KPoint(-3, 0, 3, 0), new KPoint(-1, 0, 1, 0), new KPoint(-3, -2, 1, 0));
        this.renderPolygon(p0, 4.0, 12.0, GREEN);
        p0 = new KPolygon(new KPoint(-1, 2, 3, 5), new KPoint(0, 3, 3, 5), new KPoint(1, 2, 1, 5), new KPoint(0, 1, 1, 5));
        this.renderPolygon(p0, 4.0, 12.0, GREEN);
        p0 = new KPolygon(new KPoint(-1, -2, -1, 2), new KPoint(0, -1, -1, 0), new KPoint(1, -2, -3, 2), new KPoint(0, -3, -3, 0), new KPoint(-1, -4, -3, 2), new KPoint(-2, -3, -1, 0));
        this.renderPolygon(p0, 4.0, 12.0, GREEN);
        p0 = new KPolygon(new KPoint(0, 0, 0, 5), new KPoint(2, 2, 0, 5), new KPoint(3, 2, -1, 0), new KPoint(2, 1, -1, 0), new KPoint(2, 0, -2, 5), new KPoint(3, 1, -2, 5), new KPoint(4, 1, -3, 0), new KPoint(2, -1, -3, 0));
        this.renderPolygon(p0, 4.0, 12.0, GREEN);
    }

    public static final void main(String[] a) {
        new org.fleen.geom_Kisrhombille.app.docGraphics.DG_SomePolygons();
    }
}

