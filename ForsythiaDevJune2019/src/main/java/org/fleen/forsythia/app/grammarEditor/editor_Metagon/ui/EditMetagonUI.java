/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.editor_Metagon.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.fleen.forsythia.app.grammarEditor.GE;
import org.fleen.forsythia.app.grammarEditor.editor_Metagon.Editor_Metagon;
import org.fleen.forsythia.app.grammarEditor.editor_Metagon.ui.EditMetagonGrid;
import org.fleen.forsythia.app.grammarEditor.editor_Metagon.ui.PanMetagonTags;
import org.fleen.forsythia.app.grammarEditor.util.UI;
import org.fleen.util.ui.WrapLayout;

public class EditMetagonUI
extends JPanel {
    private static final long serialVersionUID = 3242479197632226748L;
    public JPanel pantop;
    public EditMetagonGrid pangrid;
    public JLabel lblinfo;
    public JButton btnquit;
    public JButton btnsave;
    public PanMetagonTags panmetagontag;

    public EditMetagonUI() {
        this.setLayout(new BorderLayout(0, 0));
        this.pantop = new JPanel();
        this.add((Component)this.pantop, "North");
        WrapLayout layouttop = new WrapLayout();
        layouttop.setAlignment(0);
        this.pantop.setLayout(layouttop);
        this.btnquit = new JButton("Quit");
        this.btnquit.setBackground(UI.BUTTON_GREEN);
        this.btnquit.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                GE.ge.editor_metagon.quit();
            }
        });
        this.pantop.add(this.btnquit);
        this.btnsave = new JButton("Save");
        this.btnsave.setBackground(UI.BUTTON_GREEN);
        this.btnsave.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                GE.ge.editor_metagon.save();
            }
        });
        this.pantop.add(this.btnsave);
        Component horizontalStrut = Box.createHorizontalStrut(20);
        this.pantop.add(horizontalStrut);
        this.panmetagontag = new PanMetagonTags();
        this.pantop.add(this.panmetagontag);
        this.pangrid = new EditMetagonGrid();
        this.add((Component)this.pangrid, "Center");
        JPanel panbot = new JPanel();
        this.add((Component)panbot, "South");
        this.lblinfo = new JLabel("polygon count, geometry validity, etc... foo foo foo");
        panbot.add(this.lblinfo);
    }

}

