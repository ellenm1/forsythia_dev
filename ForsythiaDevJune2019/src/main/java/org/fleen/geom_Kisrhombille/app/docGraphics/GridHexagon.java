/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille.app.docGraphics;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_Kisrhombille.KPoint;

public class GridHexagon
extends DPolygon {
    KPoint center;

    public GridHexagon(DPoint p0, DPoint p1, DPoint p2, DPoint p3, DPoint p4, DPoint p5, KPoint center) {
        super(p0, p1, p2, p3, p4, p5);
        this.center = center;
    }
}

