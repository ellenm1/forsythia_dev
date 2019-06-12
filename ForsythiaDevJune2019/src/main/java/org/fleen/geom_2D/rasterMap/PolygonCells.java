/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_2D.rasterMap;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_2D.rasterMap.Cell;
import org.fleen.geom_2D.rasterMap.CellKey;
import org.fleen.geom_2D.rasterMap.RasterMap;

public class PolygonCells {
    RasterMap rastermap;
    DPolygon polygon;
    DPolygon transformedpolygon;
    Map<CellKey, Cell> localcellcache = new HashMap<CellKey, Cell>();
    Set<Cell> primaryedgecells = new HashSet<Cell>();
    List<Set<Cell>> edgeinteriorlayers = new ArrayList<Set<Cell>>();
    List<Set<Cell>> edgeexteriorlayers = new ArrayList<Set<Cell>>();
    List<Set<Cell>> interiorlayers = new ArrayList<Set<Cell>>();

    PolygonCells(RasterMap rastermap, DPolygon polygon) {
        this.rastermap = rastermap;
        this.polygon = polygon;
        this.initTransformedPolygon();
        this.doCells();
    }

    private void initTransformedPolygon() {
        int s = this.polygon.size();
        this.transformedpolygon = new DPolygon(s);
        double[] a = new double[2];
        for (DPoint p : this.polygon) {
            a[0] = p.x;
            a[1] = p.y;
            this.rastermap.transform.transform(a, 0, a, 0, 1);
            this.transformedpolygon.add(new DPoint(a));
        }
    }

    private void doCells() {
        this.doPrimaryEdgeCells();
        this.doOtherEdgeCells();
        this.doInteriorCells();
    }

    Cell getCell(int x, int y) {
        CellKey k = new CellKey(x, y);
        Cell c = this.localcellcache.get(k);
        if (c == null) {
            c = this.rastermap.getCell(x, y);
            this.localcellcache.put(k, c);
        }
        return c;
    }

    Cell getCellContainingPoint(double x, double y) {
        Cell c = this.rastermap.getCellContainingPoint(x, y);
        return this.getCell(c.x, c.y);
    }

    private void doInteriorCells() {
        Set<Cell> layer = this.edgeinteriorlayers.get(this.edgeinteriorlayers.size() - 1);
        this.removeOffMapCells(layer);
        while (!layer.isEmpty()) {
            layer = this.getLayerOfUnmarkedCells(layer);
            this.removeOffMapCells(layer);
            this.markInteriorCells(layer);
            this.interiorlayers.add(layer);
        }
    }

    private void removeOffMapCells(Set<Cell> cells) {
        Iterator<Cell> i = cells.iterator();
        while (i.hasNext()) {
            Cell c = i.next();
            if (!this.rastermap.isOffMap(c.x, c.y)) continue;
            i.remove();
        }
    }

    private void doOtherEdgeCells() {
        Set<Cell> firstinnerouteredgelayer = this.getLayerOfUnmarkedCells(this.primaryedgecells);
        this.markEdgeCells(firstinnerouteredgelayer);
        HashSet<Cell> inlayer = new HashSet<Cell>();
        HashSet<Cell> exlayer = new HashSet<Cell>();
        for (Cell c : firstinnerouteredgelayer) {
            if (c.getPresenceIntensity(this.polygon) > 0.5) {
                inlayer.add(c);
                continue;
            }
            exlayer.add(c);
        }
        this.edgeinteriorlayers.add(inlayer);
        this.edgeexteriorlayers.add(exlayer);
        int additionaledgelayerscount = (int)(this.rastermap.glowspan / 1.0) + 1;
        this.doAdditionalInteriorEdgeLayers(inlayer, additionaledgelayerscount);
        this.doAdditionalExteriorEdgeLayers(exlayer, additionaledgelayerscount);
    }

    private void doAdditionalInteriorEdgeLayers(Set<Cell> inlayer, int count) {
        Set<Cell> layer = inlayer;
        for (int i = 0; i < count; ++i) {
            layer = this.getLayerOfUnmarkedCells(layer);
            this.markInteriorEdgeCells(layer);
            this.edgeinteriorlayers.add(layer);
        }
    }

    private void doAdditionalExteriorEdgeLayers(Set<Cell> exlayer, int count) {
        Set<Cell> layer = exlayer;
        for (int i = 0; i < count; ++i) {
            layer = this.getLayerOfUnmarkedCells(layer);
            this.markExteriorEdgeCells(layer);
            this.edgeexteriorlayers.add(layer);
        }
    }

