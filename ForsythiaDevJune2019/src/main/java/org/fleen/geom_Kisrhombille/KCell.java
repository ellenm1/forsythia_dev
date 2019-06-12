/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import org.fleen.geom_2D.GD;
import org.fleen.geom_Kisrhombille.KPoint;

public class KCell {
    public static final int SEG_FISH = 0;
    public static final int SEG_GOAT = 1;
    public static final int SEG_HAWK = 2;
    public int ant;
    public int bat;
    public int cat;
    public int dog;
    private static final int[][][] DVERTEX_OFFSETS;

    static {
        int[][][] arrarrn = new int[12][][];
        int[][] arrarrn2 = new int[2][];
        int[] arrn = new int[4];
        arrn[3] = 2;
        arrarrn2[0] = arrn;
        int[] arrn2 = new int[4];
        arrn2[3] = 3;
        arrarrn2[1] = arrn2;
        arrarrn[0] = arrarrn2;
        int[][] arrarrn3 = new int[2][];
        int[] arrn3 = new int[4];
        arrn3[3] = 4;
        arrarrn3[0] = arrn3;
        int[] arrn4 = new int[4];
        arrn4[3] = 3;
        arrarrn3[1] = arrn4;
        arrarrn[1] = arrarrn3;
        int[][] arrarrn4 = new int[2][];
        int[] arrn5 = new int[4];
        arrn5[3] = 4;
        arrarrn4[0] = arrn5;
        int[] arrn6 = new int[4];
        arrn6[3] = 5;
        arrarrn4[1] = arrn6;
        arrarrn[2] = arrarrn4;
        int[][] arrarrn5 = new int[2][];
        int[] arrn7 = new int[4];
        arrn7[0] = 1;
        arrn7[2] = -1;
        arrn7[3] = 2;
        arrarrn5[0] = arrn7;
        int[] arrn8 = new int[4];
        arrn8[3] = 5;
        arrarrn5[1] = arrn8;
        arrarrn[3] = arrarrn5;
        int[][] arrarrn6 = new int[2][];
        int[] arrn9 = new int[4];
        arrn9[0] = 1;
        arrn9[2] = -1;
        arrn9[3] = 2;
        arrarrn6[0] = arrn9;
        int[] arrn10 = new int[4];
        arrn10[0] = 1;
        arrn10[2] = -1;
        arrn10[3] = 1;
        arrarrn6[1] = arrn10;
        arrarrn[4] = arrarrn6;
        int[][] arrarrn7 = new int[2][];
        int[] arrn11 = new int[4];
        arrn11[1] = -1;
        arrn11[2] = -1;
        arrn11[3] = 4;
        arrarrn7[0] = arrn11;
        int[] arrn12 = new int[4];
        arrn12[0] = 1;
        arrn12[2] = -1;
        arrn12[3] = 1;
        arrarrn7[1] = arrn12;
        arrarrn[5] = arrarrn7;
        int[][] arrarrn8 = new int[2][];
        int[] arrn13 = new int[4];
        arrn13[1] = -1;
        arrn13[2] = -1;
        arrn13[3] = 4;
        arrarrn8[0] = arrn13;
        int[] arrn14 = new int[4];
        arrn14[1] = -1;
        arrn14[2] = -1;
        arrn14[3] = 3;
        arrarrn8[1] = arrn14;
        arrarrn[6] = arrarrn8;
        int[][] arrarrn9 = new int[2][];
        int[] arrn15 = new int[4];
        arrn15[1] = -1;
        arrn15[2] = -1;
        arrn15[3] = 2;
        arrarrn9[0] = arrn15;
        int[] arrn16 = new int[4];
        arrn16[1] = -1;
        arrn16[2] = -1;
        arrn16[3] = 3;
        arrarrn9[1] = arrn16;
        arrarrn[7] = arrarrn9;
        int[][] arrarrn10 = new int[2][];
        int[] arrn17 = new int[4];
        arrn17[1] = -1;
        arrn17[2] = -1;
        arrn17[3] = 2;
        arrarrn10[0] = arrn17;
        int[] arrn18 = new int[4];
        arrn18[0] = -1;
        arrn18[1] = -1;
        arrn18[3] = 5;
        arrarrn10[1] = arrn18;
        arrarrn[8] = arrarrn10;
        int[][] arrarrn11 = new int[2][];
        int[] arrn19 = new int[4];
        arrn19[0] = -1;
        arrn19[1] = -1;
        arrn19[3] = 4;
        arrarrn11[0] = arrn19;
        int[] arrn20 = new int[4];
        arrn20[0] = -1;
        arrn20[1] = -1;
        arrn20[3] = 5;
        arrarrn11[1] = arrn20;
        arrarrn[9] = arrarrn11;
        int[][] arrarrn12 = new int[2][];
        int[] arrn21 = new int[4];
        arrn21[0] = -1;
        arrn21[1] = -1;
        arrn21[3] = 4;
        arrarrn12[0] = arrn21;
        int[] arrn22 = new int[4];
        arrn22[3] = 1;
        arrarrn12[1] = arrn22;
        arrarrn[10] = arrarrn12;
        int[][] arrarrn13 = new int[2][];
        int[] arrn23 = new int[4];
        arrn23[3] = 2;
        arrarrn13[0] = arrn23;
        int[] arrn24 = new int[4];
        arrn24[3] = 1;
        arrarrn13[1] = arrn24;
        arrarrn[11] = arrarrn13;
        DVERTEX_OFFSETS = arrarrn;
    }

