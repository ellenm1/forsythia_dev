/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.editor_Jig.gridOverlayPainter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.RectangularShape;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.fleen.forsythia.app.grammarEditor.GE;
import org.fleen.forsythia.app.grammarEditor.editor_Jig.Editor_Jig;
import org.fleen.forsythia.app.grammarEditor.editor_Jig.gridOverlayPainter.GlyphSystemModel;
import org.fleen.forsythia.app.grammarEditor.project.jig.JigEditorGeometryCache;
import org.fleen.forsythia.app.grammarEditor.project.jig.ProjectJig;
import org.fleen.forsythia.app.grammarEditor.project.jig.ProjectJigSection;
import org.fleen.forsythia.app.grammarEditor.util.UI;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_2D.GD;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.graph.DisconnectedGraph;
import org.fleen.geom_Kisrhombille.graph.GEdge;
import org.fleen.geom_Kisrhombille.graph.GVertex;
import org.fleen.geom_Kisrhombille.graph.Graph;

public class GridOverlayPainter
implements Serializable {
    private static final long serialVersionUID = 5607779324837953808L;

    public void paint(Graphics2D graphics, int w, int h, double scale, double centerx, double centery) {
        graphics.setRenderingHints(UI.RENDERING_HINTS);
        GE.ge.editor_jig.editedjig.getJigEditorGeometryCache().update(w, h, scale, centerx, centery);
        if (GE.ge.editor_jig.mode == 1 || GE.ge.editor_jig.mode == 2) {
            this.renderJigModel_EditSections(graphics);
        } else {
            this.renderJigModel_EditGeometry(graphics);
        }
    }

    private void renderJigModel_EditGeometry(Graphics2D graphics) {
        try {
            this.fillSections_EditGeometry(graphics);
            this.strokeGraphEdges_EditGeometry(graphics);
            this.renderVertices_EditGeometry(graphics);
        }
        catch (Exception x) {
            x.printStackTrace();
        }
    }

    private void fillSections_EditGeometry(Graphics2D graphics) {
        for (KPolygon m : GE.ge.editor_jig.editedjig.getGraph().getDisconnectedGraph().getUndividedPolygons()) {
            Color color = UI.EDITJIG_EDITGEOMETRY_HOSTMETAGONFILLCOLOR;
            Path2D path = GE.ge.editor_jig.editedjig.getJigEditorGeometryCache().getPath(m);
            graphics.setPaint(color);
            graphics.fill(path);
        }
    }

    private void strokeGraphEdges_EditGeometry(Graphics2D graphics) {
        graphics.setStroke(UI.GRID_DRAWINGSTROKE);
        graphics.setPaint(UI.EDITJIG_EDITGEOMETRY_STROKECOLOR);
        Iterator<GEdge> i = GE.ge.editor_jig.editedjig.getGraph().edges.iterator();
        Path2D.Double path = new Path2D.Double();
        while (i.hasNext()) {
            GEdge e = i.next();
            double[] p0 = GE.ge.editor_jig.editedjig.getJigEditorGeometryCache().getPoint(e.v0.kvertex);
            double[] p1 = GE.ge.editor_jig.editedjig.getJigEditorGeometryCache().getPoint(e.v1.kvertex);
            path.reset();
            ((Path2D)path).moveTo(p0[0], p0[1]);
            ((Path2D)path).lineTo(p1[0], p1[1]);
            graphics.draw(path);
        }
    }

    private void renderVertices_EditGeometry(Graphics2D graphics) {
        this.renderDefaultVertices(graphics, UI.EDITJIG_EDITGEOMETRY_STROKECOLOR);
        this.renderHeadDecorations(graphics);
    }

    private void renderDefaultVertices(Graphics2D graphics, Color color) {
        graphics.setPaint(color);
        float span = 12.0f;
        Ellipse2D.Double dot = new Ellipse2D.Double();
        for (GVertex v : GE.ge.editor_jig.editedjig.getGraph().vertices) {
            double[] p = GE.ge.editor_jig.editedjig.getJigEditorGeometryCache().getPoint(v.kvertex);
            ((RectangularShape)dot).setFrame(p[0] - (double)(span / 2.0f), p[1] - (double)(span / 2.0f), span, span);
            graphics.fill(dot);
        }
    }

    private void renderHeadDecorations(Graphics2D graphics) {
        graphics.setStroke(UI.EDITJIG_EDITGEOMETRY_HEADVERTEXDECORATIONSTROKE);
        int span = 32;
        if (GE.ge.editor_jig.connectedhead != null) {
            double[] p = GE.ge.editor_jig.editedjig.getJigEditorGeometryCache().getPoint(GE.ge.editor_jig.connectedhead);
            graphics.setPaint(UI.EDITJIG_EDITGEOMETRY_CONNECTEDHEADVERTEXDECORATIONCOLOR);
            graphics.drawOval((int)p[0] - span / 2, (int)p[1] - span / 2, span, span);
        } else if (GE.ge.editor_jig.unconnectedhead != null) {
            double[] p = GE.ge.editor_jig.editedjig.getJigEditorGeometryCache().getPoint(GE.ge.editor_jig.unconnectedhead);
            graphics.setPaint(UI.EDITJIG_EDITGEOMETRY_UNCONNECTEDHEADVERTEXDECORATIONCOLOR);
            graphics.drawOval((int)p[0] - span / 2, (int)p[1] - span / 2, span, span);
        }
    }

    private void renderJigModel_EditSections(Graphics2D graphics) {
        this.fillSections_EditSections(graphics);
        this.strokePolygonEdges_EditSections(graphics);
        this.renderGlyphs_EditSections(graphics);
    }

    private void fillSections_EditSections(Graphics2D graphics) {
        for (ProjectJigSection m : GE.ge.editor_jig.editedjig.sections) {
            int colorindex = m.chorusindex;
            Color color = UI.EDITJIG_EDITSECTIONS_SECTIONFILL[colorindex % UI.EDITJIG_EDITSECTIONS_SECTIONFILL.length];
            Path2D path = GE.ge.editor_jig.editedjig.getJigEditorGeometryCache().getPath(m.getPolygon());
            graphics.setPaint(color);
            graphics.fill(path);
        }
    }

    private void strokePolygonEdges_EditSections(Graphics2D graphics) {
        Path2D path;
        graphics.setStroke(UI.GRID_DRAWINGSTROKE);
        graphics.setPaint(UI.EDITJIG_EDITSECTIONS_UNFOCUSSTROKECOLOR);
        for (ProjectJigSection m : GE.ge.editor_jig.editedjig.sections) {
            if (m == GE.ge.editor_jig.focussection) continue;
            path = GE.ge.editor_jig.editedjig.getJigEditorGeometryCache().getPath(m.getPolygon());
            graphics.draw(path);
        }
        graphics.setPaint(UI.EDITJIG_EDITSECTIONS_FOCUSSTROKECOLOR);
        path = GE.ge.editor_jig.editedjig.getJigEditorGeometryCache().getPath(GE.ge.editor_jig.focussection.getPolygon());
        graphics.draw(path);
    }

    private void renderGlyphs_EditSections(Graphics2D graphics) {
        ArrayList<DPolygon> nonfocussections = new ArrayList<DPolygon>();
        for (ProjectJigSection section : GE.ge.editor_jig.editedjig.sections) {
            if (section == GE.ge.editor_jig.focussection) continue;
            nonfocussections.add(GE.ge.editor_jig.editedjig.getJigEditorGeometryCache().getDPolygon(section.getPolygon()));
        }
        DPolygon focussection = GE.ge.editor_jig.editedjig.getJigEditorGeometryCache().getDPolygon(GE.ge.editor_jig.focussection.getPolygon());
        for (DPolygon nonfocussection : nonfocussections) {
            this.renderGlyphs(graphics, nonfocussection, UI.EDITJIG_EDITSECTIONS_UNFOCUSSTROKECOLOR);
        }
        this.renderGlyphs(graphics, focussection, UI.EDITJIG_EDITSECTIONS_FOCUSSTROKECOLOR);
    }

    private void renderGlyphs(Graphics2D graphics, DPolygon polygon, Color color) {
        GlyphSystemModel glyphsystemmodel = new GlyphSystemModel(polygon, 12.0);
        if (glyphsystemmodel.isValid()) {
            this.renderV0Dot(graphics, glyphsystemmodel, color);
            graphics.setStroke(UI.GRID_DRAWINGSTROKE);
            graphics.setPaint(color);
            Path2D path = this.getPath2D(glyphsystemmodel.glyphpath);
            graphics.draw(path);
            this.renderArrowHead(graphics, glyphsystemmodel, color);
        }
    }

    private void renderV0Dot(Graphics2D graphics, GlyphSystemModel glyphsystemmodel, Color color) {
        double dotradius = 7.199999999999999;
        DPoint pv0 = glyphsystemmodel.getV0DotPoint();
        graphics.setPaint(color);
        Ellipse2D.Double dot = new Ellipse2D.Double(pv0.x - dotradius, pv0.y - dotradius, dotradius * 2.0, dotradius * 2.0);
        graphics.fill(dot);
    }

    private void renderArrowHead(Graphics2D graphics, GlyphSystemModel glyphsystemmodel, Color color) {
        graphics.setPaint(color);
        DPoint p0 = glyphsystemmodel.glyphpath.get(glyphsystemmodel.glyphpath.size() - 2);
        DPoint p1 = glyphsystemmodel.glyphpath.get(glyphsystemmodel.glyphpath.size() - 1);
        double forward = p0.getDirection(p1);
        DPoint forewardpoint = p1.getPoint(forward, 30.0);
        DPoint leftpoint = p1.getPoint(GD.normalizeDirection(forward - 1.5707963267948966), 7.199999999999999);
        DPoint rightpoint = p1.getPoint(GD.normalizeDirection(forward + 1.5707963267948966), 7.199999999999999);
        Path2D.Double triangle = new Path2D.Double();
        ((Path2D)triangle).moveTo(leftpoint.x, leftpoint.y);
        ((Path2D)triangle).lineTo(forewardpoint.x, forewardpoint.y);
        ((Path2D)triangle).lineTo(rightpoint.x, rightpoint.y);
        triangle.closePath();
        graphics.fill(triangle);
    }

    private Path2D getPath2D(List<DPoint> points) {
        Path2D.Double path2d = new Path2D.Double();
        DPoint p = points.get(0);
        ((Path2D)path2d).moveTo(p.x, p.y);
        int s = points.size();
        for (int i = 1; i < s; ++i) {
            p = points.get(i);
            ((Path2D)path2d).lineTo(p.x, p.y);
        }
        return path2d;
    }
}

