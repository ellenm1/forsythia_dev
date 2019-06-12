/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import org.fleen.forsythia.app.grammarEditor.GE;

public class UIMain
extends JFrame {
    private static final long serialVersionUID = -8290517235964873539L;
    private static final int DEFAULTWIDTH = 1024;
    private static final int DEFAULTHEIGHT = 768;
    public JPanel paneditor;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                try {
                    UIMain frame = new UIMain();
                    frame.setVisible(true);
                    frame.setTitle("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public UIMain() {
        this.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent e) {
                GE.ge.term();
            }
        });
        this.setDefaultCloseOperation(0);
        Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(new Rectangle((ss.width - 1024) / 2, (ss.height - 768) / 2, 1024, 768));
        this.setVisible(true);
        this.paneditor = new JPanel();
        this.paneditor.setLayout(new CardLayout(0, 0));
        this.paneditor.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.paneditor);
    }

}

