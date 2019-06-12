/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.editor_Jig.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import org.fleen.forsythia.app.grammarEditor.GE;
import org.fleen.forsythia.app.grammarEditor.editor_Jig.Editor_Jig;
import org.fleen.forsythia.app.grammarEditor.util.UI;

public class PanSectionTags
extends JPanel {
    private static final long serialVersionUID = 3262174629447498277L;
    public JTextField txttag;

    public PanSectionTags() {
        this.setBackground(UI.BUTTON_GREEN);
        this.setLayout(new BoxLayout(this, 0));
        Component horizontalStrut = Box.createHorizontalStrut(4);
        this.add(horizontalStrut);
        JLabel lbljigtag = new JLabel("SectionTags=");
        this.add(lbljigtag);
        lbljigtag.setFont(new Font("Dialog", 1, 14));
        Component horizontalStrut_3 = Box.createHorizontalStrut(4);
        this.add(horizontalStrut_3);
        this.txttag = new JTextField("foo", 20);
        this.txttag.setBackground(UI.BUTTON_YELLOW);
        this.add(this.txttag);
        this.txttag.setFont(new Font("DejaVu Sans Mono", 0, 18));
        this.txttag.setBorder(null);
        this.txttag.addKeyListener(new KeyAdapter(){

            @Override
            public void keyReleased(KeyEvent e) {
                GE.ge.editor_jig.setSectionTags(PanSectionTags.this.txttag.getText());
            }
        });
        Component horizontalStrut_1 = Box.createHorizontalStrut(4);
        this.add(horizontalStrut_1);
    }

}

