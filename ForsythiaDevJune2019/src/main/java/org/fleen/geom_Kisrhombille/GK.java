/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.fleen.geom_2D.GD;
import org.fleen.geom_Kisrhombille.KAnchor;
import org.fleen.geom_Kisrhombille.KMetagon;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.KVector;

public class GK {
    public static final double SQRT3 = Math.sqrt(3.0);
    public static final int AXISCOUNT = 6;
    public static final boolean TWIST_POSITIVE = true;
    public static final boolean TWIST_NEGATIVE = false;
    public static final int VERTEX_NULL = -1;
    public static final int VERTEX_12 = 0;
    public static final int VERTEX_4A = 1;
    public static final int VERTEX_6A = 2;
    public static final int VERTEX_4B = 3;
    public static final int VERTEX_6B = 4;
    public static final int VERTEX_4C = 5;
    public static final int VERTEX_GTYPE_4 = 0;
    public static final int VERTEX_GTYPE_6 = 1;
    public static final int VERTEX_GTYPE_12 = 2;
    public static final int EDGE_NULL = -1;
    public static final int EDGE_FISH = 0;
    public static final int EDGE_GOAT = 1;
    public static final int EDGE_HAWK = 2;
    public static final double EDGESLV_FISH = 1.0;
    public static final double EDGESLV_GOAT = Math.sqrt(3.0);
    public static final double EDGESLV_HAWK = 2.0;
    public static final int DIRECTION_NULL = -1;
    public static final int DIRECTION_0 = 0;
    public static final int DIRECTION_1 = 1;
    public static final int DIRECTION_2 = 2;
    public static final int DIRECTION_3 = 3;
    public static final int DIRECTION_4 = 4;
    public static final int DIRECTION_5 = 5;
    public static final int DIRECTION_6 = 6;
    public static final int DIRECTION_7 = 7;
    public static final int DIRECTION_8 = 8;
    public static final int DIRECTION_9 = 9;
    public static final int DIRECTION_10 = 10;
    public static final int DIRECTION_11 = 11;
    public static final double[] DIRECTION_2D = new double[]{0.0, 0.5235987755982988, 1.0471975511965976, 1.5707963267948966, 2.0943951023931953, 2.6179938779914944, 3.141592653589793, 3.6651914291880923, 4.1887902047863905, 4.71238898038469, 5.235987755982989, 5.759586531581287};
    public static final boolean DIRECTION_AXIS_HAWK = true;
    public static final boolean DIRECTION_AXIS_GOAT = false;
    public static final int[][] VERTEX_LIBERTIES;
    private static final double GETDIRVV_ERROR = 2.42629036971607E-6;
    private static final double DIRECTION_2D_0_ALTERNATE = 6.283185307179586;
    private static final double[][] GETDIRVV_RANGES;
    private static final double GETVERTEXVV_TRAVERSALERRORCEILING = 1.52587890625E-5;
    private static final double GETVERTEXVV_TRAVERSALERRORFLOOR = -1.52587890625E-5;
    private static final int TEST_CYCLES_COUNT = 10000;
    private static final double UINT_1 = 1.0;
    private static final double UINT_2 = 2.0;
    private static final double UINT_SQRT3;
    private static final double P2D_G;
    private static final double P2D_H = 1.5;
    private static final int[] DOGPATTERN0;
    private static final int[] DOGPATTERN1;
    private static final int[] DOGPATTERN2;
    private static final int[] DOGPATTERN3;
    private static final int[] DOGPATTERN4;
    private static final int[] DOGPATTERN5;
    private static final int[] DOGPATTERN6;
    private static final int[] DOGPATTERN7;
    private static final int[] DOGPATTERN8;
    private static final int[] DOGPATTERN9;
    private static final int[] DOGPATTERN10;
    private static final int[] DOGPATTERN11;
    private static final double GRIDCELLBOXWIDTH;
    private static final double GRIDCELLBOXHEIGHT = 3.0;

