/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.io.PrintStream;
import java.util.List;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_Kisrhombille.app.docGraphics.DocGraphics;
import org.fleen.geom_Kisrhombille.app.docGraphics.GridTriangle;

public class DG_KisrhombilleTessellation
extends DocGraphics {
    @Override
    void doGraphics() {
        this.initImage(768, 256, 30.0, BLACK);
        List<GridTriangle> triangles = this.getGridTriangles(12);
        System.out.println("triangles:" + triangles.size());
        for (GridTriangle t : triangles) {
            Color c = t.index % 2 == 0 ? BLACK : WHITE;
            this.fillPolygon(t, c);
        }
    }

    public static final void main(String[] a) {
        new org.fleen.geom_Kisrhombille.app.docGraphics.DG_KisrhombilleTessellation();
    }
}

