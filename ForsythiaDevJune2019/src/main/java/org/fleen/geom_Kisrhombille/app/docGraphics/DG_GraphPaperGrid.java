/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.util.Set;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.app.docGraphics.DocGraphics;

public class DG_GraphPaperGrid
extends DocGraphics {
    @Override
    void doGraphics() {
        this.initImage(1000, 2000, 20.0, WHITE);
        this.strokeGrid(12, 3.0, GREY5);
    }

    public static final void main(String[] a) {
        new org.fleen.geom_Kisrhombille.app.docGraphics.DG_GraphPaperGrid();
    }
}