    static {
        int[][] arrarrn = new int[6][];
        int[] arrn = new int[12];
        arrn[1] = 1;
        arrn[2] = 2;
        arrn[3] = 3;
        arrn[4] = 4;
        arrn[5] = 5;
        arrn[6] = 6;
        arrn[7] = 7;
        arrn[8] = 8;
        arrn[9] = 9;
        arrn[10] = 10;
        arrn[11] = 11;
        arrarrn[0] = arrn;
        arrarrn[1] = new int[]{2, 5, 8, 11};
        int[] arrn2 = new int[6];
        arrn2[1] = 2;
        arrn2[2] = 4;
        arrn2[3] = 6;
        arrn2[4] = 8;
        arrn2[5] = 10;
        arrarrn[2] = arrn2;
        arrarrn[3] = new int[]{1, 4, 7, 10};
        int[] arrn3 = new int[6];
        arrn3[1] = 2;
        arrn3[2] = 4;
        arrn3[3] = 6;
        arrn3[4] = 8;
        arrn3[5] = 10;
        arrarrn[4] = arrn3;
        int[] arrn4 = new int[4];
        arrn4[1] = 3;
        arrn4[2] = 6;
        arrn4[3] = 9;
        arrarrn[5] = arrn4;
        VERTEX_LIBERTIES = arrarrn;
        GETDIRVV_RANGES = new double[][]{{6.283182880889217, 2.42629036971607E-6}, {DIRECTION_2D[1] - 2.42629036971607E-6, DIRECTION_2D[1] + 2.42629036971607E-6}, {DIRECTION_2D[2] - 2.42629036971607E-6, DIRECTION_2D[2] + 2.42629036971607E-6}, {DIRECTION_2D[3] - 2.42629036971607E-6, DIRECTION_2D[3] + 2.42629036971607E-6}, {DIRECTION_2D[4] - 2.42629036971607E-6, DIRECTION_2D[4] + 2.42629036971607E-6}, {DIRECTION_2D[5] - 2.42629036971607E-6, DIRECTION_2D[5] + 2.42629036971607E-6}, {DIRECTION_2D[6] - 2.42629036971607E-6, DIRECTION_2D[6] + 2.42629036971607E-6}, {DIRECTION_2D[7] - 2.42629036971607E-6, DIRECTION_2D[7] + 2.42629036971607E-6}, {DIRECTION_2D[8] - 2.42629036971607E-6, DIRECTION_2D[8] + 2.42629036971607E-6}, {DIRECTION_2D[9] - 2.42629036971607E-6, DIRECTION_2D[9] + 2.42629036971607E-6}, {DIRECTION_2D[10] - 2.42629036971607E-6, DIRECTION_2D[10] + 2.42629036971607E-6}, {DIRECTION_2D[11] - 2.42629036971607E-6, DIRECTION_2D[11] + 2.42629036971607E-6}};
        UINT_SQRT3 = Math.sqrt(3.0);
        P2D_G = UINT_SQRT3 / 2.0;
        int[] arrn5 = new int[4];
        arrn5[1] = 2;
        arrn5[2] = 5;
        arrn5[3] = 4;
        DOGPATTERN0 = arrn5;
        int[] arrn6 = new int[4];
        arrn6[1] = 3;
        arrn6[3] = 3;
        DOGPATTERN1 = arrn6;
        int[] arrn7 = new int[4];
        arrn7[1] = 4;
        arrn7[2] = 1;
        arrn7[3] = 2;
        DOGPATTERN2 = arrn7;
        int[] arrn8 = new int[4];
        arrn8[1] = 5;
        arrn8[3] = 5;
        DOGPATTERN3 = arrn8;
        int[] arrn9 = new int[4];
        arrn9[1] = 2;
        arrn9[2] = 3;
        arrn9[3] = 4;
        DOGPATTERN4 = arrn9;
        int[] arrn10 = new int[4];
        arrn10[1] = 1;
        arrn10[3] = 1;
        DOGPATTERN5 = arrn10;
        int[] arrn11 = new int[4];
        arrn11[1] = 4;
        arrn11[2] = 5;
        arrn11[3] = 2;
        DOGPATTERN6 = arrn11;
        int[] arrn12 = new int[4];
        arrn12[1] = 3;
        arrn12[3] = 3;
        DOGPATTERN7 = arrn12;
        int[] arrn13 = new int[4];
        arrn13[1] = 2;
        arrn13[2] = 1;
        arrn13[3] = 4;
        DOGPATTERN8 = arrn13;
        int[] arrn14 = new int[4];
        arrn14[1] = 5;
        arrn14[3] = 5;
        DOGPATTERN9 = arrn14;
        int[] arrn15 = new int[4];
        arrn15[1] = 4;
        arrn15[2] = 3;
        arrn15[3] = 2;
        DOGPATTERN10 = arrn15;
        int[] arrn16 = new int[4];
        arrn16[1] = 1;
        arrn16[3] = 1;
        DOGPATTERN11 = arrn16;
        GRIDCELLBOXWIDTH = Math.sqrt(3.0);
    }

    public static final double getDirection2D(int d) {
        if (d < 0 || d > 11) {
            return -1.0;
        }
        return DIRECTION_2D[d];
    }

    public static final boolean getAxisType(int d) {
        return d % 2 == 0;
    }

    public static final boolean directionAxisIsHawky(int d) {
        return d % 2 == 0;
    }

    public static final boolean directionAxisIsGoaty(int d) {
        return d % 2 == 1;
    }

    public static final int normalizeDirection(int d) {
        if ((d %= 12) < 0) {
            d += 12;
        }
        return d;
    }

    public static final int[] getLiberties(int vdog) {
        return VERTEX_LIBERTIES[vdog];
    }

    public static final boolean hasLiberty(int vdog, int dir) {
        for (int i = 0; i < VERTEX_LIBERTIES[vdog].length; ++i) {
            if (VERTEX_LIBERTIES[vdog][i] != dir) continue;
            return true;
        }
        return false;
    }

    public static final KPoint getVertex_Adjacent(KPoint v, int dir) {
        int[] v1 = new int[4];
        GK.getVertex_Adjacent(v.coors[0], v.coors[1], v.coors[2], v.coors[3], dir, v1);
        if (v1[3] == -1) {
            return null;
        }
        return new KPoint(v1);
    }

