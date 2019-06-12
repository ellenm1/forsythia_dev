/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.List;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_2D.GD;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.app.docGraphics.DocGraphics;
import org.fleen.geom_Kisrhombille.app.docGraphics.GridHexagon;
import org.fleen.geom_Kisrhombille.app.docGraphics.GridTriangle;

public class DG_KisrhombilleTessellationWithNumberedTriangles
extends DocGraphics {
    @Override
    void doGraphics() {
        this.initImage(768, 256, 30.0, RED);
        this.doColoredHexagons();
        this.renderTriangles();
    }

    void renderTriangles() {
        List<GridTriangle> triangles = this.getGridTriangles(12);
        for (GridTriangle t : triangles) {
            this.strokePolygon(t, 2.0, BLACK);
            this.drawIndex(t);
        }
    }

    void drawIndex(GridTriangle t) {
        DPoint p = GD.getPoint_Mean(Arrays.asList((DPoint)t.get(0), (DPoint)t.get(1), (DPoint)t.get(2)));
        AffineTransform graphicstransform = this.graphics.getTransform();
        double[] pt = new double[]{p.x, p.y};
        graphicstransform.transform(pt, 0, pt, 0, 1);
        this.graphics.setTransform(new AffineTransform());
        this.graphics.setPaint(BLACK);
        this.graphics.setFont(new Font("Sans", 0, 11));
        int xoff = -4;
        int yoff = 4;
        if (t.index == 10 || t.index == 11) {
            xoff -= 2;
        }
        this.graphics.drawString(String.valueOf(t.index), (float)(pt[0] + (double)xoff), (float)(pt[1] + (double)yoff));
        this.graphics.setTransform(graphicstransform);
    }

    void doColoredHexagons() {
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
        new org.fleen.geom_Kisrhombille.app.docGraphics.DG_KisrhombilleTessellationWithNumberedTriangles();
    }
}

