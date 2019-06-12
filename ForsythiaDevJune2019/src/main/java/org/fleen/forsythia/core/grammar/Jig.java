/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.core.grammar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.fleen.forsythia.core.Forsythia;
import org.fleen.forsythia.core.composition.FGrid;
import org.fleen.forsythia.core.composition.FGridTransform;
import org.fleen.forsythia.core.composition.FPolygon;
import org.fleen.forsythia.core.composition.ForsythiaTreeNode;
import org.fleen.forsythia.core.grammar.FMetagon;
import org.fleen.forsythia.core.grammar.JigSection;
import org.fleen.geom_2D.DCircle;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_2D.IncircleCalculator;
import org.fleen.geom_Kisrhombille.KAnchor;
import org.fleen.geom_Kisrhombille.KGrid;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.util.tag.TagManager;
import org.fleen.util.tag.Tagged;
import org.fleen.util.tree.TreeNode;

public class Jig
implements Serializable,
Tagged,
Forsythia {
    private static final long serialVersionUID = -5737903972508676140L;
    public int griddensity;
    public List<JigSection> sections;
    private Double detailsizepreviewbasedetailsize = null;
    private TagManager tagmanager = new TagManager();
    public Object gpobject;

    public Jig(int griddensity, List<JigSection> sections, String[] tags) {
        this.griddensity = griddensity;
        this.sections = sections;
        this.tagmanager.setTags(tags);
    }

    public int getGridDensity() {
        return this.griddensity;
    }

    public double getFishFactor() {
        return 1.0 / (double)this.getGridDensity();
    }

    public List<ForsythiaTreeNode> createNodes(FPolygon target) {
        ArrayList<ForsythiaTreeNode> newnodes = new ArrayList<ForsythiaTreeNode>();
        FGridTransform newgrid = new FGridTransform(target.anchor.v0, target.getLocalBaseForeward(), target.anchor.twist, this.getFishFactor() * target.getLocalBaseInterval() / target.metagon.baseinterval);
        target.setChild(newgrid);
        newgrid.setParent(target);
        for (JigSection section : this.sections) {
            ForsythiaTreeNode newnode = section.createNode();
            newnode.setParent(newgrid);
            newnodes.add(newnode);
        }
        newgrid.setChildren(newnodes);
        return newnodes;
    }

    public double getDetailSizePreview(FPolygon target) {
        double bds = this.getDetailSizePreviewBaseDetailSize();
        double fish = target.getFirstAncestorGrid().getLocalKGrid().getFish() * this.getFishFactor() * target.getLocalBaseInterval() / target.metagon.baseinterval;
        double detailsize = bds * fish;
        return detailsize;
    }

    public double getDetailSizePreviewBaseDetailSize() {
        if (this.detailsizepreviewbasedetailsize == null) {
            this.initDetailSizePreviewBaseDetailSize();
        }
        return this.detailsizepreviewbasedetailsize;
    }

    void initDetailSizePreviewBaseDetailSize() {
        List<KPolygon> polygons = this.getDSPTestPolygons();
        double minradius = Double.MAX_VALUE;
        for (KPolygon p : polygons) {
            double testradius = IncircleCalculator.getIncircle((List<DPoint>)p.getDefaultPolygon2D()).r;
            if (testradius >= minradius) continue;
            minradius = testradius;
        }
        this.detailsizepreviewbasedetailsize = minradius * 2.0;
    }

    private List<KPolygon> getDSPTestPolygons() {
        ArrayList<KPolygon> polygons = new ArrayList<KPolygon>(this.sections.size());
        for (JigSection section : this.sections) {
            KPolygon p = section.productmetagon.getPolygon(section.productanchor.v0, section.productanchor.v1);
            polygons.add(p);
        }
        return polygons;
    }

    public List<DPolygon> getTestPolygons() {
        ArrayList<DPolygon> polygons = new ArrayList<DPolygon>();
        for (JigSection s : this.sections) {
            polygons.add(s.getTestPolygon());
        }
        return polygons;
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

    public String toString() {
        StringBuffer a = new StringBuffer();
        a.append("[" + this.getClass().getSimpleName() + " ");
        a.append("griddensity=" + this.griddensity + " ");
        a.append("tags=" + this.tagmanager.toString() + "\n");
        for (JigSection s : this.sections) {
            a.append(String.valueOf(s.toString()) + "\n");
        }
        return a.toString();
    }
}