    public static final void getVertex_Adjacent(int v0a, int v0b, int v0c, int v0d, int dir, int[] v1) {
        switch (v0d) {
            case 0: {
                switch (dir) {
                    case 0: {
                        v1[0] = v0a;
                        v1[1] = v0b;
                        v1[2] = v0c;
                        v1[3] = 2;
                        return;
                    }
                    case 1: {
                        v1[0] = v0a;
                        v1[1] = v0b;
                        v1[2] = v0c;
                        v1[3] = 3;
                        return;
                    }
                    case 2: {
                        v1[0] = v0a;
                        v1[1] = v0b;
                        v1[2] = v0c;
                        v1[3] = 4;
                        return;
                    }
                    case 3: {
                        v1[0] = v0a;
                        v1[1] = v0b;
                        v1[2] = v0c;
                        v1[3] = 5;
                        return;
                    }
                    case 4: {
                        v1[0] = v0a + 1;
                        v1[1] = v0b;
                        v1[2] = v0c - 1;
                        v1[3] = 2;
                        return;
                    }
                    case 5: {
                        v1[0] = v0a + 1;
                        v1[1] = v0b;
                        v1[2] = v0c - 1;
                        v1[3] = 1;
                        return;
                    }
                    case 6: {
                        v1[0] = v0a;
                        v1[1] = v0b - 1;
                        v1[2] = v0c - 1;
                        v1[3] = 4;
                        return;
                    }
                    case 7: {
                        v1[0] = v0a;
                        v1[1] = v0b - 1;
                        v1[2] = v0c - 1;
                        v1[3] = 3;
                        return;
                    }
                    case 8: {
                        v1[0] = v0a;
                        v1[1] = v0b - 1;
                        v1[2] = v0c - 1;
                        v1[3] = 2;
                        return;
                    }
                    case 9: {
                        v1[0] = v0a - 1;
                        v1[1] = v0b - 1;
                        v1[2] = v0c;
                        v1[3] = 5;
                        return;
                    }
                    case 10: {
                        v1[0] = v0a - 1;
                        v1[1] = v0b - 1;
                        v1[2] = v0c;
                        v1[3] = 4;
                        return;
                    }
                    case 11: {
                        v1[0] = v0a;
                        v1[1] = v0b;
                        v1[2] = v0c;
                        v1[3] = 1;
                        return;
                    }
                }
                v1[3] = -1;
                return;
            }
            case 1: {
                switch (dir) {
                    case 2: {
                        v1[0] = v0a;
                        v1[1] = v0b;
                        v1[2] = v0c;
                        v1[3] = 2;
                        return;
                    }
                    case 5: {
                        v1[0] = v0a;
                        v1[1] = v0b;
                        v1[2] = v0c;
                        v1[3] = 0;
                        return;
                    }
                    case 8: {
                        v1[0] = v0a - 1;
                        v1[1] = v0b - 1;
                        v1[2] = v0c;
                        v1[3] = 4;
                        return;
                    }
                    case 11: {
                        v1[0] = v0a - 1;
                        v1[1] = v0b;
                        v1[2] = v0c + 1;
                        v1[3] = 0;
                        return;
                    }
                }
                v1[3] = -1;
                return;
            }
            case 2: {
                switch (dir) {
                    case 0: {
                        v1[0] = v0a - 1;
                        v1[1] = v0b;
                        v1[2] = v0c + 1;
                        v1[3] = 5;
                        return;
                    }
                    case 2: {
                        v1[0] = v0a;
                        v1[1] = v0b + 1;
                        v1[2] = v0c + 1;
                        v1[3] = 0;
                        return;
                    }
                    case 4: {
                        v1[0] = v0a;
                        v1[1] = v0b;
                        v1[2] = v0c;
                        v1[3] = 3;
                        return;
                    }
                    case 6: {
                        v1[0] = v0a;
                        v1[1] = v0b;
                        v1[2] = v0c;
                        v1[3] = 0;
                        return;
                    }
                    case 8: {
                        v1[0] = v0a;
                        v1[1] = v0b;
                        v1[2] = v0c;
                        v1[3] = 1;
                        return;
                    }
                    case 10: {
                        v1[0] = v0a - 1;
                        v1[1] = v0b;
                        v1[2] = v0c + 1;
                        v1[3] = 0;
                        return;
                    }
                }
                v1[3] = -1;
                return;
            }
            case 3: {
                switch (dir) {
                    case 1: {
                        v1[0] = v0a;
                        v1[1] = v0b + 1;
                        v1[2] = v0c + 1;
                        v1[3] = 0;
                        return;
                    }
                    case 4: {
                        v1[0] = v0a;
                        v1[1] = v0b;
                        v1[2] = v0c;
                        v1[3] = 4;
                        return;
                    }
                    case 7: {
                        v1[0] = v0a;
                        v1[1] = v0b;
                        v1[2] = v0c;
                        v1[3] = 0;
                        return;
                    }
                    case 10: {
                        v1[0] = v0a;
                        v1[1] = v0b;
                        v1[2] = v0c;
                        v1[3] = 2;
                        return;
                    }
                }
                v1[3] = -1;
                return;
            }
            case 4: {
                switch (dir) {
                    case 0: {
                        v1[0] = v0a;
                        v1[1] = v0b + 1;
                        v1[2] = v0c + 1;
                        v1[3] = 0;
                        return;
                    }
                    case 2: {
                        v1[0] = v0a + 1;
                        v1[1] = v0b + 1;
                        v1[2] = v0c;
                        v1[3] = 1;
                        return;
                    }
                    case 4: {
                        v1[0] = v0a + 1;
                        v1[1] = v0b + 1;
                        v1[2] = v0c;
                        v1[3] = 0;
                        return;
                    }
                    case 6: {
                        v1[0] = v0a;
                        v1[1] = v0b;
                        v1[2] = v0c;
                        v1[3] = 5;
                        return;
                    }
                    case 8: {
                        v1[0] = v0a;
                        v1[1] = v0b;
                        v1[2] = v0c;
                        v1[3] = 0;
                        return;
                    }
                    case 10: {
                        v1[0] = v0a;
                        v1[1] = v0b;
                        v1[2] = v0c;
                        v1[3] = 3;
                        return;
                    }
                }
                v1[3] = -1;
                return;
            }
            case 5: {
                switch (dir) {
                    case 0: {
                        v1[0] = v0a;
                        v1[1] = v0b;
                        v1[2] = v0c;
                        v1[3] = 4;
                        return;
                    }
                    case 3: {
                        v1[0] = v0a + 1;
                        v1[1] = v0b + 1;
                        v1[2] = v0c;
                        v1[3] = 0;
                        return;
                    }
                    case 6: {
                        v1[0] = v0a + 1;
                        v1[1] = v0b;
                        v1[2] = v0c - 1;
                        v1[3] = 2;
                        return;
                    }
                    case 9: {
                        v1[0] = v0a;
                        v1[1] = v0b;
                        v1[2] = v0c;
                        v1[3] = 0;
                        return;
                    }
                }
                v1[3] = -1;
                return;
            }
        }
        v1[3] = -1;
    }

