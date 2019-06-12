/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.editor_Metagon.ui;

import java.awt.Graphics2D;
import java.util.List;
import org.fleen.forsythia.app.grammarEditor.GE;
import org.fleen.forsythia.app.grammarEditor.editor_Metagon.Editor_Metagon;
import org.fleen.forsythia.app.grammarEditor.editor_Metagon.overlayPainter.EMGridOverlayPainter;
import org.fleen.forsythia.app.grammarEditor.project.metagon.ProjectMetagon;
import org.fleen.forsythia.app.grammarEditor.util.grid.Grid;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.graph.DisconnectedGraph;
import org.fleen.geom_Kisrhombille.graph.Graph;

public class EditMetagonGrid
extends Grid {
    private static final long serialVersionUID = 5572475027375310727L;
    EMGridOverlayPainter overlaypainter = new EMGridOverlayPainter();
    static final int MOUSEMOVE_VERTEXNEAR = 0;
    static final int MOUSEMOVE_VERTEXFAR = 1;
    int mousemove;

    @Override
    protected void paintOverlay(Graphics2D g, int w, int h, double scale, double centerx, double centery) {
        try {
            this.overlaypainter.paint(g, w, h, scale, centerx, centery);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Override
    protected KPolygon getHostPolygon() {
        List<KPolygon> p = GE.ge.editor_metagon.editedmetagon.getGraph().getDisconnectedGraph().getPolygons();
        if (p.isEmpty()) {
            return null;
        }
        return p.get(0);
    }

    @Override
    protected void mouseMovedCloseToVertex(KPoint v) {
        this.mousemove = 0;
        if (GE.ge.editor_metagon.mode == 0) {
            if (GE.ge.editor_metagon.connectedhead != null && v != null && !v.isColinear(GE.ge.editor_metagon.connectedhead)) {
                this.setCursorX();
            } else {
                this.setCursorCircle();
            }
        } else {
            this.setCursorX();
        }
    }

    @Override
    protected void mouseMovedFarFromVertex(double[] p) {
        this.mousemove = 1;
        this.setCursorX();
    }

    @Override
    protected void mouseTouched(double[] p, KPoint v) {
        boolean valid = true;
        if (GE.ge.editor_metagon.connectedhead != null && v != null && !v.isColinear(GE.ge.editor_metagon.connectedhead)) {
            valid = false;
        }
        if (valid) {
            GE.ge.editor_metagon.touchVertex(v);
        }
    }
}

