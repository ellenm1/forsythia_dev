/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.editor_Grammar.ui;

import java.util.List;
import org.fleen.forsythia.app.grammarEditor.GE;
import org.fleen.forsythia.app.grammarEditor.editor_Grammar.Editor_Grammar;
import org.fleen.forsythia.app.grammarEditor.project.ProjectGrammar;
import org.fleen.forsythia.app.grammarEditor.project.metagon.ProjectMetagon;
import org.fleen.forsythia.app.grammarEditor.util.ElementMenu;
import org.fleen.forsythia.app.grammarEditor.util.ElementMenuItem;

public class PanMetagonMenu
extends ElementMenu {
    private static final long serialVersionUID = -1137924043100782395L;

    public PanMetagonMenu() {
        super(3);
    }

    @Override
    protected void doPopupMenu(int x, int y) {
    }

    @Override
    protected List<? extends ElementMenuItem> getItems() {
        if (GE.ge == null || GE.ge.focusgrammar == null) {
            return null;
        }
        return GE.ge.focusgrammar.metagons;
    }

    @Override
    protected boolean isFocusItem(ElementMenuItem i) {
        return i == GE.ge.focusmetagon;
    }

    @Override
    protected void setFocusItem(ElementMenuItem i) {
        GE.ge.editor_grammar.setFocusMetagon((ProjectMetagon)i);
    }

    @Override
    protected void doDoubleclick(ElementMenuItem i) {
        GE.ge.editor_grammar.editMetagon();
    }
}

