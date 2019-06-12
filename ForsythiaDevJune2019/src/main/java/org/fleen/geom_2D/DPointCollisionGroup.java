/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_2D;

import java.util.HashSet;
import java.util.Iterator;
import org.fleen.geom_2D.DPoint;

public class DPointCollisionGroup
extends HashSet<DPoint> {
    private double collisiondistance;
    private DPoint resolution = null;

    public DPointCollisionGroup(double collisiondistance, DPoint point) {
        this.collisiondistance = collisiondistance;
        this.add(point);
    }

    public boolean collision(DPoint p) {
        for (DPoint p0 : this) {
            if (p0.getDistance(p) >= this.collisiondistance) continue;
            return true;
        }
        return false;
    }

    public DPoint getResolution() {
        if (this.resolution == null) {
            this.initResolution();
        }
        return this.resolution;
    }

    private void initResolution() {
        if (this.size() == 1) {
            this.resolution = new DPoint((DPoint)this.iterator().next());
            return;
        }
        double xsum = 0.0;
        double ysum = 0.0;
        for (DPoint p : this) {
            xsum += p.x;
            ysum += p.y;
        }
        double s = this.size();
        this.resolution = new DPoint(xsum /= s, ysum /= s);
    }
}

