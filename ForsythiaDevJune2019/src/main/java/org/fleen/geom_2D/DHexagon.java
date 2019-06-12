/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_2D;

import java.util.List;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_2D.GD;

public class DHexagon
extends DPolygon {
    private static final long serialVersionUID = 1173218112060390822L;

    public DHexagon(DPoint p0, DPoint p1, DPoint p2, DPoint p3, DPoint p4, DPoint p5) {
        super(p0, p1, p2, p3, p4, p5);
    }

    public DHexagon(DPolygon p) {
        super(p);
    }

    public DPoint getCenter() {
        DPoint p0 = (DPoint)this.get(0);
        DPoint p3 = (DPoint)this.get(3);
        return new DPoint(GD.getPoint_Mid2Points(p0.x, p0.y, p3.x, p3.y));
    }

    public double getRadius() {
        DPoint p0 = (DPoint)this.get(0);
        DPoint p3 = (DPoint)this.get(3);
        return p0.getDistance(p3) / 2.0;
    }

    public double getInnerRadius() {
        return this.getRadius() * 2.0 / GD.SQRT3;
    }
}

