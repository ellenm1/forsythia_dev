/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.project.jig;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.fleen.forsythia.app.grammarEditor.GE;
import org.fleen.forsythia.app.grammarEditor.project.ProjectGrammar;
import org.fleen.forsythia.app.grammarEditor.project.jig.ProjectJig;
import org.fleen.forsythia.app.grammarEditor.project.jig.ProjectJigSection;
import org.fleen.forsythia.app.grammarEditor.project.metagon.ProjectMetagon;
import org.fleen.forsythia.app.grammarEditor.util.UI;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_Kisrhombille.KAnchor;
import org.fleen.geom_Kisrhombille.KMetagon;
import org.fleen.geom_Kisrhombille.KPolygon;

class GrammarEditorIconImage
extends BufferedImage
implements Serializable {
    private static final long serialVersionUID = -5631766971442231210L;

    GrammarEditorIconImage(ProjectJig pj, int span) {
        super(span, span, 1);
        Graphics2D g = this.createGraphics();
        g.setRenderingHints(UI.RENDERING_HINTS);
        g.setColor(UI.ELEMENTMENU_ICONBACKGROUND);
        g.fillRect(0, 0, span, span);
        DPolygon hostmetagonpoints = this.getHostMPPoints(pj);
        Path2D.Double hostmetagonpath = UI.getClosedPath(hostmetagonpoints);
        Rectangle2D bounds = hostmetagonpath.getBounds2D();
        double bw = bounds.getWidth();
        double bh = bounds.getHeight();
        int maxpolygonimagespan = span - 16;
        double scale = bw > bh ? (double)maxpolygonimagespan / bw : (double)maxpolygonimagespan / bh;
        AffineTransform t = new AffineTransform();
        t.scale(scale, - scale);
        double xoffset = - bounds.getMinX() + ((double)span - bw * scale) / 2.0 / scale;
        double yoffset = - bounds.getMaxY() - ((double)span - bh * scale) / 2.0 / scale;
        t.translate(xoffset, yoffset);
        g.transform(t);
        BasicStroke stroke = new BasicStroke((float)(3.0 / scale), 2, 1, 0.0f, null, 0.0f);
        g.setStroke(stroke);
        this.renderSections(g, pj);
    }

    private DPolygon getHostMPPoints(ProjectJig j) {
        KMetagon m = GE.ge.focusgrammar.getMetagon((ProjectJig)j).kmetagon;
        KPolygon p = m.getScaledPolygon(j.griddensity);
        return p.getDefaultPolygon2D();
    }

    void renderSections(Graphics2D g, ProjectJig pj) {
        for (ProjectJigSection section : pj.sections) {
            KPolygon p = section.metagon.kmetagon.getPolygon(section.getAnchor());
            DPolygon points = p.getDefaultPolygon2D();
            this.renderSectionPolygon(g, points, section);
        }
    }

    private void renderSectionPolygon(Graphics2D g, DPolygon points, ProjectJigSection section) {
        int i = section.chorusindex % UI.EDITJIG_EDITSECTIONS_SECTIONFILL.length;
        Color fill = UI.EDITJIG_EDITSECTIONS_SECTIONFILL[i];
        Path2D.Double path = UI.getClosedPath(points);
        g.setPaint(fill);
        g.fill(path);
        g.setPaint(UI.ELEMENTMENU_ICON_STROKE);
        g.draw(path);
    }
}

