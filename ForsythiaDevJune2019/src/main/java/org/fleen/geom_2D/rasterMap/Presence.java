/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_2D.rasterMap;

import org.fleen.geom_2D.DPolygon;

public class Presence {
    public DPolygon polygon;
    public double intensity;

    Presence(DPolygon polygon, double intensity) {
        this.polygon = polygon;
        this.intensity = intensity;
    }
}