    public KCell(int ant, int bat, int cat, int dog) {
        this.ant = ant;
        this.bat = bat;
        this.cat = cat;
        this.dog = dog;
    }

    public KPoint[] getVertices() {
        KPoint p0 = new KPoint(this.ant, this.bat, this.cat, 0);
        KPoint p1 = new KPoint(this.ant + DVERTEX_OFFSETS[this.dog][0][0], this.bat + DVERTEX_OFFSETS[this.dog][0][1], this.cat + DVERTEX_OFFSETS[this.dog][0][2], DVERTEX_OFFSETS[this.dog][0][3]);
        KPoint p2 = new KPoint(this.ant + DVERTEX_OFFSETS[this.dog][1][0], this.bat + DVERTEX_OFFSETS[this.dog][1][1], this.cat + DVERTEX_OFFSETS[this.dog][1][2], DVERTEX_OFFSETS[this.dog][1][3]);
        return new KPoint[]{p0, p1, p2};
    }

    public KCell getAdjacent(int face) {
        if ((face %= 3) < 0) {
            face += 3;
        }
        switch (this.dog) {
            case 0: {
                switch (face) {
                    case 0: {
                        return new KCell(this.ant, this.bat + 1, this.cat + 1, 7);
                    }
                    case 1: {
                        return new KCell(this.ant, this.bat, this.cat, 1);
                    }
                    case 2: {
                        return new KCell(this.ant, this.bat, this.cat, 11);
                    }
                }
            }
            case 1: {
                switch (face) {
                    case 0: {
                        return new KCell(this.ant, this.bat + 1, this.cat + 1, 6);
                    }
                    case 1: {
                        return new KCell(this.ant, this.bat, this.cat, 0);
                    }
                    case 2: {
                        return new KCell(this.ant, this.bat, this.cat, 2);
                    }
                }
            }
            case 2: {
                switch (face) {
                    case 0: {
                        return new KCell(this.ant + 1, this.bat + 1, this.cat, 9);
                    }
                    case 1: {
                        return new KCell(this.ant, this.bat, this.cat, 3);
                    }
                    case 2: {
                        return new KCell(this.ant, this.bat, this.cat, 1);
                    }
                }
            }
            case 3: {
                switch (face) {
                    case 0: {
                        return new KCell(this.ant + 1, this.bat + 1, this.cat, 8);
                    }
                    case 1: {
                        return new KCell(this.ant, this.bat, this.cat, 2);
                    }
                    case 2: {
                        return new KCell(this.ant, this.bat, this.cat, 4);
                    }
                }
            }
            case 4: {
                switch (face) {
                    case 0: {
                        return new KCell(this.ant + 1, this.bat, this.cat - 1, 11);
                    }
                    case 1: {
                        return new KCell(this.ant, this.bat, this.cat, 5);
                    }
                    case 2: {
                        return new KCell(this.ant, this.bat, this.cat, 3);
                    }
                }
            }
            case 5: {
                switch (face) {
                    case 0: {
                        return new KCell(this.ant + 1, this.bat, this.cat - 1, 10);
                    }
                    case 1: {
                        return new KCell(this.ant, this.bat, this.cat, 4);
                    }
                    case 2: {
                        return new KCell(this.ant, this.bat, this.cat, 6);
                    }
                }
            }
            case 6: {
                switch (face) {
                    case 0: {
                        return new KCell(this.ant, this.bat - 1, this.cat - 1, 1);
                    }
                    case 1: {
                        return new KCell(this.ant, this.bat, this.cat, 7);
                    }
                    case 2: {
                        return new KCell(this.ant, this.bat, this.cat, 5);
                    }
                }
            }
            case 7: {
                switch (face) {
                    case 0: {
                        return new KCell(this.ant, this.bat - 1, this.cat - 1, 0);
                    }
                    case 1: {
                        return new KCell(this.ant, this.bat, this.cat, 6);
                    }
                    case 2: {
                        return new KCell(this.ant, this.bat, this.cat, 8);
                    }
                }
            }
            case 8: {
                switch (face) {
                    case 0: {
                        return new KCell(this.ant - 1, this.bat - 1, this.cat, 3);
                    }
                    case 1: {
                        return new KCell(this.ant, this.bat, this.cat, 9);
                    }
                    case 2: {
                        return new KCell(this.ant, this.bat, this.cat, 7);
                    }
                }
            }
            case 9: {
                switch (face) {
                    case 0: {
                        return new KCell(this.ant - 1, this.bat - 1, this.cat, 2);
                    }
                    case 1: {
                        return new KCell(this.ant, this.bat, this.cat, 8);
                    }
                    case 2: {
                        return new KCell(this.ant, this.bat, this.cat, 10);
                    }
                }
            }
            case 10: {
                switch (face) {
                    case 0: {
                        return new KCell(this.ant - 1, this.bat, this.cat + 1, 5);
                    }
                    case 1: {
                        return new KCell(this.ant, this.bat, this.cat, 11);
                    }
                    case 2: {
                        return new KCell(this.ant, this.bat, this.cat, 9);
                    }
                }
            }
            case 11: {
                switch (face) {
                    case 0: {
                        return new KCell(this.ant - 1, this.bat, this.cat + 1, 4);
                    }
                    case 1: {
                        return new KCell(this.ant, this.bat, this.cat, 10);
                    }
                    case 2: {
                        return new KCell(this.ant, this.bat, this.cat, 0);
                    }
                }
            }
        }
        throw new IllegalArgumentException("foo");
    }

