/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.editor_Metagon.overlayPainter;

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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.fleen.forsythia.app.grammarEditor.GE;
import org.fleen.forsythia.app.grammarEditor.editor_Metagon.Editor_Metagon;
import org.fleen.forsythia.app.grammarEditor.project.metagon.MetagonEditorGeometryCache;
import org.fleen.forsythia.app.grammarEditor.project.metagon.ProjectMetagon;
import org.fleen.forsythia.app.grammarEditor.util.UI;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.graph.DisconnectedGraph;
import org.fleen.geom_Kisrhombille.graph.GEdge;
import org.fleen.geom_Kisrhombille.graph.GVertex;
import org.fleen.geom_Kisrhombille.graph.Graph;

public class EMGridOverlayPainter
implements Serializable {
    private static final long serialVersionUID = -3499620952397298974L;

    public void paint(Graphics2D graphics, int w, int h, double scale, double centerx, double centery) {
        graphics.setRenderingHints(UI.RENDERING_HINTS);
        GE.ge.editor_metagon.editedmetagon.getMetagonEditorGeometryCache().update(w, h, scale, centerx, centery);
        this.renderGraph(graphics);
    }

    private void renderGraph(Graphics2D graphics) {
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
        for (KPolygon m : GE.ge.editor_metagon.editedmetagon.getGraph().getDisconnectedGraph().getUndividedPolygons()) {
            Color color = UI.EDITJIG_EDITGEOMETRY_HOSTMETAGONFILLCOLOR;
            Path2D path = GE.ge.editor_metagon.editedmetagon.getMetagonEditorGeometryCache().getPath(m);
            graphics.setPaint(color);
            graphics.fill(path);
        }
    }

    private void strokeGraphEdges_EditGeometry(Graphics2D graphics) {
        graphics.setStroke(UI.GRID_DRAWINGSTROKE);
        graphics.setPaint(UI.EDITJIG_EDITGEOMETRY_STROKECOLOR);
        Iterator<GEdge> i = GE.ge.editor_metagon.editedmetagon.getGraph().edges.iterator();
        Path2D.Double path = new Path2D.Double();
        while (i.hasNext()) {
            GEdge e = i.next();
            double[] p0 = GE.ge.editor_metagon.editedmetagon.getMetagonEditorGeometryCache().getPoint(e.v0.kvertex);
            double[] p1 = GE.ge.editor_metagon.editedmetagon.getMetagonEditorGeometryCache().getPoint(e.v1.kvertex);
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
        for (GVertex v : GE.ge.editor_metagon.editedmetagon.getGraph().vertices) {
            double[] p = GE.ge.editor_metagon.editedmetagon.getMetagonEditorGeometryCache().getPoint(v.kvertex);
            ((RectangularShape)dot).setFrame(p[0] - (double)(span / 2.0f), p[1] - (double)(span / 2.0f), span, span);
            graphics.fill(dot);
        }
    }

    private void renderHeadDecorations(Graphics2D graphics) {
        graphics.setStroke(UI.EDITJIG_EDITGEOMETRY_HEADVERTEXDECORATIONSTROKE);
        int span = 32;
        if (GE.ge.editor_metagon.connectedhead != null) {
            double[] p = GE.ge.editor_metagon.editedmetagon.getMetagonEditorGeometryCache().getPoint(GE.ge.editor_metagon.connectedhead);
            graphics.setPaint(UI.EDITJIG_EDITGEOMETRY_CONNECTEDHEADVERTEXDECORATIONCOLOR);
            graphics.drawOval((int)p[0] - span / 2, (int)p[1] - span / 2, span, span);
        } else if (GE.ge.editor_metagon.unconnectedhead != null) {
            double[] p = GE.ge.editor_metagon.editedmetagon.getMetagonEditorGeometryCache().getPoint(GE.ge.editor_metagon.unconnectedhead);
            graphics.setPaint(UI.EDITJIG_EDITGEOMETRY_UNCONNECTEDHEADVERTEXDECORATIONCOLOR);
            graphics.drawOval((int)p[0] - span / 2, (int)p[1] - span / 2, span, span);
        }
    }
}

