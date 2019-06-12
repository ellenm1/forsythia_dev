/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.editor_Grammar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import org.fleen.forsythia.app.grammarEditor.GE;
import org.fleen.forsythia.app.grammarEditor.GrammarImportExport;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.Editor_Generator;
import org.fleen.forsythia.app.grammarEditor.editor_Grammar.ui.PanJigMenu;
import org.fleen.forsythia.app.grammarEditor.editor_Grammar.ui.PanMetagonMenu;
import org.fleen.forsythia.app.grammarEditor.editor_Grammar.ui.UIEditGrammar;
import org.fleen.forsythia.app.grammarEditor.editor_Jig.Editor_Jig;
import org.fleen.forsythia.app.grammarEditor.editor_Metagon.Editor_Metagon;
import org.fleen.forsythia.app.grammarEditor.project.ProjectGrammar;
import org.fleen.forsythia.app.grammarEditor.project.jig.ProjectJig;
import org.fleen.forsythia.app.grammarEditor.project.metagon.ProjectMetagon;
import org.fleen.forsythia.app.grammarEditor.util.Editor;

public class Editor_Grammar
extends Editor {
    private static final long serialVersionUID = 171251086665175422L;
    public static final String NAME = "GRAMMAR";

    public Editor_Grammar() {
        super(NAME);
    }

    @Override
    public void configureForOpen() {
        if (GE.ge.focusmetagon == null && GE.ge.focusgrammar.hasMetagons()) {
            GE.ge.focusmetagon = GE.ge.focusgrammar.getMetagon(0);
        }
        if (GE.ge.focusmetagon != null && GE.ge.focusjig == null && GE.ge.focusmetagon.hasJigs()) {
            GE.ge.focusjig = GE.ge.focusmetagon.getJig(0);
        }
        this.refreshUI();
    }

    @Override
    public void configureForClose() {
    }

    @Override
    protected JPanel createUI() {
        return new UIEditGrammar();
    }

    @Override
    public void refreshUI() {
        UIEditGrammar ui = (UIEditGrammar)this.getUI();
        ui.panmetagonmenu.invalidateIconArrayMetrics();
        ui.panjigmenu.invalidateIconArrayMetrics();
        ui.repaint();
        this.refreshButtons();
    }

    private void refreshButtons() {
        UIEditGrammar ui = (UIEditGrammar)this.getUI();
        ui.lblgrammarname.setText("Grammar=" + GE.ge.focusgrammar.name);
        ui.lblmetagonscount.setText("Count=" + GE.ge.focusgrammar.getMetagonCount());
        ui.lblmetagonjiglesscount.setText("Jigless=" + GE.ge.focusgrammar.getJiglessMetagonsCount());
        ui.lblmetagonsisolatedcount.setText("Isolated=" + GE.ge.focusgrammar.getIsolatedMetagonsCount());
        ui.lbljigscount.setText("Count=" + this.getJigCount());
    }

    private int getJigCount() {
        if (GE.ge.focusmetagon == null) {
            return 0;
        }
        return GE.ge.focusmetagon.getJigCount();
    }

    public void createMetagon() {
        GE.ge.focusmetagon = null;
        UIEditGrammar ui = (UIEditGrammar)this.getUI();
        ui.panmetagonmenu.invalidateIconArrayMetrics();
        GE.ge.setEditor(GE.ge.editor_metagon);
        this.refreshUI();
    }

    public void editMetagon() {
        GE.ge.setEditor(GE.ge.editor_metagon);
        this.refreshUI();
    }

    public void discardMetagon() {
        UIEditGrammar ui = (UIEditGrammar)this.getUI();
        ui.panmetagonmenu.invalidateIconArrayMetrics();
        ui.panjigmenu.invalidateIconArrayMetrics();
        int a = GE.ge.focusgrammar.getIndex(GE.ge.focusmetagon) - 1;
        GE.ge.focusgrammar.discardMetagon(GE.ge.focusmetagon);
        if (a < 0) {
            a = 0;
        }
        GE.ge.focusmetagon = GE.ge.focusgrammar.getMetagon(a);
        this.refreshUI();
    }

    public void setFocusMetagon(ProjectMetagon m) {
        GE.ge.focusmetagon = m;
        UIEditGrammar ui = (UIEditGrammar)this.getUI();
        ui.panjigmenu.invalidateIconArrayMetrics();
        this.refreshUI();
    }

    public void createJig() {
        UIEditGrammar ui = (UIEditGrammar)this.getUI();
        ui.panmetagonmenu.invalidateIconArrayMetrics();
        GE.ge.focusjig = null;
        GE.ge.setEditor(GE.ge.editor_jig);
    }

    public void editJig() {
        ((UIEditGrammar)GE.ge.editor_grammar.getUI()).panmetagonmenu.invalidateIconArrayMetrics();
        GE.ge.setEditor(GE.ge.editor_jig);
    }

    public void discardJig() {
        ((UIEditGrammar)GE.ge.editor_grammar.getUI()).panjigmenu.invalidateIconArrayMetrics();
        int a = GE.ge.focusmetagon.getJigIndex(GE.ge.focusjig) - 1;
        GE.ge.focusmetagon.discardJig(GE.ge.focusjig);
        if (a < 0) {
            a = 0;
        }
        GE.ge.focusjig = GE.ge.focusmetagon.getJig(a);
        this.refreshUI();
        this.getUI().repaint();
    }

    public void setFocusJig(ProjectJig m) {
        GE.ge.focusjig = m;
        UIEditGrammar ui = (UIEditGrammar)this.getUI();
        this.refreshUI();
        ui.panjigmenu.repaint();
    }

    public void createNewGrammar() {
        GE.ge.focusgrammar = new ProjectGrammar();
        GE.ge.focusmetagon = null;
        GE.ge.focusjig = null;
        this.refreshUI();
    }

    public void exportGrammar() {
        GE.ge.grammarimportexport.exportGrammar();
    }

    public void importGrammar() {
        GE.ge.grammarimportexport.importGrammar();
        this.refreshUI();
    }

    public void generate() {
        GE.ge.setEditor(GE.ge.editor_generator);
    }
}

