/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_Kisrhombille.KMetagon;
import org.fleen.geom_Kisrhombille.KPoint;

public class KPolygon
extends ArrayList<KPoint>
implements Serializable {
    private static final long serialVersionUID = -7629682211684455666L;
    private KPolygon reticulation = null;

    public KPolygon() {
    }

    public KPolygon(int size) {
        super.size();
    }

    public KPolygon(List<KPoint> v) {
        super(v);
    }

    public /* varargs */ KPolygon(KPoint ... v) {
        this(Arrays.asList(v));
    }

    public KMetagon getKMetagon() {
        return new KMetagon(this);
    }

    public void reverse() {
        int s = this.size();
        if (s < 3) {
            return;
        }
        ArrayList<KPoint> a = new ArrayList<KPoint>(this.size());
        int vindex = 0;
        for (int i = 0; i < s; ++i) {
            a.add((KPoint)this.get(vindex));
            if (--vindex != -1) continue;
            vindex = s - 1;
        }
        this.clear();
        this.addAll(a);
    }

    public void rotate(int i) {
        Collections.rotate(this, i);
    }

    public void removeRedundantColinearVertices() {
        ArrayList<KPoint> rcv = new ArrayList<KPoint>();
        int s = this.size();
        for (int i = 0; i < s; ++i) {
            int inext;
            int iprev = i - 1;
            if (iprev == -1) {
                iprev = s - 1;
            }
            if ((inext = i + 1) == s) {
                inext = 0;
            }
            KPoint v = (KPoint)this.get(i);
            KPoint vprev = (KPoint)this.get(iprev);
            KPoint vnext = (KPoint)this.get(inext);
            if (vprev.getDirection(v) != vprev.getDirection(vnext)) continue;
            rcv.add(v);
        }
        this.removeAll(rcv);
    }

    public DPolygon getDefaultPolygon2D() {
        int s = this.size();
        DPolygon p = new DPolygon(s);
        for (int i = 0; i < s; ++i) {
            p.add(((KPoint)this.get(i)).getBasicPoint2D());
        }
        return p;
    }

    public boolean getTwist() {
        return this.getDefaultPolygon2D().getChirality();
    }

    public boolean isLOTIsomorphic(KPolygon kpolygon) {
        KMetagon m1;
        KMetagon m0 = this.getKMetagon();
        if (m0.equals(m1 = kpolygon.getKMetagon())) {
            return true;
        }
        m0.reverseDeltas();
        return m0.equals(m1);
    }

    public KPolygon getReticulation() {
        if (this.reticulation == null) {
            this.initReticulation();
        }
        return this.reticulation;
    }

    private void initReticulation() {
        this.reticulation = new KPolygon();
        int s = this.size();
        for (int i0 = 0; i0 < s; ++i0) {
            int i1 = i0 + 1;
            if (i1 == s) {
                i1 = 0;
            }
            KPoint c0 = (KPoint)this.get(i0);
            KPoint c1 = (KPoint)this.get(i1);
            int d = c0.getDirection(c1);
            KPoint v = c0;
            while (!v.equals(c1)) {
                this.reticulation.add(v);
                v = v.getVertex_Adjacent(d);
            }
        }
        this.reticulation.trimToSize();
    }

    public KPolygon getPerimeterPoints() {
        return this.getReticulation();
    }

    public boolean touch(KPolygon p) {
        for (KPoint v : p) {
            if (!this.contains(v)) continue;
            return true;
        }
        return false;
    }

    public List<KPoint> getSharedVertices(KPolygon p) {
        ArrayList<KPoint> s = new ArrayList<KPoint>();
        for (KPoint v : p) {
            if (!this.contains(v)) continue;
            s.add(v);
        }
        return s;
    }

    public boolean isCongruent(KPolygon p0) {
        int s = p0.size();
        if (s != this.size()) {
            return false;
        }
        if (!p0.containsAll(this)) {
            return false;
        }
        int ip0 = 0;
        int ip1 = this.indexOf(p0.get(0));
        int ip1maybenext = ip1 + 1;
        if (ip1maybenext == s) {
            ip1maybenext = 0;
        }
        KPoint v = (KPoint)p0.get(1);
        int incrementp1 = -1;
        if (this.indexOf(v) == ip1maybenext) {
            incrementp1 = 1;
        }
        for (ip0 = 0; ip0 < s; ++ip0) {
            if (!((KPoint)p0.get(ip0)).equals(this.get(ip1))) {
                return false;
            }
            if ((ip1 += incrementp1) == s) {
                ip1 = 0;
            }
            if (ip1 != -1) continue;
            ip1 = s - 1;
        }
        return true;
    }

    public KPolygon getOriginForm() {
        throw new Error("Unresolved compilation problem: \n\tThis method must return a result of type KPolygon\n");
    }

    @Override
    public boolean equals(Object a) {
        KPolygon p = (KPolygon)a;
        int s = this.size();
        if (p.size() != s) {
            return false;
        }
        for (int i = 0; i < s; ++i) {
            if (((KPoint)this.get(i)).equals(p.get(i))) continue;
            return false;
        }
        return true;
    }

    @Override
    public Object clone() {
        KPolygon p = new KPolygon(this.size());
        for (KPoint q : this) {
            p.add((KPoint)q.clone());
        }
        return p;
    }

    @Override
    public String toString() {
        if (this.isEmpty()) {
            return "P[]";
        }
        StringBuffer a = new StringBuffer();
        KPoint v = (KPoint)this.get(0);
        a.append("P[" + v);
        if (this.size() > 1) {
            for (int i = 1; i < this.size(); ++i) {
                v = (KPoint)this.get(i);
                a.append("," + v);
            }
            a.append("]");
        }
        return a.toString();
    }
}

