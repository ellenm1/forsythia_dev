/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.fleen.forsythia.app.grammarEditor.GE;
import org.fleen.forsythia.app.grammarEditor.project.jig.ProjectJig;
import org.fleen.forsythia.app.grammarEditor.project.jig.ProjectJigSection;
import org.fleen.forsythia.app.grammarEditor.project.metagon.ProjectMetagon;
import org.fleen.forsythia.core.grammar.FMetagon;
import org.fleen.forsythia.core.grammar.ForsythiaGrammar;
import org.fleen.forsythia.core.grammar.Jig;
import org.fleen.forsythia.core.grammar.JigSection;
import org.fleen.geom_Kisrhombille.KAnchor;
import org.fleen.geom_Kisrhombille.KMetagon;

public class ProjectGrammar
implements Serializable {
    private static final long serialVersionUID = -8136928537083671983L;
    public String name;
    public List<ProjectMetagon> metagons = new ArrayList<ProjectMetagon>();

    public ProjectGrammar() {
    }

    public ProjectGrammar(ForsythiaGrammar fg, String name) {
        this.name = name;
        ProjectGrammar.init(this, fg);
    }

    public ProjectMetagon getMetagon(int index) {
        if (index < 0 || index >= this.metagons.size()) {
            return null;
        }
        return this.metagons.get(index);
    }

    public ProjectMetagon getMetagon(KMetagon kmetagon) {
        for (ProjectMetagon pm : this.metagons) {
            if (!pm.kmetagon.equals(kmetagon)) continue;
            return pm;
        }
        return null;
    }

    public ProjectMetagon getMetagon(ProjectJig j) {
        for (ProjectMetagon m : this.metagons) {
            for (ProjectJig ppj : m.jigs) {
                if (ppj != j) continue;
                return m;
            }
        }
        return null;
    }

    public boolean containsMetagon(KMetagon metagon) {
        for (ProjectMetagon m : this.metagons) {
            if (!m.kmetagon.equals(metagon)) continue;
            return true;
        }
        return false;
    }

    public void addMetagon(ProjectMetagon m) {
        if (m == null || this.metagons.contains(m)) {
            return;
        }
        this.metagons.add(m);
        Collections.sort(this.metagons, new MetagonComparator());
    }

    public void addMetagons(Collection<ProjectMetagon> m) {
        for (ProjectMetagon pm : m) {
            this.addMetagon(pm);
        }
    }

    public void discardMetagon(ProjectMetagon m) {
        if (m == null) {
            return;
        }
        this.metagons.remove(m);
        for (ProjectMetagon pm : this.metagons) {
            Iterator<ProjectJig> i = pm.jigs.iterator();
            while (i.hasNext()) {
                ProjectJig pj = i.next();
                if (!pj.usesInSection(m)) continue;
                i.remove();
            }
        }
    }

    public void discardMetagons(List<ProjectMetagon> pms) {
        for (ProjectMetagon a : pms) {
            this.discardMetagon(a);
        }
    }

    public int getMetagonCount() {
        return this.metagons.size();
    }

    public Iterator<ProjectMetagon> getMetagonIterator() {
        return this.metagons.iterator();
    }

    public int getIndex(ProjectMetagon m) {
        if (m == null || this.metagons.isEmpty()) {
            return -1;
        }
        return this.metagons.indexOf(m);
    }

    public boolean hasMetagons() {
        return !this.metagons.isEmpty();
    }

    public int getIsolatedMetagonsCount() {
        if (this.metagons.isEmpty()) {
            return 0;
        }
        int c = 0;
        for (ProjectMetagon m : this.metagons) {
            if (!m.isIsolated()) continue;
            ++c;
        }
        return c;
    }

    public int getJiglessMetagonsCount() {
        if (this.metagons.isEmpty()) {
            return 0;
        }
        int c = 0;
        for (ProjectMetagon m : this.metagons) {
            if (!m.isJigless()) continue;
            ++c;
        }
        return c;
    }

    public String getMetagonInfo() {
        return "MET:" + this.getMetagonCount() + " ISO:" + this.getIsolatedMetagonsCount() + " JIGLESS:" + this.getJiglessMetagonsCount();
    }

    private static final void init(ProjectGrammar projectgrammar, ForsythiaGrammar forsythiagrammar) {
        GE.ge.focusgrammar = projectgrammar;
        if (forsythiagrammar == null) {
            return;
        }
        HashSet<ProjectMetagon> projectmetagons = new HashSet<ProjectMetagon>();
        Iterator<FMetagon> i = forsythiagrammar.getMetagonIterator();
        while (i.hasNext()) {
            projectmetagons.add(new ProjectMetagon(projectgrammar, i.next()));
        }
        projectgrammar.addMetagons(projectmetagons);
        for (ProjectMetagon projectmetagon : projectgrammar.metagons) {
            List<Jig> jigs = forsythiagrammar.getJigs(projectmetagon.kmetagon);
            for (Jig jig : jigs) {
                ProjectJig projectjig = new ProjectJig(projectmetagon, jig);
                projectmetagon.addJig(projectjig);
            }
        }
    }

    public ForsythiaGrammar getForsythiaGrammar() {
        FMetagon fm;
        Hashtable<KMetagon, FMetagon> fmetagonbykmetagon = new Hashtable<KMetagon, FMetagon>();
        for (ProjectMetagon pm : this.metagons) {
            fm = new FMetagon(pm.kmetagon, pm.getTagsForExport());
            fmetagonbykmetagon.put(pm.kmetagon, fm);
        }
        Hashtable<FMetagon, List<Jig>> metagonjigs = new Hashtable<FMetagon, List<Jig>>();
        for (ProjectMetagon pm : this.metagons) {
            List<Jig> jigs = this.getJigs(pm, fmetagonbykmetagon);
            fm = (FMetagon)fmetagonbykmetagon.get(pm.kmetagon);
            metagonjigs.put(fm, jigs);
        }
        ForsythiaGrammar fg = new ForsythiaGrammar(metagonjigs);
        return fg;
    }

    private List<Jig> getJigs(ProjectMetagon pm, Map<KMetagon, FMetagon> fmetagonbykmetagon) {
        ArrayList<Jig> jigs = new ArrayList<Jig>();
        for (ProjectJig projectjig : pm.jigs) {
            int jiggriddensity = projectjig.griddensity;
            List<JigSection> jigsections = this.getJigSections(projectjig, fmetagonbykmetagon);
            String[] jigtags = projectjig.getTagsForExport();
            Jig jig = new Jig(jiggriddensity, jigsections, jigtags);
            jigs.add(jig);
        }
        return jigs;
    }

    private List<JigSection> getJigSections(ProjectJig projectjig, Map<KMetagon, FMetagon> fmetagonbykmetagon) {
        ArrayList<JigSection> jigsections = new ArrayList<JigSection>();
        jigsections.addAll(this.getPolygonJigSections(projectjig, fmetagonbykmetagon));
        return jigsections;
    }

    private List<JigSection> getPolygonJigSections(ProjectJig projectjig, Map<KMetagon, FMetagon> fmetagonbykmetagon) {
        ArrayList<JigSection> jigsections = new ArrayList<JigSection>();
        for (ProjectJigSection projectjigsection : projectjig.sections) {
            int jsproductchorusindex = projectjigsection.chorusindex;
            FMetagon jsproductmetagon = fmetagonbykmetagon.get(projectjigsection.metagon.kmetagon);
            KAnchor jsproductanchor = projectjigsection.getAnchor();
            String[] jstags = projectjigsection.getTagsForExport();
            JigSection js = new JigSection(jsproductmetagon, jsproductanchor, jsproductchorusindex, jstags);
            jigsections.add(js);
        }
        return jigsections;
    }

    private class MetagonComparator
    implements Comparator<ProjectMetagon>,
    Serializable {
        private static final long serialVersionUID = -529984029519619386L;

        private MetagonComparator() {
        }

        @Override
        public int compare(ProjectMetagon pm0, ProjectMetagon pm1) {
            int h1;
            int h0 = pm0.hashCode();
            if (h0 == (h1 = pm1.hashCode())) {
                return 0;
            }
            if (h0 < h1) {
                return -1;
            }
            return 1;
        }
    }

}

