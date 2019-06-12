/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.util.grid;

import java.awt.geom.Path2D;
import java.util.List;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_2D.GD;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;

public class DrawingPolygon
extends KPolygon {
    private static final long serialVersionUID = -3518968791640299457L;
    public boolean closed = false;
    DPolygon points = null;

    public DrawingPolygon() {
    }

    public DrawingPolygon(KPolygon p) {
        super(p);
    }

    @Override
    public void clear() {
        super.clear();
        this.closed = false;
    }

    public void touchVertex(KPoint v) {
        if (!this.closed && v != null && this.isValid(v)) {
            if (this.size() >= 3 && v.equals(this.get(0))) {
                this.closed = true;
                return;
            }
            if (this.contains(v)) {
                this.remove(v);
            } else {
                this.add(v);
            }
        }
    }

    boolean isValid(KPoint v) {
        return true;
    }

    public boolean has3Vertices() {
        return this.size() > 2;
    }

    public boolean hasNoNoncolinearAdjacentVertices() {
        int vertexcount = this.size();
        for (int i = 0; i < vertexcount; ++i) {
            int inext = i + 1;
            if (inext == vertexcount) {
                inext = 0;
            }
            if (((KPoint)this.get(i)).isColinear((KPoint)this.get(inext))) continue;
            return false;
        }
        return true;
    }

    public DPolygon getPoints() {
        if (this.points == null) {
            this.points = this.getDefaultPolygon2D();
        }
        return this.points;
    }

    public Path2D.Double getPath() {
        DPolygon points = this.getPoints();
        Path2D.Double path = new Path2D.Double();
        DPoint p = (DPoint)points.get(0);
        path.moveTo(p.x, p.y);
        for (int i = 1; i < points.size(); ++i) {
            p = (DPoint)points.get(i);
            path.lineTo(p.x, p.y);
        }
        if (this.closed) {
            path.closePath();
        }
        return path;
    }

    public boolean isClockwise() {
        return GD.isClockwise(this.getPoints().getPointsAsDoubles());
    }

    @Override
    public String toString() {
        String s = String.valueOf(this.size()) + " vertices. ";
        s = this.closed ? String.valueOf(s) + " closed" : String.valueOf(s) + "open";
        return s;
    }
}