    public static final int getEdgeType(int vdog, int dir) {
        switch (vdog) {
            case 0: {
                switch (dir) {
                    case 0: {
                        return 2;
                    }
                    case 1: {
                        return 1;
                    }
                    case 2: {
                        return 2;
                    }
                    case 3: {
                        return 1;
                    }
                    case 4: {
                        return 2;
                    }
                    case 5: {
                        return 1;
                    }
                    case 6: {
                        return 2;
                    }
                    case 7: {
                        return 1;
                    }
                    case 8: {
                        return 2;
                    }
                    case 9: {
                        return 1;
                    }
                    case 10: {
                        return 2;
                    }
                    case 11: {
                        return 1;
                    }
                }
                return -1;
            }
            case 1: {
                switch (dir) {
                    case 2: {
                        return 0;
                    }
                    case 5: {
                        return 1;
                    }
                    case 8: {
                        return 0;
                    }
                    case 11: {
                        return 1;
                    }
                }
                return -1;
            }
            case 2: {
                switch (dir) {
                    case 0: {
                        return 0;
                    }
                    case 2: {
                        return 2;
                    }
                    case 4: {
                        return 0;
                    }
                    case 6: {
                        return 2;
                    }
                    case 8: {
                        return 0;
                    }
                    case 10: {
                        return 2;
                    }
                }
                return -1;
            }
            case 3: {
                switch (dir) {
                    case 1: {
                        return 1;
                    }
                    case 4: {
                        return 0;
                    }
                    case 7: {
                        return 1;
                    }
                    case 10: {
                        return 0;
                    }
                }
                return -1;
            }
            case 4: {
                switch (dir) {
                    case 0: {
                        return 2;
                    }
                    case 2: {
                        return 0;
                    }
                    case 4: {
                        return 2;
                    }
                    case 6: {
                        return 0;
                    }
                    case 8: {
                        return 2;
                    }
                    case 10: {
                        return 0;
                    }
                }
                return -1;
            }
            case 5: {
                switch (dir) {
                    case 0: {
                        return 0;
                    }
                    case 3: {
                        return 1;
                    }
                    case 6: {
                        return 0;
                    }
                    case 9: {
                        return 1;
                    }
                }
                return -1;
            }
        }
        return -1;
    }

    public static final int getDirectionDelta(int d0, int d1) {
        int delta;
        if (d0 == d1) {
            delta = 0;
        } else {
            int w = (d1 + 12 - d0) % 12;
            if (w < 6) {
                delta = w;
            } else if (w > 6) {
                delta = w - 12;
            } else {
                throw new IllegalArgumentException("BAD GEOMETRY : direction reversal : d0=" + d0 + " d1=" + d1);
            }
        }
        return delta;
    }

    public static final int getDirection_VertexVertex(int v0a, int v0b, int v0c, int v0d, int v1a, int v1b, int v1c, int v1d) {
        double[] p0 = new double[2];
        double[] p1 = new double[2];
        GK.getBasicPoint2D_Vertex(v0a, v0b, v0c, v0d, p0);
        GK.getBasicPoint2D_Vertex(v1a, v1b, v1c, v1d, p1);
        double d2d = GD.getDirection_PointPoint(p0[0], p0[1], p1[0], p1[1]);
        if (d2d > GETDIRVV_RANGES[0][0] || d2d < GETDIRVV_RANGES[0][1]) {
            return 0;
        }
        for (int i = 1; i < 12; ++i) {
            double[] range = GETDIRVV_RANGES[i];
            if (d2d <= range[0] || d2d >= range[1]) continue;
            return i;
        }
        return -1;
    }

    public static final int getDirection_VertexVertex(KPoint v0, KPoint v1) {
        return GK.getDirection_VertexVertex(v0.getAnt(), v0.getBat(), v0.getCat(), v0.getDog(), v1.getAnt(), v1.getBat(), v1.getCat(), v1.getDog());
    }

    public static final boolean getColinear_VertexVertex(int v0a, int v0b, int v0c, int v0d, int v1a, int v1b, int v1c, int v1d) {
        int d = GK.getDirection_VertexVertex(v0a, v0b, v0c, v0d, v1a, v1b, v1c, v1d);
        if (d == -1) {
            return false;
        }
        return GK.hasLiberty(v0d, d) && GK.hasLiberty(v1d, d);
    }

    public static final double getDistance_VertexVertex(KPoint v0, KPoint v1) {
        return GK.getDistance_VertexVertex(v0.getAnt(), v0.getBat(), v0.getCat(), v0.getDog(), v1.getAnt(), v1.getBat(), v1.getCat(), v1.getDog());
    }

    public static final double getDistance_VertexVertex(int v0a, int v0b, int v0c, int v0d, int v1a, int v1b, int v1c, int v1d) {
        double[] p0 = new double[2];
        double[] p1 = new double[2];
        GK.getBasicPoint2D_Vertex(v0a, v0b, v0c, v0d, p0);
        GK.getBasicPoint2D_Vertex(v1a, v1b, v1c, v1d, p1);
        return GD.getDistance_PointPoint(p0[0], p0[1], p1[0], p1[1]);
    }

    public static final KVector getVector_VertexVertex(int v0a, int v0b, int v0c, int v0d, int v1a, int v1b, int v1c, int v1d) {
        int dir = GK.getDirection_VertexVertex(v0a, v0b, v0c, v0d, v1a, v1b, v1c, v1d);
        if (dir == -1) {
            return null;
        }
        if (!GK.hasLiberty(v0d, dir) && GK.hasLiberty(v1d, dir)) {
            return null;
        }
        double dis = GK.getDistance_VertexVertex(v0a, v0b, v0c, v0d, v1a, v1b, v1c, v1d);
        return new KVector(dir, dis);
    }

    public static final KPoint getVertex_VertexVector(KPoint v, KVector t) {
        int[] v1 = new int[4];
        GK.getVertex_VertexVector(v.getAnt(), v.getBat(), v.getCat(), v.getDog(), t.direction, t.distance, v1);
        if (v1[3] == -1) {
            return null;
        }
        KPoint vertex = new KPoint(v1);
        return vertex;
    }

    public static final int[] getVertex_VertexVector(int[] v0, int tdir, double tdis) {
        int[] v1 = new int[4];
        GK.getVertex_VertexVector(v0[0], v0[1], v0[2], v0[3], tdir, tdis, v1);
        return v1;
    }

