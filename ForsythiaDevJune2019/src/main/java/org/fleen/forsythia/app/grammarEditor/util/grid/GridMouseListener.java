/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.util.grid;

import org.fleen.geom_Kisrhombille.KPoint;

public interface GridMouseListener {
    public void touch(double[] var1, KPoint var2);

    public void movedCloseToVertex(KPoint var1);

    public void movedFarFromVertex(double[] var1);
}

