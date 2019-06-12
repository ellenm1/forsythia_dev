/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.util.tree;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.fleen.util.tree.TreeNode;

public class TreeNodeIterator
implements Iterator<TreeNode>,
Serializable {
    private static final long serialVersionUID = 4041668630049238695L;
    private LinkedList<TreeNode> nodes = new LinkedList();
    private TreeNode nextnode = null;

    public TreeNodeIterator(TreeNode node) {
        this.nodes.add(node);
        this.setNextNode();
    }

    @Override
    public boolean hasNext() {
        return this.nextnode != null;
    }

    @Override
    public TreeNode next() {
        TreeNode n = this.nextnode;
        this.setNextNode();
        return n;
    }

    @Override
    public void remove() {
        throw new IllegalArgumentException("not implemented");
    }

    private void setNextNode() {
        this.nextnode = null;
        while (!this.nodes.isEmpty() && this.nextnode == null) {
            TreeNode n = this.nodes.removeFirst();
            if (this.terminate(n)) {
                this.nextnode = n;
                continue;
            }
            if (this.skip(n)) {
                this.nodes.addAll(n.getChildren());
                continue;
            }
            this.nextnode = n;
            this.nodes.addAll(n.getChildren());
        }
    }

    protected boolean skip(TreeNode node) {
        return false;
    }

    protected boolean terminate(TreeNode node) {
        return false;
    }
}

