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

public class PanJigTags
extends JPanel {
    private static final long serialVersionUID = -1484832312439244645L;
    public JTextField txtjigtag;

    public PanJigTags() {
        this.setBackground(UI.BUTTON_PURPLE);
        this.setLayout(new BoxLayout(this, 0));
        Component horizontalStrut = Box.createHorizontalStrut(4);
        this.add(horizontalStrut);
        JLabel lbljigtag = new JLabel("Jig Tags =");
        this.add(lbljigtag);
        lbljigtag.setFont(new Font("Dialog", 1, 14));
        Component horizontalStrut_3 = Box.createHorizontalStrut(4);
        this.add(horizontalStrut_3);
        this.txtjigtag = new JTextField("foo", 20);
        this.txtjigtag.setBackground(UI.BUTTON_YELLOW);
        this.add(this.txtjigtag);
        this.txtjigtag.setFont(new Font("DejaVu Sans Mono", 0, 18));
        this.txtjigtag.setBorder(null);
        this.txtjigtag.addKeyListener(new KeyAdapter(){

            @Override
            public void keyReleased(KeyEvent e) {
                GE.ge.editor_jig.setJigTags(PanJigTags.this.txtjigtag.getText());
            }
        });
        Component horizontalStrut_1 = Box.createHorizontalStrut(4);
        this.add(horizontalStrut_1);
    }

}

