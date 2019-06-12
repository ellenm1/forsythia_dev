/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.util.tree;

import java.util.Collection;
import java.util.List;

public interface TreeNode {
    public TreeNode getParent();

    public void setParent(TreeNode var1);

    public List<? extends TreeNode> getChildren();

    public TreeNode getChild();

    public void setChildren(List<? extends TreeNode> var1);

    public void setChild(TreeNode var1);

    public void addChild(TreeNode var1);

    public int getChildCount();

    public boolean hasChildren();

    public void removeChild(TreeNode var1);

    public void removeChildren(Collection<? extends TreeNode> var1);

    public void clearChildren();

    public boolean isRoot();

    public boolean isLeaf();

    public int getDepth();

    public TreeNode getRoot();

    public TreeNode getAncestor(int var1);

    public List<TreeNode> getSiblings();
}

