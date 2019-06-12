/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.core.grammar;

import java.io.Serializable;
import java.util.List;
import org.fleen.forsythia.core.Forsythia;
import org.fleen.forsythia.core.composition.FPolygon;
import org.fleen.forsythia.core.composition.ForsythiaTreeNode;
import org.fleen.forsythia.core.grammar.FMetagon;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_Kisrhombille.KAnchor;
import org.fleen.util.tag.TagManager;

public class JigSection
implements Serializable,
Forsythia {
    private static final long serialVersionUID = -7594787438281531096L;
    public FMetagon productmetagon;
    public KAnchor productanchor;
    public int productchorusindex;
    public TagManager tags;
    private DPolygon testpolygon = null;
    public Object gpobject;

    public JigSection(FMetagon productmetagon, KAnchor productanchor, int productchorusindex, String[] producttags) {
        this.productmetagon = productmetagon;
        this.productanchor = productanchor;
        this.productchorusindex = productchorusindex;
        this.tags = new TagManager(producttags);
    }

    public ForsythiaTreeNode createNode() {
        FPolygon b = new FPolygon(this.productmetagon, this.productanchor, this.productchorusindex, this.tags.getTags());
        return b;
    }

    public DPolygon getTestPolygon() {
        if (this.testpolygon == null) {
            this.initTestPolygon();
        }
        return this.testpolygon;
    }

    private void initTestPolygon() {
        FPolygon p = new FPolygon(this.productmetagon, this.productanchor);
        this.testpolygon = p.getDPolygon();
    }

    public String toString() {
        StringBuffer a = new StringBuffer();
        a.append("[" + this.getClass().getSimpleName() + " ");
        a.append("product=" + this.productmetagon + " ");
        a.append("anchor=" + this.productanchor + " ");
        a.append("index=" + this.productchorusindex + " ");
        a.append("tags=" + this.tags.toString() + "]");
        return a.toString();
    }
}

