/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.Set;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_2D.DVector;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.app.docGraphics.DocGraphics;

public class DG_KPolygonMarkedWithKGridParams
extends DocGraphics {
    @Override
    void doGraphics() {
        this.initImage(1600, 800, 60.0, WHITE);
        Set<KPoint> points = this.strokeGrid(8, 3.0, GREY6);
        KPolygon ptriangle = new KPolygon(new KPoint(-3, -4, -1, 0), new KPoint(-3, -2, 1, 0), new KPoint(-1, -2, -1, 0));
        this.renderPolygonWithKGridParamMarks(ptriangle, 6.0, 16.0, GREEN);
    }

    void renderPolygonWithKGridParamMarks(KPolygon polygon, double strokethickness, double dotspan, Color color) {
        this.renderPolygon(polygon, strokethickness, dotspan, color);
        DPolygon dp0 = polygon.getDefaultPolygon2D();
        List<DVector> vouter = dp0.getInnerOuterPolygonVectors(-48.0 / this.imagescale);
        DPoint v0glyphcenter = ((DPoint)dp0.get(0)).getPoint(vouter.get(0));
        DPoint v1glyphcenter = ((DPoint)dp0.get(1)).getPoint(vouter.get(1));
        this.renderVertexNameGlyph((DPoint)dp0.get(0), BLACK, 33, -44, "P0");
    }

    void renderVertexNameGlyph(DPoint p, Color color, int fontsize, int offset, String nameglyph) {
        AffineTransform graphicstransform = this.graphics.getTransform();
        double[] pt = new double[]{p.x, p.y};
        graphicstransform.transform(pt, 0, pt, 0, 1);
        this.graphics.setTransform(new AffineTransform());
        this.graphics.setPaint(color);
        this.graphics.setFont(new Font("Sans", 0, fontsize));
        this.graphics.drawString(nameglyph, (float)(pt[0] + (double)offset), (float)pt[1]);
        this.graphics.setTransform(graphicstransform);
    }

    public static final void main(String[] a) {
        new org.fleen.geom_Kisrhombille.app.docGraphics.DG_KPolygonMarkedWithKGridParams();
    }
}

