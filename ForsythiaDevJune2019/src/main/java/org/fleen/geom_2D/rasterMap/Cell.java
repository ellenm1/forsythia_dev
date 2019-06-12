/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_2D.rasterMap;

import java.util.ArrayList;
import java.util.List;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_2D.rasterMap.PolygonCells;
import org.fleen.geom_2D.rasterMap.Presence;
import org.fleen.geom_2D.rasterMap.RasterMap;

public class Cell {
    RasterMap rastermap;
    boolean offmap = false;
    public int x;
    public int y;
    private static final int INITPRESENCELISTSIZE = 10;
    public List<Presence> presences = new ArrayList<Presence>(10);
    private static final int PRIME = 104729;

    Cell(RasterMap rastermap, int x, int y) {
        this.rastermap = rastermap;
        this.x = x;
        this.y = y;
    }

    Cell(RasterMap rastermap, int x, int y, boolean offmap) {
        this(rastermap, x, y);
        this.offmap = offmap;
    }

    List<Cell> getNeighbors() {
        ArrayList<Cell> n = new ArrayList<Cell>(8);
        Cell a = this.rastermap.getCell(this.x, this.y + 1);
        if (a != null) {
            n.add(a);
        }
        if ((a = this.rastermap.getCell(this.x + 1, this.y + 1)) != null) {
            n.add(a);
        }
        if ((a = this.rastermap.getCell(this.x + 1, this.y)) != null) {
            n.add(a);
        }
        if ((a = this.rastermap.getCell(this.x + 1, this.y - 1)) != null) {
            n.add(a);
        }
        if ((a = this.rastermap.getCell(this.x, this.y - 1)) != null) {
            n.add(a);
        }
        if ((a = this.rastermap.getCell(this.x - 1, this.y - 1)) != null) {
            n.add(a);
        }
        if ((a = this.rastermap.getCell(this.x - 1, this.y)) != null) {
            n.add(a);
        }
        if ((a = this.rastermap.getCell(this.x - 1, this.y + 1)) != null) {
            n.add(a);
        }
        return n;
    }

    List<Cell> getNeighbors(PolygonCells pc) {
        ArrayList<Cell> n = new ArrayList<Cell>(8);
        Cell a = pc.getCell(this.x, this.y + 1);
        if (a != null) {
            n.add(a);
        }
        if ((a = pc.getCell(this.x + 1, this.y + 1)) != null) {
            n.add(a);
        }
        if ((a = pc.getCell(this.x + 1, this.y)) != null) {
            n.add(a);
        }
        if ((a = pc.getCell(this.x + 1, this.y - 1)) != null) {
            n.add(a);
        }
        if ((a = pc.getCell(this.x, this.y - 1)) != null) {
            n.add(a);
        }
        if ((a = pc.getCell(this.x - 1, this.y - 1)) != null) {
            n.add(a);
        }
        if ((a = pc.getCell(this.x - 1, this.y)) != null) {
            n.add(a);
        }
        if ((a = pc.getCell(this.x - 1, this.y + 1)) != null) {
            n.add(a);
        }
        return n;
    }

    void addPresence(Presence p) {
        this.presences.add(p);
    }

    void addPresence(DPolygon polygon, double intensity) {
        this.addPresence(new Presence(polygon, intensity));
    }

    void addPresence(DPolygon polygon) {
        this.addPresence(polygon, 1.0);
    }

    boolean hasPresence(DPolygon polygon) {
        for (Presence p : this.presences) {
            if (p.polygon != polygon) continue;
            return true;
        }
        return false;
    }

    double getPresenceIntensity(DPolygon polygon) {
        for (Presence p : this.presences) {
            if (p.polygon != polygon) continue;
            return p.intensity;
        }
        return 0.0;
    }

    public int hashCode() {
        return this.x + this.y * 104729;
    }

    public boolean equals(Object a) {
        Cell b = (Cell)a;
        return b.x == this.x && b.y == this.y;
    }
}

