/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_2D;

import java.awt.geom.Line2D;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.fleen.geom_2D.DPoint;

public class GD {
    public static final double PI = 3.141592653589793;
    public static final double PI2 = 6.283185307179586;
    public static final double HALFPI = 1.5707963267948966;
    public static final double SQRT2 = GD.sqrt(2.0);
    public static final double SQRT3 = GD.sqrt(3.0);
    public static final double GOLDEN_RATIO = 1.618033988749894;
    private static final double BETWEENNESSERROR = 1.52587890625E-5;
    private static final double GETPOINT_2RAYSINTERSECTION_ANGLE_DIFFERENCE_ERROR = 0.7853981633974483;
    private static final double COLINEARITYERROR = 0.001;

    public static int abs(int a) {
        return a < 0 ? - a : a;
    }

    public static double abs(double a) {
        return a < 0.0 ? - a : a;
    }

    public static double sqrt(double d) {
        return StrictMath.sqrt(d);
    }

    public static List<Integer> getFactors(int i) {
        ArrayList<Integer> factors = new ArrayList<Integer>();
        for (int factor = 1; factor <= i; ++factor) {
            if (i % factor != 0) continue;
            factors.add(new Integer(factor));
        }
        return factors;
    }

    public static double sin(double a) {
        return StrictMath.sin(a);
    }

    public static double cos(double a) {
        return StrictMath.cos(a);
    }

    public static double tan(double a) {
        return StrictMath.tan(a);
    }

    public static double atan2(double x, double y) {
        return StrictMath.atan2(x, y);
    }

    public static double getDistance_PointPoint(double p0x, double p0y, double p1x, double p1y) {
        return GD.sqrt((p0x -= p1x) * p0x + (p0y -= p1y) * p0y);
    }

    public static double getDistance_PointLine(double px, double py, double lx0, double ly0, double lx1, double ly1) {
        double projlenSq;
        double dotprod;
        double lenSq;
        if ((lenSq = (px -= lx0) * px + (py -= ly0) * py - (projlenSq = (dotprod = px * (lx1 -= lx0) + py * (ly1 -= ly0)) * dotprod / (lx1 * lx1 + ly1 * ly1))) < 0.0) {
            lenSq = 0.0;
        }
        return GD.sqrt(lenSq);
    }

    public static double normalizeDirection(double a) {
        if ((a %= 6.283185307179586) < 0.0) {
            a += 6.283185307179586;
        }
        return a;
    }

    public static double getDirection_PointPoint(double p0x, double p0y, double p1x, double p1y) {
        double d = Math.atan2(p1x -= p0x, p1y -= p0y);
        if (d < 0.0) {
            d += 6.283185307179586;
        }
        return d;
    }

    public static final double getDirection_3Points(double p0x, double p0y, double p1x, double p1y, double p2x, double p2y) {
        double d0 = GD.getDirection_PointPoint(p1x, p1y, p2x, p2y);
        double d1 = GD.getDirection_PointPoint(p1x, p1y, p0x, p0y);
        return GD.getDirection_2Directions(d0, d1);
    }

    public static double getAngle_3Points(double p0x, double p0y, double p1x, double p1y, double p2x, double p2y) {
        double d0 = GD.getDirection_PointPoint(p1x, p1y, p2x, p2y);
        double d1 = GD.getDirection_PointPoint(p1x, p1y, p0x, p0y);
        return GD.getAngle_2Directions(d0, d1);
    }

    public static double getDirection_2Directions(double d0, double d1) {
        double d = 0.0;
        if (d0 <= d1) {
            d = (d1 + d0) / 2.0;
        } else {
            double a = 6.283185307179586 - d0 + d1;
            d = GD.normalizeDirection(d0 + a / 2.0);
        }
        return d;
    }

    public static double getDirection_DirectionOffset(double d, double offset) {
        d += offset;
        d = GD.normalizeDirection(d);
        return d;
    }

    public static final boolean isBetween(double px, double py, double p0x, double p0y, double p1x, double p1y) {
        double dpp1;
        double dp0p1 = GD.getDistance_PointPoint(p0x, p0y, p1x, p1y);
        double dpp0 = GD.getDistance_PointPoint(px, py, p0x, p0y);
        return dpp0 + (dpp1 = GD.getDistance_PointPoint(px, py, p1x, p1y)) < dp0p1 * 1.0000152587890625;
    }

