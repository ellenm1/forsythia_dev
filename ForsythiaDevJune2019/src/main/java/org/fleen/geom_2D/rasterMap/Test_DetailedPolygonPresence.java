/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_2D.rasterMap;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.List;
import java.util.Set;
import javax.swing.JFrame;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_2D.rasterMap.Cell;
import org.fleen.geom_2D.rasterMap.PolygonCells;
import org.fleen.geom_2D.rasterMap.RasterMap;

public class Test_DetailedPolygonPresence {
    Frame0 frame;
    DPolygon polygon = new DPolygon(new DPoint(10.0, 10.0), new DPoint(30.0, 50.0), new DPoint(50.0, 10.0));
    private static final int CELLARRAYWIDTH = 100;
    private static final int CELLARRAYHEIGHT = 100;
    private static final double GLOWSPAN = 2.5;
    RasterMap rastermap;
    PolygonCells polygoncellmap;
    private static final double RENDERSCALE = 11.0;

    public static final void main(String[] a) {
        new org.fleen.geom_2D.rasterMap.Test_DetailedPolygonPresence();
    }

    Test_DetailedPolygonPresence() {
        this.initFrame();
        this.initRasterMap();
        this.polygoncellmap = this.rastermap.castPresence(this.polygon);
        this.frame.repaint();
    }

    void initFrame() {
        this.frame = new Frame0();
    }

    void initRasterMap() {
        AffineTransform t = new AffineTransform();
        this.rastermap = new RasterMap(100, 100, t, 2.5);
    }

    BufferedImage render() {
        int i;
        double presence;
        Set<Cell> layer;
        int alpha;
        BufferedImage image = new BufferedImage(800, 800, 2);
        Graphics2D g = image.createGraphics();
        g.setPaint(new Color(224, 224, 224));
        g.fillRect(0, 0, 800, 800);
        Rectangle2D.Double e = new Rectangle2D.Double(1.0, 1.0, 1.0, 1.0);
        g.setPaint(Color.white);
        Cell[][] arrcell = this.rastermap.cells;
        int n = arrcell.length;
        for (int j = 0; j < n; ++j) {
            Cell[] row;
            for (Cell c : row = arrcell[j]) {
                e.setFrame((double)c.x * 11.0 - 5.0, (double)c.y * 11.0 - 5.0, 10.0, 10.0);
                g.fill(e);
            }
        }
        for (Cell c : this.polygoncellmap.primaryedgecells) {
            double presence2 = c.getPresenceIntensity(this.polygon);
            if (presence2 < 0.0 || presence2 > 1.0) {
                throw new IllegalArgumentException("primary edge cells -- PRESENCE OUT OF RANGE : " + presence2);
            }
            alpha = (int)(presence2 * 255.0);
            g.setPaint(new Color(0, 0, 0, alpha));
            e.setFrame((double)c.x * 11.0 - 5.0, (double)c.y * 11.0 - 5.0, 10.0, 10.0);
            g.fill(e);
        }
        for (i = 0; i < this.polygoncellmap.edgeexteriorlayers.size(); ++i) {
            layer = this.polygoncellmap.edgeexteriorlayers.get(i);
            for (Cell c : layer) {
                presence = c.getPresenceIntensity(this.polygon);
                if (presence < 0.0 || presence > 1.0) {
                    throw new IllegalArgumentException("edge exterior layers -- PRESENCE OUT OF RANGE : " + presence);
                }
                alpha = (int)(presence * 255.0);
                if (i % 2 == 0) {
                    g.setPaint(new Color(128, 0, 0, alpha));
                } else {
                    g.setPaint(new Color(0, 0, 128, alpha));
                }
                e.setFrame((double)c.x * 11.0 - 5.0, (double)c.y * 11.0 - 5.0, 10.0, 10.0);
                g.fill(e);
            }
        }
        for (i = 0; i < this.polygoncellmap.edgeinteriorlayers.size(); ++i) {
            layer = this.polygoncellmap.edgeinteriorlayers.get(i);
            for (Cell c : layer) {
                presence = c.getPresenceIntensity(this.polygon);
                if (presence < 0.0 || presence > 1.0) {
                    throw new IllegalArgumentException("edge interior layers -- PRESENCE OUT OF RANGE : " + presence);
                }
                alpha = (int)(presence * 255.0);
                if (i % 2 == 0) {
                    g.setPaint(new Color(128, 0, 0, alpha));
                } else {
                    g.setPaint(new Color(0, 0, 128, alpha));
                }
                e.setFrame((double)c.x * 11.0 - 5.0, (double)c.y * 11.0 - 5.0, 10.0, 10.0);
                g.fill(e);
            }
        }
        for (i = 0; i < this.polygoncellmap.interiorlayers.size(); ++i) {
            layer = this.polygoncellmap.interiorlayers.get(i);
            for (Cell c : layer) {
                presence = c.getPresenceIntensity(this.polygon);
                if (presence < 0.0 || presence > 1.0) {
                    throw new IllegalArgumentException("interior layers -- PRESENCE OUT OF RANGE : " + presence);
                }
                alpha = (int)(presence * 255.0);
                if (i % 2 == 0) {
                    g.setPaint(new Color(0, 128, 0, alpha));
                } else {
                    g.setPaint(new Color(128, 0, 128, alpha));
                }
                e.setFrame((double)c.x * 11.0 - 5.0, (double)c.y * 11.0 - 5.0, 10.0, 10.0);
                g.fill(e);
            }
        }
        g.setPaint(new Color(255, 255, 0, 128));
        g.setStroke(new BasicStroke(0.2f));
        g.setTransform(AffineTransform.getScaleInstance(11.0, 11.0));
        Path2D.Double path = this.polygon.getPath2D();
        g.draw(path);
        return image;
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
                g2.drawImage(Test_DetailedPolygonPresence.this.render(), null, null);
            }
            catch (Exception g2) {
                // empty catch block
            }
        }
    }

}

