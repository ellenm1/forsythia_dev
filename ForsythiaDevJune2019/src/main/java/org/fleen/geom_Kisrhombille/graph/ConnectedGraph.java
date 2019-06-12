/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.graph.GVertex;
import org.fleen.geom_Kisrhombille.graph.Strand;
import org.fleen.util.tree.TreeNode;
import org.fleen.util.tree.TreeNodeIterator;
import org.fleen.util.tree.TreeNodeServices;

public class ConnectedGraph
implements TreeNode {
    List<Strand> strands = new ArrayList<Strand>();
    List<double[]> edgecenters = null;
    Set<KPoint> kvertices;
    List<List<KPoint>> openkvertexsequences;
    List<KPolygon> kpolygons;
    KPolygon outerpolygon;
    private static final double CLOSEPOINTDISTANCE = 1.0E-4;
    List<KPolygon> undividedpolygons;
    TreeNodeServices treenodeservices = new TreeNodeServices();
    public KPolygon parentalencompassingpolygon = null;

    public ConnectedGraph(GVertex v) {
        this.build(v);
    }

    GVertex getStrandOpenVertex() {
        for (Strand s : this.strands) {
            for (GVertex v : s.gvertices) {
                if (!v.isOpen()) continue;
                return v;
            }
        }
        return null;
    }

    public Set<GVertex> getVertices() {
        HashSet<GVertex> vertices = new HashSet<GVertex>();
        for (Strand s : this.strands) {
            vertices.addAll(s.gvertices);
        }
        return vertices;
    }

    private List<double[]> getEdgeCenters() {
        if (this.edgecenters == null) {
            this.initEdgeCenters();
        }
        return this.edgecenters;
    }

    private void initEdgeCenters() {
        this.edgecenters = new ArrayList<double[]>();
        for (Strand strand : this.strands) {
            this.edgecenters.addAll(strand.getEdgeCenters());
        }
    }

    private void initArtifacts() {
        this.initKVertices();
        this.initOpenKVertexSequences();
        this.initKPolygons();
        this.initOuterPolygon();
        this.initUndividedPolygons();
    }

    public Set<KPoint> getKVertices() {
        return this.kvertices;
    }

    private void initKVertices() {
        this.kvertices = new HashSet<KPoint>();
        for (Strand s : this.strands) {
            for (GVertex gv : s.gvertices) {
                this.kvertices.add(gv.kvertex);
            }
        }
    }

    public List<List<KPoint>> getOpenKVertexSequences() {
        return this.openkvertexsequences;
    }

    private void initOpenKVertexSequences() {
        this.openkvertexsequences = new ArrayList<List<KPoint>>();
        for (Strand s : this.strands) {
            if (s.isPolygonal()) continue;
            this.openkvertexsequences.add(s.getOpenKVertexSequence());
        }
    }

    public List<KPolygon> getKPolygons() {
        return this.kpolygons;
    }

    private void initKPolygons() {
        this.kpolygons = new ArrayList<KPolygon>();
        for (Strand s : this.strands) {
            if (!s.isPolygonal()) continue;
            this.kpolygons.add(s.getKPolygon());
        }
        this.cullDupes();
    }

    private void cullDupes() {
        Iterator<KPolygon> i0 = this.kpolygons.iterator();
        block0 : while (i0.hasNext()) {
            KPolygon p0 = i0.next();
            for (KPolygon p1 : this.kpolygons) {
                if (p0 == p1 || !this.dupes(p0, p1)) continue;
                i0.remove();
                continue block0;
            }
        }
    }

    private boolean dupes(KPolygon p0, KPolygon p1) {
        if (p0.size() != p1.size()) {
            return false;
        }
        return p0.containsAll(p1);
    }

    public KPolygon getOuterPolygon() {
        return this.outerpolygon;
    }

    private void initOuterPolygon() {
        for (KPolygon polygon : this.getKPolygons()) {
            if (!this.isOuterPolygon(polygon)) continue;
            this.outerpolygon = polygon;
            break;
        }
    }

    private boolean isOuterPolygon(KPolygon polygon) {
        DPolygon polygon2d = polygon.getDefaultPolygon2D();
        for (KPoint v : this.getKVertices()) {
            if (polygon.contains(v)) continue;
            DPoint p2d = v.getBasicPoint2D();
            if (polygon2d.containsPoint(p2d.x, p2d.y)) continue;
            return false;
        }
        return true;
    }

    public List<KPolygon> getUndividedPolygons() {
        return this.undividedpolygons;
    }

    private void initUndividedPolygons() {
        this.undividedpolygons = new ArrayList<KPolygon>();
        for (KPolygon polygon : this.getKPolygons()) {
            if (!this.isUndivided(polygon)) continue;
            this.undividedpolygons.add(polygon);
        }
    }

    private boolean isUndivided(KPolygon polygon) {
        DPolygon polygon2d = polygon.getDefaultPolygon2D();
        List<double[]> point2ds = this.getKVertexPoint2Ds();
        for (double[] p : point2ds) {
            if (polygon2d.getDistance(p[0], p[1]) < 1.0E-4 || !polygon2d.containsPoint(p[0], p[1])) continue;
            return false;
        }
        List<double[]> edgecenters = this.getEdgeCenters();
        for (double[] p : edgecenters) {
            if (polygon2d.getDistance(p[0], p[1]) < 1.0E-4 || !polygon2d.containsPoint(p[0], p[1])) continue;
            return false;
        }
        return true;
    }

    private List<double[]> getKVertexPoint2Ds() {
        ArrayList<double[]> p = new ArrayList<double[]>();
        for (KPoint v : this.getKVertices()) {
            p.add(v.getBasicPointCoor());
        }
        return p;
    }

    @Override
    public TreeNode getParent() {
        return this.treenodeservices.getParent();
    }

    @Override
    public void setParent(TreeNode node) {
        this.treenodeservices.setParent(node);
    }

    @Override
    public List<? extends TreeNode> getChildren() {
        return this.treenodeservices.getChildren();
    }

    @Override
    public TreeNode getChild() {
        return this.treenodeservices.getChild();
    }

    @Override
    public void setChildren(List<? extends TreeNode> nodes) {
        this.treenodeservices.setChildren(nodes);
    }

    @Override
    public void addChild(TreeNode node) {
        this.treenodeservices.addChild(node);
    }

    @Override
    public void setChild(TreeNode node) {
        this.treenodeservices.setChild(node);
    }

    @Override
    public int getChildCount() {
        return this.treenodeservices.getChildCount();
    }

    @Override
    public boolean hasChildren() {
        return this.treenodeservices.hasChildren();
    }

    @Override
    public void removeChild(TreeNode child) {
        this.treenodeservices.removeChild(child);
    }

    @Override
    public void clearChildren() {
        this.treenodeservices.clearChildren();
    }

    @Override
    public boolean isRoot() {
        return this.treenodeservices.isRoot();
    }

    @Override
    public boolean isLeaf() {
        return this.treenodeservices.isLeaf();
    }

    @Override
    public int getDepth() {
        return this.treenodeservices.getDepth(this);
    }

    @Override
    public TreeNode getRoot() {
        return this.treenodeservices.getRoot(this);
    }

    @Override
    public TreeNode getAncestor(int levels) {
        return this.treenodeservices.getAncestor(this, levels);
    }

    public List<ConnectedGraph> getChildrenContainedByUndividedPolygon(KPolygon undividedpolygon) {
        ArrayList<ConnectedGraph> contained = new ArrayList<ConnectedGraph>();
        ArrayList<? extends TreeNode> children = new ArrayList<TreeNode>(this.getChildren());
        for (TreeNode n : children) {
            ConnectedGraph cg = (ConnectedGraph)n;
            if (!this.polygonContainsConnectedGraph(undividedpolygon, cg)) continue;
            contained.add(cg);
        }
        return contained;
    }

    public boolean polygonContainsThisConnectedGraph(KPolygon polygon) {
        return this.polygonContainsConnectedGraph(polygon, this);
    }

    private boolean polygonContainsConnectedGraph(KPolygon polygon, ConnectedGraph cg) {
        DPolygon p2d = polygon.getDefaultPolygon2D();
        KPoint v = cg.kvertices.iterator().next();
        DPoint p = v.getBasicPoint2D();
        boolean contained = p2d.containsPoint(p.x, p.y);
        return contained;
    }

    private void build(GVertex v0) {
        Strand strand = new Strand(v0);
        this.strands.add(strand);
        boolean finished = false;
        while (!finished) {
            boolean extended = strand.extend();
            if (extended) continue;
            v0 = this.getStrandOpenVertex();
            if (v0 == null) {
                finished = true;
                continue;
            }
            strand = new Strand(v0);
            this.strands.add(strand);
        }
        this.initArtifacts();
    }

    public String toString() {
        StringBuffer a = new StringBuffer();
        a.append("[");
        for (KPolygon p : this.getKPolygons()) {
            a.append(p);
        }
        a.append("]");
        return a.toString();
    }

    public String getTreeString() {
        StringBuffer a = new StringBuffer();
        TreeNodeIterator i = new TreeNodeIterator(this);
        while (i.hasNext()) {
            ConnectedGraph cg = (ConnectedGraph)i.next();
            a.append(cg + "\n");
        }
        a.append("\n");
        return a.toString();
    }

    @Override
    public void removeChildren(Collection<? extends TreeNode> children) {
    }

    @Override
    public List<TreeNode> getSiblings() {
        return null;
    }
}

