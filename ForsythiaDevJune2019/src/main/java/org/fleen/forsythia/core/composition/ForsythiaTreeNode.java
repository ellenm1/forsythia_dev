/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.core.composition;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.fleen.forsythia.core.Forsythia;
import org.fleen.forsythia.core.composition.FGrid;
import org.fleen.forsythia.core.composition.FPolygon;
import org.fleen.util.tree.TreeNode;
import org.fleen.util.tree.TreeNodeIterator;
import org.fleen.util.tree.TreeNodeServices;

public abstract class ForsythiaTreeNode
implements TreeNode,
Serializable,
Forsythia {
    private static final long serialVersionUID = 6049549726585045831L;
    public TreeNodeServices treenodeservices = new TreeNodeServices();
    public Object gpobject;

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
    public void setChild(TreeNode node) {
        this.treenodeservices.setChild(node);
    }

    @Override
    public void addChild(TreeNode node) {
        this.treenodeservices.addChild(node);
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
    public void clearChildren() {
        this.treenodeservices.clearChildren();
    }

    @Override
    public void removeChild(TreeNode child) {
        this.treenodeservices.removeChild(child);
    }

    @Override
    public void removeChildren(Collection<? extends TreeNode> children) {
        this.treenodeservices.removeChildren(children);
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

    @Override
    public List<TreeNode> getSiblings() {
        return this.treenodeservices.getSiblings(this);
    }

    public int getPolygonDepth() {
        int c = 0;
        ForsythiaTreeNode n = this;
        while (n != null) {
            if ((n = n.getFirstAncestorPolygon()) == null) continue;
            ++c;
        }
        return c;
    }

    public int getGridDepth() {
        int c = 0;
        ForsythiaTreeNode n = this;
        while (n != null) {
            if ((n = n.getFirstAncestorGrid()) == null) continue;
            ++c;
        }
        return c;
    }

    public FGrid getFirstAncestorGrid() {
        TreeNode n = this.treenodeservices.getParent();
        while (!(n instanceof FGrid)) {
            if (n == null) {
                return null;
            }
            n = n.getParent();
        }
        return (FGrid)n;
    }

    public FPolygon getFirstAncestorPolygon() {
        TreeNode n = this.treenodeservices.getParent();
        while (!(n instanceof FPolygon)) {
            if (n == null) {
                return null;
            }
            n = n.getParent();
        }
        return (FPolygon)n;
    }

    public TreeNodeIterator getLeafPolygonIterator() {
        return new TreeNodeIterator(this){

            @Override
            public boolean skip(TreeNode node) {
                return !(node instanceof FPolygon) || !node.isLeaf();
            }
        };
    }

    public TreeNodeIterator getNodeIterator() {
        return new TreeNodeIterator(this);
    }

}

