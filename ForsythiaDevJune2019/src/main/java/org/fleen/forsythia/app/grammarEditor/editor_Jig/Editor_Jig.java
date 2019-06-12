/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.editor_Jig;

import java.io.PrintStream;
import java.util.Collection;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.fleen.forsythia.app.grammarEditor.GE;
import org.fleen.forsythia.app.grammarEditor.editor_Grammar.Editor_Grammar;
import org.fleen.forsythia.app.grammarEditor.editor_Jig.ui.GridEditJig;
import org.fleen.forsythia.app.grammarEditor.editor_Jig.ui.PanGridDensity;
import org.fleen.forsythia.app.grammarEditor.editor_Jig.ui.PanJigTags;
import org.fleen.forsythia.app.grammarEditor.editor_Jig.ui.PanSectionTags;
import org.fleen.forsythia.app.grammarEditor.editor_Jig.ui.UIEditJig;
import org.fleen.forsythia.app.grammarEditor.project.ProjectGrammar;
import org.fleen.forsythia.app.grammarEditor.project.jig.ProjectJig;
import org.fleen.forsythia.app.grammarEditor.project.jig.ProjectJigSection;
import org.fleen.forsythia.app.grammarEditor.project.metagon.ProjectMetagon;
import org.fleen.forsythia.app.grammarEditor.util.Editor;
import org.fleen.forsythia.app.grammarEditor.util.grid.GridRenderer;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.graph.DisconnectedGraph;
import org.fleen.geom_Kisrhombille.graph.GEdge;
import org.fleen.geom_Kisrhombille.graph.GVertex;
import org.fleen.geom_Kisrhombille.graph.Graph;

