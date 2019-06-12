/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.editor_Generator.ui;

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
import org.fleen.forsythia.app.grammarEditor.editor_Generator.Editor_Generator;
import org.fleen.forsythia.app.grammarEditor.util.UI;

public class PanDetailFloor
extends JPanel {
    private static final long serialVersionUID = 4200492407974666454L;
    public JTextField txtfloor;

    public PanDetailFloor() {
        this.setBackground(UI.BUTTON_BLUE);
        this.setLayout(new BoxLayout(this, 0));
        Component horizontalStrut = Box.createHorizontalStrut(8);
        this.add(horizontalStrut);
        JLabel lbljigtag = new JLabel("DetailFloor = ");
        this.add(lbljigtag);
        lbljigtag.setFont(new Font("Dialog", 1, 14));
        Component horizontalStrut_3 = Box.createHorizontalStrut(8);
        this.add(horizontalStrut_3);
        this.txtfloor = new JTextField("12.34", 8);
        this.txtfloor.setBackground(UI.BUTTON_YELLOW);
        this.add(this.txtfloor);
        this.txtfloor.setFont(new Font("DejaVu Sans Mono", 0, 18));
        this.txtfloor.setBorder(null);
        Component horizontalStrut_1 = Box.createHorizontalStrut(8);
        this.add(horizontalStrut_1);
        this.txtfloor.addKeyListener(new KeyAdapter(){

            @Override
            public void keyReleased(KeyEvent e) {
                GE.ge.editor_generator.setDetailFloor(PanDetailFloor.this.txtfloor.getText());
            }
        });
    }

}

