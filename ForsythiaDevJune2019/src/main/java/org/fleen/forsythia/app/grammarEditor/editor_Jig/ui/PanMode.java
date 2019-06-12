/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.editor_Jig.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.fleen.forsythia.app.grammarEditor.GE;
import org.fleen.forsythia.app.grammarEditor.editor_Jig.Editor_Jig;

public class PanMode
extends JPanel {
    private static final long serialVersionUID = -1790032330974577808L;
    public JButton btnmode;
    private static final Color BACKGROUND_MODEEDITSECTIONS = new Color(128, 255, 128);
    private static final Color BACKGROUND_MODEEDITGEOMETRY = new Color(255, 255, 128);

    public PanMode() {
        this.setBackground(new Color(204, 153, 255));
        this.setLayout(new BoxLayout(this, 0));
        Box verticalBox = Box.createVerticalBox();
        this.add(verticalBox);
        Box horizontalboxtop = Box.createHorizontalBox();
        verticalBox.add(horizontalboxtop);
        Component rigidArea = Box.createRigidArea(new Dimension(44, 4));
        horizontalboxtop.add(rigidArea);
        Box horizontalboxmid = Box.createHorizontalBox();
        verticalBox.add(horizontalboxmid);
        Component horizontalStrut = Box.createHorizontalStrut(4);
        horizontalboxmid.add(horizontalStrut);
        this.btnmode = new JButton("MODE FOO");
        this.btnmode.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                GE.ge.editor_jig.toggleMode();
            }
        });
        this.btnmode.setFont(new Font("Dialog", 1, 12));
        horizontalboxmid.add(this.btnmode);
        Component horizontalStrut_3 = Box.createHorizontalStrut(4);
        horizontalboxmid.add(horizontalStrut_3);
        Box horizontalboxbottom = Box.createHorizontalBox();
        verticalBox.add(horizontalboxbottom);
        Component rigidArea_1 = Box.createRigidArea(new Dimension(44, 4));
        horizontalboxbottom.add(rigidArea_1);
    }

    public void setMode(int mode) {
        if (mode == 1) {
            this.btnmode.setText("MODE = EDIT SECTIONS");
            this.btnmode.setBackground(BACKGROUND_MODEEDITSECTIONS);
        } else {
            this.btnmode.setText("MODE = EDIT GEOMETRY");
            this.btnmode.setBackground(BACKGROUND_MODEEDITGEOMETRY);
        }
    }

}

