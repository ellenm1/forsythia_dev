/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_2D.rasterMap;

import java.util.Iterator;
import org.fleen.geom_2D.rasterMap.Cell;
import org.fleen.geom_2D.rasterMap.RasterMap;

class RasterCellIterator
implements Iterator<Cell> {
    RasterMap rastermap;
    int x = 0;
    int y = 0;

    RasterCellIterator(RasterMap rastermap) {
        this.rastermap = rastermap;
    }

    @Override
    public boolean hasNext() {
        return this.y < this.rastermap.cellarrayheight;
    }

    @Override
    public Cell next() {
        Cell c = this.rastermap.cells[this.x][this.y];
        ++this.x;
        if (this.x == this.rastermap.cellarraywidth) {
            this.x = 0;
            ++this.y;
        }
        return c;
    }

    @Override
    public void remove() {
        throw new IllegalArgumentException("not implemented");
    }
}

