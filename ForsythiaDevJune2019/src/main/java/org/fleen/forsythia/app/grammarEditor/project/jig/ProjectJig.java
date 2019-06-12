/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.project.jig;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.fleen.forsythia.app.grammarEditor.GE;
import org.fleen.forsythia.app.grammarEditor.project.ProjectGrammar;
import org.fleen.forsythia.app.grammarEditor.project.jig.GrammarEditorIconImage;
import org.fleen.forsythia.app.grammarEditor.project.jig.JigEditorGeometryCache;
import org.fleen.forsythia.app.grammarEditor.project.jig.MetagonAndAnchors;
import org.fleen.forsythia.app.grammarEditor.project.jig.ProjectJigSection;
import org.fleen.forsythia.app.grammarEditor.project.metagon.ProjectMetagon;
import org.fleen.forsythia.app.grammarEditor.util.ElementMenuItem;
import org.fleen.forsythia.core.grammar.Jig;
import org.fleen.forsythia.core.grammar.JigSection;
import org.fleen.geom_Kisrhombille.KAnchor;
import org.fleen.geom_Kisrhombille.KMetagon;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.graph.DisconnectedGraph;
import org.fleen.geom_Kisrhombille.graph.Graph;

public class ProjectJig
implements ElementMenuItem,
Serializable {
    private static final long serialVersionUID = -8487660919916811634L;
    public ProjectMetagon jiggedmetagon;
    private static final int GRIDDENSITYLOWERLIMIT = 1;
    public int griddensity = 1;
    private transient Graph graph;
    public String tags = "";
    public List<ProjectJigSection> sections = new ArrayList<ProjectJigSection>();
    transient BufferedImage grammareditoriconimage = null;
    private transient JigEditorGeometryCache jigeditorgeometrycache = null;
    public List<ProjectMetagon> localsectionmetagons = new ArrayList<ProjectMetagon>();
    private int highestindexsofar;

    public ProjectJig(ProjectMetagon jiggedmetagon) {
        this.jiggedmetagon = jiggedmetagon;
        this.initGraph();
    }

    public ProjectJig(ProjectMetagon jiggedmetagon, Jig jig) {
        this.jiggedmetagon = jiggedmetagon;
        this.initForImport(jig);
    }

    public void initForImport(Jig jig) {
        this.griddensity = jig.griddensity;
        this.setTags(jig.getTags());
        for (JigSection js : jig.sections) {
            if (!(js instanceof JigSection)) continue;
            ProjectJigSection pjspolygon = new ProjectJigSection(this, js);
            this.sections.add(pjspolygon);
        }
        ((ArrayList)this.sections).trimToSize();
    }

    public void incrementGridDensity() {
        ++this.griddensity;
        this.initGraph();
        this.invalidateJigEditorGeometryCache();
    }

    public void decrementGridDensity() {
        if (this.griddensity > 1) {
            --this.griddensity;
            this.initGraph();
            this.invalidateJigEditorGeometryCache();
        }
    }

    public String getGridDensityString() {
        return String.format("%03d", this.griddensity);
    }

    public double getFishFactor() {
        return 1.0 / (double)this.griddensity;
    }

    public Graph getGraph() {
        if (this.graph == null) {
            this.initGraph();
        }
        return this.graph;
    }

    void initGraph() {
        this.graph = new Graph(this.jiggedmetagon.kmetagon.getPolygon(this.griddensity, true));
    }

    private void setTags(List<String> tags) {
        if (tags == null || tags.isEmpty()) {
            this.tags = "";
            return;
        }
        StringBuffer a = new StringBuffer();
        int s = tags.size();
        for (int i = 0; i < s; ++i) {
            String tag = tags.get(i);
            a.append(tag);
            if (i >= s - 1) continue;
            a.append(" ");
        }
        this.tags = a.toString();
    }

    public String[] getTagsForExport() {
        if (this.tags.equals("")) {
            return new String[0];
        }
        String[] a = this.tags.split(" ");
        return a;
    }

    public boolean usesInSection(ProjectMetagon m) {
        for (ProjectJigSection pjs : this.sections) {
            if (pjs.metagon != m) continue;
            return true;
        }
        return false;
    }

    public int getMaxSectionChorusIndex() {
        return this.sections.size();
    }

    private ProjectMetagon getMetagonByChorusIndex(int chorusindex) {
        for (ProjectJigSection section : this.sections) {
            if (section.chorusindex != chorusindex) continue;
            return section.metagon;
        }
        return null;
    }

    int getNextValidChorusIndex(ProjectJigSection section, int chorusindex) {
        boolean valid = false;
        int maxtries = this.getMaxSectionChorusIndex() * 2;
        int tries = 0;
        while (!valid) {
            ProjectMetagon test;
            if (++tries == ++maxtries) {
                throw new IllegalArgumentException("infinite loop");
            }
            if (++chorusindex > this.getMaxSectionChorusIndex()) {
                chorusindex = 0;
            }
            if ((test = this.getMetagonByChorusIndex(chorusindex)) != null && test != section.metagon) continue;
            valid = true;
        }
        return chorusindex;
    }

    @Override
    public BufferedImage getGrammarEditorIconImage(int span) {
        if (this.grammareditoriconimage == null) {
            this.grammareditoriconimage = new GrammarEditorIconImage(this, span);
        }
        return this.grammareditoriconimage;
    }

    public void invalidateGrammarEditorIconImage() {
        this.grammareditoriconimage = null;
    }

    public JigEditorGeometryCache getJigEditorGeometryCache() {
        if (this.jigeditorgeometrycache == null) {
            this.jigeditorgeometrycache = new JigEditorGeometryCache();
        }
        return this.jigeditorgeometrycache;
    }

    void invalidateJigEditorGeometryCache() {
        this.jigeditorgeometrycache.invalidate();
    }

    public void deriveSectionsFromGraph() {
        this.sections.clear();
        this.localsectionmetagons.clear();
        List<KPolygon> rawpolygons = this.graph.getDisconnectedGraph().getUndividedPolygons();
        List<KPolygon> cleanpolygons = this.getCleanedSectionPolygonsForSectionsInit(rawpolygons);
        List<MetagonAndAnchors> metagonandanchors = this.getJigSectionModelComponentsForSectionsInit(cleanpolygons);
        List<Integer> sectionmodelchorusindices = this.getJigSectionModelChorusIndicesForSectionsInit(metagonandanchors);
        int sectioncount = rawpolygons.size();
        for (int i = 0; i < sectioncount; ++i) {
            MetagonAndAnchors ma = metagonandanchors.get(i);
            int chorusindex = sectionmodelchorusindices.get(i);
            this.sections.add(new ProjectJigSection(this, ma, chorusindex));
        }
    }

    List<KPolygon> getCleanedSectionPolygonsForSectionsInit(List<KPolygon> rawpolygons) {
        ArrayList<KPolygon> cleanpolygons = new ArrayList<KPolygon>(rawpolygons.size());
        for (KPolygon raw : rawpolygons) {
            KPolygon clean = new KPolygon(raw);
            clean.removeRedundantColinearVertices();
            cleanpolygons.add(clean);
        }
        return cleanpolygons;
    }

    private List<Integer> getJigSectionModelChorusIndicesForSectionsInit(List<MetagonAndAnchors> sectionmodelmetagonandanchors) {
        ArrayList<Integer> chorusindices = new ArrayList<Integer>();
        HashMap<ProjectMetagon, Integer> indicesbymetagon = new HashMap<ProjectMetagon, Integer>();
        this.highestindexsofar = 0;
        for (MetagonAndAnchors ma : sectionmodelmetagonandanchors) {
            int chorusindex = this.getChorusIndexForSectionsInit(ma.metagon, indicesbymetagon);
            chorusindices.add(chorusindex);
        }
        return chorusindices;
    }

    private int getChorusIndexForSectionsInit(ProjectMetagon m, Map<ProjectMetagon, Integer> indicesbymetagon) {
        Integer i = indicesbymetagon.get(m);
        if (i != null) {
            return i;
        }
        i = this.highestindexsofar;
        indicesbymetagon.put(m, this.highestindexsofar);
        ++this.highestindexsofar;
        return i;
    }

    private List<MetagonAndAnchors> getJigSectionModelComponentsForSectionsInit(List<KPolygon> polygons) {
        ArrayList<MetagonAndAnchors> components = new ArrayList<MetagonAndAnchors>(polygons.size());
        for (KPolygon polygon : polygons) {
            components.add(this.getJigSectionModelComponents(polygon));
        }
        return components;
    }

    private MetagonAndAnchors getJigSectionModelComponents(KPolygon polygon) {
        MetagonAndAnchors c = this.getComponentsFromGrammar(polygon);
        if (c != null) {
            return c;
        }
        c = this.getComponentsLocally(polygon);
        if (c != null) {
            return c;
        }
        c = this.createComponents(polygon);
        return c;
    }

    private MetagonAndAnchors getComponentsFromGrammar(KPolygon polygon) {
        for (ProjectMetagon metagon : GE.ge.focusgrammar.metagons) {
            List<KAnchor> anchors = metagon.kmetagon.getAnchorOptions(polygon);
            if (anchors == null) continue;
            return new MetagonAndAnchors(metagon, anchors);
        }
        return null;
    }

    private MetagonAndAnchors getComponentsLocally(KPolygon polygon) {
        for (ProjectMetagon metagon : this.localsectionmetagons) {
            List<KAnchor> anchors = metagon.kmetagon.getAnchorOptions(polygon);
            if (anchors == null) continue;
            return new MetagonAndAnchors(metagon, anchors);
        }
        return null;
    }

    private MetagonAndAnchors createComponents(KPolygon polygon) {
        KMetagon m = polygon.getKMetagon();
        List<KAnchor> anchors = m.getAnchorOptions(polygon);
        ProjectMetagon pm = new ProjectMetagon(GE.ge.focusgrammar, polygon, "");
        this.localsectionmetagons.add(pm);
        MetagonAndAnchors c = new MetagonAndAnchors(pm, anchors);
        return c;
    }
}