    private void doPrimaryEdgeCells() {
        int s = this.polygon.size();
        for (int i0 = 0; i0 < s; ++i0) {
            int i1 = i0 + 1;
            if (i1 == s) {
                i1 = 0;
            }
            DPoint p0 = (DPoint)this.transformedpolygon.get(i0);
            DPoint p1 = (DPoint)this.transformedpolygon.get(i1);
            Cell c0 = this.getCellContainingPoint(p0.x, p0.y);
            Cell c1 = this.getCellContainingPoint(p1.x, p1.y);
            this.primaryedgecells.addAll(this.getSegCells(c0.x, c0.y, c1.x, c1.y));
        }
        this.markEdgeCells(this.primaryedgecells);
    }

    List<Cell> getSegCells(int x0, int y0, int x1, int y1) {
        ArrayList<Cell> segcells;
        int xstep;
        int ystep;
        segcells = new ArrayList<Cell>();
        int y = y0;
        int x = x0;
        int dx = x1 - x0;
        int dy = y1 - y0;
        if (dy < 0) {
            ystep = -1;
            dy = - dy;
        } else {
            ystep = 1;
        }
        if (dx < 0) {
            xstep = -1;
            dx = - dx;
        } else {
            xstep = 1;
        }
        double ddy = 2 * dy;
        double ddx = 2 * dx;
        if (ddx >= ddy) {
            int error;
            int errorprev = error = dx;
            for (int i = 0; i < dx; ++i) {
                x += xstep;
                if ((double)(error = (int)((double)error + ddy)) > ddx) {
                    y += ystep;
                    if ((double)((error = (int)((double)error - ddx)) + errorprev) < ddx) {
                        segcells.add(this.getCell(x, y - ystep));
                    } else if ((double)(error + errorprev) > ddx) {
                        segcells.add(this.getCell(x - xstep, y));
                    } else {
                        segcells.add(this.getCell(x, y - ystep));
                        segcells.add(this.getCell(x - xstep, y));
                    }
                }
                segcells.add(this.getCell(x, y));
                errorprev = error;
            }
        } else {
            int error;
            int errorprev = error = dy;
            for (int i = 0; i < dy; ++i) {
                y += ystep;
                if ((double)(error = (int)((double)error + ddx)) > ddy) {
                    x += xstep;
                    if ((double)((error = (int)((double)error - ddy)) + errorprev) < ddy) {
                        segcells.add(this.getCell(x - xstep, y));
                    } else if ((double)(error + errorprev) > ddy) {
                        segcells.add(this.getCell(x, y - ystep));
                    } else {
                        segcells.add(this.getCell(x - xstep, y));
                        segcells.add(this.getCell(x, y - ystep));
                    }
                }
                segcells.add(this.getCell(x, y));
                errorprev = error;
            }
        }
        return segcells;
    }

    private void markEdgeCells(Collection<Cell> cells) {
        for (Cell c : cells) {
            boolean isinterior = this.transformedpolygon.containsPoint(c.x, c.y);
            double dis = this.transformedpolygon.getDistance(c.x, c.y);
            double presence = isinterior ? 0.5 + dis / this.rastermap.glowspan * 0.5 : 0.5 - dis / this.rastermap.glowspan * 0.5;
            if (presence < 0.0) {
                presence = 0.0;
            }
            if (presence > 1.0) {
                presence = 1.0;
            }
            c.addPresence(this.polygon, presence);
        }
    }

    private void markInteriorEdgeCells(Collection<Cell> cells) {
        for (Cell c : cells) {
            double dis = this.transformedpolygon.getDistance(c.x, c.y);
            double presence = dis > this.rastermap.glowspan ? 1.0 : 0.5 + dis / this.rastermap.glowspan * 0.5;
            c.addPresence(this.polygon, presence);
        }
    }

    private void markExteriorEdgeCells(Collection<Cell> cells) {
        for (Cell c : cells) {
            double dis = this.transformedpolygon.getDistance(c.x, c.y);
            double presence = dis > this.rastermap.glowspan ? 0.0 : 0.5 - dis / this.rastermap.glowspan * 0.5;
            c.addPresence(this.polygon, presence);
        }
    }

    private void markInteriorCells(Collection<Cell> cells) {
        for (Cell c : cells) {
            c.addPresence(this.polygon);
        }
    }

    Set<Cell> getLayerOfUnmarkedCells(Collection<Cell> cells) {
        HashSet<Cell> unmarkedcells = new HashSet<Cell>();
        for (Cell c : cells) {
            for (Cell d : c.getNeighbors(this)) {
                if (d.hasPresence(this.polygon)) continue;
                unmarkedcells.add(d);
            }
        }
        return unmarkedcells;
    }
}

