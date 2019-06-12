/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.util.tag;

import java.util.List;

public interface Tagged {
    public /* varargs */ void setTags(String ... var1);

    public void setTags(List<String> var1);

    public List<String> getTags();

    public /* varargs */ boolean hasTags(String ... var1);

    public boolean hasTags(List<String> var1);

    public /* varargs */ void addTags(String ... var1);

    public void addTags(List<String> var1);

    public /* varargs */ void removeTags(String ... var1);

    public void removeTags(List<String> var1);
}