    public KCell[] getAdjacents() {
        KCell[] a = new KCell[]{this.getAdjacent(0), this.getAdjacent(1), this.getAdjacent(2)};
        return a;
    }

    public Path2D.Double getPath2D() {
        KPoint[] v = this.getVertices();
        double[] p0 = v[0].getBasicPointCoor();
        double[] p1 = v[1].getBasicPointCoor();
        double[] p2 = v[2].getBasicPointCoor();
        Path2D.Double path = new Path2D.Double();
        path.moveTo(p0[0], p0[1]);
        path.lineTo(p1[0], p1[1]);
        path.lineTo(p2[0], p2[1]);
        path.closePath();
        return path;
    }

    public boolean contains(double x, double y) {
        KCell c = KCell.getCell(x, y);
        return c.equals(this);
    }

    public static final KCell getCell(double x, double y) {
        KCell cell;
        int mrx = (int)Math.floor(x / GD.SQRT3);
        int mry = (int)Math.floor(y / 3.0);
        int mrxc = mrx % 2;
        int mryc = mry % 2;
        if (mrxc == 0 && mryc == 0 || mrxc != 0 && mryc != 0) {
            int ant = (mrx - mry) / 2;
            int cat = mry;
            int bat = KPoint.getBat(ant, cat);
            KPoint dpr = new KPoint(ant, bat, cat, 0);
            cell = KCell.getCell_RectA(x, y, dpr);
        } else {
            int ant = (mrx - mry + 1) / 2;
            int cat = mry;
            int bat = KPoint.getBat(ant, cat);
            KPoint dpr = new KPoint(ant, bat, cat, 0);
            cell = KCell.getCell_RectB(x, y, dpr);
        }
        return cell;
    }

