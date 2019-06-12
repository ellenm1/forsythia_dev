/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.KYard;
import org.fleen.geom_Kisrhombille.graph.ConnectedGraph;
import org.fleen.geom_Kisrhombille.graph.GEdge;
import org.fleen.geom_Kisrhombille.graph.GVertex;
import org.fleen.geom_Kisrhombille.graph.Graph;
import org.fleen.geom_Kisrhombille.graph.TreesOfConnectedGraphs;
import org.fleen.util.tree.TreeNode;
import org.fleen.util.tree.TreeNodeIterator;

public class DisconnectedGraph {
    Graph graph;
    public List<ConnectedGraph> connectedgraphs = new ArrayList<ConnectedGraph>();

    DisconnectedGraph(Graph graph) {
        this.graph = graph;
        this.build();
    }

    private void build() {
        for (GEdge e : this.graph.edges) {
            e.initTraversalRecord();
        }
        ArrayList<GVertex> vertices = new ArrayList<GVertex>(this.graph.vertices);
        while (!vertices.isEmpty()) {
            GVertex v = (GVertex)vertices.remove(0);
            ConnectedGraph cga = new ConnectedGraph(v);
            this.connectedgraphs.add(cga);
            vertices.removeAll(cga.getVertices());
        }
    }

    public boolean hasMutipleConnectedGraphs() {
        return this.connectedgraphs.size() > 1;
    }

    public List<List<KPoint>> getOpenKVertexSequences() {
        ArrayList<List<KPoint>> seq = new ArrayList<List<KPoint>>();
        for (ConnectedGraph cg : this.connectedgraphs) {
            seq.addAll(cg.getOpenKVertexSequences());
        }
        return seq;
    }

    public List<KPolygon> getPolygons() {
        ArrayList<KPolygon> polygons = new ArrayList<KPolygon>();
        for (ConnectedGraph cga : this.connectedgraphs) {
            polygons.addAll(cga.getKPolygons());
        }
        return polygons;
    }

    public boolean describesSinglePolygon() {
        if (this.connectedgraphs.size() != 1) {
            return false;
        }
        ConnectedGraph a = this.connectedgraphs.get(0);
        return a.getKPolygons().size() == 1;
    }

    public List<KPolygon> getUndividedPolygons() {
        ArrayList<KPolygon> p = new ArrayList<KPolygon>();
        for (ConnectedGraph g : this.connectedgraphs) {
            p.addAll(g.getUndividedPolygons());
        }
        return p;
    }

    public List<KYard> getYards() {
        TreesOfConnectedGraphs cgt = new TreesOfConnectedGraphs(this);
        ArrayList<KYard> yards = new ArrayList<KYard>();
        for (ConnectedGraph root : cgt.roots) {
            yards.addAll(this.getYardsForCGTreeRoot(root));
        }
        return yards;
    }

    private List<KYard> getYardsForCGTreeRoot(ConnectedGraph root) {
        ArrayList<KYard> yards = new ArrayList<KYard>();
        TreeNodeIterator i = new TreeNodeIterator(root);
        while (i.hasNext()) {
            ConnectedGraph cg = (ConnectedGraph)i.next();
            yards.addAll(this.getYardsForCG(cg));
        }
        return yards;
    }

    private List<KYard> getYardsForCG(ConnectedGraph cg) {
        ArrayList<KYard> yards = new ArrayList<KYard>();
        for (KPolygon up : cg.getUndividedPolygons()) {
            List<ConnectedGraph> children = cg.getChildrenContainedByUndividedPolygon(up);
            if (children.isEmpty()) continue;
            yards.add(this.getYardForUndividedPolygon(up, children));
        }
        return yards;
    }

    private KYard getYardForUndividedPolygon(KPolygon undividedpolygon, List<ConnectedGraph> children) {
        KYard yard = new KYard(children.size() + 1);
        yard.add(undividedpolygon);
        for (ConnectedGraph cg : children) {
            yard.add(cg.getOuterPolygon());
        }
        return yard;
    }

    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append("-----------------------\n");
        s.append("vertices : " + this.graph.vertices.size() + "\n");
        s.append("edges : " + this.graph.edges.size() + "\n");
        s.append("polygons : " + this.getPolygons().size() + "\n");
        s.append("connected graphs : " + this.connectedgraphs.size() + "\n");
        s.append("undivided polygons : " + this.getUndividedPolygons().size() + "\n");
        s.append("yards : " + this.getYards().size() + "\n");
        s.append("=======\n");
        s.append("CGTREES\n");
        TreesOfConnectedGraphs cgt = new TreesOfConnectedGraphs(this);
        for (ConnectedGraph cg : cgt.roots) {
            s.append("\n" + cg.getTreeString());
        }
        return s.toString();
    }
}