    public static void TEST_getDirection_2Directions() {
        System.out.println("---TEST_getDirection_2Directions---");
        double d0 = 1.5707963267948966;
        double d1 = 3.141592653589793;
        double d2 = GD.getDirection_2Directions(d0, d1);
        System.out.println("TEST1:" + d2);
        d2 = GD.getDirection_2Directions(d1, d0);
        System.out.println("TEST2:" + d2);
        System.out.println("----------");
    }

    public static final double getAngle_2Directions(double d0, double d1) {
        double span = d0 == d1 ? 0.0 : (d0 < d1 ? d1 - d0 : 6.283185307179586 - d0 + d1);
        return span;
    }

    public static final double getDirectionalDeviation_3Points(double p0x, double p0y, double p1x, double p1y, double p2x, double p2y) {
        double d0 = GD.getDirection_PointPoint(p0x, p0y, p1x, p1y);
        double d1 = GD.getDirection_PointPoint(p1x, p1y, p2x, p2y);
        double d = GD.getDeviation_2Directions(d0, d1);
        return d;
    }

    public static final double getDeviation_2Directions(double d0, double d1) {
        d0 = GD.normalizeDirection(d0);
        d1 = GD.normalizeDirection(d1);
        double s = GD.getAngle_2Directions(d0, d1);
        if (d0 == d1) {
            double d = 0.0;
        }
        double dev = s < 3.141592653589793 ? s : - 6.283185307179586 - s;
        return dev;
    }

    public static final double getAbsDeviation_2Directions(double d0, double d1) {
        return GD.abs(GD.getDeviation_2Directions(d0, d1));
    }

    public static final double[] getPoint_PointDirectionInterval(double x, double y, double d, double i) {
        if (i == 0.0) {
            return new double[]{x, y};
        }
        double oY = i * Math.cos(d);
        double oX = i * Math.sin(d);
        double[] p = new double[]{x + oX, y + oY};
        return p;
    }

    public static final double[] getPoint_PointPointInterval(double p0x, double p0y, double p1x, double p1y, double i) {
        double d = GD.getDirection_PointPoint(p0x, p0y, p1x, p1y);
        double oY = i * Math.cos(d);
        double oX = i * Math.sin(d);
        double[] p = new double[]{p0x + oX, p0y + oY};
        return p;
    }

    public static double[] getPoint_Mid2Points(double p0x, double p0y, double p1x, double p1y) {
        if (p0x == p1x && p0y == p1y) {
            return new double[]{p0x, p0y};
        }
        p0x = (p0x + p1x) / 2.0;
        p0y = (p0y + p1y) / 2.0;
        return new double[]{p0x, p0y};
    }

    public static final double getSlope(double x0, double y0, double x1, double y1) {
        double dy = y1 - y0;
        double dx = x1 - x0;
        return dy / dx;
    }

    public static final double[] getPoint_ClosestOnLineToPoint(double lx0, double ly0, double lx1, double ly1, double px, double py) {
        double m = GD.getSlope(lx0, ly0, lx1, ly1);
        double x = (m * m * lx0 - m * (ly0 - py) + px) / (m * m + 1.0);
        double y = (m * m * py - m * (lx0 - px) + ly0) / (m * m + 1.0);
        return new double[]{x, y};
    }

    public static final double[] getPoint_ClosestOnSegToPoint(double sx0, double sy0, double sx1, double sy1, double px, double py) {
        double xDelta = sx1 - sx0;
        double yDelta = sy1 - sy0;
        if (xDelta == 0.0 && yDelta == 0.0) {
            throw new IllegalArgumentException("Segment start equals segment end : [" + sx0 + "," + sy0 + "]-[" + sx1 + "," + sy1 + "]");
        }
        double u = ((px - sx0) * xDelta + (py - sy0) * yDelta) / (xDelta * xDelta + yDelta * yDelta);
        double[] closestPoint = u < 0.0 ? new double[]{sx0, sy0} : (u > 1.0 ? new double[]{sx1, sy1} : new double[]{sx0 + u * xDelta, sy0 + u * yDelta});
        return closestPoint;
    }

    public static final double getPoint_ClosestOnSegToPoint_AsOffset(double sx0, double sy0, double sx1, double sy1, double px, double py) {
        double[] a = GD.getPoint_ClosestOnSegToPoint(sx0, sy0, sx1, sy1, px, py);
        double offset = GD.getDistance_PointPoint(sx0, sy0, a[0], a[1]);
        return offset;
    }

