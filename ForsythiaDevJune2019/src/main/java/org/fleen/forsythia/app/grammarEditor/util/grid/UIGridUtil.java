/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.util.grid;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_Kisrhombille.GK;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;

public class UIGridUtil
implements Serializable {
    private static final long serialVersionUID = -8363625515185944702L;

    public static final List<double[]> convertGridVerticesToViewPoints(List<KPoint> vertices, int viewwidth, int viewheight, double viewscale, double viewcenterx, double viewcentery) {
        ArrayList<double[]> a = new ArrayList<double[]>(vertices.size());
        for (KPoint vertex : vertices) {
            a.add(UIGridUtil.convertGridVertexToViewPoint(vertex, viewwidth, viewheight, viewscale, viewcenterx, viewcentery));
        }
        return a;
    }

    public static final Map<KPoint, double[]> mapKVerticesToViewPoints(Collection<KPoint> vertices, int viewwidth, int viewheight, double viewscale, double viewcenterx, double viewcentery) {
        Hashtable<KPoint, double[]> a = new Hashtable<KPoint, double[]>();
        for (KPoint kv : vertices) {
            a.put(kv, UIGridUtil.convertGridVertexToViewPoint(kv, viewwidth, viewheight, viewscale, viewcenterx, viewcentery));
        }
        return a;
    }

    public static final double[] convertGridVertexToViewPoint(KPoint vertex, int viewwidth, int viewheight, double viewscale, double viewcenterx, double viewcentery) {
        double[] p;
        double[] arrd = p = GK.getBasicPoint2D_Vertex(vertex.coors);
        arrd[0] = arrd[0] - viewcenterx;
        double[] arrd2 = p;
        arrd2[1] = arrd2[1] - viewcentery;
        double[] arrd3 = p;
        arrd3[0] = arrd3[0] * viewscale;
        double[] arrd4 = p;
        arrd4[1] = arrd4[1] * viewscale;
        p[1] = (double)viewheight - p[1];
        double[] arrd5 = p;
        arrd5[0] = arrd5[0] + (double)(viewwidth / 2);
        double[] arrd6 = p;
        arrd6[1] = arrd6[1] - (double)(viewheight / 2);
        return p;
    }

    public static final Rectangle2D.Double getPolygonBounds2D(KPolygon polygon) {
        double minx;
        double maxx;
        DPolygon points = polygon.getDefaultPolygon2D();
        double maxy = maxx = Double.MIN_VALUE;
        double miny = minx = Double.MAX_VALUE;
        for (DPoint p : points) {
            if (minx > p.x) {
                minx = p.x;
            }
            if (miny > p.y) {
                miny = p.y;
            }
            if (maxx < p.x) {
                maxx = p.x;
            }
            if (maxy >= p.y) continue;
            maxy = p.y;
        }
        return new Rectangle2D.Double(minx, miny, maxx - minx, maxy - miny);
    }
}

