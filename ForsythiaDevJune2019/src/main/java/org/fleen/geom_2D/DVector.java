/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_2D;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.GD;

public class DVector {
    public double direction = 0.0;
    public double magnitude = 0.0;

    public DVector() {
    }

    public /* varargs */ DVector(DVector ... v) {
        for (DVector a : v) {
            this.add(a);
        }
    }

    public DVector(double direction, double magnitude) {
        this.direction = direction;
        this.magnitude = magnitude;
    }

    public double[] getLocation(DPoint p) {
        double[] a = GD.getPoint_PointDirectionInterval(p.x, p.y, this.direction, this.magnitude);
        return a;
    }

    public double[] getLocation(double x, double y) {
        double[] a = GD.getPoint_PointDirectionInterval(x, y, this.direction, this.magnitude);
        return a;
    }

    public double[] getLocation(double[] p) {
        double[] a = GD.getPoint_PointDirectionInterval(p[0], p[1], this.direction, this.magnitude);
        return a;
    }

    public void move(DPoint p) {
        double[] a = this.getLocation(p);
        p.x = a[0];
        p.y = a[1];
    }

    public static final /* varargs */ DVector getSum(DVector ... vectors) {
        DVector v2;
        double[] xy = new double[]{0.0, 0.0};
        for (DVector v2 : vectors) {
            xy = GD.getPoint_PointDirectionInterval(xy[0], xy[1], v2.direction, v2.magnitude);
        }
        v2 = new DVector();
        v2.direction = GD.getDirection_PointPoint(0.0, 0.0, xy[0], xy[1]);
        v2.magnitude = GD.getDistance_PointPoint(0.0, 0.0, xy[0], xy[1]);
        return v2;
    }

    public void add(DVector v) {
        double[] xy = new double[]{0.0, 0.0};
        xy = GD.getPoint_PointDirectionInterval(xy[0], xy[1], this.direction, this.magnitude);
        xy = GD.getPoint_PointDirectionInterval(xy[0], xy[1], v.direction, v.magnitude);
        this.direction = GD.getDirection_PointPoint(0.0, 0.0, xy[0], xy[1]);
        this.magnitude = GD.getDistance_PointPoint(0.0, 0.0, xy[0], xy[1]);
    }

    public String toString() {
        return "[" + this.direction + "," + this.magnitude + "]";
    }
}

