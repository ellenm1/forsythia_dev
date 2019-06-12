/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.editor_Generator;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.fleen.forsythia.core.composition.FPolygon;
import org.fleen.forsythia.core.composition.FPolygonSignature;
import org.fleen.forsythia.core.composition.ForsythiaComposition;
import org.fleen.forsythia.core.composition.ForsythiaTreeNode;
import org.fleen.forsythia.core.grammar.FMetagon;
import org.fleen.forsythia.core.grammar.ForsythiaGrammar;
import org.fleen.forsythia.core.grammar.Jig;
import org.fleen.util.tree.TreeNode;
import org.fleen.util.tree.TreeNodeIterator;

public class Composer
implements Serializable {
    private static final long serialVersionUID = 3399571378455322265L;
    public int buildcycleindex;
    private double detailfloor;
    Map<FPolygonSignature, Jig> jigbypolygonsig = new Hashtable<FPolygonSignature, Jig>();
    Random rnd = new Random();
    private Set<FPolygonSignature> capped = new HashSet<FPolygonSignature>();

    public ForsythiaComposition compose(ForsythiaGrammar grammar, double detailfloor) {
        this.detailfloor = detailfloor;
        ForsythiaComposition composition = this.initComposition(grammar);
        this.build(composition);
        return composition;
    }

    private void build(ForsythiaComposition composition) {
        boolean creatednodes = true;
        this.buildcycleindex = 0;
        while (creatednodes) {
            System.out.println("created nodes");
            ++this.buildcycleindex;
            creatednodes = this.createNodes(composition);
        }
    }

    private boolean createNodes(ForsythiaComposition composition) {
        boolean creatednodes = false;
        TreeNodeIterator i = composition.getLeafPolygonIterator();
        ForsythiaGrammar grammar = composition.getGrammar();
        while (i.hasNext()) {
            FPolygon leaf = (FPolygon)i.next();
            if (this.isCapped(leaf)) continue;
            Jig jig = this.selectJig(grammar, leaf);
            if (jig == null) {
                this.cap(leaf);
                continue;
            }
            jig.createNodes(leaf);
            creatednodes = true;
        }
        this.jigbypolygonsig.clear();
        return creatednodes;
    }

    private Jig selectJig(ForsythiaGrammar forsythiagrammar, FPolygon polygon) {
        Jig j = this.jigbypolygonsig.get(polygon.getSignature());
        if (j != null) {
            return j;
        }
        j = this.getRandomJig(forsythiagrammar, polygon);
        if (j == null) {
            return null;
        }
        this.jigbypolygonsig.put(polygon.getSignature(), j);
        return j;
    }

    private Jig getRandomJig(ForsythiaGrammar fg, FPolygon target) {
        List<Jig> jigs = fg.getJigsAboveDetailSizeFloor(target, this.detailfloor);
        if (jigs.isEmpty()) {
            return null;
        }
        Jig jig = jigs.get(new Random().nextInt(jigs.size()));
        return jig;
    }

    private ForsythiaComposition initComposition(ForsythiaGrammar grammar) {
        ForsythiaComposition composition = new ForsythiaComposition();
        composition.setGrammar(grammar);
        FPolygon rootpolygon = this.createRootPolygon(grammar);
        composition.initTree(rootpolygon);
        return composition;
    }

    private FPolygon createRootPolygon(ForsythiaGrammar grammar) {
        FMetagon m3;
        List<FMetagon> metagons = grammar.getMetagons();
        if (metagons.isEmpty()) {
            throw new IllegalArgumentException("this grammar has no metagons");
        }
        ArrayList<FMetagon> rootmetagons = new ArrayList<FMetagon>();
        for (FMetagon m4 : metagons) {
            if (!m4.hasTags("root")) continue;
            rootmetagons.add(m4);
        }
        Object m4 = !rootmetagons.isEmpty() ? (FMetagon)rootmetagons.get(new Random().nextInt(rootmetagons.size())) : metagons.get(new Random().nextInt(metagons.size()));
        FPolygon p = new FPolygon(m4);
        return p;
    }

    protected void cap(FPolygon polygon) {
        this.capped.add(polygon.getSignature());
    }

    protected boolean isCapped(FPolygon polygon) {
        return this.capped.contains(polygon.getSignature());
    }

    protected void flushCappedSet() {
        this.capped.clear();
    }
}

