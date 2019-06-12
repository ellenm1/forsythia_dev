/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.editor_Generator.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import javax.swing.JPanel;
import org.fleen.forsythia.app.grammarEditor.GE;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.Editor_Generator;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.Generator;

public class PanViewer
extends JPanel {
    private static final long serialVersionUID = 1186533064411024017L;

    @Override
    public void paint(Graphics g) {
        try {
            Graphics2D g2d = (Graphics2D)g;
            BufferedImage i = GE.ge.editor_generator.generator.viewerimage;
            if (i != null) {
                g2d.drawImage(i, null, null);
            }
        }
        catch (Exception x) {
            x.printStackTrace();
        }
    }
}

