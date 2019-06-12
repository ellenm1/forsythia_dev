/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.editor_Metagon;

import java.io.PrintStream;
import java.util.List;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.fleen.forsythia.app.grammarEditor.GE;
import org.fleen.forsythia.app.grammarEditor.editor_Grammar.Editor_Grammar;
import org.fleen.forsythia.app.grammarEditor.editor_Metagon.ui.EditMetagonGrid;
import org.fleen.forsythia.app.grammarEditor.editor_Metagon.ui.EditMetagonUI;
import org.fleen.forsythia.app.grammarEditor.editor_Metagon.ui.PanMetagonTags;
import org.fleen.forsythia.app.grammarEditor.project.ProjectGrammar;
import org.fleen.forsythia.app.grammarEditor.project.metagon.ProjectMetagon;
import org.fleen.forsythia.app.grammarEditor.util.Editor;
import org.fleen.forsythia.app.grammarEditor.util.grid.GridRenderer;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.graph.GEdge;
import org.fleen.geom_Kisrhombille.graph.GVertex;
import org.fleen.geom_Kisrhombille.graph.Graph;

public class Editor_Metagon
extends Editor {
    private static final long serialVersionUID = 8128638341406087342L;
    private static final String NAME = "METAGON";
    public static final int MODE_CREATE = 0;
    public static final int MODE_RETOUCH = 1;
    public int mode;
    public ProjectMetagon editedmetagon;
    public KPoint connectedhead;
    public KPoint unconnectedhead;
    private String tagsbackup;
    boolean graphisvalid;

    public Editor_Metagon() {
        super(NAME);
    }

    @Override
    public void configureForOpen() {
        if (GE.ge.focusmetagon == null) {
            this.initEditingObjectsForCreate();
            this.setMode_Create();
        } else {
            this.initEditingObjectsForRetouch();
            this.setMode_Retouch();
        }
        this.refreshUI();
    }

    @Override
    public void configureForClose() {
        this.discardEditingObjects();
    }

    void setMode_Create() {
        this.mode = 0;
        EditMetagonUI ui = (EditMetagonUI)this.getUI();
        ui.btnsave.setVisible(false);
        this.initGridPerspective();
        this.refreshUI();
    }

    void setMode_Retouch() {
        this.mode = 1;
        EditMetagonUI ui = (EditMetagonUI)this.getUI();
        ui.btnsave.setVisible(true);
        this.initGridPerspective();
        this.refreshGridGeometryAndImage();
        this.refreshButtons();
    }

    @Override
    protected JPanel createUI() {
        return new EditMetagonUI();
    }

    void initGridPerspective() {
        EditMetagonUI ui = (EditMetagonUI)this.getUI();
        ui.pangrid.centerAndFit();
    }

    @Override
    public void refreshUI() {
        this.refreshGridGeometryAndImage();
        this.refreshButtons();
    }

    public void refreshGridImage() {
        EditMetagonUI ui = (EditMetagonUI)this.getUI();
        ui.pangrid.repaint();
    }

    public void refreshGridGeometryAndImage() {
        EditMetagonUI ui = (EditMetagonUI)this.getUI();
        ui.pangrid.gridrenderer.invalidateTileImage();
        ui.pangrid.repaint();
    }

    void refreshButtons() {
        EditMetagonUI ui = (EditMetagonUI)this.getUI();
        ui.panmetagontag.txtmetagontags.setText(this.editedmetagon.tags);
        if (this.graphisvalid) {
            ui.btnsave.setVisible(true);
        } else {
            ui.btnsave.setVisible(false);
        }
        this.refreshInfo();
    }

    private void refreshInfo() {
        EditMetagonUI ui = (EditMetagonUI)this.getUI();
        ui.lblinfo.setText(this.getInfoString());
    }

    private String getInfoString() {
        return "vertex count=" + this.editedmetagon.getGraph().vertices.size();
    }

    private void initEditingObjectsForCreate() {
        this.editedmetagon = new ProjectMetagon(GE.ge.focusgrammar);
        this.connectedhead = null;
        this.unconnectedhead = null;
        this.graphisvalid = false;
    }

    private void initEditingObjectsForRetouch() {
        this.editedmetagon = GE.ge.focusmetagon;
        this.tagsbackup = GE.ge.focusmetagon.tags;
        this.graphisvalid = true;
    }

    private void discardEditingObjects() {
        this.editedmetagon = null;
        this.connectedhead = null;
        this.unconnectedhead = null;
    }

    private void validateGraph() {
        this.graphisvalid = this.editedmetagon.getGraphValidity();
    }

    public void touchVertex(KPoint v) {
        if (this.mode == 1) {
            return;
        }
        if (this.connectedhead != null && v.equals(this.connectedhead)) {
            this.unconnectedhead = this.connectedhead;
            this.connectedhead = null;
        } else if (this.unconnectedhead != null && v.equals(this.unconnectedhead)) {
            this.editedmetagon.getGraph().removeVertex(v);
            this.connectedhead = null;
            this.unconnectedhead = null;
        } else if (this.editedmetagon.getGraph().contains(v)) {
            if (this.connectedhead != null) {
                this.editedmetagon.getGraph().connect(v, this.connectedhead);
            }
            this.connectedhead = v;
            this.unconnectedhead = null;
        } else {
            GEdge edge = this.editedmetagon.getGraph().getCrossingEdge(v);
            if (edge != null) {
                this.editedmetagon.getGraph().disconnect(edge.v0.kvertex, edge.v1.kvertex);
                this.editedmetagon.getGraph().addVertex(v);
                this.editedmetagon.getGraph().connect(edge.v0.kvertex, v);
                this.editedmetagon.getGraph().connect(edge.v1.kvertex, v);
                if (this.connectedhead != null) {
                    this.editedmetagon.getGraph().connect(this.connectedhead, v);
                }
                this.connectedhead = v;
                this.unconnectedhead = null;
            } else {
                this.editedmetagon.getGraph().addVertex(v);
                if (this.connectedhead != null) {
                    this.editedmetagon.getGraph().connect(v, this.connectedhead);
                }
                this.connectedhead = v;
                this.unconnectedhead = null;
            }
        }
        this.editedmetagon.getGraph().invalidateDisconnectedGraph();
        this.validateGraph();
        this.refreshUI();
    }

    public void save() {
        if (this.mode == 0) {
            this.editedmetagon.initGeometryForMetagonEditorCreate();
            GE.ge.focusgrammar.addMetagon(this.editedmetagon);
            GE.ge.focusmetagon = this.editedmetagon;
        }
        GE.ge.setEditor(GE.ge.editor_grammar);
    }

    public void quit() {
        if (this.mode == 0) {
            this.editedmetagon = null;
            GE.ge.focusmetagon = GE.ge.focusgrammar.metagons.isEmpty() ? null : GE.ge.focusgrammar.metagons.get(0);
        } else {
            GE.ge.focusmetagon.tags = this.tagsbackup;
        }
        GE.ge.setEditor(GE.ge.editor_grammar);
    }

    public void setMetagonTags(String a) {
        System.out.println("set jig tags : " + a);
        this.editedmetagon.tags = a;
    }
}

