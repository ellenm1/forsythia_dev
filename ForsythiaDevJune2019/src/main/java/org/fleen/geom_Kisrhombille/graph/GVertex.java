/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille.graph;

import java.util.ArrayList;
import java.util.List;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.graph.GEdge;

public class GVertex {
    public KPoint kvertex;
    public List<GEdge> edges = new ArrayList<GEdge>(12);

    public GVertex(KPoint kvertex) {
        this.kvertex = kvertex;
    }

    public boolean isClosed() {
        for (GEdge e : this.edges) {
            if (!e.isOpen(this)) continue;
            return false;
        }
        return true;
    }

    public boolean isOpen() {
        for (GEdge e : this.edges) {
            if (!e.isOpen(this)) continue;
            return true;
        }
        return false;
    }

    public GEdge getOpenEdge() {
        for (GEdge e : this.edges) {
            if (!e.isOpen(this)) continue;
            return e;
        }
        throw new IllegalArgumentException("this vertex is not open");
    }

    public int hashCode() {
        return this.kvertex.hashCode();
    }

    public boolean equals(Object a) {
        GVertex b = (GVertex)a;
        return b.kvertex.equals(this.kvertex);
    }

    public String toString() {
        StringBuffer a = new StringBuffer();
        a.append("----------------\n");
        a.append("kv:" + this.kvertex.toString() + "\n");
        a.append("----------------\n");
        for (GEdge e : this.edges) {
            a.append(e.toString());
            a.append("----------------\n");
        }
        return a.toString();
    }
}

