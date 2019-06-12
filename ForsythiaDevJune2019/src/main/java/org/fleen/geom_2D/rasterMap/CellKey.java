/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_2D.rasterMap;

import org.fleen.geom_2D.rasterMap.Cell;

class CellKey {
    int x;
    int y;

    CellKey(int x, int y) {
        this.x = x;
        this.y = y;
    }

    CellKey(Cell c) {
        this.x = c.x;
        this.y = c.y;
    }

    public int hashCode() {
        return this.x + this.y * 19;
    }

    public boolean equals(Object a) {
        CellKey b = (CellKey)a;
        return b.x == this.x && b.y == this.y;
    }
}

