/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import javax.swing.JFrame;
import org.fleen.geom_Kisrhombille.app.docGraphics.DocGraphics;

public class DGUI
extends JFrame {
    private static final long serialVersionUID = 8413646909208773971L;
    DocGraphics dg;
    AffineTransform t = new AffineTransform();

    DGUI(DocGraphics dg) {
        this.dg = dg;
        this.setExtendedState(6);
        this.setVisible(true);
        this.setDefaultCloseOperation(3);
        this.addKeyListener(new KL0());
    }

    @Override
    public void paint(Graphics g) {
        if (this.dg.image != null) {
            Container c = this.getContentPane();
            int xoff = (c.getWidth() - this.dg.imagewidth) / 2;
            int yoff = (c.getHeight() - this.dg.imageheight) / 2;
            this.t.setToIdentity();
            this.t.translate(xoff, yoff);
            Graphics2D h = (Graphics2D)c.getGraphics();
            h.drawImage(this.dg.image, this.t, null);
        }
    }

    class KL0
    extends KeyAdapter {
        KL0() {
        }

        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == 'r') {
                DGUI.this.dg.regenerate();
            }
            if (e.getKeyChar() == 'e') {
                DGUI.this.dg.export();
            }
        }
    }

}

