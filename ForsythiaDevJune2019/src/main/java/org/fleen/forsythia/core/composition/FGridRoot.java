/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.core.composition;

import org.fleen.forsythia.core.composition.FGrid;
import org.fleen.geom_Kisrhombille.KGrid;

public class FGridRoot
extends FGrid {
    private static final long serialVersionUID = -515164684907387061L;
    private static final double[] ORIGIN_DEFAULT = new double[]{0.0, 0.0};
    private static final double FOREWARD_DEFAULT = 0.0;
    private static final boolean TWIST_DEFAULT = true;
    private static final double FISH_DEFAULT = 1.0;

    public FGridRoot(double[] origin, double foreward, boolean twist, double fish) {
        this.localkgrid = new KGrid(origin, foreward, twist, fish);
    }

    public FGridRoot() {
        this(ORIGIN_DEFAULT, 0.0, true, 1.0);
    }

    @Override
    protected KGrid initLocalKGrid() {
        return null;
    }

    protected void flushLocalGeometryCache() {
    }
}

