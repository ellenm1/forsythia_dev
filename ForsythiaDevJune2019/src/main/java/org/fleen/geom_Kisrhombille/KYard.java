/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.fleen.geom_Kisrhombille.KPolygon;

public class KYard
extends ArrayList<KPolygon> {
    private static final long serialVersionUID = -6074974764136594169L;

    public /* varargs */ KYard(KPolygon ... polygons) {
        super(Arrays.asList(polygons));
    }

    public KYard(List<KPolygon> polygons) {
        super(polygons);
    }

    public KYard(int s) {
        super(s);
    }

    public KPolygon getOuterEdge() {
        if (this.isEmpty()) {
            return null;
        }
        return (KPolygon)this.get(0);
    }

    public List<KPolygon> getInnerEdges() {
        if (this.size() < 2) {
            return new ArrayList<KPolygon>(0);
        }
        return this.subList(1, this.size());
    }
}

