/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_2D.GD;
import org.fleen.geom_Kisrhombille.GK;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;

public class KGrid
implements Serializable {
    private static final long serialVersionUID = -7272582675212521562L;
    private static final double[] DEFAULT_ROOT_ORIGIN_2D = new double[]{0.0, 0.0};
    private static final double DEFAULT_ROOT_FOREWARD = 0.0;
    private static final boolean DEFAULT_ROOT_TWIST = true;
    private static final double DEFAULT_ROOT_FISH = 1.0;
    private static final double UINT_1 = 1.0;
    private static final double UINT_2 = 2.0;
    private static final double UINT_SQRT3 = Math.sqrt(3.0);
    private static final double DIRECTION12UNIT = 0.5235987755982988;
    public double[] origin = null;
    public double north;
    public boolean twist;
    public double fish;

    public KGrid(double originx, double originy, double north, boolean twist, double fish) {
        this(new double[]{originx, originy}, north, twist, fish);
    }

    public KGrid(double[] origin, double north, boolean twist, double fish) {
        this.origin = origin;
        this.north = north;
        this.twist = twist;
        this.fish = fish;
    }

    public KGrid() {
        this(DEFAULT_ROOT_ORIGIN_2D, 0.0, true, 1.0);
    }

    public double[] getOrigin() {
        return this.origin;
    }

    public double getNorth() {
        return this.north;
    }

    public boolean getTwist() {
        return this.twist;
    }

    public double getFish() {
        return this.fish;
    }

    public double[] getPoint2D(KPoint v) {
        return this.getPoint2D(v.getAnt(), v.getBat(), v.getCat(), v.getDog());
    }

    public double[] getPoint2D(int ant, int bat, int cat, int dog) {
        double dir0;
        double dis0;
        double[] pv12 = new double[]{(double)(ant + bat) * UINT_SQRT3, (double)cat * 3.0};
        double pv12dir = GD.getDirection_PointPoint(0.0, 0.0, pv12[0], pv12[1]);
        double pv12dis = GD.getDistance_PointPoint(0.0, 0.0, pv12[0], pv12[1]);
        pv12dir = this.twist ? GD.normalizeDirection(this.north + pv12dir) : GD.normalizeDirection(this.north - pv12dir);
        pv12 = GD.getPoint_PointDirectionInterval(this.origin[0], this.origin[1], pv12dir, pv12dis *= this.fish);
        if (dog == 0) {
            dir0 = 0.0;
            dis0 = 0.0;
        } else if (dog == 1) {
            dir0 = -0.5235987755982988;
            dis0 = GK.EDGESLV_GOAT;
        } else if (dog == 2) {
            dir0 = 0.0;
            dis0 = 2.0;
        } else if (dog == 3) {
            dir0 = 0.5235987755982988;
            dis0 = GK.EDGESLV_GOAT;
        } else if (dog == 4) {
            dir0 = 1.0471975511965976;
            dis0 = 2.0;
        } else if (dog == 5) {
            dir0 = 1.5707963267948966;
            dis0 = GK.EDGESLV_GOAT;
        } else {
            throw new IllegalArgumentException("invalid dog : " + dog);
        }
        dir0 = this.twist ? GD.normalizeDirection(this.north + dir0) : GD.normalizeDirection(this.north - dir0);
        double[] pv = GD.getPoint_PointDirectionInterval(pv12[0], pv12[1], dir0, dis0 *= this.fish);
        return pv;
    }

    public double getDirection2D(int kdir) {
        double d = this.north;
        d = this.twist ? GD.normalizeDirection(d + (double)kdir * 0.5235987755982988) : GD.normalizeDirection(6.283185307179586 + d - (double)kdir * 0.5235987755982988);
        return d;
    }

    public DPolygon getPolygon2D(KPolygon kpolygon) {
        int s = kpolygon.size();
        DPolygon p = new DPolygon(s);
        for (int i = 0; i < s; ++i) {
            p.add(new DPoint(this.getPoint2D((KPoint)kpolygon.get(i))));
        }
        return p;
    }

    public KPoint getKVertex(double[] point) {
        double pointdir = GD.getDirection_PointPoint(this.origin[0], this.origin[1], point[0], point[1]);
        int pointsector = this.getKVertex_GetSector(pointdir);
        double[] xlineb = this.getPoint2D(1, 1, 0, 0);
        double[] ylineb = this.getPoint2D(-1, 1, 2, 0);
        double ky = GD.getDistance_PointLine(point[0], point[1], 0.0, 0.0, xlineb[0], xlineb[1]);
        double kx = GD.getDistance_PointLine(point[0], point[1], 0.0, 0.0, ylineb[0], ylineb[1]);
        int a = (int)(ky / (this.fish * 3.0));
        if (pointsector == 1 || pointsector == 2) {
            a *= -1;
        }
        int ant = (- a) / 2;
        int bat = a / 2;
        int cat = a;
        int dog = 0;
        if (a % 2 == 1) {
            dog = 5;
        }
        a = (int)(kx / (this.fish * GD.SQRT3));
        if (pointsector == 2 || pointsector == 3) {
            a *= -1;
        }
        if (dog == 0) {
            ant += a / 2;
            bat += a / 2;
        } else {
            ant += (a + 1) / 2;
            bat += (a + 1) / 2;
        }
        KPoint close = new KPoint(ant, bat, cat, dog);
        List<KPoint> closegroup = this.getKVertex_GetGroup(close);
        KPoint closest = this.getKVertex_GetClosestVertex(point, closegroup);
        return closest;
    }

    private KPoint getKVertex_GetClosestVertex(double[] point, List<KPoint> vertices) {
        double closestdist = Double.MAX_VALUE;
        KPoint closest = null;
        for (KPoint v : vertices) {
            double[] testpoint = this.getPoint2D(v);
            double testdist = GD.getDistance_PointPoint(point[0], point[1], testpoint[0], testpoint[1]);
            if (testdist >= closestdist) continue;
            closestdist = testdist;
            closest = v;
        }
        return closest;
    }

    private List<KPoint> getKVertex_GetGroup(KPoint c) {
        ArrayList<KPoint> a = new ArrayList<KPoint>();
        a.add(c);
        int[] b = c.coors;
        if (c.getDog() == 0) {
            a.addAll(KPoint.getV12LocalGroup(c));
        } else {
            a.add(new KPoint(b[0], b[1], b[2], 0));
            a.add(new KPoint(b[0], b[1], b[2], 3));
            a.add(new KPoint(b[0], b[1], b[2], 4));
            a.add(new KPoint(b[0] + 1, b[1] + 1, b[2], 1));
            a.add(new KPoint(b[0] + 1, b[1] + 1, b[2], 0));
            a.add(new KPoint(b[0] + 1, b[1], b[2] - 1, 3));
            a.add(new KPoint(b[0] + 1, b[1], b[2] - 1, 2));
            a.add(new KPoint(b[0] + 1, b[1], b[2] - 1, 1));
        }
        return a;
    }

    private int getKVertex_GetSector(double dir) {
        double r = this.twist ? (dir > this.north ? dir - this.north : 6.283185307179586 - (this.north - dir)) : (dir < this.north ? dir - this.north : 6.283185307179586 - (this.north - dir));
        if (r >= 0.0 && r < 1.5707963267948966) {
            return 0;
        }
        if (r >= 1.5707963267948966 && r < 3.141592653589793) {
            return 1;
        }
        if (r >= 3.141592653589793 && r < 4.71238898038469) {
            return 2;
        }
        return 3;
    }

    public String toString() {
        return "[" + this.hashCode() + "]";
    }

    public static final void main(String[] a) {
        KGrid.test0();
    }

    private static final void test0() {
        System.out.println("KGRID TEST0");
        KGrid g = new KGrid(0.0, 0.0, 1.0471975511965976, false, 1.0);
        double[] point = new double[]{4.0 * GD.SQRT3, 6.0};
        KPoint v = g.getKVertex(point);
        System.out.println("KGRID : " + g);
        System.out.println("POINT : (" + point[0] + "," + point[1] + ")");
        System.out.println("KVERTEX : " + v);
    }
}

