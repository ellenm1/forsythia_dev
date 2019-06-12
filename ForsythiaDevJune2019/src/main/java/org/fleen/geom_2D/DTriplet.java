/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_2D;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.GD;

public class DTriplet {
    DPoint p0;
    DPoint p1;
    DPoint p2;

    public DTriplet(DPoint p0, DPoint p1, DPoint p2) {
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
    }

    public double getRightAngle() {
        return GD.getAngle_3Points(this.p0.x, this.p0.y, this.p1.x, this.p1.y, this.p2.x, this.p2.y);
    }
}

