/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.util.List;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.app.docGraphics.DocGraphics;
import org.fleen.geom_Kisrhombille.app.docGraphics.GridHexagon;

public class DG_HexagonTessellation
extends DocGraphics {
    @Override
    void doGraphics() {
        this.initImage(768, 256, 30.0, RED);
        List<GridHexagon> hexagons = this.getGridHexagons(12);
        for (int i = 0; i < hexagons.size(); ++i) {
            int bat;
            GridHexagon h = hexagons.get(i);
            int ant = h.center.getAnt() + 1000;
            int z = (ant + (bat = h.center.getBat() + 1000)) % 3;
            Color c = z == 0 ? RED : (z == 1 ? YELLOW : BLUE);
            this.fillPolygon(h, c);
        }
    }

    public static final void main(String[] a) {
        new org.fleen.geom_Kisrhombille.app.docGraphics.DG_HexagonTessellation();
    }
}

