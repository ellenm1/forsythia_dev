/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.project.jig;

import java.awt.geom.Path2D;
import java.io.Serializable;
import java.util.List;
import org.fleen.forsythia.app.grammarEditor.GE;
import org.fleen.forsythia.app.grammarEditor.project.ProjectGrammar;
import org.fleen.forsythia.app.grammarEditor.project.jig.MetagonAndAnchors;
import org.fleen.forsythia.app.grammarEditor.project.jig.ProjectJig;
import org.fleen.forsythia.app.grammarEditor.project.metagon.ProjectMetagon;
import org.fleen.forsythia.core.grammar.FMetagon;
import org.fleen.forsythia.core.grammar.JigSection;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_Kisrhombille.KAnchor;
import org.fleen.geom_Kisrhombille.KMetagon;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.util.tag.TagManager;

public class ProjectJigSection
implements Serializable {
    private static final long serialVersionUID = -654455489262628496L;
    public ProjectJig owner;
    public ProjectMetagon metagon;
    public int anchorindex = 0;
    public List<KAnchor> anchors = null;
    public int chorusindex = 0;
    public String tags = "";
    private Path2D.Double path2dforjigimagecomponent = null;

    public ProjectJigSection(ProjectJig owner, MetagonAndAnchors metagonandanchors, int chorusindex) {
        this.owner = owner;
        this.metagon = metagonandanchors.metagon;
        this.anchors = metagonandanchors.anchors;
        this.chorusindex = chorusindex;
    }

    public ProjectJigSection(ProjectJig owner, JigSection jigsection) {
        this.owner = owner;
        this.initForImport(jigsection);
    }

    public String getAnchorIndexString() {
        return String.format("%03d", this.anchorindex);
    }

    public void incrementAnchor() {
        int maxanchor = this.anchors.size() - 1;
        ++this.anchorindex;
        if (this.anchorindex > maxanchor) {
            this.anchorindex = 0;
        }
    }

    public KAnchor getAnchor() {
        return this.anchors.get(this.anchorindex);
    }

    public String getChorusString() {
        return String.format("%03d", this.chorusindex);
    }

    public void incrementChorus() {
        this.chorusindex = this.owner.getNextValidChorusIndex(this, this.chorusindex);
    }

    public void setTags(List<String> tags) {
        if (tags.isEmpty()) {
            this.tags = "";
            return;
        }
        StringBuffer a = new StringBuffer();
        for (String b : tags) {
            a.append(String.valueOf(b) + " ");
        }
        a.delete(a.length() - 1, a.length());
        this.tags = a.toString();
    }

    public String[] getTagsForExport() {
        if (this.tags.equals("")) {
            return new String[0];
        }
        String[] a = this.tags.split(" ");
        return a;
    }

    private void initForImport(JigSection jigsection) {
        this.metagon = GE.ge.focusgrammar.getMetagon(jigsection.productmetagon);
        if (this.metagon == null) {
            throw new IllegalArgumentException("metagon in jigsection not found in focus grammar");
        }
        this.anchors = this.metagon.kmetagon.getAnchorOptions(this.metagon.kmetagon.getPolygon(jigsection.productanchor));
        this.anchorindex = this.getAnchorIndex(jigsection.productanchor, this.anchors);
        this.chorusindex = jigsection.productchorusindex;
        this.setTags(jigsection.tags.getTags());
    }

    private int getAnchorIndex(KAnchor testanchor, List<KAnchor> anchors) {
        for (int i = 0; i < anchors.size(); ++i) {
            if (!anchors.get(i).equals(testanchor)) continue;
            return i;
        }
        throw new IllegalArgumentException("anchor not in list");
    }

    public KPolygon getPolygon() {
        return this.metagon.kmetagon.getPolygon(this.getAnchor());
    }

    public Path2D.Double getPath2DForJigImageComponent() {
        if (this.path2dforjigimagecomponent == null) {
            this.initImagePath2DForJigImageComponent();
        }
        return this.path2dforjigimagecomponent;
    }

    private void initImagePath2DForJigImageComponent() {
        this.path2dforjigimagecomponent = null;
        try {
            KMetagon p1 = new KMetagon(this.owner.jiggedmetagon.kpolygon);
            KAnchor anchor = this.getAnchor();
            if (!anchor.twist) {
                p1.reverseDeltas();
            }
            KPolygon p0 = p1.getPolygon(anchor.v0, anchor.v1);
            DPolygon points = p0.getDefaultPolygon2D();
            this.path2dforjigimagecomponent = new Path2D.Double();
            DPoint p = (DPoint)points.get(0);
            this.path2dforjigimagecomponent.moveTo(p.x, p.y);
            for (int i = 1; i < points.size(); ++i) {
                p = (DPoint)points.get(i);
                this.path2dforjigimagecomponent.lineTo(p.x, p.y);
            }
            this.path2dforjigimagecomponent.closePath();
        }
        catch (Exception e) {
            this.path2dforjigimagecomponent = null;
        }
    }
}