    public static final double[] getPoint_Between2Points(double x0, double y0, double x1, double y1, double bias) {
        if (bias < 0.0 || bias > 1.0) {
            throw new IllegalArgumentException("INVALID BIAS VALUE! bias=" + bias + ". Valid range is [0,1]");
        }
        double x = x0 * (1.0 - bias) + x1 * bias;
        double y = y0 * (1.0 - bias) + y1 * bias;
        return new double[]{x, y};
    }

    public static final boolean testIntersection_2Segs(double s0x0, double s0y0, double s0x1, double s0y1, double s1x0, double s1y0, double s1x1, double s1y1) {
        return Line2D.linesIntersect(s0x0, s0y0, s0x1, s0y1, s1x0, s1y0, s1x1, s1y1);
    }

    public static final boolean testIntersection_2Rectangles(double r0x, double r0y, double r0width, double r0height, double r1x, double r1y, double r1width, double r1height) {
        return r0x < r1x + r1width && r0x + r0width > r1x && r0y < r1y + r1height && r0y + r0height > r1y;
    }

    public static final double[] getPoint_2LinesIntersection(double l0x0, double l0y0, double l0x1, double l0y1, double l1x0, double l1y0, double l1x1, double l1y1) {
        double d = (l0x0 - l0x1) * (l1y0 - l1y1) - (l0y0 - l0y1) * (l1x0 - l1x1);
        if (d == 0.0) {
            return null;
        }
        double xi = ((l1x0 - l1x1) * (l0x0 * l0y1 - l0y0 * l0x1) - (l0x0 - l0x1) * (l1x0 * l1y1 - l1y0 * l1x1)) / d;
        double yi = ((l1y0 - l1y1) * (l0x0 * l0y1 - l0y0 * l0x1) - (l0y0 - l0y1) * (l1x0 * l1y1 - l1y0 * l1x1)) / d;
        return new double[]{xi, yi};
    }

    public static final double[] getPoint_2RaysIntersection(double r0x, double r0y, double r0d, double r1x, double r1y, double r1d) {
        double[] p1;
        double[] p0 = GD.getPoint_PointDirectionInterval(r0x, r0y, r0d, 1.0);
        double[] i = GD.getPoint_2LinesIntersection(r0x, r0y, p0[0], p0[1], r1x, r1y, (p1 = GD.getPoint_PointDirectionInterval(r1x, r1y, r1d, 1.0))[0], p1[1]);
        if (i == null) {
            return null;
        }
        double dir = GD.getDirection_PointPoint(r0x, r0y, i[0], i[1]);
        double dif = GD.getAbsDeviation_2Directions(dir, r0d);
        if (dif > 0.7853981633974483) {
            return null;
        }
        dir = GD.getDirection_PointPoint(r1x, r1y, i[0], i[1]);
        dif = GD.getAbsDeviation_2Directions(dir, r1d);
        if (dif > 0.7853981633974483) {
            return null;
        }
        return i;
    }

    public static final int getPoint_Closest(DPoint p, List<DPoint> points) {
        double dclosest = Double.MAX_VALUE;
        int iclosest = 0;
        for (int i = 0; i < points.size(); ++i) {
            DPoint ptest = points.get(i);
            double dtest = p.getDistance(ptest);
            if (dtest >= dclosest) continue;
            dclosest = dtest;
            iclosest = i;
        }
        return iclosest;
    }

    public static final double[] getIntersection_RaySeg(double rx, double ry, double rd, double sp0x, double sp0y, double sp1x, double sp1y) {
        double[] rp1 = GD.getPoint_PointDirectionInterval(rx, ry, rd, 1.0);
        return GD.getIntersection_RaySeg(rx, ry, rp1[0], rp1[1], sp0x, sp0y, sp1x, sp1y);
    }

    public static final double[] getIntersection_RaySeg(double rp0x, double rp0y, double rp1x, double rp1y, double sp0x, double sp0y, double sp1x, double sp1y) {
        double d0;
        double[] i = GD.getPoint_2LinesIntersection(rp0x, rp0y, rp1x, rp1y, sp0x, sp0y, sp1x, sp1y);
        if (i == null) {
            return null;
        }
        double dray = GD.getDirection_PointPoint(rp0x, rp0y, rp1x, rp1y);
        if (GD.getDeviation_2Directions(dray, d0 = GD.getDirection_PointPoint(rp0x, rp0y, i[0], i[1])) > 1.5707963267948966) {
            return null;
        }
        if (GD.isBetween(i[0], i[1], sp0x, sp0y, sp1x, sp1y)) {
            // empty if block
        }
        return i;
    }

