/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.app.docGraphics.DocGraphics;

public class DG_HexagonAndSplitHexagon
extends DocGraphics {
    @Override
    void doGraphics() {
        this.initImage(192, 192, 30.0, WHITE);
        KPoint v0 = new ArrayList<KPoint>(this.getV0s(1)).get(0);
        KPoint[] c = this.getClockKPoints(v0);
        this.strokeSeg(c[1], c[3], 4.0, GREEN);
        this.strokeSeg(c[3], c[5], 4.0, GREEN);
        this.strokeSeg(c[5], c[7], 4.0, GREEN);
        this.strokeSeg(c[7], c[9], 4.0, GREEN);
        this.strokeSeg(c[9], c[11], 4.0, GREEN);
        this.strokeSeg(c[11], c[1], 4.0, GREEN);
        for (int i = 1; i <= 12; ++i) {
            this.strokeSeg(c[0], c[i], 4.0, GREEN);
        }
    }

    public static final void main(String[] a) {
        new org.fleen.geom_Kisrhombille.app.docGraphics.DG_HexagonAndSplitHexagon();
    }
}

