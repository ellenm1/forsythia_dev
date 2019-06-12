/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.fleen.geom_Kisrhombille.GK;
import org.fleen.geom_Kisrhombille.KAnchor;
import org.fleen.geom_Kisrhombille.KMetagonVector;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.KVector;

public class KMetagon
implements Serializable {
    private static final long serialVersionUID = -4829434091754784682L;
    private static final int MAXTRANSITIONSFORBASEINTERVALNORMALIZATION = 200;
    private static final KPoint ORIGIN = new KPoint(0, 0, 0, 0);
    public double baseinterval;
    public KMetagonVector[] vectors;
    private static final int DEFAULTFOREWARD = 0;
    private static final double DEFAULTSCALE = 1.0;
    private static final double BASEINTERVALEQUALITYERROR = 1.0E-6;

    public KMetagon(KMetagon km) {
        this.baseinterval = km.baseinterval;
        this.vectors = new KMetagonVector[km.vectors.length];
        for (int i = 0; i < this.vectors.length; ++i) {
            this.vectors[i] = (KMetagonVector)km.vectors[i].clone();
        }
    }

    public KMetagon(double baseinterval, KMetagonVector[] vectors) {
        this.baseinterval = baseinterval;
        this.vectors = vectors;
        this.normalizeBaseInterval();
    }

    public KMetagon(KPolygon polygon) {
        this(polygon.toArray(new KPoint[polygon.size()]));
    }

    public KMetagon(List<KPoint> vertices) {
        this(vertices.toArray(new KPoint[vertices.size()]));
    }

    public /* varargs */ KMetagon(KPoint ... vertices) {
        this.baseinterval = vertices[0].getDistance(vertices[1]);
        int dir0 = vertices[0].getDirection(vertices[1]);
        int vectorcount = vertices.length - 2;
        this.vectors = new KMetagonVector[vectorcount];
        for (int i = 0; i < vectorcount; ++i) {
            int dir1 = vertices[i + 1].getDirection(vertices[i + 2]);
            double dis = vertices[i + 1].getDistance(vertices[i + 2]);
            int delta = GK.getDirectionDelta(dir0, dir1);
            this.vectors[i] = new KMetagonVector(delta, dis / this.baseinterval);
            dir0 = dir1;
        }
        this.normalizeBaseInterval();
    }

    private void normalizeBaseInterval() {
        int prospectivebaseinterval = 0;
        boolean found = false;
        for (int transitioncount = 0; !found && transitioncount < 200; ++transitioncount) {
            switch (transitioncount % 4) {
                case 0: {
                    prospectivebaseinterval += 2;
                    break;
                }
                case 1: {
                    ++prospectivebaseinterval;
                    break;
                }
                case 2: {
                    ++prospectivebaseinterval;
                    break;
                }
                case 3: {
                    prospectivebaseinterval += 2;
                }
            }
            found = this.tryBaseInterval(prospectivebaseinterval);
        }
        if (!found) {
            throw new IllegalArgumentException("KMetagon.normalizeBaseInterval() failed : KMETAGON : " + this);
        }
        this.baseinterval = prospectivebaseinterval;
    }

    private boolean tryBaseInterval(int prospectivebaseinterval) {
        KPoint v = new KPoint(0, 0, 0, 0);
        KVector e = new KVector(0, prospectivebaseinterval);
        if ((v = v.getVertex_Vector(e)) == null) {
            return false;
        }
        for (int i = 0; i < this.vectors.length; ++i) {
            e.direction = (e.direction + this.vectors[i].directiondelta + 12) % 12;
            e.distance = this.vectors[i].relativeinterval * (double)prospectivebaseinterval;
            if ((v = v.getVertex_Vector(e)) != null) continue;
            return false;
        }
        return true;
    }

    public boolean isClockwise() {
        int a = 0;
        for (KMetagonVector v : this.vectors) {
            a += v.directiondelta;
        }
        return a > 0;
    }

    public void reverseDeltas() {
        for (KMetagonVector v : this.vectors) {
            v.directiondelta *= -1;
        }
    }

    public KPolygon getPolygon() {
        return this.getPolygon(ORIGIN, 0, 1.0);
    }

    public KPolygon getPolygon(boolean twist) {
        return this.getPolygon(ORIGIN, 0, 1.0, twist);
    }

    public KPolygon getPolygon(KPoint v0, int d0) {
        return this.getPolygon(v0, d0, 1.0);
    }

    public KPolygon getPolygon(KPoint v0, KPoint v1) {
        return this.getPolygon(v0, v1, true);
    }

    public KPolygon getPolygon(KPoint v0, KPoint v1, boolean twist) {
        int dir = v0.getDirection(v1);
        double scale = v0.getDistance(v1) / this.baseinterval;
        return this.getPolygon(v0, dir, scale, twist);
    }

    public KPolygon getPolygon(KAnchor anchor) {
        return this.getPolygon(anchor.v0, anchor.v1, anchor.twist);
    }

    public KPolygon getPolygon(double v0v1dis) {
        double scale = v0v1dis / this.baseinterval;
        KPolygon p = this.getPolygon(ORIGIN, 0, scale);
        return p;
    }

    public KPolygon getScaledPolygon(int scale) {
        KPolygon p = this.getPolygon(ORIGIN, 0, scale);
        return p;
    }

    public KPolygon getPolygon(int scale, boolean twist) {
        KPolygon p = this.getPolygon(ORIGIN, 0, scale, twist);
        return p;
    }

    public KPolygon getPolygon(KPoint v0, int direction, double scale) {
        KPolygon p = this.getPolygon(v0, direction, scale, true);
        return p;
    }

    public KPolygon getPolygon(KPoint v0, int direction, double scale, boolean twist) {
        KPolygon p = new KPolygon();
        p.add(v0);
        KPoint vertex = v0;
        KVector vector = new KVector(direction, this.baseinterval * scale);
        vertex = vertex.getVertex_Vector(vector);
        p.add(vertex);
        for (int i = 0; i < this.vectors.length; ++i) {
            vector.direction = twist ? (vector.direction + this.vectors[i].directiondelta + 12) % 12 : (vector.direction - this.vectors[i].directiondelta + 12) % 12;
            vector.distance = scale * this.vectors[i].relativeinterval * this.baseinterval;
            if ((vertex = vertex.getVertex_Vector(vector)) == null) {
                return null;
            }
            p.add(vertex);
        }
        return p;
    }

    public List<KAnchor> getAnchorOptions(KPolygon polygon) {
        KPolygon pmeta;
        KPoint v1;
        KPolygon protated;
        KPoint v0;
        int polygonsize = polygon.size();
        if (polygonsize != this.vectors.length + 2) {
            return null;
        }
        ArrayList<KAnchor> anchors = new ArrayList<KAnchor>();
        for (int i = 0; i < polygonsize; ++i) {
            protated = (KPolygon)polygon.clone();
            Collections.rotate(protated, i);
            v0 = (KPoint)protated.get(0);
            v1 = (KPoint)protated.get(1);
            pmeta = this.getPolygon(v0, v1, true);
            if (pmeta != null && protated.equals(pmeta)) {
                anchors.add(new KAnchor(v0, v1, true));
            }
            if ((pmeta = this.getPolygon(v0, v1, false)) == null || !protated.equals(pmeta)) continue;
            anchors.add(new KAnchor(v0, v1, false));
        }
        KPolygon preversed = (KPolygon)polygon.clone();
        Collections.reverse(preversed);
        for (int i = 0; i < polygonsize; ++i) {
            protated = (KPolygon)preversed.clone();
            Collections.rotate(protated, i);
            v0 = (KPoint)protated.get(0);
            v1 = (KPoint)protated.get(1);
            pmeta = this.getPolygon(v0, v1, true);
            if (pmeta != null && protated.equals(pmeta)) {
                anchors.add(new KAnchor(v0, v1, true));
            }
            if ((pmeta = this.getPolygon(v0, v1, false)) == null || !protated.equals(pmeta)) continue;
            anchors.add(new KAnchor(v0, v1, false));
        }
        if (anchors.isEmpty()) {
            return null;
        }
        return anchors;
    }

    public int hashCode() {
        return this.vectors.length * 7919;
    }

    public boolean equals(Object a) {
        if (a.hashCode() != this.hashCode()) {
            return false;
        }
        KMetagon m = (KMetagon)a;
        if (!this.equals(m.baseinterval, this.baseinterval, 1.0E-6)) {
            return false;
        }
        int s = this.vectors.length;
        if (m.vectors.length != s) {
            return false;
        }
        for (int i = 0; i < s; ++i) {
            if (m.vectors[i].equals(this.vectors[i])) continue;
            return false;
        }
        return true;
    }

    private boolean equals(double a, double b, double error) {
        if (a < b) {
            return b - a < error;
        }
        return a - b < error;
    }

    public String toString() {
        StringBuffer a = new StringBuffer();
        a.append("[" + this.getClass().getSimpleName() + " ");
        a.append("baseinterval=" + this.baseinterval + " ");
        a.append("vectors=[");
        for (int i = 0; i < this.vectors.length - 1; ++i) {
            a.append(String.valueOf(this.vectors[i].toString()) + " ");
        }
        a.append(String.valueOf(this.vectors[this.vectors.length - 1].toString()) + "]]");
        return a.toString();
    }
}

