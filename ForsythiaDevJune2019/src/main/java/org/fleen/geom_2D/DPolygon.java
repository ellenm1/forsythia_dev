/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_2D;

import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.fleen.geom_2D.DCircle;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DSeg;
import org.fleen.geom_2D.DVector;
import org.fleen.geom_2D.GD;
import org.fleen.geom_2D.IncircleCalculator;

public class DPolygon
extends ArrayList<DPoint> {
    private static final long serialVersionUID = 5206593635245516246L;
    public Object object;
    public Double signedarea = null;
    public Double perimeter = null;
    private double[][] pointsasdoubles = null;
    public DCircle incircle = null;
    DCircle outcircle = null;
    private List<DSeg> segs = null;
    Rectangle2D.Double bounds = null;
    transient Path2D.Double path = null;

    public DPolygon() {
    }

    public DPolygon(int s) {
        super(s);
    }

    public /* varargs */ DPolygon(DPoint ... p) {
        super(Arrays.asList(p));
    }

    public DPolygon(List<DPoint> p) {
        super(p);
    }

    public double getSignedArea() {
        if (this.signedarea == null) {
            this.signedarea = GD.getSignedArea2D(this.getPointsAsDoubles());
        }
        return this.signedarea;
    }

    public double getArea() {
        return Math.abs(this.getSignedArea());
    }

    public double getPerimeter() {
        if (this.perimeter == null) {
            this.perimeter = GD.getPerimeter(this.getPointsAsDoubles());
        }
        return this.perimeter;
    }

    public boolean getChirality() {
        return this.getSignedArea() < 0.0;
    }

    public double getChunkiness() {
        return this.getArea() / this.getPerimeter();
    }

    public double getGangliness() {
        return this.getPerimeter() / this.getArea();
    }

    public double[][] getPointsAsDoubles() {
        if (this.pointsasdoubles == null) {
            this.initPointsAsDoubles();
        }
        return this.pointsasdoubles;
    }

    public void initPointsAsDoubles() {
        int s = this.size();
        this.pointsasdoubles = new double[s][2];
        for (int i = 0; i < s; ++i) {
            DPoint p = (DPoint)this.get(i);
            this.pointsasdoubles[i] = new double[]{p.x, p.y};
        }
    }

    public boolean containsPoint(double x, double y) {
        Path2D.Double p = this.getPath2D();
        return p.contains(x, y);
    }

    public boolean containsPoint(DPoint a) {
        Path2D.Double p = this.getPath2D();
        return p.contains(a.x, a.y);
    }

    public double getDistance(double x, double y) {
        double dclosest = Double.MAX_VALUE;
        for (DSeg seg : this.getSegs()) {
            double dtest = seg.getDistance(x, y);
            if (dtest >= dclosest) continue;
            dclosest = dtest;
        }
        return dclosest;
    }

    public double getDistance(DPolygon p) {
        double closest = Double.MAX_VALUE;
        for (DSeg s0 : this.getSegs()) {
            for (DSeg s1 : p.getSegs()) {
                double test = s0.getDistance(s1);
                if (test >= closest) continue;
                closest = test;
            }
        }
        return closest;
    }

    public boolean intersect(DPolygon other) {
        Rectangle2D.Double otherpolygonbounds = other.getBounds();
        Rectangle2D.Double thispolygonbounds = this.getBounds();
        if (!GD.testIntersection_2Rectangles(thispolygonbounds.x, thispolygonbounds.y, thispolygonbounds.width, thispolygonbounds.height, otherpolygonbounds.x, otherpolygonbounds.y, otherpolygonbounds.width, otherpolygonbounds.height)) {
            return false;
        }
        for (DPoint p : this) {
            if (!other.containsPoint(p.x, p.y)) continue;
            return true;
        }
        for (DPoint p : other) {
            if (!this.containsPoint(p.x, p.y)) continue;
            return true;
        }
        List<DSeg> segs0 = this.getSegs();
        List<DSeg> segs1 = other.getSegs();
        for (DSeg s0 : segs0) {
            for (DSeg s1 : segs1) {
                if (!s0.intersects(s1)) continue;
                return true;
            }
        }
        return false;
    }

    public DCircle getIncircle() {
        if (this.incircle == null) {
            this.incircle = IncircleCalculator.getIncircle(this.getPointsAsDoubles());
        }
        return this.incircle;
    }

    public double[] getInPoint() {
        DCircle c = this.getIncircle();
        return new double[]{c.x, c.y};
    }

    public double getDepth() {
        DCircle c = this.getIncircle();
        return c.r;
    }

    public double getDetailSize() {
        return this.getDepth() * 2.0;
    }

    public DCircle getOutcircle() {
        if (this.outcircle == null) {
            this.initOutcircle();
        }
        return this.outcircle;
    }

    private void initOutcircle() {
        DCircle ic = this.getIncircle();
        double dfurthest = 0.0;
        for (DPoint p : this) {
            double dtest = p.getDistance(ic.x, ic.y);
            if (dtest <= dfurthest) continue;
            dfurthest = dtest;
        }
        this.outcircle = new DCircle(ic.x, ic.y, dfurthest);
    }

    public boolean intersectOutcircles(DPolygon other) {
        DCircle thisoc = this.getOutcircle();
        DCircle otheroc = other.getOutcircle();
        double a = GD.getDistance_PointPoint(thisoc.x, thisoc.y, otheroc.x, otheroc.y);
        boolean i = a < thisoc.r + otheroc.r;
        return i;
    }

    public List<DSeg> getSegs() {
        if (this.segs == null) {
            this.initSegs();
        }
        return this.segs;
    }

    private void initSegs() {
        this.segs = new ArrayList<DSeg>(this.size());
        int s = this.size();
        for (int i0 = 0; i0 < s; ++i0) {
            int i1 = i0 + 1;
            if (i1 == s) {
                i1 = 0;
            }
            this.segs.add(new DSeg((DPoint)this.get(i0), (DPoint)this.get(i1)));
        }
    }

    public List<DVector> getInnerOuterPolygonVectors(double offset) {
        boolean clockwise = this.getChirality();
        int s = this.size();
        ArrayList<DVector> vectors = new ArrayList<DVector>(s);
        for (int i = 0; i < s; ++i) {
            int inext;
            int iprior = i - 1;
            if (iprior == -1) {
                iprior = s - 1;
            }
            if ((inext = i + 1) == s) {
                inext = 0;
            }
            DPoint p = (DPoint)this.get(i);
            DPoint pprior = (DPoint)this.get(iprior);
            DPoint pnext = (DPoint)this.get(inext);
            DVector v = this.getInnerOuterPointVector(pprior, p, pnext, clockwise, offset);
            vectors.add(v);
        }
        return vectors;
    }

    private DVector getInnerOuterPointVector(DPoint p0, DPoint p1, DPoint p2, boolean clockwise, double offset) {
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
        double dis = offset / GD.sin(angle / 2.0);
        return new DVector(dir, dis);
    }

    public DPolygon getInnerOuterPolygon(double offset) {
        List<DVector> vectors = this.getInnerOuterPolygonVectors(offset);
        int s = this.size();
        DPolygon pinnerouter = new DPolygon(s);
        for (int i = 0; i < s; ++i) {
            DPoint p0 = (DPoint)this.get(i);
            DVector v = vectors.get(i);
            DPoint p1 = p0.getPoint(v);
            pinnerouter.add(p1);
        }
        return pinnerouter;
    }

    public Rectangle2D.Double getBounds() {
        if (this.bounds == null) {
            this.initBounds();
        }
        return this.bounds;
    }

    private void initBounds() {
        double minx;
        double maxx;
        double[][] pa = this.getPointsAsDoubles();
        double maxy = maxx = Double.MIN_VALUE;
        double miny = minx = Double.MAX_VALUE;
        for (int i = 0; i < pa.length; ++i) {
            if (minx > pa[i][0]) {
                minx = pa[i][0];
            }
            if (miny > pa[i][1]) {
                miny = pa[i][1];
            }
            if (maxx < pa[i][0]) {
                maxx = pa[i][0];
            }
            if (maxy >= pa[i][1]) continue;
            maxy = pa[i][1];
        }
        this.bounds = new Rectangle2D.Double(minx, miny, maxx - minx, maxy - miny);
    }

    public Path2D.Double getPath2D() {
        if (this.path == null) {
            this.initPath2D();
        }
        return this.path;
    }

    private void initPath2D() {
        this.path = new Path2D.Double();
        DPoint p = (DPoint)this.get(0);
        this.path.moveTo(p.x, p.y);
        for (int i = 1; i < this.size(); ++i) {
            p = (DPoint)this.get(i);
            this.path.lineTo(p.x, p.y);
        }
        this.path.closePath();
    }

    public boolean sharesVertices(DPolygon polygon) {
        for (DPoint p : polygon) {
            if (!this.contains(p)) continue;
            return true;
        }
        return false;
    }

    public boolean sloppyMatch(DPolygon polygon) {
        int s = polygon.size();
        if (s != this.size()) {
            return false;
        }
        for (DPoint p : polygon) {
            if (this.getEqualPoint(p) != null) continue;
            return false;
        }
        return true;
    }

    public DPoint getEqualPoint(DPoint p) {
        for (DPoint p0 : this) {
            if (!p0.equals(p)) continue;
            return p0;
        }
        return null;
    }

    @Override
    public Object clone() {
        DPolygon clone = new DPolygon();
        for (DPoint p : this) {
            clone.add(new DPoint(p));
        }
        return clone;
    }

    @Override
    public String toString() {
        StringBuffer a = new StringBuffer("[");
        for (DPoint p : this) {
            a.append("(" + p.x + "," + p.y + "),");
        }
        a.append("]");
        return a.toString();
    }
}

