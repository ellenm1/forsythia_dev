/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.core.composition;

import java.util.ArrayList;
import java.util.List;
import org.fleen.forsythia.core.composition.FGrid;
import org.fleen.forsythia.core.composition.FPolygonSignature;
import org.fleen.forsythia.core.composition.ForsythiaTreeNode;
import org.fleen.forsythia.core.grammar.FMetagon;
import org.fleen.geom_2D.DCircle;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_2D.GD;
import org.fleen.geom_2D.IncircleCalculator;
import org.fleen.geom_Kisrhombille.GK;
import org.fleen.geom_Kisrhombille.KAnchor;
import org.fleen.geom_Kisrhombille.KGrid;
import org.fleen.geom_Kisrhombille.KMetagon;
import org.fleen.geom_Kisrhombille.KMetagonVector;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.util.tag.TagManager;
import org.fleen.util.tag.Tagged;
import org.fleen.util.tree.TreeNode;

public class FPolygon
extends ForsythiaTreeNode
implements Tagged {
    private static final long serialVersionUID = 7403675520824450721L;
    public FMetagon metagon;
    public KAnchor anchor;
    private KPoint[] vertices;
    private DPolygon dpolygon = null;
    private Double detailsize = null;
    double perimeter2d = -1.0;
    double area2d = -1.0;
    private FPolygonSignature signature = null;
    private FPolygonSignature.SigComponent signaturecomponent = null;
    public int chorusindex;
    private TagManager tagmanager = new TagManager();

    public FPolygon(FMetagon metagon, KAnchor anchor, int chorusindex, List<String> tags) {
        this.metagon = metagon;
        this.anchor = anchor;
        this.chorusindex = chorusindex;
        this.setTags(metagon.getTags());
        this.tagmanager.addTags(tags);
        this.initVertices();
    }

    public FPolygon(Object m4) {
        this.metagon = (FMetagon) m4;
        KPolygon p = ((KMetagon) m4).getPolygon();
        this.anchor = new KAnchor((KPoint)p.get(0), (KPoint)p.get(1), true);
        this.chorusindex = 0;
        this.initVertices();
    }

    public FPolygon(FMetagon metagon, KAnchor anchor) {
        this.metagon = metagon;
        this.anchor = anchor;
        this.chorusindex = 0;
        this.initVertices();
    }

    public boolean isRootPolygon() {
        return this.getDepth() == 1;
    }

    public FPolygon getPolygonParent() {
        if (this.isRootPolygon()) {
            return null;
        }
        return (FPolygon)this.getAncestor(2);
    }

    public List<FPolygon> getPolygonChildren() {
        if (this.isLeaf()) {
            return new ArrayList<FPolygon>(0);
        }
        List<? extends TreeNode> children = this.getChild().getChildren();
        ArrayList<FPolygon> polygons = new ArrayList<FPolygon>(children.size());
        for (TreeNode n : children) {
            polygons.add((FPolygon)n);
        }
        return polygons;
    }

    public KPoint[] getVertices() {
        return this.vertices;
    }

    public int getVertexCount() {
        return this.vertices.length;
    }

    private void initVertices() {
        int vectorcount = this.metagon.vectors.length;
        this.vertices = new KPoint[vectorcount + 2];
        double localbaseinterval = this.getLocalBaseInterval();
        this.vertices[0] = this.anchor.v0;
        this.vertices[1] = this.anchor.v1;
        int direction = this.getLocalBaseForeward();
        for (int i = 0; i < vectorcount; ++i) {
            double distance = this.metagon.vectors[i].relativeinterval * localbaseinterval;
            int delta = this.metagon.vectors[i].directiondelta;
            if (!this.anchor.twist) {
                delta *= -1;
            }
            direction = (direction + delta + 12) % 12;
            this.vertices[i + 2] = new KPoint(GK.getVertex_VertexVector(this.vertices[i + 1].coors, direction, distance));
            if (this.vertices[i + 2].coors[3] != -1) continue;
            throw new IllegalArgumentException("BAD GEOMETRY. " + this.vertices[i + 1] + " , direction:" + direction + " distance:" + distance);
        }
    }

    public int getLocalBaseForeward() {
        int f = GK.getDirection_VertexVertex(this.anchor.v0.getAnt(), this.anchor.v0.getBat(), this.anchor.v0.getCat(), this.anchor.v0.getDog(), this.anchor.v1.getAnt(), this.anchor.v1.getBat(), this.anchor.v1.getCat(), this.anchor.v1.getDog());
        if (f == -1) {
            throw new IllegalArgumentException("local foreward is direction null " + this.anchor.v0 + " " + this.anchor.v1);
        }
        return f;
    }

    public double getLocalBaseInterval() {
        return this.anchor.v0.getDistance(this.anchor.v1);
    }

    public DPolygon getDPolygon() {
        if (this.dpolygon == null) {
            this.initDPolygon();
        }
        this.dpolygon.object = this;
        return this.dpolygon;
    }

    private void initDPolygon() {
        KPoint[] v = this.getVertices();
        int s = v.length;
        this.dpolygon = new DPolygon(s);
        FGrid fag = this.getFirstAncestorGrid();
        KGrid grid = fag != null ? fag.getLocalKGrid() : new KGrid();
        for (int i = 0; i < s; ++i) {
            this.dpolygon.add(new DPoint(grid.getPoint2D(v[i])));
        }
    }

    private double[][] getDPolygonAsDoubleArray() {
        DPolygon vp2d = this.getDPolygon();
        int s = vp2d.size();
        double[][] b = new double[s][2];
        for (int i = 0; i < s; ++i) {
            DPoint p = (DPoint)vp2d.get(i);
            b[i] = new double[]{p.x, p.y};
        }
        return b;
    }

    public double getDetailSize() {
        if (this.detailsize == null) {
            this.detailsize = IncircleCalculator.getIncircle((List<DPoint>)this.getDPolygon()).r * 2.0;
        }
        return this.detailsize;
    }

    public double getPerimeter2D() {
        if (this.perimeter2d == -1.0) {
            this.initPerimeterAndArea();
        }
        return this.perimeter2d;
    }

    public double getArea2D() {
        if (this.area2d == -1.0) {
            this.initPerimeterAndArea();
        }
        return this.area2d;
    }

    private void initPerimeterAndArea() {
        double[][] a = this.getDPolygonAsDoubleArray();
        this.perimeter2d = GD.getPerimeter(a);
        this.area2d = GD.getAbsArea2D(a);
    }

    public FPolygonSignature getSignature() {
        if (this.signature == null) {
            this.signature = new FPolygonSignature(this);
        }
        return this.signature;
    }

    public FPolygonSignature.SigComponent getSignatureComponent() {
        if (this.signaturecomponent == null) {
            this.signaturecomponent = new FPolygonSignature.SigComponent(this);
        }
        return this.signaturecomponent;
    }

    public int getChorusIndex() {
        return this.chorusindex;
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
        return String.valueOf(this.getClass().getSimpleName()) + "[" + this.hashCode() + "]" + this.tagmanager;
    }
}

