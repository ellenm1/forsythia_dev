/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_2D;

import java.io.Serializable;
import org.fleen.geom_2D.DVector;
import org.fleen.geom_2D.GD;

public class DPoint
implements Serializable {
    private static final long serialVersionUID = -329615825957656621L;
    public double x;
    public double y;

    public DPoint() {
    }

    public DPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public DPoint(double[] xy) {
        this.x = xy[0];
        this.y = xy[1];
    }

    public DPoint(DPoint p) {
        this.x = p.x;
        this.y = p.y;
    }

    public void setLocation(double[] coor) {
        this.x = coor[0];
        this.y = coor[1];
    }

    public void setLocation(DPoint p) {
        this.x = p.x;
        this.y = p.y;
    }

    public double getDistance(DPoint p) {
        return GD.getDistance_PointPoint(this.x, this.y, p.x, p.y);
    }

    public double getDistance(double x, double y) {
        return GD.getDistance_PointPoint(this.x, this.y, x, y);
    }

    public double getDirection(DPoint p) {
        return GD.getDirection_PointPoint(this.x, this.y, p.x, p.y);
    }

    public double getDirection(double x, double y) {
        return GD.getDirection_PointPoint(this.x, this.y, x, y);
    }

    public DPoint getPoint(double dir, double dis) {
        double[] a = GD.getPoint_PointDirectionInterval(this.x, this.y, dir, dis);
        return new DPoint(a);
    }

    public DPoint getPoint(DVector v) {
        return this.getPoint(v.direction, v.magnitude);
    }

    public void applyVector(DVector vector) {
        double[] a = vector.getLocation(this);
        this.x = a[0];
        this.y = a[1];
    }

    public int hashCode() {
        return (int)(this.x + 23.0 * this.y);
    }

    public boolean equals(Object a) {
        DPoint b = (DPoint)a;
        return this.x == b.x && this.y == b.y;
    }

    public String toString() {
        return "[" + this.x + "," + this.y + "]";
    }
}

