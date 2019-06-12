/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.editor_Grammar.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.fleen.forsythia.app.grammarEditor.GE;
import org.fleen.forsythia.app.grammarEditor.editor_Grammar.Editor_Grammar;
import org.fleen.forsythia.app.grammarEditor.editor_Grammar.ui.PanJigMenu;
import org.fleen.forsythia.app.grammarEditor.editor_Grammar.ui.PanMetagonMenu;
import org.fleen.forsythia.app.grammarEditor.util.UI;
import org.fleen.util.ui.WrapLayout;

public class UIEditGrammar
extends JPanel {
    private static final long serialVersionUID = -2823752807780069865L;
    public JLabel lblgrammarname;
    private JButton btngrammarimport;
    private JButton btngrammarexport;
    private JButton btngrammarnew;
    private JButton btngenerate;
    public JLabel lblmetagonstitle;
    public JLabel lblmetagonscount;
    public JLabel lblmetagonjiglesscount;
    public JLabel lblmetagonsisolatedcount;
    public JButton btnmetagonscreate;
    public JButton btnmetagonsedit;
    public JButton btnmetagonsdiscard;
    public PanMetagonMenu panmetagonmenu;
    public JLabel lbljigstitle;
    public JLabel lbljigscount;
    public JButton btnjigscreate;
    public JButton btnjigsedit;
    public JButton btnjigsdiscard;
    public PanJigMenu panjigmenu;

    public UIEditGrammar() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[2];
        gridBagLayout.rowHeights = new int[6];
        gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
        this.setLayout(gridBagLayout);
        JPanel pantop = new JPanel();
        pantop.setBackground(SystemColor.control);
        WrapLayout wl_pantop = new WrapLayout();
        wl_pantop.setAlignment(0);
        pantop.setLayout(wl_pantop);
        GridBagConstraints gbc_pantop = new GridBagConstraints();
        gbc_pantop.insets = new Insets(0, 0, 5, 0);
        gbc_pantop.fill = 1;
        gbc_pantop.gridx = 0;
        gbc_pantop.gridy = 0;
        this.add((Component)pantop, gbc_pantop);
        this.lblgrammarname = new JLabel("Grammar = foo");
        this.lblgrammarname.setFont(new Font("Dialog", 1, 18));
        pantop.add(this.lblgrammarname);
        Component horizontalStrut_1 = Box.createHorizontalStrut(20);
        pantop.add(horizontalStrut_1);
        this.btngrammarimport = new JButton("Import");
        this.btngrammarimport.setBackground(UI.BUTTON_BLUE);
        this.btngrammarimport.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                GE.ge.editor_grammar.importGrammar();
            }
        });
        pantop.add(this.btngrammarimport);
        this.btngrammarexport = new JButton("Export");
        this.btngrammarexport.setBackground(UI.BUTTON_BLUE);
        this.btngrammarexport.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                GE.ge.editor_grammar.exportGrammar();
            }
        });
        pantop.add(this.btngrammarexport);
        this.btngrammarnew = new JButton("New");
        this.btngrammarnew.setBackground(UI.BUTTON_BLUE);
        this.btngrammarnew.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                GE.ge.editor_grammar.createNewGrammar();
            }
        });
        pantop.add(this.btngrammarnew);
        Component horizontalStrut = Box.createHorizontalStrut(20);
        pantop.add(horizontalStrut);
        this.btngenerate = new JButton("Generate");
        this.btngenerate.setBackground(UI.BUTTON_YELLOW);
        this.btngenerate.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                GE.ge.editor_grammar.generate();
            }
        });
        pantop.add(this.btngenerate);
        JPanel panmetagonbuttons = new JPanel();
        panmetagonbuttons.setBackground(SystemColor.control);
        WrapLayout wl_panmetagonbuttons = new WrapLayout();
        wl_panmetagonbuttons.setAlignment(0);
        panmetagonbuttons.setLayout(wl_panmetagonbuttons);
        GridBagConstraints gbc_panmetagonbuttons = new GridBagConstraints();
        gbc_panmetagonbuttons.insets = new Insets(0, 0, 5, 0);
        gbc_panmetagonbuttons.fill = 1;
        gbc_panmetagonbuttons.gridx = 0;
        gbc_panmetagonbuttons.gridy = 1;
        this.add((Component)panmetagonbuttons, gbc_panmetagonbuttons);
        this.lblmetagonstitle = new JLabel("Metagons");
        this.lblmetagonstitle.setFont(new Font("Dialog", 1, 18));
        panmetagonbuttons.add(this.lblmetagonstitle);
        Component horizontalStrut_5 = Box.createHorizontalStrut(20);
        panmetagonbuttons.add(horizontalStrut_5);
        this.lblmetagonscount = new JLabel("Count=foo");
        this.lblmetagonscount.setFont(new Font("Dialog", 1, 16));
        panmetagonbuttons.add(this.lblmetagonscount);
        Component horizontalStrut_2 = Box.createHorizontalStrut(20);
        panmetagonbuttons.add(horizontalStrut_2);
        this.lblmetagonjiglesscount = new JLabel("Jigless=foo");
        this.lblmetagonjiglesscount.setFont(new Font("Dialog", 1, 16));
        panmetagonbuttons.add(this.lblmetagonjiglesscount);
        Component horizontalStrut_4 = Box.createHorizontalStrut(20);
        panmetagonbuttons.add(horizontalStrut_4);
        this.btnmetagonscreate = new JButton("Create");
        this.btnmetagonscreate.setBackground(UI.BUTTON_ORANGE);
        this.btnmetagonscreate.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                GE.ge.editor_grammar.createMetagon();
            }
        });
        this.lblmetagonsisolatedcount = new JLabel("Isolated=foo");
        this.lblmetagonsisolatedcount.setFont(new Font("Dialog", 1, 16));
        panmetagonbuttons.add(this.lblmetagonsisolatedcount);
        Component horizontalStrut_7 = Box.createHorizontalStrut(20);
        panmetagonbuttons.add(horizontalStrut_7);
        panmetagonbuttons.add(this.btnmetagonscreate);
        this.btnmetagonsedit = new JButton("Edit");
        this.btnmetagonsedit.setBackground(UI.BUTTON_ORANGE);
        this.btnmetagonsedit.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                GE.ge.editor_grammar.editMetagon();
            }
        });
        panmetagonbuttons.add(this.btnmetagonsedit);
        this.btnmetagonsdiscard = new JButton("Discard");
        this.btnmetagonsdiscard.setBackground(UI.BUTTON_ORANGE);
        this.btnmetagonsdiscard.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                GE.ge.editor_grammar.discardMetagon();
            }
        });
        panmetagonbuttons.add(this.btnmetagonsdiscard);
        this.panmetagonmenu = new PanMetagonMenu();
        this.panmetagonmenu.setBackground(Color.MAGENTA);
        GridBagConstraints gbc_panmetagonmenu = new GridBagConstraints();
        gbc_panmetagonmenu.insets = new Insets(0, 0, 5, 0);
        gbc_panmetagonmenu.fill = 1;
        gbc_panmetagonmenu.gridx = 0;
        gbc_panmetagonmenu.gridy = 2;
        this.add((Component)this.panmetagonmenu, gbc_panmetagonmenu);
        JPanel panjigbuttons = new JPanel();
        panjigbuttons.setBackground(SystemColor.control);
        GridBagConstraints gbc_panjigbuttons = new GridBagConstraints();
        gbc_panjigbuttons.insets = new Insets(0, 0, 5, 0);
        gbc_panjigbuttons.fill = 1;
        gbc_panjigbuttons.gridx = 0;
        gbc_panjigbuttons.gridy = 3;
        this.add((Component)panjigbuttons, gbc_panjigbuttons);
        this.panjigmenu = new PanJigMenu();
        this.panjigmenu.setBackground(Color.YELLOW);
        WrapLayout wl_panjigbuttons = new WrapLayout();
        wl_panjigbuttons.setAlignment(0);
        panjigbuttons.setLayout(wl_panjigbuttons);
        this.lbljigstitle = new JLabel("Jigs");
        this.lbljigstitle.setFont(new Font("Dialog", 1, 18));
        panjigbuttons.add(this.lbljigstitle);
        Component horizontalStrut_3 = Box.createHorizontalStrut(20);
        panjigbuttons.add(horizontalStrut_3);
        this.lbljigscount = new JLabel("Count=foo");
        this.lbljigscount.setFont(new Font("Dialog", 1, 16));
        panjigbuttons.add(this.lbljigscount);
        Component horizontalStrut_6 = Box.createHorizontalStrut(20);
        panjigbuttons.add(horizontalStrut_6);
        this.btnjigscreate = new JButton("Create");
        this.btnjigscreate.setBackground(UI.BUTTON_GREEN);
        this.btnjigscreate.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                GE.ge.editor_grammar.createJig();
            }
        });
        panjigbuttons.add(this.btnjigscreate);
        this.btnjigsedit = new JButton("Edit");
        this.btnjigsedit.setBackground(UI.BUTTON_GREEN);
        this.btnjigsedit.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                GE.ge.editor_grammar.editJig();
            }
        });
        panjigbuttons.add(this.btnjigsedit);
        this.btnjigsdiscard = new JButton("Discard");
        this.btnjigsdiscard.setBackground(UI.BUTTON_GREEN);
        this.btnjigsdiscard.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                GE.ge.editor_grammar.discardJig();
            }
        });
        panjigbuttons.add(this.btnjigsdiscard);
        GridBagConstraints gbc_panjigmenu = new GridBagConstraints();
        gbc_panjigmenu.fill = 1;
        gbc_panjigmenu.gridx = 0;
        gbc_panjigmenu.gridy = 4;
        this.add((Component)this.panjigmenu, gbc_panjigmenu);
    }

}

