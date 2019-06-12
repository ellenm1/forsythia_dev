/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_2D;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.fleen.geom_2D.DPolygon;

public class DYard
extends ArrayList<DPolygon> {
    private static final long serialVersionUID = -1952265148800512304L;

    public DYard(List<DPolygon> polygons) {
        this.addAll(polygons);
    }

    public DYard(int size) {
        super(size);
    }
}

