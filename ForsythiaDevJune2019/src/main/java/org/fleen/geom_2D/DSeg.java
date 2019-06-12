/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_2D;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.GD;

public class DSeg {
    public DPoint p0;
    public DPoint p1;

    public DSeg(double x0, double y0, double x1, double y1) {
        this.p0 = new DPoint(x0, y0);
        this.p1 = new DPoint(x1, y1);
    }

    public DSeg(DPoint p0, DPoint p1) {
        this.p0 = p0;
        this.p1 = p1;
    }

    public double getForeward() {
        return GD.getDirection_PointPoint(this.p0.x, this.p0.y, this.p1.x, this.p1.y);
    }

    public double getLength() {
        return GD.getDistance_PointPoint(this.p0.x, this.p0.y, this.p1.x, this.p1.y);
    }

    public double[] getPointAtRealOffset(double a) {
        return GD.getPoint_PointPointInterval(this.p0.x, this.p0.y, this.p1.x, this.p1.y, a);
    }

    public DPoint getDPointAtRealOffset(double a) {
        return new DPoint(GD.getPoint_PointPointInterval(this.p0.x, this.p0.y, this.p1.x, this.p1.y, a));
    }

    public double[] getPointAtProportionalOffset(double a) {
        return GD.getPoint_PointPointInterval(this.p0.x, this.p0.y, this.p1.x, this.p1.y, a *= this.getLength());
    }

    public double getDistance(double x, double y) {
        return GD.getDistance_PointSeg(x, y, this.p0.x, this.p0.y, this.p1.x, this.p1.y);
    }

    public DPoint getPoint_Closest(DPoint p) {
        double[] c = GD.getPoint_ClosestOnSegToPoint(this.p0.x, this.p0.y, this.p1.x, this.p1.y, p.x, p.y);
        return new DPoint(c);
    }

    public boolean intersects(DSeg other) {
        return GD.testIntersection_2Segs(this.p0.x, this.p0.y, this.p1.x, this.p1.y, other.p0.x, other.p0.y, other.p1.x, other.p1.y);
    }

    public double getDistance(DSeg s) {
        if (this.intersects(s)) {
            return 0.0;
        }
        double[] a = new double[]{GD.getDistance_PointSeg(s.p0.x, s.p0.y, this.p0.x, this.p0.y, this.p1.x, this.p1.y), GD.getDistance_PointSeg(s.p1.x, s.p1.y, this.p0.x, this.p0.y, this.p1.x, this.p1.y), GD.getDistance_PointSeg(this.p0.x, this.p0.y, s.p0.x, s.p0.y, s.p1.x, s.p1.y), GD.getDistance_PointSeg(this.p1.x, this.p1.y, s.p0.x, s.p0.y, s.p1.x, s.p1.y)};
        double smallest = Double.MAX_VALUE;
        for (double test : a) {
            if (test >= smallest) continue;
            smallest = test;
        }
        return smallest;
    }

    public boolean equals(Object a) {
        DSeg b = (DSeg)a;
        return b.p0.x == this.p0.x && b.p0.y == this.p0.y && b.p1.x == this.p1.x && b.p1.y == this.p1.y;
    }
}

