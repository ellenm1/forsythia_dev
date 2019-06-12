/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.project.metagon;

import java.awt.geom.Path2D;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_Kisrhombille.GK;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;

public class MetagonEditorGeometryCache
implements Serializable {
    private static final long serialVersionUID = -1760731649711575167L;
    private Map<KPoint, double[]> p2dbykvertex = new Hashtable<KPoint, double[]>();
    private int viewwidth = -1;
    private int viewheight = -1;
    private double viewscale = -1.0;
    private double viewcenterx = Double.MIN_VALUE;
    private double viewcentery = Double.MIN_VALUE;
    private Map<KPolygon, Path2D> path2dbykpolygon = new Hashtable<KPolygon, Path2D>();

    public void update(int w, int h, double scale, double centerx, double centery) {
        if (w != this.viewwidth || h != this.viewheight || scale != this.viewscale || centerx != this.viewcenterx || centery != this.viewcentery) {
            this.invalidate();
            this.viewwidth = w;
            this.viewheight = h;
            this.viewscale = scale;
            this.viewcenterx = centerx;
            this.viewcentery = centery;
        }
    }

    public void invalidate() {
        System.out.println("invalidate display geometry cache");
        this.p2dbykvertex.clear();
        this.path2dbykpolygon.clear();
    }

    public double[] getPoint(KPoint v) {
        double[] p = this.p2dbykvertex.get(v);
        if (p == null) {
            p = this.convertGridVertexToViewPoint(v);
            this.p2dbykvertex.put(v, p);
        }
        return p;
    }

    public DPolygon getDPolygon(KPolygon polygon) {
        int s = polygon.size();
        DPolygon dpolygon = new DPolygon(s);
        for (int i = 0; i < s; ++i) {
            dpolygon.add(new DPoint(this.getPoint((KPoint)polygon.get(i))));
        }
        return dpolygon;
    }

    private double[] convertGridVertexToViewPoint(KPoint vertex) {
        double[] p;
        double[] arrd = p = GK.getBasicPoint2D_Vertex(vertex.coors);
        arrd[0] = arrd[0] - this.viewcenterx;
        double[] arrd2 = p;
        arrd2[1] = arrd2[1] - this.viewcentery;
        double[] arrd3 = p;
        arrd3[0] = arrd3[0] * this.viewscale;
        double[] arrd4 = p;
        arrd4[1] = arrd4[1] * this.viewscale;
        p[1] = (double)this.viewheight - p[1];
        double[] arrd5 = p;
        arrd5[0] = arrd5[0] + (double)(this.viewwidth / 2);
        double[] arrd6 = p;
        arrd6[1] = arrd6[1] - (double)(this.viewheight / 2);
        return p;
    }

    public Path2D getPath(KPolygon polygon) {
        Path2D path = this.path2dbykpolygon.get(polygon);
        if (path == null) {
            path = this.createPath(polygon);
            this.path2dbykpolygon.put(polygon, path);
        }
        return path;
    }

    private Path2D createPath(KPolygon kp) {
        int pointcount = kp.size();
        Path2D.Double path = new Path2D.Double();
        double[] p = this.getPoint((KPoint)kp.get(0));
        path.moveTo(p[0], p[1]);
        for (int i = 1; i < pointcount; ++i) {
            p = this.getPoint((KPoint)kp.get(i));
            path.lineTo(p[0], p[1]);
        }
        path.closePath();
        return path;
    }
}

