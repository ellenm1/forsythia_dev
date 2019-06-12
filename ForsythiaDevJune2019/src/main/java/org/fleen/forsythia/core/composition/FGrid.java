/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.core.composition;

import java.io.Serializable;
import org.fleen.forsythia.core.composition.ForsythiaTreeNode;
import org.fleen.geom_Kisrhombille.KGrid;

public abstract class FGrid
extends ForsythiaTreeNode
implements Serializable {
    private static final long serialVersionUID = 3593560887876552589L;
    protected KGrid localkgrid = null;

    public KGrid getLocalKGrid() {
        if (this.localkgrid == null) {
            this.localkgrid = this.initLocalKGrid();
        }
        return this.localkgrid;
    }

    protected abstract KGrid initLocalKGrid();
}