    public static final double[] getIntersection_SegSeg(double s0p0x, double s0p0y, double s0p1x, double s0p1y, double s1p0x, double s1p0y, double s1p1x, double s1p1y) {
        double denom = (s1p1y - s1p0y) * (s0p1x - s0p0x) - (s1p1x - s1p0x) * (s0p1y - s0p0y);
        if (denom == 0.0) {
            return null;
        }
        double ua = ((s1p1x - s1p0x) * (s0p0y - s1p0y) - (s1p1y - s1p0y) * (s0p0x - s1p0x)) / denom;
        double ub = ((s0p1x - s0p0x) * (s0p0y - s1p0y) - (s0p1y - s0p0y) * (s0p0x - s1p0x)) / denom;
        if (ua >= 0.0 && ua <= 1.0 && ub >= 0.0 && ub <= 1.0) {
            return new double[]{s0p0x + ua * (s0p1x - s0p0x), s0p0y + ua * (s0p1y - s0p0y)};
        }
        return null;
    }

    public static double[] getIntersection_2Circles(double c0x, double c0y, double c0r, double c1x, double c1y, double c1r) {
        double dy = c1y - c0y;
        double dx = c1x - c0x;
        double d = GD.sqrt(dy * dy + dx * dx);
        if (d > c0r + c1r) {
            return null;
        }
        if (d < GD.abs(c0r - c1r)) {
            return null;
        }
        double a = (c0r * c0r - c1r * c1r + d * d) / (2.0 * d);
        double p2x = c0x + dx * a / d;
        double p2y = c0y + dy * a / d;
        double h = GD.sqrt(c0r * c0r - a * a);
        double rx = (- dy) * (h / d);
        double ry = dx * (h / d);
        double i0x = p2x + rx;
        double i0y = p2y + ry;
        double i1x = p2x - rx;
        double i1y = p2y - ry;
        return new double[]{i0x, i0y, i1x, i1y};
    }

    public static final double[] getCentroid2D(double[][] vp) {
        double cx = 0.0;
        double cy = 0.0;
        for (int i = 0; i < vp.length; ++i) {
            int inext = i + 1;
            if (inext == vp.length) {
                inext = 0;
            }
            cx += (vp[i][0] + vp[inext][0]) * (vp[i][1] * vp[inext][0] - vp[i][0] * vp[inext][1]);
            cy += (vp[i][1] + vp[inext][1]) * (vp[i][1] * vp[inext][0] - vp[i][0] * vp[inext][1]);
        }
        double area = GD.getAbsArea2D(vp);
        double[] centroid2d = new double[]{cx /= 6.0 * area, cy /= 6.0 * area};
        return centroid2d;
    }

    public static final DPoint getPoint_Mean(List<? extends DPoint> points) {
        double x = 0.0;
        double y = 0.0;
        for (DPoint p : points) {
            x += p.x;
            y += p.y;
        }
        int s = points.size();
        return new DPoint(x /= (double)s, y /= (double)s);
    }

    public static final double getPerimeter(double[][] points) {
        int s = points.length;
        double perimeter = 0.0;
        for (int i0 = 0; i0 < s; ++i0) {
            int i1 = i0 + 1;
            if (i1 == s) {
                i1 = 0;
            }
            perimeter += GD.getDistance_PointPoint(points[i0][0], points[i0][1], points[i1][0], points[i1][1]);
        }
        return perimeter;
    }

    public static final double getAbsArea2D(double[][] p) {
        return Math.abs(GD.getSignedArea2D(p));
    }

    public static boolean isClockwise(List<double[]> p) {
        double a = GD.getSignedArea2D(p);
        return a < 0.0;
    }

    public static boolean isClockwise(double[][] p) {
        double a = GD.getSignedArea2D(p);
        return a < 0.0;
    }

    public static final void makeClockwise(List<double[]> p) {
        if (!GD.isClockwise(p)) {
            Collections.reverse(p);
        }
    }

    public static final boolean isClockwiseD(List<? extends DPoint> points) {
        double sum = 0.0;
        int s = points.size();
        for (int i = 0; i < s; ++i) {
            int inext = i + 1;
            if (inext == s) {
                inext = 0;
            }
            DPoint pi = points.get(i);
            DPoint pinext = points.get(inext);
            sum = sum + pi.x * pinext.y - pi.y * pinext.x;
        }
        double signedarea2d = 0.5 * sum;
        return signedarea2d < 0.0;
    }

