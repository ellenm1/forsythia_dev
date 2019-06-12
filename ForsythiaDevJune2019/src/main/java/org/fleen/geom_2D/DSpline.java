/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_2D;

import java.awt.geom.Point2D;
import java.util.List;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.Spline;

public class DSpline {
    private double[] t;
    private Spline splineX;
    private Spline splineY;
    private double length;

    public DSpline(List<? extends DPoint> points) {
        int s = points.size();
        double[] x = new double[s];
        double[] y = new double[s];
        for (int i = 0; i < s; ++i) {
            x[i] = points.get((int)i).x;
            y[i] = points.get((int)i).y;
        }
        this.init(x, y);
    }

    public DSpline(Point2D[] points) {
        double[] x = new double[points.length];
        double[] y = new double[points.length];
        for (int i = 0; i < points.length; ++i) {
            x[i] = points[i].getX();
            y[i] = points[i].getY();
        }
        this.init(x, y);
    }

    public DSpline(double[][] points) {
        double[] x = new double[points.length];
        double[] y = new double[points.length];
        for (int i = 0; i < points.length; ++i) {
            x[i] = points[i][0];
            y[i] = points[i][1];
        }
        this.init(x, y);
    }

    public DSpline(double[] x, double[] y) {
        this.init(x, y);
    }

    private void init(double[] x, double[] y) {
        int i;
        if (x.length != y.length) {
            throw new IllegalArgumentException("Arrays must have the same length.");
        }
        if (x.length < 2) {
            throw new IllegalArgumentException("Spline edges must have at least two points.");
        }
        this.t = new double[x.length];
        this.t[0] = 0.0;
        for (i = 1; i < this.t.length; ++i) {
            double lx = x[i] - x[i - 1];
            double ly = y[i] - y[i - 1];
            this.t[i] = 0.0 == lx ? Math.abs(ly) : (0.0 == ly ? Math.abs(lx) : Math.sqrt(lx * lx + ly * ly));
            this.length += this.t[i];
            double[] arrd = this.t;
            int n = i;
            arrd[n] = arrd[n] + this.t[i - 1];
        }
        for (i = 1; i < this.t.length - 1; ++i) {
            this.t[i] = this.t[i] / this.length;
        }
        this.t[this.t.length - 1] = 1.0;
        this.splineX = new Spline(this.t, x);
        this.splineY = new Spline(this.t, y);
    }

    public double[] getPoint(double t) {
        double[] result = new double[]{this.splineX.getValue(t), this.splineY.getValue(t)};
        return result;
    }

    public boolean checkValues() {
        return this.splineX.checkValues() && this.splineY.checkValues();
    }

    public double getDx(double t) {
        return this.splineX.getDx(t);
    }

    public double getDy(double t) {
        return this.splineY.getDx(t);
    }

    public Spline getSplineX() {
        return this.splineX;
    }

    public Spline getSplineY() {
        return this.splineY;
    }

    public double getLength() {
        return this.length;
    }
}