    private static final KCell getCell_RectA(double px, double py, KPoint dpr) {
        double[] pr = dpr.getBasicPointCoor();
        double p0x = pr[0];
        double p0y = pr[1] + 2.0;
        double p1x = pr[0];
        double p1y = pr[1] + 3.0;
        double p2x = pr[0] + GD.SQRT3;
        double p2y = pr[1] + 3.0;
        double p3x = pr[0] + GD.SQRT3 / 2.0;
        double p3y = pr[1] + 1.5;
        double p4x = pr[0] + GD.SQRT3;
        double p4y = pr[1] + 1.0;
        KCell cell = KCell.triangleContainsPoint(pr[0], pr[1], p1x, p1y, p2x, p2y, px, py) ? (KCell.triangleContainsPoint(pr[0], pr[1], p0x, p0y, p2x, p2y, px, py) ? (KCell.triangleContainsPoint(pr[0], pr[1], p0x, p0y, p3x, p3y, px, py) ? new KCell(dpr.coors[0], dpr.coors[1], dpr.coors[2], 0) : new KCell(dpr.coors[0], dpr.coors[1] + 1, dpr.coors[2] + 1, 7)) : new KCell(dpr.coors[0], dpr.coors[1] + 1, dpr.coors[2] + 1, 8)) : (KCell.triangleContainsPoint(pr[0], pr[1], p2x, p2y, p4x, p4y, px, py) ? (KCell.triangleContainsPoint(pr[0], pr[1], p3x, p3y, p4x, p4y, px, py) ? new KCell(dpr.coors[0], dpr.coors[1], dpr.coors[2], 1) : new KCell(dpr.coors[0], dpr.coors[1] + 1, dpr.coors[2] + 1, 6)) : new KCell(dpr.coors[0], dpr.coors[1], dpr.coors[2], 2));
        return cell;
    }

    private static final KCell getCell_RectB(double px, double py, KPoint dpr) {
        double[] pr = dpr.getBasicPointCoor();
        double p0x = pr[0];
        double p0y = pr[1] + 2.0;
        double p1x = pr[0];
        double p1y = pr[1] + 3.0;
        double p2x = pr[0] - GD.SQRT3;
        double p2y = pr[1] + 3.0;
        double p3x = pr[0] - GD.SQRT3 / 2.0;
        double p3y = pr[1] + 1.5;
        double p4x = pr[0] - GD.SQRT3;
        double p4y = pr[1] + 1.0;
        KCell cell = KCell.triangleContainsPoint(pr[0], pr[1], p1x, p1y, p2x, p2y, px, py) ? (KCell.triangleContainsPoint(pr[0], pr[1], p0x, p0y, p2x, p2y, px, py) ? (KCell.triangleContainsPoint(pr[0], pr[1], p0x, p0y, p3x, p3y, px, py) ? new KCell(dpr.coors[0], dpr.coors[1], dpr.coors[2], 11) : new KCell(dpr.coors[0] - 1, dpr.coors[1], dpr.coors[2] + 1, 4)) : new KCell(dpr.coors[0] - 1, dpr.coors[1], dpr.coors[2] + 1, 3)) : (KCell.triangleContainsPoint(pr[0], pr[1], p2x, p2y, p4x, p4y, px, py) ? (KCell.triangleContainsPoint(pr[0], pr[1], p3x, p3y, p4x, p4y, px, py) ? new KCell(dpr.coors[0], dpr.coors[1], dpr.coors[2], 10) : new KCell(dpr.coors[0] - 1, dpr.coors[1], dpr.coors[2] + 1, 5)) : new KCell(dpr.coors[0], dpr.coors[1], dpr.coors[2], 9));
        return cell;
    }

    public static final boolean triangleContainsPoint(double tp0x, double tp0y, double tp1x, double tp1y, double tp2x, double tp2y, double px, double py) {
        Point2D.Double v0 = new Point2D.Double(tp2x - tp0x, tp2y - tp0y);
        Point2D.Double v1 = new Point2D.Double(tp1x - tp0x, tp1y - tp0y);
        Point2D.Double v2 = new Point2D.Double(px - tp0x, py - tp0y);
        double dot00 = v0.x * v0.y + v0.y * v0.x;
        double dot01 = v0.x * v1.y + v0.y * v1.x;
        double dot02 = v0.x * v2.y + v0.y * v2.x;
        double dot11 = v1.x * v1.y + v1.y * v1.x;
        double dot12 = v1.x * v2.y + v1.y * v2.x;
        double invDenom = 1.0 / (dot00 * dot11 - dot01 * dot01);
        double u = (dot11 * dot02 - dot01 * dot12) * invDenom;
        double v = (dot00 * dot12 - dot01 * dot02) * invDenom;
        return u > 0.0 && v > 0.0 && u + v < 1.0;
    }

    public boolean equals(Object a) {
        if (!(a instanceof KCell)) {
            return false;
        }
        KCell b = (KCell)a;
        boolean e = b.ant == this.ant && b.bat == this.bat && b.cat == this.cat && b.dog == this.dog;
        return e;
    }

    public int hashCode() {
        return this.ant * 65536 + this.bat * 4096 + this.cat * 256 + this.dog * 16;
    }

    public String toString() {
        String s = "[" + this.ant + "," + this.bat + "," + this.cat + "," + this.dog + "]";
        return s;
    }
}