    public static final double getSignedArea2D(List<double[]> vp) {
        double[][] a = (double[][])vp.toArray((T[])new double[vp.size()][]);
        return GD.getSignedArea2D(a);
    }

    public static final double getSignedArea2D(double[][] vp) {
        double sum = 0.0;
        for (int i = 0; i < vp.length; ++i) {
            int inext = i + 1;
            if (inext == vp.length) {
                inext = 0;
            }
            sum = sum + vp[i][0] * vp[inext][1] - vp[i][1] * vp[inext][0];
        }
        double signedarea2d = 0.5 * sum;
        return signedarea2d;
    }

    public static final double getArea_Triangle(double[] p0, double[] p1, double[] p2) {
        double a = Math.abs((p0[0] * (p1[1] - p2[1]) + p1[0] * (p2[1] - p0[1]) + p2[0] * (p0[1] - p1[1])) / 2.0);
        return a;
    }

    public static final double getArea_Triangle(double p0x, double p0y, double p1x, double p1y, double p2x, double p2y) {
        double a = Math.abs((p0x * (p1y - p2y) + p1x * (p2y - p0y) + p2x * (p0y - p1y)) / 2.0);
        return a;
    }

    public static final double[] getIntersection_SegHLine(double p0x, double p0y, double p1x, double p1y, double hy) {
        if (p0x == p1x) {
            return new double[]{p0x, hy};
        }
        double dx = p1x - p0x;
        double dy = p1y - p0y;
        double slope = dy / dx;
        double x = p0x - (p0y - hy) / slope;
        return new double[]{x, hy};
    }

    public static final double[] getIntersection_SegVLine(double p0x, double p0y, double p1x, double p1y, double hx) {
        if (p0y == p1y) {
            return new double[]{hx, p0y};
        }
        double dx = p1x - p0x;
        double dy = p1y - p0y;
        double slope = dy / dx;
        double y = p0y - (p0x - hx) * slope;
        return new double[]{hx, y};
    }

    public static final double getDistance_PointSeg(double point_x, double point_y, double sp0x, double sp0y, double sp1x, double sp1y) {
        double px = sp1x - sp0x;
        double py = sp1y - sp0y;
        double something = px * px + py * py;
        double u = ((point_x - sp0x) * px + (point_y - sp0y) * py) / something;
        if (u > 1.0) {
            u = 1.0;
        } else if (u < 0.0) {
            u = 0.0;
        }
        double x = sp0x + u * px;
        double y = sp0y + u * py;
        double dx = x - point_x;
        double dy = y - point_y;
        double dist = Math.sqrt(dx * dx + dy * dy);
        return dist;
    }

    public static final double getDistance_PointCircle(double px, double py, double cx, double cy, double cr) {
        double d = GD.getDistance_PointPoint(px, py, cx, cy) - cr;
        return d;
    }

    public static final double getDistanceSquared_PointSeg(double point_x, double point_y, double sp0x, double sp0y, double sp1x, double sp1y) {
        double px = sp1x - sp0x;
        double py = sp1y - sp0y;
        double something = px * px + py * py;
        double u = ((point_x - sp0x) * px + (point_y - sp0y) * py) / something;
        if (u > 1.0) {
            u = 1.0;
        } else if (u < 0.0) {
            u = 0.0;
        }
        double x = sp0x + u * px;
        double y = sp0y + u * py;
        double dx = x - point_x;
        double dy = y - point_y;
        double dist = dx * dx + dy * dy;
        return dist;
    }

    public static final double getDistanceSq_PointPolygon(double px, double py, double[][] polygon) {
        double closestsegdistsq = Double.MAX_VALUE;
        int s = polygon.length;
        for (int p0 = 0; p0 < s; ++p0) {
            double testsegdistsq;
            int p1 = p0 + 1;
            if (p1 == s) {
                p1 = 0;
            }
            if ((testsegdistsq = GD.getDistanceSquared_PointSeg(px, py, polygon[p0][0], polygon[p0][1], polygon[p1][0], polygon[p1][1])) >= closestsegdistsq) continue;
            closestsegdistsq = testsegdistsq;
        }
        return closestsegdistsq;
    }

