/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DSeg;
import org.fleen.geom_Kisrhombille.app.docGraphics.DocGraphics;
import org.fleen.geom_Kisrhombille.app.docGraphics.GridTriangle;

public class DG_KisrhombilleTessellation_StrokesAndDots
extends DocGraphics {
    @Override
    void doGraphics() {
        this.initImage(768, 256, 30.0, WHITE);
        List<GridTriangle> triangles = this.getGridTriangles(12);
        ArrayList<DSeg> segs = new ArrayList<DSeg>();
        for (GridTriangle t : triangles) {
            segs.addAll(t.getSegs());
        }
        ArrayList<DPoint> points = new ArrayList<DPoint>();
        for (DSeg s : segs) {
            points.add(s.p0);
            this.strokeSeg(s.p0, s.p1, 4.0, GREEN);
        }
        for (DPoint p : points) {
            this.renderPoint(p, 8.0, BLACK);
        }
    }

    public static final void main(String[] a) {
        new org.fleen.geom_Kisrhombille.app.docGraphics.DG_KisrhombilleTessellation_StrokesAndDots();
    }
}

