/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille.graph;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.graph.ConnectedGraph;
import org.fleen.geom_Kisrhombille.graph.DisconnectedGraph;
import org.fleen.geom_Kisrhombille.graph.GVertex;
import org.fleen.util.tree.TreeNode;

class TreesOfConnectedGraphs {
    DisconnectedGraph disconnectedgraph;
    List<ConnectedGraph> roots = new ArrayList<ConnectedGraph>();
    Map<ConnectedGraph, List<EnclosureModel>> enclosuremodels_enclosed = new Hashtable<ConnectedGraph, List<EnclosureModel>>();
    Map<ConnectedGraph, List<EnclosureModel>> enclosuremodels_enclosing = new Hashtable<ConnectedGraph, List<EnclosureModel>>();

    TreesOfConnectedGraphs(DisconnectedGraph dg) {
        this.disconnectedgraph = dg;
        this.build();
    }

    private void build() {
        this.initEnclosureModels();
        this.connectNodes();
        System.out.println("ROOTCOUNT=" + this.roots.size());
    }

    private void initEnclosureModels() {
        for (ConnectedGraph cg0 : this.disconnectedgraph.connectedgraphs) {
            ArrayList<EnclosureModel> enclosed = new ArrayList<EnclosureModel>();
            this.enclosuremodels_enclosed.put(cg0, enclosed);
            ArrayList<EnclosureModel> enclosing = new ArrayList<EnclosureModel>();
            this.enclosuremodels_enclosing.put(cg0, enclosing);
            for (ConnectedGraph cg1 : this.disconnectedgraph.connectedgraphs) {
                if (cg0 == cg1) continue;
                KPolygon enclosingpolygon = this.getEnclosingPolygon(cg0, cg1);
                if (enclosingpolygon != null) {
                    enclosed.add(new EnclosureModel(cg1, enclosingpolygon));
                }
                if ((enclosingpolygon = this.getEnclosingPolygon(cg1, cg0)) == null) continue;
                enclosing.add(new EnclosureModel(cg1, enclosingpolygon));
            }
        }
    }

    private KPolygon getEnclosingPolygon(ConnectedGraph enclosing, ConnectedGraph enclosed) {
        for (KPolygon p : enclosing.getUndividedPolygons()) {
            if (!this.encloses(p, enclosed)) continue;
            return p;
        }
        return null;
    }

    private boolean encloses(KPolygon polygon, ConnectedGraph cg) {
        KPoint v = cg.getVertices().iterator().next().kvertex;
        DPoint p = v.getBasicPoint2D();
        boolean c = polygon.getDefaultPolygon2D().containsPoint(p.x, p.y);
        return c;
    }

    private void connectNodes() {
        for (ConnectedGraph cg : this.disconnectedgraph.connectedgraphs) {
            cg.clearChildren();
            cg.setParent(null);
        }
        for (ConnectedGraph cg : this.disconnectedgraph.connectedgraphs) {
            EnclosureModel em = this.getImmediateEnclosingParent(cg);
            if (em == null) {
                this.roots.add(cg);
                continue;
            }
            em.cg.addChild(cg);
            cg.setParent(em.cg);
            cg.parentalencompassingpolygon = em.polygon;
        }
    }

    private EnclosureModel getImmediateEnclosingParent(ConnectedGraph test) {
        List<EnclosureModel> enclosing = this.enclosuremodels_enclosing.get(test);
        if (enclosing.isEmpty()) {
            return null;
        }
        for (EnclosureModel m : enclosing) {
            if (!this.enclosesNoOtherEnclosing(m, enclosing)) continue;
            return m;
        }
        return null;
    }

    private boolean enclosesNoOtherEnclosing(EnclosureModel m, List<EnclosureModel> enclosing) {
        List<EnclosureModel> enclosed = this.enclosuremodels_enclosed.get(m.cg);
        for (EnclosureModel m0 : enclosed) {
            for (EnclosureModel m1 : enclosing) {
                if (m0.cg != m1.cg) continue;
                return false;
            }
        }
        return true;
    }

    private class EnclosureModel {
        ConnectedGraph cg;
        KPolygon polygon;

        EnclosureModel(ConnectedGraph cg, KPolygon polygon) {
            this.cg = cg;
            this.polygon = polygon;
        }
    }

}

