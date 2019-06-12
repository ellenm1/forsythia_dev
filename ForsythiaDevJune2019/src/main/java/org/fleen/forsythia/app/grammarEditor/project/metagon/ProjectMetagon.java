/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.project.metagon;

import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.fleen.forsythia.app.grammarEditor.project.ProjectGrammar;
import org.fleen.forsythia.app.grammarEditor.project.jig.ProjectJig;
import org.fleen.forsythia.app.grammarEditor.project.metagon.MetagonEditorGeometryCache;
import org.fleen.forsythia.app.grammarEditor.project.metagon.ProjectMetagonEditGrammarIconImage;
import org.fleen.forsythia.app.grammarEditor.util.ElementMenuItem;
import org.fleen.forsythia.app.grammarEditor.util.UI;
import org.fleen.forsythia.core.grammar.FMetagon;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_Kisrhombille.KMetagon;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.graph.DisconnectedGraph;
import org.fleen.geom_Kisrhombille.graph.Graph;

public class ProjectMetagon
implements Serializable,
ElementMenuItem {
    private static final long serialVersionUID = -1143710212336234939L;
    private transient MetagonEditorGeometryCache metagoneditorgeometrycache = null;
    ProjectGrammar grammar;
    private transient Graph graph = null;
    public KMetagon kmetagon;
    public KPolygon kpolygon;
    public DPolygon polygon2d;
    public String tags = "";
    public List<ProjectJig> jigs = new ArrayList<ProjectJig>();
    Path2D.Double imagepath;
    transient BufferedImage iconimage = null;

    public ProjectMetagon(ProjectGrammar grammar) {
        this.grammar = grammar;
        this.initGraph();
    }

    public ProjectMetagon(ProjectGrammar grammar, List<KPoint> vertices, String tags) {
        this.grammar = grammar;
        this.initGeometry(vertices);
        this.tags = tags;
    }

    public ProjectMetagon(ProjectGrammar grammar, FMetagon fm) {
        this.grammar = grammar;
        this.initGeometry(fm.getPolygon());
        this.setTagsForImport(fm.getTags());
    }

    public MetagonEditorGeometryCache getMetagonEditorGeometryCache() {
        if (this.metagoneditorgeometrycache == null) {
            this.metagoneditorgeometrycache = new MetagonEditorGeometryCache();
        }
        return this.metagoneditorgeometrycache;
    }

    void invalidateJigEditorGeometryCache() {
        this.metagoneditorgeometrycache.invalidate();
    }

    private void initGraph() {
        this.graph = new Graph();
    }

    public Graph getGraph() {
        if (this.graph == null) {
            this.graph = new Graph(this.kpolygon);
        }
        return this.graph;
    }

    public boolean getGraphValidity() {
        int a = this.graph.getDisconnectedGraph().getPolygons().size();
        if (a != 1) {
            return false;
        }
        a = this.graph.getDisconnectedGraph().getOpenKVertexSequences().size();
        return a == 0;
    }

    private void initGeometry(List<KPoint> vertices) {
        this.kmetagon = new KMetagon(vertices);
        this.kpolygon = this.kmetagon.getPolygon();
        this.polygon2d = this.kpolygon.getDefaultPolygon2D();
    }

    public void initGeometryForMetagonEditorCreate() {
        this.kpolygon = this.graph.getDisconnectedGraph().getPolygons().get(0);
        this.kmetagon = new KMetagon(this.kpolygon);
        this.polygon2d = this.kpolygon.getDefaultPolygon2D();
    }

    private void setTagsForImport(List<String> tags) {
        if (tags.isEmpty()) {
            this.tags = "";
            return;
        }
        StringBuffer a = new StringBuffer();
        for (String b : tags) {
            a.append(String.valueOf(b) + " ");
        }
        a.delete(a.length() - 1, a.length());
        this.tags = a.toString();
    }

    public String[] getTagsForExport() {
        if (this.tags.equals("")) {
            return new String[0];
        }
        String[] a = this.tags.split(" ");
        return a;
    }

    public List<ProjectJig> getJigs() {
        return this.jigs;
    }

    public ProjectJig getJig(int i) {
        if (this.jigs.isEmpty()) {
            return null;
        }
        return this.jigs.get(i);
    }

    public boolean addJig(ProjectJig j) {
        if (this.jigs.contains(j)) {
            return false;
        }
        this.jigs.add(j);
        Collections.sort(this.jigs, new ProjectJigComparator());
        return true;
    }

    public boolean discardJig(ProjectJig j) {
        return this.jigs.remove(j);
    }

    public int getJigCount() {
        return this.jigs.size();
    }

    public boolean hasJigs() {
        return !this.jigs.isEmpty();
    }

    public int getJigIndex(ProjectJig j) {
        return this.jigs.indexOf(j);
    }

    public boolean isIsolated() {
        for (ProjectMetagon m : this.grammar.metagons) {
            for (ProjectJig j : m.jigs) {
                if (!j.usesInSection(this)) continue;
                return false;
            }
        }
        return true;
    }

    public boolean isJigless() {
        return this.jigs.isEmpty();
    }

    public Path2D.Double getImagePath() {
        if (this.imagepath == null) {
            this.imagepath = UI.getClosedPath(this.polygon2d);
        }
        return this.imagepath;
    }

    @Override
    public BufferedImage getGrammarEditorIconImage(int span) {
        if (this.iconimage == null) {
            this.iconimage = new ProjectMetagonEditGrammarIconImage(this, span);
        }
        return this.iconimage;
    }

    public void invalidateOverviewIconImage() {
        this.iconimage = null;
    }

    public int hashCode() {
        return this.kmetagon.hashCode();
    }

    public boolean equals(Object a) {
        ProjectMetagon b = (ProjectMetagon)a;
        return this.kmetagon.equals(b.kmetagon);
    }

    class ProjectJigComparator
    implements Comparator<ProjectJig>,
    Serializable {
        private static final long serialVersionUID = 8299780665610819376L;

        ProjectJigComparator() {
        }

        @Override
        public int compare(ProjectJig j0, ProjectJig j1) {
            int h1;
            int h0 = j0.hashCode();
            if (h0 == (h1 = j1.hashCode())) {
                return 0;
            }
            if (h0 < h1) {
                return -1;
            }
            return 1;
        }
    }

}

