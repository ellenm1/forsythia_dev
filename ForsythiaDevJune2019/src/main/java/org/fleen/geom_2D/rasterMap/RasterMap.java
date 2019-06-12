/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_2D.rasterMap;

import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_2D.rasterMap.Cell;
import org.fleen.geom_2D.rasterMap.PolygonCells;
import org.fleen.geom_2D.rasterMap.RasterCellIterator;

public class RasterMap
implements Iterable<Cell> {
    AffineTransform transform;
    double glowspan;
    static final double CELLSPAN = 1.0;
    Cell[][] cells;
    int cellarraywidth;
    int cellarrayheight;

    public RasterMap(int w, int h, AffineTransform t, double glowspan) {
        this.glowspan = glowspan;
        this.transform = t;
        this.initCells(w, h);
    }

    public RasterMap(int w, int h, AffineTransform t, double glowspan, DPolygon polygon) {
        this(w, h, t, glowspan);
        this.castPresence(polygon);
    }

    public RasterMap(int w, int h, AffineTransform t, double glowspan, List<DPolygon> polygons) {
        this(w, h, t, glowspan);
        this.castPresence(polygons);
    }

    public /* varargs */ RasterMap(int w, int h, AffineTransform t, double glowspan, DPolygon ... polygons) {
        this(w, h, t, glowspan);
        this.castPresence(polygons);
    }

    private void initCells(int w, int h) {
        this.cellarraywidth = w;
        this.cellarrayheight = h;
        this.cells = new Cell[w][h];
        for (int x = 0; x < w; ++x) {
            for (int y = 0; y < h; ++y) {
                this.cells[x][y] = new Cell(this, x, y);
            }
        }
    }

    Cell getCellContainingPoint(double x, double y) {
        x = x - Math.floor(x) < 0.5 ? Math.floor(x) : Math.ceil(x);
        y = y - Math.floor(y) < 0.5 ? Math.floor(y) : Math.ceil(y);
        return this.getCell((int)x, (int)y);
    }

    Cell getCell(int x, int y) {
        if (this.isOffMap(x, y)) {
            return new Cell(this, x, y, true);
        }
        return this.cells[x][y];
    }

    boolean isOffMap(int x, int y) {
        return x < 0 || x >= this.cellarraywidth || y < 0 || y >= this.cellarrayheight;
    }

    @Override
    public Iterator<Cell> iterator() {
        return new RasterCellIterator(this);
    }

    public PolygonCells castPresence(DPolygon polygon) {
        PolygonCells pcm = new PolygonCells(this, polygon);
        return pcm;
    }

    public void castPresence(List<DPolygon> polygons) {
        for (DPolygon polygon : polygons) {
            this.castPresence(polygon);
        }
    }

    public /* varargs */ void castPresence(DPolygon ... polygons) {
        this.castPresence(Arrays.asList(polygons));
    }
}