    public static final void getVertex_VertexVector(int v0a, int v0b, int v0c, int v0d, int tdir, double tdis, int[] v1) {
        double remaining = tdis;
        v1[0] = v0a;
        v1[1] = v0b;
        v1[2] = v0c;
        v1[3] = v0d;
        while (remaining > 1.52587890625E-5) {
            if ((remaining -= GK.getAdjacentDistance(v1[3], tdir)) < -1.52587890625E-5) {
                v1[3] = -1;
                return;
            }
            GK.getVertex_Adjacent(v1[0], v1[1], v1[2], v1[3], tdir, v1);
            if (v1[3] != -1) continue;
            return;
        }
    }

    public static final double getAdjacentDistance(int vdog, int dir) {
        if (dir % 2 == 1) {
            return EDGESLV_GOAT;
        }
        switch (vdog) {
            case 0: {
                return 2.0;
            }
            case 1: {
                return 1.0;
            }
            case 2: {
                if (dir % 4 == 0) {
                    return 1.0;
                }
                return 2.0;
            }
            case 3: {
                return 1.0;
            }
            case 4: {
                if (dir % 4 == 0) {
                    return 2.0;
                }
                return 1.0;
            }
            case 5: {
                return 1.0;
            }
        }
        throw new IllegalArgumentException("invalid value for vdog : " + vdog);
    }

    public static final void TEST_getVertex_VertexVector() {
        int failurecount = 0;
        for (int i = 0; i < 10000; ++i) {
            int[] v0 = GK.getRandomVertex();
            int[] vector = GK.getRandomVector(v0);
            int[] v1a = new int[4];
            int[] v1b = new int[4];
            GK.getVertex_VertexVector_AdjProcessForTest(v0[0], v0[1], v0[2], v0[3], vector[0], vector[1], v1a);
            GK.getVertex_VertexVector(v0[0], v0[1], v0[2], v0[3], vector[0], vector[1], v1b);
            if (v1a[3] == -1 && v1b[3] == -1 || v1a[0] == v1b[0] && v1a[1] == v1b[1] && v1a[2] == v1b[2] && v1a[3] == v1b[3]) {
                System.out.println("TEST INDEX : " + i + " : --- SUCCEESS ---");
                continue;
            }
            ++failurecount;
            System.out.println("TEST INDEX : " + i + " : ##>> FAIL <<##");
            System.out.println("v0 : " + v0[0] + "," + v0[1] + "," + v0[2] + "," + v0[3]);
            System.out.println("vector dir : " + vector[0]);
            System.out.println("vector dis : " + vector[1]);
            System.out.println("v1a : " + v1a[0] + "," + v1a[1] + "," + v1a[2] + "," + v1a[3]);
            System.out.println("v1b : " + v1b[0] + "," + v1b[1] + "," + v1b[2] + "," + v1b[3]);
            System.out.println("#><##><##><##><##><##><##><##><#");
        }
        if (failurecount == 0) {
            System.out.println("^^^^^^^^^^^^^^");
            System.out.println("TEST SUCCEEDED");
            System.out.println("TEST CYCLES : 10000");
            System.out.println("^^^^^^^^^^^^^^");
        } else {
            System.out.println("#><##><##><##><##><##><##><##><#");
            System.out.println("TEST FAILED");
            System.out.println("TEST CYCLES : 10000");
            System.out.println("FAILURE COUNT : " + failurecount);
            System.out.println("#><##><##><##><##><##><##><##><#");
        }
    }

    private static final void getVertex_VertexVector_AdjProcessForTest(int v0a, int v0b, int v0c, int v0d, int tdir, int tdis, int[] v1) {
        int edgelength;
        int distancetraversed;
        v1[0] = v0a;
        v1[1] = v0b;
        v1[2] = v0c;
        v1[3] = v0d;
        for (distancetraversed = 0; distancetraversed < tdis; distancetraversed += edgelength) {
            edgelength = GK.getEdgeType(v1[3], tdir) == 2 ? 2 : 1;
            GK.getVertex_Adjacent(v1[0], v1[1], v1[2], v1[3], tdir, v1);
        }
        if (distancetraversed > tdis) {
            v1[3] = -1;
        }
    }

    private static final int[] getRandomVertex() {
        Random r = new Random();
        int a = r.nextInt(21) - 10;
        int b = r.nextInt(21) - 10;
        int c = b - a;
        int d = r.nextInt(6);
        return new int[]{a, b, c, d};
    }

    private static final int[] getRandomVector(int[] v) {
        Random r = new Random();
        int dir = VERTEX_LIBERTIES[v[3]][r.nextInt(VERTEX_LIBERTIES[v[3]].length)];
        int dis = r.nextInt(33) + 1;
        return new int[]{dir, dis};
    }

    public static final double[] getBasicPoint2D_Vertex(int[] p) {
        double[] p2 = new double[2];
        GK.getBasicPoint2D_Vertex(p[0], p[1], p[2], p[3], p2);
        return p2;
    }

    public static final void getBasicPoint2D_Vertex(int ant, int bat, int cat, int dog, double[] p2d) {
        p2d[0] = (double)(ant + bat) * UINT_SQRT3;
        p2d[1] = (double)cat * 3.0;
        switch (dog) {
            case 0: {
                break;
            }
            case 1: {
                double[] arrd = p2d;
                arrd[0] = arrd[0] - P2D_G;
                double[] arrd2 = p2d;
                arrd2[1] = arrd2[1] + 1.5;
                break;
            }
            case 2: {
                double[] arrd = p2d;
                arrd[1] = arrd[1] + 2.0;
                break;
            }
            case 3: {
                double[] arrd = p2d;
                arrd[0] = arrd[0] + P2D_G;
                double[] arrd3 = p2d;
                arrd3[1] = arrd3[1] + 1.5;
                break;
            }
            case 4: {
                double[] arrd = p2d;
                arrd[0] = arrd[0] + UINT_SQRT3;
                double[] arrd4 = p2d;
                arrd4[1] = arrd4[1] + 1.0;
                break;
            }
            case 5: {
                double[] arrd = p2d;
                arrd[0] = arrd[0] + UINT_SQRT3;
                break;
            }
            default: {
                throw new IllegalArgumentException("dog out of range [0,5]. dog==" + dog);
            }
        }
    }

