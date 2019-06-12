/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.util.Set;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.GD;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.app.docGraphics.DocGraphics;

public class DG_GridWithAxes
extends DocGraphics {
    private static final double SHAFTLENGTH = 3.0;
    private static final double HEADLENGTH = 1.0;
    private static final double HEADSPAN = 0.8;

    @Override
    void doGraphics() {
        this.initImage(768, 256, 30.0, WHITE);
        Set<KPoint> points = this.strokeGrid(8, 4.0, GREY6);
        for (KPoint p : points) {
            this.renderPoint(p, 12.0, GREY6);
        }
        this.renderAxesStar();
    }

    private void renderAxesStar() {
        DPoint pc = new KPoint(0, 0, 0, 0).getBasicPoint2D();
        double[] dir = new double[12];
        DPoint[] pa = new DPoint[12];
        for (int i = 0; i < 12; ++i) {
            dir[i] = (double)i * 3.141592653589793 / 6.0;
            pa[i] = new DPoint(GD.getPoint_PointDirectionInterval(pc.x, pc.y, dir[i], 3.0));
            this.strokeSeg(pc, pa[i], 6.0, BLUE);
            this.renderArrowhead(pa[i], dir[i]);
        }
    }

    private void renderArrowhead(DPoint p, double d) {
        double dleft = GD.normalizeDirection(d - 1.5707963267948966);
        double dright = GD.normalizeDirection(d + 1.5707963267948966);
        DPoint pleft = p.getPoint(dleft, 0.4);
        DPoint pright = p.getPoint(dright, 0.4);
        DPoint pend = p.getPoint(d, 1.0);
        Path2D.Double path = new Path2D.Double();
        ((Path2D)path).moveTo(pleft.x, pleft.y);
        ((Path2D)path).lineTo(pend.x, pend.y);
        ((Path2D)path).lineTo(pright.x, pright.y);
        path.closePath();
        this.graphics.setPaint(BLUE);
        this.graphics.fill(path);
    }

    public static final void main(String[] a) {
        new org.fleen.geom_Kisrhombille.app.docGraphics.DG_GridWithAxes();
    }
}

