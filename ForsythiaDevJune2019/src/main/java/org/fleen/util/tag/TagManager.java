/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.util.tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class TagManager
implements Serializable {
    private static final long serialVersionUID = 7475268738463881008L;
    private String[] tags = new String[0];

    public TagManager() {
    }

    public TagManager(String[] tags) {
        this.setTags(tags);
    }

    public /* varargs */ void setTags(String ... tags) {
        this.setTags(Arrays.asList(tags));
    }

    public void setTags(List<String> tags) {
        this.tags = tags.toArray(new String[tags.size()]);
    }

    public List<String> getTags() {
        return new ArrayList<String>(Arrays.asList(this.tags));
    }

    public boolean hasTags(List<String> tags) {
        for (String tag : tags) {
            if (this.hasTag(tag)) continue;
            return false;
        }
        return true;
    }

    public /* varargs */ boolean hasTags(String ... tags) {
        for (String tag : tags) {
            if (this.hasTag(tag)) continue;
            return false;
        }
        return true;
    }

    public boolean hasTag(String tag) {
        for (String t : this.tags) {
            if (!t.equals(tag)) continue;
            return true;
        }
        return false;
    }

    public /* varargs */ void addTags(String ... tags) {
        this.addTags(Arrays.asList(tags));
    }

    public void addTags(List<String> tags) {
        if (tags == null) {
            return;
        }
        HashSet<String> a = new HashSet<String>(Arrays.asList(this.tags));
        a.addAll(tags);
        this.tags = a.toArray(new String[a.size()]);
    }

    public /* varargs */ void removeTags(String ... tags) {
        this.removeTags(Arrays.asList(tags));
    }

    public void removeTags(List<String> tags) {
        HashSet<String> a = new HashSet<String>(Arrays.asList(this.tags));
        a.removeAll(tags);
        this.tags = a.toArray(new String[a.size()]);
    }

    public String toString() {
        if (this.tags.length == 0) {
            return "[]";
        }
        StringBuffer a = new StringBuffer();
        a.append("[");
        for (int i = 0; i < this.tags.length - 1; ++i) {
            a.append(String.valueOf(this.tags[i]) + " ");
        }
        a.append(String.valueOf(this.tags[this.tags.length - 1]) + "]");
        return a.toString();
    }
}

