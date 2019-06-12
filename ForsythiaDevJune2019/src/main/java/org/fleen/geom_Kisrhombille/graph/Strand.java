/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille.graph;

import java.util.ArrayList;
import java.util.List;
import org.fleen.geom_2D.GD;
import org.fleen.geom_Kisrhombille.GK;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.graph.GEdge;
import org.fleen.geom_Kisrhombille.graph.GVertex;

class Strand {
    GEdge elast;
    List<GVertex> gvertices = new ArrayList<GVertex>();

    Strand(GVertex v) {
        this.gvertices.add(v);
    }

    GVertex getFirst() {
        return this.gvertices.get(0);
    }

    List<KPoint> getOpenKVertexSequence() {
        ArrayList<KPoint> a = new ArrayList<KPoint>();
        for (GVertex gv : this.gvertices) {
            a.add(gv.kvertex);
        }
        return a;
    }

    boolean isPolygonal() {
        boolean a = this.gvertices.size() > 2;
        a = a && this.gvertices.get(0).equals(this.gvertices.get(this.gvertices.size() - 1));
        return a;
    }

    KPolygon getKPolygon() {
        int s = this.gvertices.size() - 1;
        KPolygon p = new KPolygon(s);
        for (int i = 0; i < s; ++i) {
            p.add(this.gvertices.get((int)i).kvertex);
        }
        return p;
    }

    List<double[]> getEdgeCenters() {
        ArrayList<double[]> centers = new ArrayList<double[]>();
        int s = this.gvertices.size();
        for (int i0 = 0; i0 < s; ++i0) {
            int i1 = i0 + 1;
            if (i1 == s) {
                i1 = 0;
            }
            double[] p0 = this.gvertices.get((int)i0).kvertex.getBasicPointCoor();
            double[] p1 = this.gvertices.get((int)i1).kvertex.getBasicPointCoor();
            double[] pcenter = GD.getPoint_Mid2Points(p0[0], p0[1], p1[0], p1[1]);
            centers.add(pcenter);
        }
        return centers;
    }

    boolean extend() {
        if (this.elast == null) {
            GVertex vlast = this.gvertices.get(this.gvertices.size() - 1);
            if (vlast.isClosed()) {
                return false;
            }
            this.elast = vlast.getOpenEdge();
            this.elast.registerTraversal(vlast);
            vlast = this.elast.getOther(vlast);
            this.gvertices.add(vlast);
            return true;
        }
        GVertex vlast = this.gvertices.get(this.gvertices.size() - 1);
        if (vlast.edges.size() == 1) {
            return false;
        }
        int vcount = this.gvertices.size();
        GVertex vfirst = this.gvertices.get(0);
        if (vcount > 1 && vfirst.equals(vlast)) {
            return false;
        }
        this.elast = this.getNextEdge(this.elast, vlast);
        if (this.elast == null) {
            return false;
        }
        this.elast.registerTraversal(vlast);
        vlast = this.elast.getOther(vlast);
        this.gvertices.add(vlast);
        return true;
    }

    public GEdge getNextEdge(GEdge eprior, GVertex vpresent) {
        int dreverseprior = vpresent.kvertex.getDirection(eprior.getOther((GVertex)vpresent).kvertex);
        int minoffset = Integer.MAX_VALUE;
        GEdge rightturnedge = null;
        for (GEdge e : vpresent.edges) {
            int dtest;
            int testoffset;
            if (e.equals(eprior) || e.isClosed(vpresent) || (testoffset = GK.getCCWOffset(dreverseprior, dtest = vpresent.kvertex.getDirection(e.getOther((GVertex)vpresent).kvertex))) >= minoffset) continue;
            minoffset = testoffset;
            rightturnedge = e;
        }
        return rightturnedge;
    }

    public String toString() {
        StringBuffer a = new StringBuffer();
        for (GVertex v : this.gvertices) {
            a.append(v);
        }
        return a.toString();
    }
}

