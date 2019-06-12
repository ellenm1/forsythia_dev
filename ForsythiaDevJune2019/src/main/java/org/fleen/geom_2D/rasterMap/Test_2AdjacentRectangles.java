/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_2D.rasterMap;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.List;
import javax.swing.JFrame;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_2D.rasterMap.Cell;
import org.fleen.geom_2D.rasterMap.PolygonCells;
import org.fleen.geom_2D.rasterMap.Presence;
import org.fleen.geom_2D.rasterMap.RasterMap;

public class Test_2AdjacentRectangles {
    Frame0 frame;
    DPolygon rectangle0 = new DPolygon(new DPoint(0.0, 0.0), new DPoint(0.0, 10.0), new DPoint(10.0, 10.0), new DPoint(10.0, 0.0));
    DPolygon rectangle1 = new DPolygon(new DPoint(10.0, 0.0), new DPoint(10.0, 10.0), new DPoint(20.0, 10.0), new DPoint(20.0, 0.0));
    private static final int CELLARRAYWIDTH = 800;
    private static final int CELLARRAYHEIGHT = 800;
    private static final double GLOWSPAN = 1.5;
    RasterMap rastermap;
    PolygonCells polygoncellmap;
    int imagewidth = 800;
    int imageheight = 800;
    private static final Color COLOR_R0 = new Color(112, 229, 254);
    private static final Color COLOR_R1 = new Color(255, 194, 111);

    public static final void main(String[] a) {
        new org.fleen.geom_2D.rasterMap.Test_2AdjacentRectangles();
    }

    Test_2AdjacentRectangles() {
        this.initFrame();
        this.initRasterMap();
        this.rastermap.castPresence(this.rectangle0, this.rectangle1);
        this.frame.repaint();
    }

    void initFrame() {
        this.frame = new Frame0();
    }

    void initRasterMap() {
        AffineTransform t = new AffineTransform();
        t.translate(60.0, 60.0);
        t.rotate(0.2);
        t.scale(12.0, 12.0);
        this.rastermap = new RasterMap(800, 800, t, 1.5);
    }

    BufferedImage render() {
        BufferedImage image = new BufferedImage(this.imagewidth, this.imageheight, 1);
        for (Cell c : this.rastermap) {
            image.setRGB(c.x, c.y, this.getColor(c).getRGB());
        }
        return image;
    }

    private Color getColor(Cell c) {
        double intensitysum = 0.0;
        for (Presence p : c.presences) {
            intensitysum += p.intensity;
        }
        int r = 0;
        int g = 0;
        int b = 0;
        for (Presence p : c.presences) {
            double normalized = p.intensity / intensitysum;
            Color color = this.getPolygonColor(p.polygon);
            r += (int)((double)color.getRed() * normalized);
            g += (int)((double)color.getGreen() * normalized);
            b += (int)((double)color.getBlue() * normalized);
        }
        return new Color(r, g, b);
    }

    private Color getPolygonColor(DPolygon polygon) {
        if (polygon == this.rectangle0) {
            return COLOR_R0;
        }
        return COLOR_R1;
    }

    class Frame0
    extends JFrame {
        Frame0() {
            this.setDefaultCloseOperation(3);
            this.setBounds(100, 100, 800, 800);
            this.setVisible(true);
        }

        @Override
        public void paint(Graphics g) {
            try {
                Graphics2D g2 = (Graphics2D)g;
                g2.drawImage(Test_2AdjacentRectangles.this.render(), null, null);
            }
            catch (Exception g2) {
                // empty catch block
            }
        }
    }

}

