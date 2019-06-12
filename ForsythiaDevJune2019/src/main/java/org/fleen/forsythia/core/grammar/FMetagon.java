/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.core.grammar;

import java.io.Serializable;
import java.util.List;
import org.fleen.forsythia.core.Forsythia;
import org.fleen.geom_Kisrhombille.KMetagon;
import org.fleen.geom_Kisrhombille.KMetagonVector;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.util.tag.TagManager;
import org.fleen.util.tag.Tagged;

public class FMetagon
extends KMetagon
implements Serializable,
Tagged,
Forsythia {
    private static final long serialVersionUID = 2763461150931052809L;
    private TagManager tagmanager = new TagManager();
    public Object gpobject;

    public FMetagon(double baseinterval, KMetagonVector[] vectors) {
        super(baseinterval, vectors);
    }

    public /* varargs */ FMetagon(KPoint ... vertices) {
        super(vertices);
    }

    public FMetagon(KPolygon polygon) {
        super(polygon);
    }

    public FMetagon(KMetagon km, String[] tags) {
        super(km);
        this.setTags(tags);
    }

    @Override
    public /* varargs */ void setTags(String ... tags) {
        this.tagmanager.setTags(tags);
    }

    @Override
    public void setTags(List<String> tags) {
        this.tagmanager.setTags(tags);
    }

    @Override
    public List<String> getTags() {
        return this.tagmanager.getTags();
    }

    @Override
    public /* varargs */ boolean hasTags(String ... tags) {
        return this.tagmanager.hasTags(tags);
    }

    @Override
    public boolean hasTags(List<String> tags) {
        return this.tagmanager.hasTags(tags);
    }

    @Override
    public /* varargs */ void addTags(String ... tags) {
        this.tagmanager.addTags(tags);
    }

    @Override
    public void addTags(List<String> tags) {
        this.tagmanager.addTags(tags);
    }

    @Override
    public /* varargs */ void removeTags(String ... tags) {
        this.tagmanager.removeTags(tags);
    }

    @Override
    public void removeTags(List<String> tags) {
        this.tagmanager.removeTags(tags);
    }

    @Override
    public String toString() {
        StringBuffer a = new StringBuffer();
        a.append("[" + this.getClass().getSimpleName() + " ");
        a.append("tags=" + this.tagmanager.toString() + " ");
        a.append("baseinterval=" + this.baseinterval + " ");
        a.append("vectors=[");
        for (int i = 0; i < this.vectors.length - 1; ++i) {
            a.append(String.valueOf(this.vectors[i].toString()) + " ");
        }
        a.append(String.valueOf(this.vectors[this.vectors.length - 1].toString()) + "]]");
        return a.toString();
    }
}

