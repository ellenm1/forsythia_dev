/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.core.composition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.fleen.forsythia.core.Forsythia;
import org.fleen.forsythia.core.composition.FGrid;
import org.fleen.forsythia.core.composition.FGridRoot;
import org.fleen.forsythia.core.composition.FPolygon;
import org.fleen.forsythia.core.composition.ForsythiaTreeNode;
import org.fleen.forsythia.core.grammar.FMetagon;
import org.fleen.forsythia.core.grammar.ForsythiaGrammar;
import org.fleen.util.tree.TreeNode;
import org.fleen.util.tree.TreeNodeIterator;

public class ForsythiaComposition
implements Forsythia,
Serializable {
    private static final long serialVersionUID = 8710335928675291405L;
    protected ForsythiaGrammar grammar = null;
    public FGridRoot root;

    public void setGrammar(ForsythiaGrammar grammar) {
        this.grammar = grammar;
    }

    public ForsythiaGrammar getGrammar() {
        return this.grammar;
    }

    public void initTree(FMetagon rootpolygonmetagon) {
        this.root = new FGridRoot();
        FPolygon p = new FPolygon(rootpolygonmetagon);
        this.root.setChild(p);
        p.setParent(this.root);
    }

    public void initTree(FPolygon rootpolygon) {
        this.root = new FGridRoot();
        this.root.setChild(rootpolygon);
        rootpolygon.setParent(this.root);
    }

    public void initTree(FGridRoot grid, FPolygon rootpolygon) {
        this.root = grid;
        this.root.setChild(rootpolygon);
        rootpolygon.setParent(this.root);
    }

    public FGridRoot getRoot() {
        return this.root;
    }

    public void setRoot(FGridRoot root) {
        this.root = root;
    }

    public TreeNodeIterator getNodeIterator() {
        return new TreeNodeIterator(this.root);
    }

    public List<ForsythiaTreeNode> getNodes() {
        ArrayList<ForsythiaTreeNode> nodes = new ArrayList<ForsythiaTreeNode>();
        TreeNodeIterator i = this.getNodeIterator();
        while (i.hasNext()) {
            nodes.add((ForsythiaTreeNode)i.next());
        }
        return nodes;
    }

    public TreeNodeIterator getGridIterator() {
        return new GridNodeIterator(this.root);
    }

    public FPolygon getRootPolygon() {
        FPolygon a = (FPolygon)this.root.getChild();
        return a;
    }

    public TreeNodeIterator getPolygonIterator() {
        return new PolygonIterator(this.root);
    }

    public List<FPolygon> getPolygons() {
        ArrayList<FPolygon> polygons = new ArrayList<FPolygon>();
        TreeNodeIterator i = this.getPolygonIterator();
        while (i.hasNext()) {
            polygons.add((FPolygon)i.next());
        }
        return polygons;
    }

    public TreeNodeIterator getLeafPolygonIterator() {
        return new LeafPolygonIterator(this.root);
    }

    public List<FPolygon> getLeafPolygons() {
        ArrayList<FPolygon> polygons = new ArrayList<FPolygon>();
        TreeNodeIterator i = this.getLeafPolygonIterator();
        while (i.hasNext()) {
            polygons.add((FPolygon)i.next());
        }
        return polygons;
    }

    private class GridNodeIterator
    extends TreeNodeIterator {
        public GridNodeIterator(ForsythiaTreeNode root) {
            super(root);
        }

        @Override
        protected boolean skip(TreeNode node) {
            return !(node instanceof FGrid);
        }
    }

    private class LeafPolygonIterator
    extends TreeNodeIterator {
        public LeafPolygonIterator(ForsythiaTreeNode root) {
            super(root);
        }

        @Override
        protected boolean skip(TreeNode node) {
            return !(node instanceof FPolygon) || !node.isLeaf();
        }
    }

    private class PolygonIterator
    extends TreeNodeIterator {
        public PolygonIterator(TreeNode root) {
            super(root);
        }

        @Override
        protected boolean skip(TreeNode node) {
            return !(node instanceof FPolygon);
        }
    }

}

