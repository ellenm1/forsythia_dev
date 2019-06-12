/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.core.grammar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.fleen.forsythia.core.Forsythia;
import org.fleen.forsythia.core.composition.FPolygon;
import org.fleen.forsythia.core.grammar.Jig;

public class JigList
extends ArrayList<Jig>
implements Forsythia {
    private static final long serialVersionUID = 866462351907868545L;

    public JigList(Collection<Jig> jigs) {
        super(jigs.size());
        this.init(jigs);
    }

    private void init(Collection<Jig> jigs) {
        this.addAll(jigs);
        Collections.sort(this, new DetailSizeComparator());
    }

    public List<Jig> getJigsAboveDetailSizeFloor(FPolygon target, double floor) {
        ArrayList<Jig> jigs = new ArrayList<Jig>();
        for (int i = this.size() - 1; i > -1; --i) {
            Jig jig = (Jig)this.get(i);
            if (jig.getDetailSizePreview(target) < floor) break;
            jigs.add(jig);
        }
        return jigs;
    }

    public List<Jig> getJigsAboveDetailSizeFloorWithTags(FPolygon target, double floor, String[] tags) {
        List<Jig> a = this.getJigsAboveDetailSizeFloor(target, floor);
        ArrayList<Jig> b = new ArrayList<Jig>();
        for (Jig j : a) {
            if (!j.hasTags(tags)) continue;
            b.add(j);
        }
        return b;
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getSimpleName() + " " + "size=" + this.size() + "]";
    }

    private class DetailSizeComparator
    implements Comparator<Jig> {
        private DetailSizeComparator() {
        }

        @Override
        public int compare(Jig j0, Jig j1) {
            double bds1;
            double bds0 = j0.getDetailSizePreviewBaseDetailSize();
            if (bds0 == (bds1 = j1.getDetailSizePreviewBaseDetailSize())) {
                return 0;
            }
            if (bds0 > bds1) {
                return 1;
            }
            return -1;
        }
    }

}

