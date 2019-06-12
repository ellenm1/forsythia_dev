/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille.graph;

import java.io.PrintStream;
import java.util.List;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.GD;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.graph.GVertex;

public class GEdge {
    public GVertex v0;
    public GVertex v1;
    public boolean traversal_open_v0_v1 = true;
    public boolean traversal_open_v1_v0 = true;

    public GEdge() {
    }

    public GEdge(GVertex v0, GVertex v1) {
        this.v0 = v0;
        this.v1 = v1;
    }

    public boolean uses(GVertex v) {
        return this.v0.equals(v) || this.v1.equals(v);
    }

    public GVertex getOther(GVertex v) {
        if (v.equals(this.v0)) {
            return this.v1;
        }
        if (v.equals(this.v1)) {
            return this.v0;
        }
        throw new IllegalArgumentException("the specified vertex is not used by this edge");
    }

    public void disconnect() {
        this.v0.edges.remove(this);
        this.v1.edges.remove(this);
    }

    public boolean crosses(KPoint v) {
        DPoint pv0 = this.v0.kvertex.getBasicPoint2D();
        DPoint pv1 = this.v1.kvertex.getBasicPoint2D();
        DPoint pv = v.getBasicPoint2D();
        boolean b = GD.isBetween(pv.x, pv.y, pv0.x, pv0.y, pv1.x, pv1.y);
        System.out.println("between=" + b);
        return b;
    }

    public void registerTraversal(GVertex v) {
        if (v.equals(this.v0)) {
            this.traversal_open_v0_v1 = false;
        } else if (v.equals(this.v1)) {
            this.traversal_open_v1_v0 = false;
        } else {
            throw new IllegalArgumentException("specified vertex not on this edge");
        }
    }

    public void initTraversalRecord() {
        this.traversal_open_v0_v1 = true;
        this.traversal_open_v1_v0 = true;
    }

    public boolean isOpen(GVertex v) {
        if (v.equals(this.v0)) {
            return this.traversal_open_v0_v1;
        }
        if (v.equals(this.v1)) {
            return this.traversal_open_v1_v0;
        }
        throw new IllegalArgumentException("specified vertex not on this edge");
    }

    public boolean isClosed(GVertex v) {
        return !this.isOpen(v);
    }

    public int hashCode() {
        return this.v0.hashCode() + this.v1.hashCode();
    }

    public boolean equals(Object a) {
        GEdge b = (GEdge)a;
        return b.v0.equals(this.v0) && b.v1.equals(this.v1) || b.v1.equals(this.v0) && b.v0.equals(this.v1);
    }

    public String toString() {
        return "[" + this.v0.kvertex.toString() + this.v1.kvertex.toString() + "]";
    }
}

