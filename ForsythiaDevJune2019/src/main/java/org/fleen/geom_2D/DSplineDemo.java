/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_2D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.RectangularShape;
import javax.swing.JFrame;
import org.fleen.geom_2D.DSpline;

public class DSplineDemo
extends JFrame {
    static DSplineDemo splinedemo;
    static double[][] points;

    static {
        points = new double[][]{{100.0, 400.0}, {100.0, 300.0}, {200.0, 250.0}, {100.0, 200.0}, {100.0, 100.0}, {300.0, 100.0}, {300.0, 200.0}, {400.0, 250.0}, {300.0, 300.0}, {300.0, 400.0}};
    }

    public static final void main(String[] a) {
        splinedemo = new DSplineDemo();
    }

    public DSplineDemo() {
        this.setVisible(true);
        this.setBounds(100, 100, 700, 700);
    }

    @Override
    public void paint(Graphics g) {
        DSpline spline = new DSpline(points);
        Path2D.Double path = new Path2D.Double();
        for (double i = 0.0; i < 1.0; i += 0.01) {
            double[] p = spline.getPoint(i);
            if (i == 0.0) {
                ((Path2D)path).moveTo(p[0], p[1]);
                continue;
            }
            ((Path2D)path).lineTo(p[0], p[1]);
        }
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(2.0f));
        g2d.setPaint(Color.black);
        g2d.draw(path);
        g2d.setPaint(Color.red);
        Ellipse2D.Double e = new Ellipse2D.Double(0.0, 0.0, 5.0, 5.0);
        double dotspan = 6.0;
        for (double[] a : points) {
            ((RectangularShape)e).setFrame(a[0] - dotspan / 2.0, a[1] - dotspan / 2.0, dotspan, dotspan);
            g2d.fill(e);
        }
    }
}