public class Editor_Jig
extends Editor {
    private static final long serialVersionUID = -8986352947770948434L;
    private static final String NAME = "JIG";
    public static final int MODE_CREATE_A = 0;
    public static final int MODE_CREATE_B = 1;
    public static final int MODE_RETOUCH = 2;
    public int mode;
    boolean modelocked = false;
    public ProjectJig editedjig;
    public ProjectJigSection focussection;
    public KPoint connectedhead;
    public KPoint unconnectedhead;

    public Editor_Jig() {
        super(NAME);
    }

    @Override
    public void configureForOpen() {
        this.modelocked = false;
        if (GE.ge.focusjig == null) {
            this.initEditingObjectsForCreate();
            this.setMode_CREATE_A();
        } else {
            this.initEditingObjectsForRetouch();
            this.setMode_RETOUCH();
        }
        this.refreshUI();
    }

    @Override
    public void configureForClose() {
        this.discardEditingObjects();
    }

    void setMode_CREATE_A() {
        this.mode = 0;
        UIEditJig ui = (UIEditJig)this.getUI();
        ui.btnsave.setVisible(false);
        ui.pangriddensity.setVisible(true);
        ui.btnsectionanchor.setVisible(false);
        ui.btnsectionchorus.setVisible(false);
        ui.pansectiontags.setVisible(false);
        this.setFocusSection(null);
        this.initGridPerspective();
        this.refreshUI();
    }

    void setMode_CREATE_B() {
        this.mode = 1;
        this.editedjig.deriveSectionsFromGraph();
        UIEditJig ui = (UIEditJig)this.getUI();
        ui.btnsave.setVisible(true);
        ui.pangriddensity.setVisible(false);
        ui.btnsectionanchor.setVisible(true);
        ui.btnsectionchorus.setVisible(true);
        ui.pansectiontags.setVisible(true);
        this.focussection = this.editedjig.sections.get(0);
        this.initGridPerspective();
        this.refreshGridGeometryAndImage();
        this.refreshButtons();
    }

    void setMode_RETOUCH() {
        this.mode = 2;
        this.modelocked = true;
        UIEditJig ui = (UIEditJig)this.getUI();
        ui.btnsave.setVisible(true);
        ui.pangriddensity.setVisible(false);
        ui.btnsectionanchor.setVisible(true);
        ui.btnsectionchorus.setVisible(true);
        ui.pansectiontags.setVisible(true);
        this.focussection = this.editedjig.sections.get(0);
        this.initGridPerspective();
        this.refreshGridGeometryAndImage();
        this.refreshButtons();
    }

    @Override
    protected JPanel createUI() {
        return new UIEditJig();
    }

    void initGridPerspective() {
        UIEditJig ui = (UIEditJig)this.getUI();
        ui.pangrid.centerAndFit();
    }

    @Override
    public void refreshUI() {
        this.refreshGridGeometryAndImage();
        this.refreshButtons();
    }

    public void refreshGridImage() {
        UIEditJig ui = (UIEditJig)this.getUI();
        ui.pangrid.repaint();
    }

    public void refreshGridGeometryAndImage() {
        UIEditJig ui = (UIEditJig)this.getUI();
        ui.pangrid.gridrenderer.invalidateTileImage();
        ui.pangrid.repaint();
    }

    void refreshButtons() {
        UIEditJig ui = (UIEditJig)this.getUI();
        ui.pangriddensity.lblgriddensity.setText("Grid Density = " + this.editedjig.getGridDensityString());
        ui.panjigtag.txtjigtag.setText(this.editedjig.tags);
        this.refreshSectionAnchorButton();
        this.refreshSectionChorusButton();
        this.refreshSectionTags();
        this.refreshModeButton();
        this.refreshInfo();
    }

    private void refreshModeButton() {
        UIEditJig ui = (UIEditJig)this.getUI();
        if (this.modelocked) {
            ui.btnmode.setVisible(false);
        } else {
            ui.btnmode.setVisible(true);
        }
        if (this.mode == 0) {
            ui.btnmode.setText("Edit Sections");
        } else {
            ui.btnmode.setText("Edit Geometry");
        }
    }

    private void refreshSectionAnchorButton() {
        UIEditJig ui = (UIEditJig)this.getUI();
        if (this.focussection == null) {
            ui.btnsectionanchor.setText("Section Anchor = ---");
        } else {
            ui.btnsectionanchor.setText("Section Anchor = " + this.focussection.getAnchorIndexString());
        }
    }

    private void refreshSectionChorusButton() {
        UIEditJig ui = (UIEditJig)this.getUI();
        if (this.focussection == null) {
            ui.btnsectionchorus.setText("Section Chorus = ---");
        } else {
            ui.btnsectionchorus.setText("Section Chorus = " + this.focussection.getChorusString());
        }
    }

    private void refreshSectionTags() {
        UIEditJig ui = (UIEditJig)this.getUI();
        if (this.focussection == null) {
            ui.pansectiontags.txttag.setText("---");
        } else {
            ui.pansectiontags.txttag.setText(this.focussection.tags);
        }
    }

    private void refreshInfo() {
        UIEditJig ui = (UIEditJig)this.getUI();
        ui.lblinfo.setText(this.getInfoString());
    }

    private String getInfoString() {
        if (this.mode == 0) {
            Graph graph = this.editedjig.getGraph();
            int opensequencecount = graph.getDisconnectedGraph().getOpenKVertexSequences().size();
            int undividedpolygoncount = graph.getDisconnectedGraph().getUndividedPolygons().size();
            return "opensequences=" + opensequencecount + " polygons=" + undividedpolygoncount;
        }
        int sectioncount = this.editedjig.sections.size();
        return "sections=" + sectioncount;
    }

    private void initEditingObjectsForCreate() {
        this.editedjig = new ProjectJig(GE.ge.focusmetagon);
        this.connectedhead = null;
        this.unconnectedhead = null;
        this.focussection = null;
    }

    private void initEditingObjectsForRetouch() {
        this.editedjig = GE.ge.focusjig;
        this.focussection = null;
    }

    private void discardEditingObjects() {
        this.editedjig = null;
        this.connectedhead = null;
        this.unconnectedhead = null;
        this.focussection = null;
    }

    public void setFocusSection(ProjectJigSection m) {
        this.focussection = m;
    }

    public ProjectJigSection getFocusSection() {
        if (this.focussection == null) {
            this.focussection = this.editedjig.sections.get(0);
        }
        return this.focussection;
    }

    public void touchVertex(KPoint v) {
        if (this.mode == 1 || this.mode == 2) {
            return;
        }
        if (this.connectedhead != null && v.equals(this.connectedhead)) {
            this.unconnectedhead = this.connectedhead;
            this.connectedhead = null;
        } else if (this.unconnectedhead != null && v.equals(this.unconnectedhead)) {
            this.editedjig.getGraph().removeVertex(v);
            this.connectedhead = null;
            this.unconnectedhead = null;
        } else if (this.editedjig.getGraph().contains(v)) {
            if (this.connectedhead != null) {
                this.editedjig.getGraph().connect(v, this.connectedhead);
            }
            this.connectedhead = v;
            this.unconnectedhead = null;
        } else {
            GEdge edge = this.editedjig.getGraph().getCrossingEdge(v);
            if (edge != null) {
                Graph graph = this.editedjig.getGraph();
                graph.disconnect(edge.v0.kvertex, edge.v1.kvertex);
                graph.addVertex(v);
                graph.connect(edge.v0.kvertex, v);
                graph.connect(edge.v1.kvertex, v);
                if (this.connectedhead != null) {
                    this.editedjig.getGraph().connect(this.connectedhead, v);
                }
                this.connectedhead = v;
                this.unconnectedhead = null;
            } else {
                this.editedjig.getGraph().addVertex(v);
                if (this.connectedhead != null) {
                    this.editedjig.getGraph().connect(v, this.connectedhead);
                }
                this.connectedhead = v;
                this.unconnectedhead = null;
            }
        }
        this.editedjig.getGraph().invalidateDisconnectedGraph();
        this.refreshUI();
    }

    public void touchSection(ProjectJigSection m) {
        if (m == null) {
            return;
        }
        if (this.mode == 0) {
            return;
        }
        System.out.println("touch section");
        this.focussection = m;
        this.refreshUI();
    }

    public void save() {
        if (this.mode != 2) {
            GE.ge.focusgrammar.addMetagons(this.editedjig.localsectionmetagons);
            GE.ge.focusmetagon.invalidateOverviewIconImage();
            GE.ge.focusmetagon.addJig(this.editedjig);
            GE.ge.focusjig = this.editedjig;
        }
        GE.ge.setEditor(GE.ge.editor_grammar);
    }

    public void quit() {
        GE.ge.setEditor(GE.ge.editor_grammar);
    }

    public void gridDensity_Increment() {
        System.out.println("grid density increment");
        this.connectedhead = null;
        this.unconnectedhead = null;
        this.focussection = null;
        this.editedjig.incrementGridDensity();
        UIEditJig ui = (UIEditJig)this.getUI();
        ui.pangrid.gridrenderer.invalidateTileImage();
        this.initGridPerspective();
        this.refreshUI();
    }

    public void gridDensity_Decrement() {
        System.out.println("grid density decrement");
        this.connectedhead = null;
        this.unconnectedhead = null;
        this.focussection = null;
        this.editedjig.decrementGridDensity();
        UIEditJig ui = (UIEditJig)this.getUI();
        ui.pangrid.gridrenderer.invalidateTileImage();
        this.initGridPerspective();
        this.refreshUI();
    }

    public void toggleMode() {
        System.out.println("toggle geometry lock");
        if (this.mode == 1) {
            this.setMode_CREATE_A();
        } else {
            this.setMode_CREATE_B();
        }
        this.initGridPerspective();
        this.refreshUI();
    }

    public void setJigTags(String a) {
        System.out.println("set jig tags : " + a);
        this.editedjig.tags = a;
    }

    public void incrementSectionAnchor() {
        System.out.println("increment section anchor");
        this.focussection.incrementAnchor();
        this.refreshButtons();
        this.refreshGridImage();
    }

    public void incrementSectionChorus() {
        System.out.println("increment section chorus");
        this.focussection.incrementChorus();
        this.refreshButtons();
        this.refreshGridImage();
    }

    public void setSectionTags(String a) {
        System.out.println("set jig section tags " + a);
        this.focussection.tags = a;
    }
}

