/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.project.metagon;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.fleen.forsythia.app.grammarEditor.project.metagon.ProjectMetagon;
import org.fleen.forsythia.app.grammarEditor.util.UI;

class ProjectMetagonEditGrammarIconImage
extends BufferedImage
implements Serializable {
    private static final long serialVersionUID = 5867193485788399162L;

    ProjectMetagonEditGrammarIconImage(ProjectMetagon pm, int span) {
        super(span, span, 1);
        Path2D.Double path = pm.getImagePath();
        Graphics2D g = this.createGraphics();
        g.setRenderingHints(UI.RENDERING_HINTS);
        g.setColor(UI.ELEMENTMENU_ICONBACKGROUND);
        g.fillRect(0, 0, span, span);
        Rectangle2D pbounds = path.getBounds2D();
        double pw = pbounds.getWidth();
        double ph = pbounds.getHeight();
        int maxpolygonimagespan = span - 16;
        double scale = pw > ph ? (double)maxpolygonimagespan / pw : (double)maxpolygonimagespan / ph;
        AffineTransform t = new AffineTransform();
        t.scale(scale, - scale);
        double xoffset = - pbounds.getMinX() + ((double)span - pw * scale) / 2.0 / scale;
        double yoffset = - pbounds.getMaxY() - ((double)span - ph * scale) / 2.0 / scale;
        t.translate(xoffset, yoffset);
        g.transform(t);
        int pjcount = pm.getJigCount();
        if (pjcount < UI.GRAMMAR_EDITOR_METAGON_ICONS_FILLCOLOR.length) {
            g.setColor(UI.GRAMMAR_EDITOR_METAGON_ICONS_FILLCOLOR[pjcount]);
        } else {
            g.setColor(UI.GRAMMAR_EDITOR_METAGON_ICONS_FILLCOLOR[UI.GRAMMAR_EDITOR_METAGON_ICONS_FILLCOLOR.length - 1]);
        }
        g.fill(path);
        g.setColor(UI.ELEMENTMENU_ICON_STROKE);
        g.setStroke(new BasicStroke((float)(3.0 / scale), 2, 1, 0.0f, null, 0.0f));
        g.draw(path);
    }
}

