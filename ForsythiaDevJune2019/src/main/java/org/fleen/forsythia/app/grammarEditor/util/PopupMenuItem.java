/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import org.fleen.forsythia.app.grammarEditor.util.UI;

public abstract class PopupMenuItem
extends JMenuItem {
    private static final long serialVersionUID = -1131176675047120902L;

    public PopupMenuItem(String s, boolean enabled) {
        super(s);
        this.setBackground(Color.orange);
        this.setForeground(Color.black);
        this.setEnabled(enabled);
        this.setFont(UI.POPUPMENUFONT);
        this.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                PopupMenuItem.this.doThing();
            }
        });
    }

    public PopupMenuItem(String s) {
        this(s, true);
    }

    protected abstract void doThing();

}

