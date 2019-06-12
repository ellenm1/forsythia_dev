/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille;

import java.util.ArrayList;
import java.util.List;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.GD;
import org.fleen.geom_Kisrhombille.KPoint;

public class KSeg {
    private KPoint vertex0;
    private KPoint vertex1;
    List<KPoint> betweenpoints = null;
    private Integer hashcode = null;

    public KSeg(KPoint a, KPoint b) {
        int gta = a.getGeneralType();
        int gtb = b.getGeneralType();
        if (gta == 0) {
            if (gtb == 1) {
                this.vertex0 = a;
                this.vertex1 = b;
            } else {
                this.vertex0 = b;
                this.vertex1 = a;
            }
        } else if (gta == 1) {
            if (gtb == 2) {
                this.vertex0 = a;
                this.vertex1 = b;
            } else {
                this.vertex0 = b;
                this.vertex1 = a;
            }
        } else if (gtb == 0) {
            this.vertex0 = a;
            this.vertex1 = b;
        } else {
            this.vertex0 = b;
            this.vertex1 = a;
        }
    }

    public KPoint getVertex0() {
        return this.vertex0;
    }

    public KPoint getVertex1() {
        return this.vertex1;
    }

    public boolean intersects(KSeg s) {
        DPoint s0p0 = this.vertex0.getBasicPoint2D();
        DPoint s0p1 = this.vertex1.getBasicPoint2D();
        DPoint s1p0 = s.vertex0.getBasicPoint2D();
        DPoint s1p1 = s.vertex1.getBasicPoint2D();
        boolean i = GD.getIntersection_SegSeg(s0p0.x, s0p0.y, s0p1.x, s0p1.y, s1p0.x, s1p0.y, s1p1.x, s1p1.y) != null;
        return i;
    }

    public List<KPoint> getBetweenPoints() {
        if (this.betweenpoints == null) {
            this.initBetweenPoints();
        }
        return this.betweenpoints;
    }

    private void initBetweenPoints() {
        this.betweenpoints = new ArrayList<KPoint>();
        int d = this.vertex0.getDirection(this.vertex1);
        KPoint p0 = this.vertex0.getVertex_Adjacent(d);
        while (!p0.equals(this.vertex1)) {
            this.betweenpoints.add(p0);
            p0 = p0.getVertex_Adjacent(d);
        }
    }

    public int hashCode() {
        if (this.hashcode == null) {
            this.hashcode = this.vertex0.coors[0] * 65536 + this.vertex0.coors[1] * 32668 + this.vertex0.coors[2] * 16384 + this.vertex0.coors[3] * 16 + this.vertex1.coors[0] * 8192 + this.vertex1.coors[1] * 4096 + this.vertex1.coors[2] * 2048 + this.vertex1.coors[3];
        }
        return this.hashcode;
    }

    public boolean equals(Object a) {
        KSeg equals_a = (KSeg)a;
        if (equals_a.hashCode() == this.hashCode()) {
            return equals_a.vertex0.coors[0] == this.vertex0.coors[0] && equals_a.vertex0.coors[1] == this.vertex0.coors[1] && equals_a.vertex0.coors[2] == this.vertex0.coors[2] && equals_a.vertex0.coors[3] == this.vertex0.coors[3] && equals_a.vertex1.coors[0] == this.vertex1.coors[0] && equals_a.vertex1.coors[1] == this.vertex1.coors[1] && equals_a.vertex1.coors[2] == this.vertex1.coors[2] && equals_a.vertex1.coors[3] == this.vertex1.coors[3];
        }
        return false;
    }

    public String toString() {
        return "[" + this.vertex0 + "," + this.vertex1 + "]";
    }
}

