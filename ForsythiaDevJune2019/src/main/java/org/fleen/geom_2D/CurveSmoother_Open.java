/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_2D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Path2D;
import javax.swing.JFrame;
import org.fleen.geom_2D.GD;

public class CurveSmoother_Open {
    public double[][] getSmoothedOpenCurve(double[][] opencurve, int smoothness) {
        double[][] curve = opencurve;
        for (int i = 0; i < smoothness; ++i) {
            curve = this.doSplitTweak(curve);
        }
        return curve;
    }

    private double[][] doSplitTweak(double[][] oldcurve) {
        int i;
        int oldsize = oldcurve.length;
        int newsize = oldsize * 2 - 1;
        double[][] newcurve = new double[newsize][2];
        for (i = 0; i < oldsize; ++i) {
            newcurve[i * 2] = new double[]{oldcurve[i][0], oldcurve[i][1]};
        }
        for (i = 0; i < newsize - 1; ++i) {
            if (i % 2 == 0) continue;
            newcurve[i] = GD.getPoint_Mid2Points(newcurve[i - 1][0], newcurve[i - 1][1], newcurve[i + 1][0], newcurve[i + 1][1]);
        }
        for (int i2 = 1; i2 < oldsize - 1; ++i2) {
            int imid0 = i2 * 2 - 1;
            int imid1 = i2 * 2 + 1;
            int iold = i2 * 2;
            double[] midmid = GD.getPoint_Mid2Points(newcurve[imid0][0], newcurve[imid0][1], newcurve[imid1][0], newcurve[imid1][1]);
            newcurve[iold] = GD.getPoint_Mid2Points(midmid[0], midmid[1], newcurve[iold][0], newcurve[iold][1]);
        }
        return newcurve;
    }

    public static final void main(String[] a) {
        JFrame frame = new JFrame(){

            @Override
            public void paint(Graphics g) {
                double[][] curve = new double[][]{{50.0, 50.0}, {50.0, 250.0}, {250.0, 250.0}};
                curve = new CurveSmoother_Open().getSmoothedOpenCurve(curve, 3);
                Path2D.Double path = new Path2D.Double();
                double[] p = curve[0];
                ((Path2D)path).moveTo(p[0], p[1]);
                for (int i = 1; i < curve.length; ++i) {
                    ((Path2D)path).lineTo(curve[i][0], curve[i][1]);
                }
                Graphics2D g2 = (Graphics2D)g;
                g2.setPaint(Color.black);
                g2.setStroke(new BasicStroke(2.0f));
                g2.draw(path);
            }
        };
        frame.setSize(400, 400);
        frame.setVisible(true);
        frame.repaint();
    }

}