    public static final void getVertex_Transitionswise(int v0a, int v0b, int v0c, int v0d, int dir, int trans, int[] v1) {
        if (trans == 0) {
            v1[0] = v0a;
            v1[1] = v0b;
            v1[2] = v0c;
            v1[3] = v0d;
        }
        switch (v0d) {
            case 0: {
                switch (dir) {
                    case 0: {
                        v1[0] = v0a - (trans + 2) / 4;
                        v1[1] = v0b + trans / 4;
                        v1[2] = v0c + trans / 2;
                        v1[3] = DOGPATTERN0[trans % 4];
                        return;
                    }
                    case 1: {
                        v1[0] = v0a;
                        v1[1] = v0b + trans / 2;
                        v1[2] = v0c + trans / 2;
                        v1[3] = DOGPATTERN1[trans % 4];
                        return;
                    }
                    case 2: {
                        v1[0] = v0a + (trans + 2) / 4;
                        v1[1] = v0b + trans / 2;
                        v1[2] = v0c + trans / 4;
                        v1[3] = DOGPATTERN2[trans % 4];
                        return;
                    }
                    case 3: {
                        v1[0] = v0a + trans / 2;
                        v1[1] = v0b + trans / 2;
                        v1[2] = v0c;
                        v1[3] = DOGPATTERN3[trans % 4];
                        return;
                    }
                    case 4: {
                        v1[0] = v0a + trans / 2;
                        if (trans % 4 == 1) {
                            int[] arrn = v1;
                            arrn[0] = arrn[0] + 1;
                        }
                        v1[1] = v0b + trans / 4;
                        v1[2] = v0c - (trans + 3) / 4;
                        v1[3] = DOGPATTERN4[trans % 4];
                        return;
                    }
                    case 5: {
                        v1[0] = v0a + (trans + 1) / 2;
                        v1[1] = v0b;
                        v1[2] = v0c - (trans + 1) / 2;
                        v1[3] = DOGPATTERN5[trans % 4];
                        return;
                    }
                    case 6: {
                        v1[0] = v0a + (trans + 1) / 4;
                        v1[1] = v0b - (trans + 3) / 4;
                        v1[2] = v0c - (trans + 1) / 2;
                        v1[3] = DOGPATTERN6[trans % 4];
                        return;
                    }
                    case 7: {
                        v1[0] = v0a;
                        v1[1] = v0b - (trans + 1) / 2;
                        v1[2] = v0c - (trans + 1) / 2;
                        v1[3] = DOGPATTERN7[trans % 4];
                        return;
                    }
                    case 8: {
                        v1[0] = v0a - (trans + 1) / 4;
                        v1[1] = v0b - (trans + 1) / 2;
                        v1[2] = v0c - (trans + 3) / 4;
                        v1[3] = DOGPATTERN8[trans % 4];
                        return;
                    }
                    case 9: {
                        v1[0] = v0a - (trans + 1) / 2;
                        v1[1] = v0b - (trans + 1) / 2;
                        v1[2] = v0c;
                        v1[3] = DOGPATTERN9[trans % 4];
                        return;
                    }
                    case 10: {
                        v1[0] = v0a - trans / 2;
                        if (trans % 4 == 1) {
                            int[] arrn = v1;
                            arrn[0] = arrn[0] - 1;
                        }
                        v1[1] = v0b - (trans + 3) / 4;
                        v1[2] = v0c + trans / 4;
                        v1[3] = DOGPATTERN10[trans % 4];
                        return;
                    }
                    case 11: {
                        v1[0] = v0a - trans / 2;
                        v1[1] = v0b;
                        v1[2] = v0c + trans / 2;
                        v1[3] = DOGPATTERN11[trans % 4];
                        return;
                    }
                }
                return;
            }
            case 1: {
                switch (dir) {
                    case 2: {
                        v1[0] = v0a + trans / 4;
                        v1[1] = v0b + trans / 2;
                        v1[2] = v0c + (trans + 2) / 4;
                        v1[3] = DOGPATTERN2[(trans + 2) % 4];
                        return;
                    }
                    case 5: {
                        v1[0] = v0a + trans / 2;
                        v1[1] = v0b;
                        v1[2] = v0c - trans / 2;
                        v1[3] = DOGPATTERN5[(trans + 1) % 4];
                        return;
                    }
                    case 8: {
                        v1[0] = v0a - (trans + 3) / 4;
                        v1[1] = v0b - (trans + 1) / 2;
                        v1[2] = v0c - (trans + 1) / 4;
                        v1[3] = DOGPATTERN8[(trans + 2) % 4];
                        return;
                    }
                    case 11: {
                        v1[0] = v0a - (trans + 1) / 2;
                        v1[1] = v0b;
                        v1[2] = v0c + (trans + 1) / 2;
                        v1[3] = DOGPATTERN5[(trans + 1) % 4];
                        return;
                    }
                }
                return;
            }
            case 2: {
                switch (dir) {
                    case 0: {
                        v1[0] = v0a - (trans + 3) / 4;
                        v1[1] = v0b + (trans + 1) / 4;
                        v1[2] = v0c + (trans + 1) / 2;
                        v1[3] = DOGPATTERN0[(trans + 1) % 4];
                        return;
                    }
                    case 2: {
                        v1[0] = v0a + (trans + 1) / 4;
                        v1[1] = v0b + (trans + 1) / 2;
                        v1[2] = v0c + (trans + 3) / 4;
                        v1[3] = DOGPATTERN2[(trans + 3) % 4];
                        return;
                    }
                    case 4: {
                        v1[0] = v0a + trans / 2;
                        if (trans % 4 == 2) {
                            int[] arrn = v1;
                            arrn[0] = arrn[0] - 1;
                        }
                        v1[1] = v0b + (trans + 1) / 4;
                        v1[2] = v0c - trans / 4;
                        v1[3] = DOGPATTERN4[(trans + 1) % 4];
                        return;
                    }
                    case 6: {
                        v1[0] = v0a + trans / 4;
                        v1[1] = v0b - (trans + 2) / 4;
                        v1[2] = v0c - trans / 2;
                        v1[3] = DOGPATTERN6[(trans + 3) % 4];
                        return;
                    }
                    case 8: {
                        v1[0] = v0a - (trans + 2) / 4;
                        v1[1] = v0b - trans / 2;
                        v1[2] = v0c - trans / 4;
                        v1[3] = DOGPATTERN8[(trans + 1) % 4];
                        return;
                    }
                    case 10: {
                        v1[0] = v0a - (trans + 1) / 2;
                        if (trans % 4 == 2) {
                            int[] arrn = v1;
                            arrn[0] = arrn[0] - 1;
                        }
                        v1[1] = v0b - (trans + 2) / 4;
                        v1[2] = v0c + (trans + 3) / 4;
                        v1[3] = DOGPATTERN10[(trans + 3) % 4];
                        return;
                    }
                }
                return;
            }
            case 3: {
                switch (dir) {
                    case 1: {
                        v1[0] = v0a;
                        v1[1] = v0b + (trans + 1) / 2;
                        v1[2] = v0c + (trans + 1) / 2;
                        v1[3] = DOGPATTERN1[(trans + 1) % 4];
                        return;
                    }
                    case 4: {
                        v1[0] = v0a + (trans + 1) / 2;
                        if (trans % 4 == 1) {
                            int[] arrn = v1;
                            arrn[0] = arrn[0] - 1;
                        }
                        v1[1] = v0b + (trans + 2) / 4;
                        v1[2] = v0c - (trans + 1) / 4;
                        v1[3] = DOGPATTERN4[(trans + 2) % 4];
                        return;
                    }
                    case 7: {
                        v1[0] = v0a;
                        v1[1] = v0b - trans / 2;
                        v1[2] = v0c - trans / 2;
                        v1[3] = DOGPATTERN7[(trans + 1) % 4];
                        return;
                    }
                    case 10: {
                        v1[0] = v0a - trans / 2;
                        if (trans % 4 == 3) {
                            int[] arrn = v1;
                            arrn[0] = arrn[0] - 1;
                        }
                        v1[1] = v0b - (trans + 1) / 4;
                        v1[2] = v0c + (trans + 2) / 4;
                        v1[3] = DOGPATTERN10[(trans + 2) % 4];
                        return;
                    }
                }
                return;
            }
            case 4: {
                switch (dir) {
                    case 0: {
                        v1[0] = v0a - (trans + 1) / 4;
                        v1[1] = v0b + (trans + 3) / 4;
                        v1[2] = v0c + (trans + 1) / 2;
                        v1[3] = DOGPATTERN0[(trans + 3) % 4];
                        return;
                    }
                    case 2: {
                        v1[0] = v0a + (trans + 3) / 4;
                        v1[1] = v0b + (trans + 1) / 2;
                        v1[2] = v0c + (trans + 1) / 4;
                        v1[3] = DOGPATTERN2[(trans + 1) % 4];
                        return;
                    }
                    case 4: {
                        v1[0] = v0a + (trans + 1) / 2;
                        if (trans % 4 == 2) {
                            int[] arrn = v1;
                            arrn[0] = arrn[0] + 1;
                        }
                        v1[1] = v0b + (trans + 3) / 4;
                        v1[2] = v0c - (trans + 2) / 4;
                        v1[3] = DOGPATTERN4[(trans + 3) % 4];
                        return;
                    }
                    case 6: {
                        v1[0] = v0a + (trans + 2) / 4;
                        v1[1] = v0b - trans / 4;
                        v1[2] = v0c - trans / 2;
                        v1[3] = DOGPATTERN6[(trans + 1) % 4];
                        return;
                    }
                    case 8: {
                        v1[0] = v0a - trans / 4;
                        v1[1] = v0b - trans / 2;
                        v1[2] = v0c - (trans + 2) / 4;
                        v1[3] = DOGPATTERN8[(trans + 3) % 4];
                        return;
                    }
                    case 10: {
                        v1[0] = v0a - trans / 2;
                        if (trans % 4 == 2) {
                            int[] arrn = v1;
                            arrn[0] = arrn[0] + 1;
                        }
                        v1[1] = v0b - trans / 4;
                        v1[2] = v0c + (trans + 1) / 4;
                        v1[3] = DOGPATTERN10[(trans + 1) % 4];
                        return;
                    }
                }
                return;
            }
            case 5: {
                switch (dir) {
                    case 0: {
                        v1[0] = v0a - trans / 4;
                        v1[1] = v0b + (trans + 2) / 4;
                        v1[2] = v0c + trans / 2;
                        v1[3] = DOGPATTERN0[(trans + 2) % 4];
                        return;
                    }
                    case 3: {
                        v1[0] = v0a + (trans + 1) / 2;
                        v1[1] = v0b + (trans + 1) / 2;
                        v1[2] = v0c;
                        v1[3] = DOGPATTERN3[(trans + 1) % 4];
                        return;
                    }
                    case 6: {
                        v1[0] = v0a + (trans + 3) / 4;
                        v1[1] = v0b - (trans + 1) / 4;
                        v1[2] = v0c - (trans + 1) / 2;
                        v1[3] = DOGPATTERN6[(trans + 2) % 4];
                        return;
                    }
                    case 9: {
                        v1[0] = v0a - trans / 2;
                        v1[1] = v0b - trans / 2;
                        v1[2] = v0c;
                        v1[3] = DOGPATTERN9[(trans + 1) % 4];
                        return;
                    }
                }
                return;
            }
        }
    }

