/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_2D;

import java.util.Arrays;

class Spline {
    private double[] xx;
    private double[] yy;
    private double[] a;
    private double[] b;
    private double[] c;
    private double[] d;
    private int storageIndex = 0;

    public Spline(double[] xx, double[] yy) {
        this.setValues(xx, yy);
    }

    public void setValues(double[] xx, double[] yy) {
        this.xx = xx;
        this.yy = yy;
        if (xx.length > 1) {
            this.calculateCoefficients();
        }
    }

    public double getValue(double x) {
        if (this.xx.length == 0) {
            return Double.NaN;
        }
        if (this.xx.length == 1) {
            if (this.xx[0] == x) {
                return this.yy[0];
            }
            return Double.NaN;
        }
        int index = Arrays.binarySearch(this.xx, x);
        if (index > 0) {
            return this.yy[index];
        }
        if ((index = - index + 1 - 1) < 0) {
            return this.yy[0];
        }
        return this.a[index] + this.b[index] * (x - this.xx[index]) + this.c[index] * Math.pow(x - this.xx[index], 2.0) + this.d[index] * Math.pow(x - this.xx[index], 3.0);
    }

    public double getFastValue(double x) {
        if (this.storageIndex <= -1 || this.storageIndex >= this.xx.length - 1 || x <= this.xx[this.storageIndex] || x >= this.xx[this.storageIndex + 1]) {
            int index = Arrays.binarySearch(this.xx, x);
            if (index > 0) {
                return this.yy[index];
            }
            this.storageIndex = index = - index + 1 - 1;
        }
        if (this.storageIndex < 0) {
            return this.yy[0];
        }
        double value = x - this.xx[this.storageIndex];
        return this.a[this.storageIndex] + this.b[this.storageIndex] * value + this.c[this.storageIndex] * (value * value) + this.d[this.storageIndex] * (value * value * value);
    }

    public boolean checkValues() {
        return this.xx.length >= 2;
    }

    public double getDx(double x) {
        if (this.xx.length == 0 || this.xx.length == 1) {
            return 0.0;
        }
        int index = Arrays.binarySearch(this.xx, x);
        if (index < 0) {
            index = - index + 1 - 1;
        }
        return this.b[index] + 2.0 * this.c[index] * (x - this.xx[index]) + 3.0 * this.d[index] * Math.pow(x - this.xx[index], 2.0);
    }

    private void calculateCoefficients() {
        int i;
        int N = this.yy.length;
        this.a = new double[N];
        this.b = new double[N];
        this.c = new double[N];
        this.d = new double[N];
        if (N == 2) {
            this.a[0] = this.yy[0];
            this.b[0] = this.yy[1] - this.yy[0];
            return;
        }
        double[] h = new double[N - 1];
        for (int i2 = 0; i2 < N - 1; ++i2) {
            this.a[i2] = this.yy[i2];
            h[i2] = this.xx[i2 + 1] - this.xx[i2];
            if (h[i2] != 0.0) continue;
            h[i2] = 0.01;
        }
        this.a[N - 1] = this.yy[N - 1];
        double[][] A = new double[N - 2][N - 2];
        double[] y = new double[N - 2];
        for (i = 0; i < N - 2; ++i) {
            y[i] = 3.0 * ((this.yy[i + 2] - this.yy[i + 1]) / h[i + 1] - (this.yy[i + 1] - this.yy[i]) / h[i]);
            A[i][i] = 2.0 * (h[i] + h[i + 1]);
            if (i > 0) {
                A[i][i - 1] = h[i];
            }
            if (i >= N - 3) continue;
            A[i][i + 1] = h[i + 1];
        }
        this.solve(A, y);
        for (i = 0; i < N - 2; ++i) {
            this.c[i + 1] = y[i];
            this.b[i] = (this.a[i + 1] - this.a[i]) / h[i] - (2.0 * this.c[i] + this.c[i + 1]) / 3.0 * h[i];
            this.d[i] = (this.c[i + 1] - this.c[i]) / (3.0 * h[i]);
        }
        this.b[N - 2] = (this.a[N - 1] - this.a[N - 2]) / h[N - 2] - (2.0 * this.c[N - 2] + this.c[N - 1]) / 3.0 * h[N - 2];
        this.d[N - 2] = (this.c[N - 1] - this.c[N - 2]) / (3.0 * h[N - 2]);
    }

    public void solve(double[][] A, double[] b) {
        int i;
        int n = b.length;
        for (i = 1; i < n; ++i) {
            A[i][i - 1] = A[i][i - 1] / A[i - 1][i - 1];
            A[i][i] = A[i][i] - A[i - 1][i] * A[i][i - 1];
            b[i] = b[i] - A[i][i - 1] * b[i - 1];
        }
        b[n - 1] = b[n - 1] / A[n - 1][n - 1];
        for (i = b.length - 2; i >= 0; --i) {
            b[i] = (b[i] - A[i][i + 1] * b[i + 1]) / A[i][i];
        }
    }
}

