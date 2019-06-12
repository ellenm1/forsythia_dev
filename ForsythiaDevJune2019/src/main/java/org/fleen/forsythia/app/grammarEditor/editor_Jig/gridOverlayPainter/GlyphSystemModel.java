/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.editor_Jig.gridOverlayPainter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_2D.DSeg;
import org.fleen.geom_2D.GD;

public class GlyphSystemModel
implements Serializable {
    private static final long serialVersionUID = 1958965206775431418L;
    double inset;
    private DPolygon innerpolygon;
    private static final double TOOSMALL = 2.2;
    boolean valid = true;
    private static final int SHORTLASTSIDELIMIT = 6;
    List<DPoint> glyphpath;

    public GlyphSystemModel(DPolygon polygon, double inset) {
        this.inset = inset;
        this.createInnerPolygon(polygon);
        this.doValidityTest();
        if (this.valid) {
            // empty if block
        }
        this.createGlyphPath();
    }

    public DPolygon getInnerPolygon() {
        return this.innerpolygon;
    }

    private void createInnerPolygon(DPolygon outerpolygon) {
        boolean clockwise = outerpolygon.getChirality();
        int s = outerpolygon.size();
        this.innerpolygon = new DPolygon(s);
        for (int i = 0; i < s; ++i) {
            int inext;
            int iprior = i - 1;
            if (iprior == -1) {
                iprior = s - 1;
            }
            if ((inext = i + 1) == s) {
                inext = 0;
            }
            DPoint p = (DPoint)outerpolygon.get(i);
            DPoint pprior = (DPoint)outerpolygon.get(iprior);
            DPoint pnext = (DPoint)outerpolygon.get(inext);
            DPoint pinner = this.getInnerPoint(pprior, p, pnext, clockwise, this.inset);
            this.innerpolygon.add(pinner);
        }
    }

    private DPoint getInnerPoint(DPoint p0, DPoint p1, DPoint p2, boolean clockwise, double inset) {
        double angle;
        double dir;
        if (clockwise) {
            angle = GD.getAngle_3Points(p0.x, p0.y, p1.x, p1.y, p2.x, p2.y);
            dir = GD.getDirection_3Points(p0.x, p0.y, p1.x, p1.y, p2.x, p2.y);
        } else {
            angle = GD.getAngle_3Points(p2.x, p2.y, p1.x, p1.y, p0.x, p0.y);
            dir = GD.getDirection_3Points(p2.x, p2.y, p1.x, p1.y, p0.x, p0.y);
        }
        if (angle > 3.141592653589793) {
            angle = 6.283185307179586 - angle;
        }
        double dis = inset / GD.sin(angle / 2.0);
        DPoint innerpoint = p1.getPoint(dir, dis);
        return innerpoint;
    }

    public boolean isValid() {
        return this.valid;
    }

    private void doValidityTest() {
        List<DSeg> segs = this.innerpolygon.getSegs();
        double limit = this.inset * 2.2;
        for (DSeg seg : segs) {
            if (seg.getLength() >= limit) continue;
            this.valid = false;
            return;
        }
    }

    public DPoint getV0DotPoint() {
        return (DPoint)this.innerpolygon.get(0);
    }

    private void createGlyphPath() {
        List<DSeg> segs = this.innerpolygon.getSegs();
        this.glyphpath = new ArrayList<DPoint>(this.innerpolygon.size());
        int lastside = this.getLastSide(segs);
        for (int i = 0; i < lastside + 1; ++i) {
            this.glyphpath.add((DPoint)this.innerpolygon.get(i));
        }
        this.glyphpath.add(new DPoint(segs.get(lastside).getPointAtProportionalOffset(0.5)));
    }

    private int getLastSide(List<DSeg> segs) {
        double shortsidelimit = 6.0 * this.inset;
        int s = segs.size();
        for (int i = s - 1; i > -1; --i) {
            DSeg seg = segs.get(i);
            if (seg.getLength() <= shortsidelimit) continue;
            return i;
        }
        throw new IllegalArgumentException("couldn't get the last side seg index");
    }
}

