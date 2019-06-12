/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_2D;

import java.util.ArrayList;
import java.util.List;
import org.fleen.geom_2D.GD;

public class CurveSmoother_Closed {
    public List<double[]> getSmoothedPolygon(List<double[]> polygon, int smoothness) {
        List<double[]> p = polygon;
        for (int i = 0; i < smoothness; ++i) {
            p = this.getSplitTweak(p);
        }
        return p;
    }

    public List<double[]> getSplitTweak(List<double[]> polygon) {
        double[] tmp;
        int oldsize = polygon.size();
        int newsize = oldsize * 2;
        ArrayList<double[]> newpolygon = new ArrayList<double[]>(newsize);
        for (int i = 0; i < oldsize; ++i) {
            int inext = i + 1;
            if (inext == oldsize) {
                inext = 0;
            }
            double[] p0 = polygon.get(i);
            double[] p1 = polygon.get(inext);
            tmp = GD.getPoint_Mid2Points(p0[0], p0[1], p1[0], p1[1]);
            double[] pmid = new double[]{tmp[0], tmp[1]};
            newpolygon.add((double[])p0.clone());
            newpolygon.add(pmid);
        }
        for (int iold = 0; iold < newsize; iold += 2) {
            int inew1;
            int inew0 = iold - 1;
            if (inew0 < 0) {
                inew0 = newsize - 1;
            }
            if ((inew1 = iold + 1) == newsize) {
                inew1 = 0;
            }
            double[] pold = (double[])newpolygon.get(iold);
            double[] pnew0 = (double[])newpolygon.get(inew0);
            double[] pnew1 = (double[])newpolygon.get(inew1);
            tmp = GD.getPoint_Mid2Points(pnew0[0], pnew0[1], pnew1[0], pnew1[1]);
            tmp = GD.getPoint_Mid2Points(tmp[0], tmp[1], pold[0], pold[1]);
            pold[0] = tmp[0];
            pold[1] = tmp[1];
        }
        return newpolygon;
    }
}

