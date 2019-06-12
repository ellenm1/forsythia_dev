/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille;

import java.io.Serializable;

public class KMetagonVector
implements Serializable {
    private static final long serialVersionUID = 1794434316990821717L;
    public int directiondelta;
    public double relativeinterval;
    private static final double RELATIVEINTERVALEQUALITYERROR = 1.0E-6;

    public KMetagonVector(int directiondelta, double relativeinterval) {
        this.directiondelta = directiondelta;
        this.relativeinterval = relativeinterval;
    }

    public KMetagonVector() {
    }

    public boolean equals(Object a) {
        boolean e;
        KMetagonVector b = (KMetagonVector)a;
        boolean bl = e = this.directiondelta == b.directiondelta;
        if (!e) {
            return false;
        }
        e = this.equals(this.relativeinterval, b.relativeinterval, 1.0E-6);
        return e;
    }

    public int hashCode() {
        return this.directiondelta * 7919;
    }

    public String toString() {
        String s = "[" + this.directiondelta + "," + this.relativeinterval + "]";
        return s;
    }

    public Object clone() {
        return new KMetagonVector(this.directiondelta, this.relativeinterval);
    }

    private boolean equals(double a, double b, double error) {
        if (a < b) {
            return b - a < error;
        }
        return a - b < error;
    }
}

