/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.editor_Metagon.ui;

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
import org.fleen.forsythia.app.grammarEditor.editor_Metagon.Editor_Metagon;
import org.fleen.forsythia.app.grammarEditor.util.UI;

public class PanMetagonTags
extends JPanel {
    private static final long serialVersionUID = 1080948326559731578L;
    public JTextField txtmetagontags;

    public PanMetagonTags() {
        this.setBackground(UI.BUTTON_RED);
        this.setLayout(new BoxLayout(this, 0));
        Component horizontalStrut = Box.createHorizontalStrut(4);
        this.add(horizontalStrut);
        JLabel lbljigtag = new JLabel("MetagonTags=");
        this.add(lbljigtag);
        lbljigtag.setFont(new Font("Dialog", 1, 14));
        Component horizontalStrut_3 = Box.createHorizontalStrut(4);
        this.add(horizontalStrut_3);
        this.txtmetagontags = new JTextField("foo", 20);
        this.txtmetagontags.setBackground(UI.BUTTON_YELLOW);
        this.add(this.txtmetagontags);
        this.txtmetagontags.setFont(new Font("DejaVu Sans Mono", 0, 18));
        this.txtmetagontags.setBorder(null);
        this.txtmetagontags.addKeyListener(new KeyAdapter(){

            @Override
            public void keyReleased(KeyEvent e) {
                GE.ge.editor_metagon.setMetagonTags(PanMetagonTags.this.txtmetagontags.getText());
            }
        });
        Component horizontalStrut_1 = Box.createHorizontalStrut(4);
        this.add(horizontalStrut_1);
    }

}

