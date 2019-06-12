/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.core.composition;

import org.fleen.forsythia.core.composition.FGrid;
import org.fleen.geom_Kisrhombille.KGrid;
import org.fleen.geom_Kisrhombille.KPoint;

public class FGridTransform
extends FGrid {
    private static final long serialVersionUID = -220936433111379307L;
    public KPoint origintransform;
    public int forewardtransform;
    public boolean twisttransform;
    public double fishtransform;

    public FGridTransform(KPoint origintransform, int forewardtransform, boolean twisttransform, double fishtransform) {
        this.origintransform = origintransform;
        this.forewardtransform = forewardtransform;
        this.twisttransform = twisttransform;
        this.fishtransform = fishtransform;
    }

    protected void flushLocalGeometryCache() {
        this.localkgrid = null;
    }

    @Override
    protected KGrid initLocalKGrid() {
        KGrid priorgrid = this.getFirstAncestorGrid().getLocalKGrid();
        double[] gridorigin = priorgrid.getPoint2D(this.origintransform);
        double gridforeward = priorgrid.getDirection2D(this.forewardtransform);
        boolean gridtwist = priorgrid.getTwist();
        if (!this.twisttransform) {
            gridtwist = !gridtwist;
        }
        double gridfish = priorgrid.getFish() * this.fishtransform;
        return new KGrid(gridorigin, gridforeward, gridtwist, gridfish);
    }
}

