/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_2D;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.GD;

public class DCircle {
    public double x = 0.0;
    public double y = 0.0;
    public double r = 0.0;

    public DCircle(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public DCircle(double[] c, double r) {
        this.x = c[0];
        this.y = c[1];
        this.r = r;
    }

    public DCircle(DPoint c, double r) {
        this.x = c.x;
        this.y = c.y;
        this.r = r;
    }

    public DCircle() {
    }

    public double getRadius() {
        return this.r;
    }

    public double getDiameter() {
        return this.r * 2.0;
    }

    public double[] getCenter() {
        return new double[]{this.x, this.y};
    }

    public double getArea() {
        return 3.141592653589793 * this.r * this.r;
    }

    public double getCircumference() {
        return 6.283185307179586 * this.r;
    }

    public double getDistance(double[] p) {
        return GD.getDistance_PointCircle(p[0], p[1], this.x, this.y, this.r);
    }

    public boolean contains(double[] p) {
        return this.getDistance(p) < this.r;
    }
}

