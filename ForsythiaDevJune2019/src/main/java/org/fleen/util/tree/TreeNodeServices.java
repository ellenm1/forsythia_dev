/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.util.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.fleen.util.tree.TreeNode;

public class TreeNodeServices
implements Serializable {
    private static final long serialVersionUID = 6396974348380889560L;
    private TreeNode parent = null;
    private TreeNode[] children = new TreeNode[0];

    public TreeNode getParent() {
        return this.parent;
    }

    public List<TreeNode> getChildren() {
        return new ArrayList<TreeNode>(Arrays.asList(this.children));
    }

    public TreeNode getChild() {
        if (!this.hasChildren()) {
            throw new IllegalArgumentException("this node has no children");
        }
        return this.children[0];
    }

    public void setParent(TreeNode node) {
        this.parent = node;
    }

    public void setChildren(List<? extends TreeNode> nodes) {
        this.children = nodes.toArray(new TreeNode[nodes.size()]);
    }

    public void setChild(TreeNode node) {
        this.children = new TreeNode[]{node};
    }

    public void addChild(TreeNode node) {
        ArrayList<TreeNode> a = new ArrayList<TreeNode>(Arrays.asList(this.children));
        a.add(node);
        this.children = a.toArray(new TreeNode[a.size()]);
    }

    public int getChildCount() {
        return this.children.length;
    }

    public boolean hasChildren() {
        return this.children.length > 0;
    }

    public void removeChild(TreeNode child) {
        ArrayList<TreeNode> a = new ArrayList<TreeNode>(Arrays.asList(this.children));
        a.remove(child);
        this.children = a.toArray(new TreeNode[a.size()]);
    }

    public void removeChildren(Collection<? extends TreeNode> c) {
        ArrayList<TreeNode> a = new ArrayList<TreeNode>(Arrays.asList(this.children));
        a.removeAll(c);
        this.children = a.toArray(new TreeNode[a.size()]);
    }

    public void clearChildren() {
        this.children = new TreeNode[0];
    }

    public int getChildIndex(TreeNode child) {
        return Arrays.asList(this.children).indexOf(child);
    }

    public boolean isLeaf() {
        return this.getChildCount() == 0;
    }

    public boolean isRoot() {
        return this.parent == null;
    }

    public int getDepth(TreeNode node) {
        int c = 0;
        TreeNode n = node;
        while (n.getParent() != null) {
            ++c;
            n = n.getParent();
        }
        return c;
    }

    public TreeNode getRoot(TreeNode node) {
        if (node.isRoot()) {
            return node;
        }
        return node.getParent().getRoot();
    }

    public TreeNode getAncestor(TreeNode node, int levels) {
        for (int i = 0; i < levels; ++i) {
            if (node == null) {
                throw new IllegalArgumentException("levels > depth");
            }
            node = node.getParent();
        }
        return node;
    }

    public List<TreeNode> getSiblings(TreeNode n) {
        ArrayList<TreeNode> siblings = new ArrayList<TreeNode>();
        if (this.isRoot()) {
            return siblings;
        }
        siblings.addAll(this.getParent().getChildren());
        siblings.remove(n);
        return siblings;
    }
}