    public static final KPoint getVertex_Transitionswise(KPoint v0, int dir, int transitions) {
        int[] a = new int[4];
        GK.getVertex_Transitionswise(v0.getAnt(), v0.getBat(), v0.getCat(), v0.getDog(), dir, transitions, a);
        return new KPoint(a);
    }

    public static final KPoint getStandardVertex(double[] p, double margin) {
        return GK.getStandardVertex(p[0], p[1], margin);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static final KPoint getStandardVertex(double px, double py, double margin) {
        int kb;
        int kc;
        int kd;
        int ka;
        int bx = (int)Math.floor(px / GRIDCELLBOXWIDTH);
        int by = (int)Math.floor(py / 3.0);
        int ibx = (int)((px - (double)bx * GRIDCELLBOXWIDTH) / (GRIDCELLBOXWIDTH / 4.0));
        int iby = (int)((py - (double)by * 3.0) / 0.25);
        if (Math.abs(bx % 2) == Math.abs(by % 2)) {
            ka = (bx - by) / 2;
            kc = by;
            kb = ka + kc;
            if (ibx == 0) {
                if (iby == 0 || iby == 1) {
                    kd = 0;
                } else if (iby == 6 || iby == 7 || iby == 8 || iby == 9) {
                    kd = 2;
                } else {
                    if (iby != 10 && iby != 11) return null;
                    --ka;
                    ++kc;
                    kd = 5;
                }
            } else if (ibx == 1 || ibx == 2) {
                if (iby != 4 && iby != 5 && iby != 6 && iby != 7) return null;
                kd = 3;
            } else if (iby == 0 || iby == 1) {
                kd = 5;
            } else if (iby == 2 || iby == 3 || iby == 4 || iby == 5) {
                kd = 4;
            } else {
                if (iby != 10 && iby != 11) return null;
                ++kb;
                ++kc;
                kd = 0;
            }
        } else {
            ka = (bx - by + 1) / 2;
            kc = by;
            kb = ka + kc;
            if (ibx == 0) {
                if (iby == 0 || iby == 1) {
                    --ka;
                    --kb;
                    kd = 5;
                } else if (iby == 2 || iby == 3 || iby == 4 || iby == 5) {
                    --ka;
                    --kb;
                    kd = 4;
                } else {
                    if (iby != 10 && iby != 11) return null;
                    --ka;
                    ++kc;
                    kd = 0;
                }
            } else if (ibx == 1 || ibx == 2) {
                if (iby != 4 && iby != 5 && iby != 6 && iby != 7) return null;
                kd = 1;
            } else if (iby == 0 || iby == 1) {
                kd = 0;
            } else if (iby == 6 || iby == 7 || iby == 8 || iby == 9) {
                kd = 2;
            } else {
                if (iby != 10 && iby != 11) return null;
                --ka;
                ++kc;
                kd = 5;
            }
        }
        KPoint v = new KPoint(ka, kb, kc, kd);
        double[] precisepoint = v.getBasicPointCoor();
        if (Math.abs(px - precisepoint[0]) >= margin || Math.abs(py - precisepoint[1]) >= margin) return null;
        return v;
    }

    public static final int getRightAngle(KPoint v0, KPoint v1, KPoint v2) {
        int d0 = GK.getDirection_VertexVertex(v0, v1);
        int d1 = GK.getDirection_VertexVertex(v1, v2);
        int d = GK.getDirectionDelta(d0, d1);
        return 6 - d;
    }

    public static final int getLeftAngle(KPoint v0, KPoint v1, KPoint v2) {
        int d0 = GK.getDirection_VertexVertex(v0, v1);
        int d1 = GK.getDirection_VertexVertex(v1, v2);
        int d = GK.getDirectionDelta(d0, d1);
        return 6 + d;
    }

    public static final boolean isBetweenRight(int d0, int d1, int d) {
        int d1ccw;
        int d0cw = GK.getCWOffset(d, d0);
        return d0cw + (d1ccw = GK.getCCWOffset(d, d1)) < 11;
    }

    public static final boolean isBetweenLeft(int d0, int d1, int d) {
        int d1cw;
        int d0ccw = GK.getCCWOffset(d, d0);
        return d0ccw + (d1cw = GK.getCWOffset(d, d1)) < 11;
    }

    public static final int getCWOffset(int d0, int d1) {
        if (d1 >= d0) {
            return d1 - d0;
        }
        return 12 - d0 + d1;
    }

    public static final int getCCWOffset(int d0, int d1) {
        int f = 12 - GK.getCWOffset(d0, d1);
        if (f == 12) {
            f = 0;
        }
        return f;
    }

    public static final void main(String[] a) {
        Random r = new Random();
        for (int i = 0; i < 10; ++i) {
            int a0 = r.nextInt(12);
            int a1 = r.nextInt(12);
            int cwoff = GK.getCCWOffset(a0, a1);
            System.out.println("a0=" + a0 + " : a1=" + a1 + " : cwoff=" + cwoff);
        }
    }

    public static final List<KAnchor> getMetagonAnchorOptions(KMetagon metagon, KAnchor anchor) {
        KPolygon sample = metagon.getPolygon(anchor);
        List<KAnchor> prospects = GK.getProspectiveAnchorsForMAA(sample);
        ArrayList<KAnchor> validprospects = new ArrayList<KAnchor>();
        for (KAnchor a : prospects) {
            KPolygon test = metagon.getPolygon(a);
            if (test == null || !test.isCongruent(sample)) continue;
            validprospects.add(a);
        }
        System.out.println("VALID PROSPECTS COUNT : " + validprospects.size());
        return validprospects;
    }

    private static final List<KAnchor> getProspectiveAnchorsForMAA(KPolygon p) {
        ArrayList<KAnchor> anchors = new ArrayList<KAnchor>();
        int s = p.size();
        for (int i0 = 0; i0 < s; ++i0) {
            int i1 = i0 + 1;
            if (i1 == s) {
                i1 = 0;
            }
            KPoint v0 = (KPoint)p.get(i0);
            KPoint v1 = (KPoint)p.get(i1);
            anchors.add(new KAnchor(v0, v1, true));
            anchors.add(new KAnchor(v0, v1, false));
            anchors.add(new KAnchor(v1, v0, true));
            anchors.add(new KAnchor(v1, v0, false));
        }
        return anchors;
    }

    public static enum Orbit {
        CLOCKWISE,
        COUNTERCLOCKWISE;
        
    }

}

