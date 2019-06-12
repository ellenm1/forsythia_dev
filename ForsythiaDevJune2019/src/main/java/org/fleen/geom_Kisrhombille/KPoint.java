/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_Kisrhombille.GK;
import org.fleen.geom_Kisrhombille.KVector;

public class KPoint
implements Serializable {
    private static final long serialVersionUID = 5215014207521492571L;
    public int[] coors;

    public KPoint(int ant, int bat, int cat, int dog) {
        this.coors = new int[]{ant, bat, cat, dog};
    }

    public KPoint(int[] params) {
        this.coors = params;
    }

    public KPoint(KPoint v) {
        this.coors = new int[]{v.coors[0], v.coors[1], v.coors[2], v.coors[3]};
    }

    public KPoint() {
        this.coors = new int[4];
    }

    public void setCoordinates(int a, int b, int c, int d) {
        this.coors[0] = a;
        this.coors[1] = b;
        this.coors[2] = c;
        this.coors[3] = d;
    }

    public int getAnt() {
        return this.coors[0];
    }

    public int getBat() {
        return this.coors[1];
    }

    public int getCat() {
        return this.coors[2];
    }

    public int getDog() {
        return this.coors[3];
    }

    public static final int getAnt(int bat, int cat) {
        return bat - cat;
    }

    public static final int getBat(int ant, int cat) {
        return ant + cat;
    }

    public static final int getCat(int ant, int bat) {
        return bat - ant;
    }

    public int getGeneralType() {
        switch (this.coors[3]) {
            case 0: {
                return 2;
            }
            case 1: {
                return 0;
            }
            case 2: {
                return 1;
            }
            case 3: {
                return 0;
            }
            case 4: {
                return 1;
            }
            case 5: {
                return 0;
            }
        }
        throw new IllegalArgumentException("VERTEX TYPE INVALID");
    }

    public int getDirection(KPoint v) {
        return GK.getDirection_VertexVertex(this, v);
    }

    public double getDistance(KPoint v) {
        return GK.getDistance_VertexVertex(this.coors[0], this.coors[1], this.coors[2], this.coors[3], v.coors[0], v.coors[1], v.coors[2], v.coors[3]);
    }

    public KPoint getVertex_Adjacent(int dir) {
        return GK.getVertex_Adjacent(this, dir);
    }

    public KPoint getVertex_Transitionswise(int dir, int trans) {
        int[] v1coor = new int[4];
        GK.getVertex_Transitionswise(this.coors[0], this.coors[1], this.coors[2], this.coors[3], dir, trans, v1coor);
        if (v1coor[3] == -1) {
            return null;
        }
        return new KPoint(v1coor);
    }

    public KPoint getVertex_DirDis(int dir, double dis) {
        int[] v1coor = new int[4];
        GK.getVertex_VertexVector(this.coors[0], this.coors[1], this.coors[2], this.coors[3], dir, dis, v1coor);
        if (v1coor[3] == -1) {
            return null;
        }
        return new KPoint(v1coor);
    }

    public KPoint getVertex_Vector(KVector vector) {
        int[] v1coor = new int[4];
        GK.getVertex_VertexVector(this.coors[0], this.coors[1], this.coors[2], this.coors[3], vector.direction, vector.distance, v1coor);
        if (v1coor[3] == -1) {
            return null;
        }
        return new KPoint(v1coor);
    }

    public boolean isColinear(KPoint v) {
        return GK.getColinear_VertexVertex(this.coors[0], this.coors[1], this.coors[2], this.coors[3], v.coors[0], v.coors[1], v.coors[2], v.coors[3]);
    }

    public double[] getBasicPointCoor() {
        double[] c = new double[2];
        GK.getBasicPoint2D_Vertex(this.coors[0], this.coors[1], this.coors[2], this.coors[3], c);
        return c;
    }

    public DPoint getBasicPoint2D() {
        return new DPoint(this.getBasicPointCoor());
    }

    public static final List<KPoint> getV12LocalGroup(KPoint v) {
        ArrayList<KPoint> a = new ArrayList<KPoint>();
        a.add(new KPoint(v.coors[0], v.coors[1], v.coors[2], 1));
        a.add(new KPoint(v.coors[0], v.coors[1], v.coors[2], 2));
        a.add(new KPoint(v.coors[0], v.coors[1], v.coors[2], 3));
        a.add(new KPoint(v.coors[0], v.coors[1], v.coors[2], 4));
        a.add(new KPoint(v.coors[0], v.coors[1], v.coors[2], 5));
        a.add(new KPoint(v.coors[0] + 1, v.coors[1], v.coors[2] - 1, 2));
        a.add(new KPoint(v.coors[0] + 1, v.coors[1], v.coors[2] - 1, 1));
        a.add(new KPoint(v.coors[0], v.coors[1] - 1, v.coors[2] - 1, 4));
        a.add(new KPoint(v.coors[0], v.coors[1] - 1, v.coors[2] - 1, 3));
        a.add(new KPoint(v.coors[0], v.coors[1] - 1, v.coors[2] - 1, 2));
        a.add(new KPoint(v.coors[0] - 1, v.coors[1] - 1, v.coors[2], 5));
        a.add(new KPoint(v.coors[0] - 1, v.coors[1] - 1, v.coors[2], 4));
        return a;
    }

    public boolean equals(Object a) {
        KPoint b = (KPoint)a;
        return this.coors[0] == b.coors[0] && this.coors[1] == b.coors[1] && this.coors[2] == b.coors[2] && this.coors[3] == b.coors[3];
    }

    public int hashCode() {
        int a = this.coors[0];
        a = a * 31 + this.coors[1];
        a = a * 37 + this.coors[2];
        a = a * 41 + this.coors[3];
        return a;
    }

    public String toString() {
        String s = "[" + this.coors[0] + "," + this.coors[1] + "," + this.coors[2] + "," + this.coors[3] + "]";
        return s;
    }

    public Object clone() {
        return new KPoint(this.coors[0], this.coors[1], this.coors[2], this.coors[3]);
    }
}

