/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_2D;

import java.awt.geom.Path2D;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.fleen.geom_2D.DCircle;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.GD;

public class IncircleCalculator {
    private static final int SAMPLESIZE = 80;
    private static final int SCANCOUNT = 5;
    private List<double[]> polygonpoints;
    private List<double[]> polygonsegs;
    private Random random = new Random();
    private Path2D.Double polygonpath;
    private double[] deeppoint = new double[2];
    private double scanboxleft;
    private double scanboxtop;
    private double scanboxwidth;
    private double scanboxheight;
    private double deeppointdepthsquared;
    private static final double[][] TESTPOLYGON3 = new double[][]{{0.0, 0.0}, {1.0, 3.0}, {3.0, 3.0}, {5.0, 4.0}, {5.0, 2.0}, {3.0, 2.0}, {5.0, 0.0}};
    private static final double[][] TESTPOLYGON_SQUARE = new double[][]{{0.0, 0.0}, {0.0, 1.0}, {1.0, 1.0}, {1.0, 0.0}};

    public static final DCircle getIncircle(double[][] polygonpoints) {
        IncircleCalculator irc = new IncircleCalculator(Arrays.asList(polygonpoints));
        return new DCircle(irc.deeppoint[0], irc.deeppoint[1], Math.sqrt(irc.deeppointdepthsquared));
    }

    public static final DCircle getIncircle(List<DPoint> polygonpoints) {
        ArrayList<double[]> a = new ArrayList<double[]>(polygonpoints.size());
        for (DPoint p : polygonpoints) {
            a.add(new double[]{p.x, p.y});
        }
        IncircleCalculator irc = new IncircleCalculator(a);
        return new DCircle(irc.deeppoint[0], irc.deeppoint[1], Math.sqrt(irc.deeppointdepthsquared));
    }

    private IncircleCalculator(List<double[]> polygonpoints) {
        this.polygonpoints = polygonpoints;
        this.initPolygonPath();
        this.initPolygonSegs();
        this.initScanBox();
        this.gleanDeepPoint();
        for (int i = 0; i < 5; ++i) {
            this.updateScanBox();
            this.gleanDeepPoint();
        }
    }

    private void initScanBox() {
        double xmin = Double.MAX_VALUE;
        double xmax = Double.MIN_VALUE;
        double ymin = xmin;
        double ymax = xmax;
        int s = this.polygonpoints.size();
        for (int i = 0; i < s; ++i) {
            double[] p = this.polygonpoints.get(i);
            if (p[0] < xmin) {
                xmin = p[0];
            }
            if (p[0] > xmax) {
                xmax = p[0];
            }
            if (p[1] < ymin) {
                ymin = p[1];
            }
            if (p[1] <= ymax) continue;
            ymax = p[1];
        }
        this.scanboxleft = xmin;
        this.scanboxtop = ymin;
        this.scanboxwidth = xmax - xmin;
        this.scanboxheight = ymax - ymin;
    }

    private void updateScanBox() {
        this.scanboxwidth /= 2.0;
        this.scanboxheight /= 2.0;
        this.scanboxleft = this.deeppoint[0] - this.scanboxwidth / 2.0;
        this.scanboxtop = this.deeppoint[1] - this.scanboxheight / 2.0;
    }

    private void gleanDeepPoint() {
        double[] testpoint = new double[2];
        for (int i = 0; i < 80; ++i) {
            double testdepth;
            testpoint[0] = this.random.nextDouble() * this.scanboxwidth + this.scanboxleft;
            testpoint[1] = this.random.nextDouble() * this.scanboxheight + this.scanboxtop;
            if (!this.polygonpath.contains(testpoint[0], testpoint[1]) || (testdepth = this.getPointDepth(testpoint)) <= this.deeppointdepthsquared) continue;
            this.deeppoint = testpoint;
            testpoint = new double[2];
            this.deeppointdepthsquared = testdepth;
        }
    }

    private double getPointDepth(double[] point) {
        double dmin = Double.MAX_VALUE;
        for (double[] seg : this.polygonsegs) {
            double dtest = GD.getDistanceSquared_PointSeg(point[0], point[1], seg[0], seg[1], seg[2], seg[3]);
            if (dtest >= dmin) continue;
            dmin = dtest;
        }
        return dmin;
    }

    private void initPolygonPath() {
        this.polygonpath = new Path2D.Double();
        double[] point = this.polygonpoints.get(0);
        this.polygonpath.moveTo(point[0], point[1]);
        int s = this.polygonpoints.size();
        for (int i = 1; i < s; ++i) {
            point = this.polygonpoints.get(i);
            this.polygonpath.lineTo(point[0], point[1]);
        }
        this.polygonpath.closePath();
    }

    private void initPolygonSegs() {
        this.polygonsegs = new ArrayList<double[]>();
        int s = this.polygonpoints.size();
        for (int i0 = 0; i0 < s; ++i0) {
            int i1 = i0 + 1;
            if (i1 == s) {
                i1 = 0;
            }
            double[] p0 = this.polygonpoints.get(i0);
            double[] p1 = this.polygonpoints.get(i1);
            this.polygonsegs.add(new double[]{p0[0], p0[1], p1[0], p1[1]});
        }
    }

    public static final void main(String[] a) {
        IncircleCalculator.test0();
    }

    private static final void test0() {
        System.out.println("################################");
        System.out.println("TESTING INCIRCLERADIUSCALCULATOR");
        System.out.println("################################");
        int testcount = 1000;
        System.out.println("TEST SQUARE");
        double[] truecenter = GD.getPoint_Mid2Points(TESTPOLYGON_SQUARE[0][0], TESTPOLYGON_SQUARE[0][1], TESTPOLYGON_SQUARE[2][0], TESTPOLYGON_SQUARE[2][1]);
        System.out.println("truecenter = " + truecenter[0] + " , " + truecenter[1]);
        double offsetsum = 0.0;
        for (int i = 0; i < testcount; ++i) {
            DCircle incircle = IncircleCalculator.getIncircle(TESTPOLYGON_SQUARE);
            double offsetfromtrue = GD.getDistance_PointPoint(incircle.x, incircle.y, truecenter[0], truecenter[1]);
            offsetsum += offsetfromtrue;
        }
        double offsetaverage = offsetsum / (double)testcount;
        double proportionaldeviation = offsetaverage / GD.getDistance_PointPoint(TESTPOLYGON_SQUARE[0][0], TESTPOLYGON_SQUARE[0][1], TESTPOLYGON_SQUARE[2][0], TESTPOLYGON_SQUARE[2][1]);
        System.out.println("PROPORTIONAL DEVIATION=" + proportionaldeviation);
        System.out.println("%ACCURACY=" + (1.0 - proportionaldeviation) * 100.0);
    }
}

