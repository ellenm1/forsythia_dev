/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille.graph;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.graph.DisconnectedGraph;
import org.fleen.geom_Kisrhombille.graph.GEdge;
import org.fleen.geom_Kisrhombille.graph.GVertex;

public class Graph {
    public Set<GVertex> vertices = new HashSet<GVertex>();
    public Set<GEdge> edges = new HashSet<GEdge>();
    private DisconnectedGraph disconnectedgraph = null;

    public Graph() {
    }

    public Graph(KPolygon polygon) {
        this.addPolygon(polygon);
    }

    public boolean contains(KPoint v) {
        for (GVertex gv : this.vertices) {
            if (!gv.kvertex.equals(v)) continue;
            return true;
        }
        return false;
    }

    public void addVertex(KPoint v) {
        this.invalidateDisconnectedGraph();
        GVertex gv = new GVertex(v);
        this.vertices.add(gv);
    }

    public void removeVertex(KPoint v) {
        this.invalidateDisconnectedGraph();
        GVertex gv = this.getGVertex(v);
        this.vertices.remove(gv);
        Iterator<GEdge> i = this.edges.iterator();
        while (i.hasNext()) {
            GEdge edge = i.next();
            if (!edge.uses(gv)) continue;
            edge.disconnect();
            i.remove();
        }
    }

    public void connect(KPoint v0, KPoint v1) {
        System.out.println("CONNECT V0 V1");
        this.invalidateDisconnectedGraph();
        GVertex gv0 = this.getGVertex(v0);
        GVertex gv1 = this.getGVertex(v1);
        GEdge e = new GEdge(gv0, gv1);
        if (!this.edges.contains(e)) {
            this.edges.add(e);
            gv0.edges.add(e);
            gv1.edges.add(e);
        }
    }

    public void disconnect(KPoint v0, KPoint v1) {
        GVertex gv0 = this.getGVertex(v0);
        GVertex gv1 = this.getGVertex(v1);
        this.disconnect(gv0, gv1);
    }

    public void disconnect(GVertex v0, GVertex v1) {
        this.invalidateDisconnectedGraph();
        GEdge e = new GEdge(v0, v1);
        v0.edges.remove(e);
        v1.edges.remove(e);
        this.edges.remove(e);
    }

    public GVertex getGVertex(KPoint kv) {
        GVertex gv0 = null;
        for (GVertex gv : this.vertices) {
            if (!gv.kvertex.equals(kv)) continue;
            gv0 = gv;
            break;
        }
        if (gv0 == null) {
            throw new IllegalArgumentException("specified kvertex is not in this graph : " + kv);
        }
        return gv0;
    }

    public GEdge getCrossingEdge(KPoint v) {
        for (GEdge e : this.edges) {
            if (!e.crosses(v)) continue;
            return e;
        }
        return null;
    }

    public void addPolygon(KPolygon polygon) {
        int s = polygon.size();
        for (KPoint v : polygon) {
            this.addVertex(v);
        }
        for (int i0 = 0; i0 < s; ++i0) {
            int i1 = i0 + 1;
            if (i1 == s) {
                i1 = 0;
            }
            KPoint v0 = (KPoint)polygon.get(i0);
            KPoint v1 = (KPoint)polygon.get(i1);
            this.connect(v0, v1);
        }
    }

    public DisconnectedGraph getDisconnectedGraph() {
        if (this.disconnectedgraph == null) {
            this.disconnectedgraph = new DisconnectedGraph(this);
        }
        return this.disconnectedgraph;
    }

    public void invalidateDisconnectedGraph() {
        this.disconnectedgraph = null;
    }
}