    public static final double getDistance_PointPolygon(double px, double py, double[][] polygon) {
        double closestsegdistsq = Double.MAX_VALUE;
        int s = polygon.length;
        for (int p0 = 0; p0 < s; ++p0) {
            double testsegdistsq;
            int p1 = p0 + 1;
            if (p1 == s) {
                p1 = 0;
            }
            if ((testsegdistsq = GD.getDistance_PointSeg(px, py, polygon[p0][0], polygon[p0][1], polygon[p1][0], polygon[p1][1])) >= closestsegdistsq) continue;
            closestsegdistsq = testsegdistsq;
        }
        return closestsegdistsq;
    }

    public static final double getDistance_PointPolygon(double px, double py, List<DPoint> polygon) {
        double closestsegdist = Double.MAX_VALUE;
        int s = polygon.size();
        for (int i0 = 0; i0 < s; ++i0) {
            int i1 = i0 + 1;
            if (i1 == s) {
                i1 = 0;
            }
            DPoint p0 = polygon.get(i0);
            DPoint p1 = polygon.get(i1);
            double testsegdist = GD.getDistance_PointSeg(px, py, p0.x, p0.y, p1.x, p1.y);
            if (testsegdist >= closestsegdist) continue;
            closestsegdist = testsegdist;
        }
        return closestsegdist;
    }

    public static final boolean getSide_PointPolygon(double x, double y, double[][] polygon) {
        int j = polygon.length - 1;
        boolean c = false;
        int i = 0;
        j = polygon.length - 1;
        while (i < polygon.length) {
            if (polygon[i][1] > y != polygon[j][1] > y && x < (polygon[j][0] - polygon[i][0]) * (y - polygon[i][1]) / (polygon[j][1] - polygon[i][1]) + polygon[i][0]) {
                c = !c;
            }
            j = i++;
        }
        return c;
    }

    public static final boolean getSide_PointPolygon(double x, double y, List<? extends DPoint> polygon) {
        int j = polygon.size() - 1;
        boolean c = false;
        int i = 0;
        j = polygon.size() - 1;
        while (i < polygon.size()) {
            if (polygon.get((int)i).y > y != polygon.get((int)j).y > y && x < (polygon.get((int)j).x - polygon.get((int)i).x) * (y - polygon.get((int)i).y) / (polygon.get((int)j).y - polygon.get((int)i).y) + polygon.get((int)i).x) {
                c = !c;
            }
            j = i++;
        }
        return c;
    }

    public static final double getHeight_Triangle(double b0x, double b0y, double b1x, double b1y, double ax, double ay) {
        double a = GD.getArea_Triangle(b0x, b0y, b1x, b1y, ax, ay);
        double b = GD.getDistance_PointPoint(b0x, b0y, b1x, b1y);
        double h = 2.0 * a / b;
        return h;
    }

    public static final boolean getColinearity_3Points(double p0x, double p0y, double p1x, double p1y, double p2x, double p2y) {
        double dp0p1 = GD.getDistance_PointPoint(p0x, p0y, p1x, p1y);
        double dp1p2 = GD.getDistance_PointPoint(p1x, p1y, p2x, p2y);
        double dp0p2 = GD.getDistance_PointPoint(p0x, p0y, p2x, p2y);
        double error = Math.abs(dp0p2 - (dp0p1 + dp1p2));
        boolean colinear = error < 0.001 * dp0p2;
        return colinear;
    }

    public static final double getPointLocation_RelativeToLine(double px, double py, double lp0x, double lp0y, double lp1x, double lp1y) {
        return (lp1x - lp0x) * (py - lp0y) - (lp1y - lp0y) * (px - lp0x);
    }

    public static final boolean testPoint_IsLeftOfLine(double px, double py, double lp0x, double lp0y, double lp1x, double lp1y) {
        return GD.getPointLocation_RelativeToLine(px, py, lp0x, lp0y, lp1x, lp1y) > 0.0;
    }

    public static final boolean testPoint_IsRightOfLine(double px, double py, double lp0x, double lp0y, double lp1x, double lp1y) {
        return GD.getPointLocation_RelativeToLine(px, py, lp0x, lp0y, lp1x, lp1y) < 0.0;
    }

    public static final boolean testPoint_IsOnLine(double px, double py, double lp0x, double lp0y, double lp1x, double lp1y) {
        return GD.getPointLocation_RelativeToLine(px, py, lp0x, lp0y, lp1x, lp1y) == 0.0;
    }

    public static void main(String[] a) {
        System.out.println(GD.getPointLocation_RelativeToLine(1.0, 3.0, 0.0, 0.0, 0.0, 5.0));
    }
}

