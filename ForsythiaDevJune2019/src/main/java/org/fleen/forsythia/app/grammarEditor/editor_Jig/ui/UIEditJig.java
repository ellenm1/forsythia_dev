/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.editor_Jig.ui;

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
import org.fleen.forsythia.app.grammarEditor.editor_Jig.Editor_Jig;
import org.fleen.forsythia.app.grammarEditor.editor_Jig.ui.GridEditJig;
import org.fleen.forsythia.app.grammarEditor.editor_Jig.ui.PanGridDensity;
import org.fleen.forsythia.app.grammarEditor.editor_Jig.ui.PanJigTags;
import org.fleen.forsythia.app.grammarEditor.editor_Jig.ui.PanSectionTags;
import org.fleen.forsythia.app.grammarEditor.util.UI;
import org.fleen.util.ui.WrapLayout;

public class UIEditJig
extends JPanel {
    private static final long serialVersionUID = 8375171748091392216L;
    public JPanel pantop;
    public GridEditJig pangrid;
    public JLabel lblinfo;
    public JButton btnquit;
    public JButton btnsave;
    public JButton btnmode;
    public PanGridDensity pangriddensity;
    public PanJigTags panjigtag;
    public PanSectionTags pansectiontags;
    public JButton btnsectionchorus;
    public JButton btnsectionanchor;
    private Component horizontalStrut_1;

    public UIEditJig() {
        this.setLayout(new BorderLayout(0, 0));
        this.pantop = new JPanel();
        this.add((Component)this.pantop, "North");
        WrapLayout layouttop = new WrapLayout();
        layouttop.setAlignment(0);
        this.pantop.setLayout(layouttop);
        this.btnquit = new JButton("Quit");
        this.btnquit.setBackground(UI.BUTTON_RED);
        this.btnquit.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                GE.ge.editor_jig.quit();
            }
        });
        this.pantop.add(this.btnquit);
        this.btnsave = new JButton("Save");
        this.btnsave.setBackground(UI.BUTTON_RED);
        this.btnsave.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                GE.ge.editor_jig.save();
            }
        });
        this.pantop.add(this.btnsave);
        Component horizontalStrut = Box.createHorizontalStrut(20);
        this.pantop.add(horizontalStrut);
        this.btnmode = new JButton("Mode=foo");
        this.btnmode.setBackground(UI.BUTTON_ORANGE);
        this.btnmode.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                GE.ge.editor_jig.toggleMode();
            }
        });
        this.pantop.add(this.btnmode);
        this.horizontalStrut_1 = Box.createHorizontalStrut(20);
        this.pantop.add(this.horizontalStrut_1);
        this.pangriddensity = new PanGridDensity();
        this.pantop.add(this.pangriddensity);
        this.panjigtag = new PanJigTags();
        this.pantop.add(this.panjigtag);
        this.btnsectionchorus = new JButton("SectionChorus=000");
        this.btnsectionchorus.setBackground(UI.BUTTON_GREEN);
        this.btnsectionchorus.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                GE.ge.editor_jig.incrementSectionChorus();
            }
        });
        this.pantop.add(this.btnsectionchorus);
        this.btnsectionanchor = new JButton("SectionAnchor=000");
        this.btnsectionanchor.setBackground(UI.BUTTON_GREEN);
        this.btnsectionanchor.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                GE.ge.editor_jig.incrementSectionAnchor();
            }
        });
        this.pantop.add(this.btnsectionanchor);
        this.pansectiontags = new PanSectionTags();
        this.pantop.add(this.pansectiontags);
        this.pangrid = new GridEditJig();
        this.add((Component)this.pangrid, "Center");
        JPanel panbot = new JPanel();
        this.add((Component)panbot, "South");
        this.lblinfo = new JLabel("polygon count, geometry validity, etc... foo foo foo");
        panbot.add(this.lblinfo);
    }

}

