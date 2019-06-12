/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.editor_Grammar.ui;

import java.util.List;
import org.fleen.forsythia.app.grammarEditor.GE;
import org.fleen.forsythia.app.grammarEditor.editor_Grammar.Editor_Grammar;
import org.fleen.forsythia.app.grammarEditor.project.jig.ProjectJig;
import org.fleen.forsythia.app.grammarEditor.project.metagon.ProjectMetagon;
import org.fleen.forsythia.app.grammarEditor.util.ElementMenu;
import org.fleen.forsythia.app.grammarEditor.util.ElementMenuItem;

public class PanJigMenu
extends ElementMenu {
    private static final long serialVersionUID = 3987915843219850992L;

    public PanJigMenu() {
        super(1);
    }

    @Override
    protected void doPopupMenu(int x, int y) {
    }

    @Override
    protected List<? extends ElementMenuItem> getItems() {
        if (GE.ge == null || GE.ge.focusmetagon == null) {
            return null;
        }
        return GE.ge.focusmetagon.getJigs();
    }

    @Override
    protected boolean isFocusItem(ElementMenuItem i) {
        return i == GE.ge.focusjig;
    }

    @Override
    protected void setFocusItem(ElementMenuItem i) {
        GE.ge.editor_grammar.setFocusJig((ProjectJig)i);
    }

    @Override
    protected void doDoubleclick(ElementMenuItem i) {
        GE.ge.editor_grammar.editJig();
    }
}

