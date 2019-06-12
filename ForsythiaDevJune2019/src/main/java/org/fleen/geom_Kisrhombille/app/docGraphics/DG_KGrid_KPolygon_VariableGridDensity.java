/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_Kisrhombille.KGrid;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.app.docGraphics.DocGraphics;

public class DG_KGrid_KPolygon_VariableGridDensity
extends DocGraphics {
    static final Color FILL0 = new Color(220, 255, 220);

    @Override
    void doGraphics() {
        this.initImage(768, 512, 60.0, WHITE);
        Set<KPoint> points = this.strokeGrid(8, 4.0, GREY6);
        for (KPoint p : points) {
            this.renderPoint(p, 12.0, GREY6);
        }
        KPolygon a = new KPolygon(new KPoint(-1, -1, 0, 0), new KPoint(-1, 0, 1, 0), new KPoint(0, 1, 1, 0), new KPoint(1, 1, 0, 0), new KPoint(1, 0, -1, 0), new KPoint(0, -1, -1, 0));
        this.fillPolygon(a, FILL0);
        this.strokePolygon(a, 4.0, GREEN);
        Path2D.Double clip = a.getDefaultPolygon2D().getPath2D();
        this.graphics.setClip(clip);
        KGrid g = new KGrid(new double[]{0.0, 0.0}, 0.0, true, 0.25);
        this.renderKGrid(g);
        this.graphics.setClip(null);
        this.renderLabel("density=3");
    }

    private void renderLabel(String label) {
        AffineTransform graphicstransform = this.graphics.getTransform();
        this.graphics.setTransform(new AffineTransform());
        this.graphics.setPaint(GREY2);
        this.graphics.setFont(new Font("Sans", 0, 30));
        this.graphics.drawString(label, 300.0f, 66.0f);
        this.graphics.setTransform(graphicstransform);
    }

    private void renderKGrid(KGrid g) {
        this.renderKGrid(g, 8, 2.0, GREY3);
    }

    Set<KPoint> renderKGrid(KGrid grid, int range, double thickness, Color color) {
        HashSet<KPoint> points = new HashSet<KPoint>();
        Set<KPoint> v0s = this.getV0s(range);
        for (KPoint p : v0s) {
            points.addAll(Arrays.asList(this.getClockKPoints(p)));
            this.strokeClock(grid, p, thickness, color);
        }
        return points;
    }

    void strokeClock(KGrid grid, KPoint v, double thickness, Color color) {
        KPoint[] cp = this.getClockKPoints(v);
        for (int i = 1; i < cp.length; ++i) {
            int j = i + 1;
            if (j == cp.length) {
                j = 1;
            }
            this.strokeSeg(grid, cp[i], cp[j], thickness, color);
            this.strokeSeg(grid, cp[0], cp[i], thickness, color);
        }
    }

    void strokeSeg(KGrid grid, KPoint v0, KPoint v1, double thickness, Color color) {
        DPoint p0 = new DPoint(grid.getPoint2D(v0));
        DPoint p1 = new DPoint(grid.getPoint2D(v1));
        this.strokeSeg(p0, p1, thickness, color);
    }

    public static final void main(String[] a) {
        new org.fleen.geom_Kisrhombille.app.docGraphics.DG_KGrid_KPolygon_VariableGridDensity();
    }
}

