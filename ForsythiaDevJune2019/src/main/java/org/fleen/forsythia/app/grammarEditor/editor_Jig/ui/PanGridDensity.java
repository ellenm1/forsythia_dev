/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.editor_Jig.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.fleen.forsythia.app.grammarEditor.GE;
import org.fleen.forsythia.app.grammarEditor.editor_Jig.Editor_Jig;
import org.fleen.forsythia.app.grammarEditor.util.UI;

public class PanGridDensity
extends JPanel {
    private static final long serialVersionUID = 617916980288078402L;
    public JLabel lblgriddensity;
    JButton btngriddensityincrement;
    JButton btngriddensitydecrement;

    public PanGridDensity() {
        this.setBackground(UI.BUTTON_PURPLE);
        this.setLayout(new BoxLayout(this, 0));
        this.lblgriddensity = new JLabel("Grid Density = 000");
        this.add(this.lblgriddensity);
        this.lblgriddensity.setFont(new Font("Dialog", 1, 12));
        Component horizontalStrut = Box.createHorizontalStrut(8);
        this.add(horizontalStrut);
        this.btngriddensityincrement = new JButton("+");
        this.btngriddensityincrement.setBackground(UI.BUTTON_GREEN);
        this.add(this.btngriddensityincrement);
        this.btngriddensityincrement.setFont(new Font("Dialog", 1, 12));
        this.btngriddensityincrement.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                if (PanGridDensity.this.btngriddensityincrement.isEnabled()) {
                    GE.ge.editor_jig.gridDensity_Increment();
                }
            }
        });
        Component horizontalStrut_1 = Box.createHorizontalStrut(8);
        this.add(horizontalStrut_1);
        this.btngriddensitydecrement = new JButton("-");
        this.btngriddensitydecrement.setBackground(UI.BUTTON_GREEN);
        this.add(this.btngriddensitydecrement);
        this.btngriddensitydecrement.setFont(new Font("Dialog", 1, 12));
        this.btngriddensitydecrement.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                if (PanGridDensity.this.btngriddensitydecrement.isEnabled()) {
                    GE.ge.editor_jig.gridDensity_Decrement();
                }
            }
        });
        Component horizontalStrut_2 = Box.createHorizontalStrut(8);
        this.add(horizontalStrut_2);
    }

}

