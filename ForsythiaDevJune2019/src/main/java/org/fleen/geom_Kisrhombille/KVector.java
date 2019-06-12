/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille;

public class KVector {
    public int direction;
    public double distance;

    public KVector() {
    }

    public KVector(int direction, double distance) {
        this.direction = direction;
        this.distance = distance;
    }

    public boolean equals(Object a) {
        KVector b = (KVector)a;
        return this.direction == b.direction && this.distance == b.distance;
    }

    public int hashCode() {
        int a = this.direction * 65536 + (int)this.distance;
        return a;
    }

    public String toString() {
        String s = "[" + this.direction + "," + this.distance + "]";
        return s;
    }
}

