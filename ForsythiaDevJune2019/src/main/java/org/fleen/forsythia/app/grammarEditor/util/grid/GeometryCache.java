/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.util.grid;

import java.awt.geom.Path2D;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.fleen.forsythia.app.grammarEditor.util.grid.Grid;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_Kisrhombille.GK;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;

public class GeometryCache
implements Serializable {
    private static final long serialVersionUID = 2104701982735798049L;
    Grid grid;
    private Map<KPoint, DPoint> p2dbykvertex = new Hashtable<KPoint, DPoint>();
    private Map<KPolygon, Path2D> path2dbykpolygon = new Hashtable<KPolygon, Path2D>();

    GeometryCache(Grid grid) {
        this.grid = grid;
    }

    public void clear() {
        System.out.println("clear geometry cache");
        this.p2dbykvertex.clear();
        this.path2dbykpolygon.clear();
    }

    public Iterator<DPoint> getPoint2DIterator() {
        return this.p2dbykvertex.values().iterator();
    }

    public List<DPoint> getPoint2Ds(KPolygon p) {
        ArrayList<DPoint> points = new ArrayList<DPoint>();
        for (KPoint v : p) {
            points.add(this.getPoint2D(v));
        }
        return points;
    }

    public List<DPoint> getPoint2Ds(List<KPoint> vertices) {
        ArrayList<DPoint> points = new ArrayList<DPoint>();
        for (KPoint v : vertices) {
            points.add(this.getPoint2D(v));
        }
        return points;
    }

    public DPoint getPoint2D(KPoint v) {
        DPoint p = this.p2dbykvertex.get(v);
        if (p == null) {
            p = this.createPoint2D(v);
            this.p2dbykvertex.put(v, p);
        }
        return p;
    }

    private DPoint createPoint2D(KPoint vertex) {
        double viewwidth = this.grid.getWidth();
        double viewheight = this.grid.getHeight();
        DPoint p = new DPoint(GK.getBasicPoint2D_Vertex(vertex.coors));
        p.x -= this.grid.viewcenterx;
        p.y -= this.grid.viewcentery;
        p.x *= this.grid.viewscale;
        p.y *= this.grid.viewscale;
        p.y = viewheight - p.y;
        p.x += viewwidth / 2.0;
        p.y -= viewheight / 2.0;
        return p;
    }

    public Path2D getPath2D(KPolygon p) {
        Path2D path = this.path2dbykpolygon.get(p);
        if (path == null) {
            path = this.createPath2D(p);
            this.path2dbykpolygon.put(p, path);
        }
        return path;
    }

    private Path2D createPath2D(KPolygon kp) {
        int pointcount = kp.size();
        Path2D.Double path = new Path2D.Double();
        DPoint p = this.getPoint2D((KPoint)kp.get(0));
        path.moveTo(p.x, p.y);
        for (int i = 1; i < pointcount; ++i) {
            p = this.getPoint2D((KPoint)kp.get(i));
            path.lineTo(p.x, p.y);
        }
        path.closePath();
        return path;
    }
}

