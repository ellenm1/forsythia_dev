/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.core.grammar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.fleen.forsythia.core.Forsythia;
import org.fleen.forsythia.core.composition.FPolygon;
import org.fleen.forsythia.core.grammar.FMetagon;
import org.fleen.forsythia.core.grammar.Jig;
import org.fleen.forsythia.core.grammar.JigList;
import org.fleen.geom_Kisrhombille.KMetagon;

public class ForsythiaGrammar
implements Forsythia,
Serializable {
    private static final long serialVersionUID = 3018836034565752313L;
    private Map<FMetagon, JigList> metagonjigs = new Hashtable<FMetagon, JigList>();

    public ForsythiaGrammar(Map<FMetagon, ? extends Collection<Jig>> metagonjigs) {
        for (FMetagon a : metagonjigs.keySet()) {
            Collection<Jig> c = metagonjigs.get(a);
            this.metagonjigs.put(a, new JigList(c));
        }
    }

    public int getMetagonCount() {
        return this.metagonjigs.keySet().size();
    }

    public Iterator<FMetagon> getMetagonIterator() {
        return this.metagonjigs.keySet().iterator();
    }

    public List<FMetagon> getMetagons() {
        ArrayList<FMetagon> m = new ArrayList<FMetagon>(this.metagonjigs.keySet());
        return m;
    }

    public List<Jig> getJigs(FMetagon metagon) {
        ArrayList<Jig> a = new ArrayList<Jig>(this.metagonjigs.get(metagon));
        return a;
    }

    public List<Jig> getJigs(FPolygon polygon) {
        ArrayList<Jig> a = new ArrayList<Jig>(this.metagonjigs.get(polygon.metagon));
        return a;
    }

    public List<Jig> getJigs(KMetagon kmetagon) {
        FMetagon fm = null;
        for (FMetagon sm : this.metagonjigs.keySet()) {
            if (!sm.equals(kmetagon)) continue;
            fm = sm;
            break;
        }
        if (fm == null) {
            return new ArrayList<Jig>(0);
        }
        return this.getJigs(fm);
    }

    public List<Jig> getJigsAboveDetailSizeFloor(FPolygon target, double detailsizefloor) {
        JigList a = this.metagonjigs.get(target.metagon);
        List<Jig> b = a.getJigsAboveDetailSizeFloor(target, detailsizefloor);
        return b;
    }

    public List<Jig> getJigsAboveDetailSizeFloorWithTags(FPolygon target, double detailsizefloor, String[] tags) {
        JigList a = this.metagonjigs.get(target.metagon);
        List<Jig> b = a.getJigsAboveDetailSizeFloorWithTags(target, detailsizefloor, tags);
        return b;
    }

    public String toString() {
        StringBuffer a = new StringBuffer();
        a.append("\n\n");
        a.append("################\n");
        a.append("### FGRAMMAR ###\n\n");
        a.append("metagoncount=" + this.getMetagonCount() + "\n\n");
        for (FMetagon m : this.metagonjigs.keySet()) {
            a.append("+++ METAGON +++\n");
            a.append(String.valueOf(m.toString()) + "\n");
            a.append("+++ JIGS +++\n");
            JigList jiglist = this.metagonjigs.get(m);
            a.append("jigcount=" + jiglist.size() + "\n");
            for (Jig jig : jiglist) {
                a.append(String.valueOf(jig.toString()) + "\n");
            }
        }
        a.append("### FGRAMMAR ###\n");
        a.append("################\n");
        return a.toString();
    }
}

