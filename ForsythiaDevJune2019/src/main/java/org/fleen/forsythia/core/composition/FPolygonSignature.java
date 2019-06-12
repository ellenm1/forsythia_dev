/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.core.composition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.fleen.forsythia.core.composition.FPolygon;
import org.fleen.forsythia.core.grammar.FMetagon;

public class FPolygonSignature
implements Serializable {
    private static final long serialVersionUID = 2037581696842008653L;
    private List<SigComponent> components = new ArrayList<SigComponent>();
    public Integer hashcode = null;
    private String objectstring = null;

    public FPolygonSignature(FPolygon p) {
        for (FPolygon node = p; node != null; node = node.getFirstAncestorPolygon()) {
            this.components.add(node.getSignatureComponent());
        }
    }

    public FPolygonSignature() {
    }

    public int hashCode() {
        if (this.hashcode == null) {
            this.initHashCode();
        }
        return this.hashcode;
    }

    private void initHashCode() {
        this.hashcode = new Integer(0);
        for (SigComponent c : this.components) {
            this.hashcode = this.hashcode + c.chorusindex;
        }
    }

    public boolean equals(Object a) {
        int c1;
        FPolygonSignature s0 = (FPolygonSignature)a;
        if (s0.hashCode() != this.hashCode()) {
            return false;
        }
        int c0 = s0.components.size();
        if (c0 != (c1 = this.components.size())) {
            return false;
        }
        for (int i = 0; i < c0; ++i) {
            SigComponent g1;
            SigComponent g0 = s0.components.get(i);
            if (g0.equals(g1 = this.components.get(i))) continue;
            return false;
        }
        return true;
    }

    public String toString() {
        if (this.objectstring == null) {
            this.initObjectString();
        }
        return this.objectstring;
    }

    private void initObjectString() {
        this.objectstring = "BubbleSignature[";
        int s = this.components.size() - 1;
        for (int i = 0; i < s; ++i) {
            this.objectstring = String.valueOf(this.objectstring) + this.components.get(i) + ",";
        }
        this.objectstring = String.valueOf(this.objectstring) + this.components.get(s) + "]";
    }

    public static class SigComponent
    implements Serializable {
        private static final long serialVersionUID = 1335327793096896698L;
        FMetagon metagon;
        int chorusindex;

        public SigComponent(FPolygon p) {
            this.metagon = p.metagon;
            this.chorusindex = p.chorusindex;
        }

        public boolean equals(Object a) {
            SigComponent b = (SigComponent)a;
            boolean e = b.chorusindex == this.chorusindex && b.metagon.equals(this.metagon);
            return e;
        }
    }

}

