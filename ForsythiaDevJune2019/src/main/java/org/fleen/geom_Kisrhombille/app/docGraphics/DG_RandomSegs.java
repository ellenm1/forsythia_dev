/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.util.List;
import java.util.Set;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KSeg;
import org.fleen.geom_Kisrhombille.app.docGraphics.DocGraphics;

public class DG_RandomSegs
extends DocGraphics {
    @Override
    void doGraphics() {
        this.initImage(512, 512, 20.0, WHITE);
        this.strokeGrid(8, 3.0, GREY6);
        List<KSeg> segs = null;
        while (segs == null || this.segsIntersect(segs)) {
            segs = this.getSegs(18, 3, 5);
        }
        for (KSeg s : segs) {
            this.renderSeg(s, 4.0, 12.0, GREEN);
        }
    }

    public static final void main(String[] a) {
        new org.fleen.geom_Kisrhombille.app.docGraphics.DG_RandomSegs();
    }
}

