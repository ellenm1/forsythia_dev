/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.editor_Grammar.ui;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.List;
import org.fleen.forsythia.app.grammarEditor.GE;
import org.fleen.forsythia.app.grammarEditor.editor_Jig.Editor_Jig;
import org.fleen.forsythia.app.grammarEditor.editor_Jig.gridOverlayPainter.GridOverlayPainter;
import org.fleen.forsythia.app.grammarEditor.project.jig.JigEditorGeometryCache;
import org.fleen.forsythia.app.grammarEditor.project.jig.ProjectJig;
import org.fleen.forsythia.app.grammarEditor.project.jig.ProjectJigSection;
import org.fleen.forsythia.app.grammarEditor.project.metagon.ProjectMetagon;
import org.fleen.forsythia.app.grammarEditor.util.grid.Grid;
import org.fleen.geom_Kisrhombille.KMetagon;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;

public class GridEditMetagon
extends Grid {
    private static final long serialVersionUID = 5154407826012214745L;
    GridOverlayPainter overlaypainter = new GridOverlayPainter();
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
        int d = 1;
        ProjectJig jig = GE.ge.editor_jig.editedjig;
        if (jig != null) {
            d = jig.griddensity;
        }
        return GE.ge.focusmetagon.kmetagon.getScaledPolygon(d);
    }

    @Override
    protected void mouseMovedCloseToVertex(KPoint v) {
        this.mousemove = 0;
        if (GE.ge.editor_jig.mode == 0) {
            if (GE.ge.editor_jig.connectedhead != null && v != null && !v.isColinear(GE.ge.editor_jig.connectedhead)) {
                this.setCursorX();
            } else {
                this.setCursorCircle();
            }
        } else {
            this.setCursorSquare();
        }
    }

    @Override
    protected void mouseMovedFarFromVertex(double[] p) {
        this.mousemove = 1;
        if (GE.ge.editor_jig.mode == 0) {
            this.setCursorX();
        } else {
            this.setCursorSquare();
        }
    }

    @Override
    protected void mouseTouched(double[] p, KPoint v) {
        if (GE.ge.editor_jig.mode == 0 && this.mousemove == 0) {
            boolean valid = true;
            if (GE.ge.editor_jig.connectedhead != null && v != null && !v.isColinear(GE.ge.editor_jig.connectedhead)) {
                valid = false;
            }
            if (valid) {
                GE.ge.editor_jig.touchVertex(v);
            }
        } else {
            GE.ge.editor_jig.touchSection(this.getSection(p));
        }
    }

    private ProjectJigSection getSection(double[] p) {
        for (ProjectJigSection m : GE.ge.editor_jig.editedjig.sections) {
            Path2D path = GE.ge.editor_jig.editedjig.getJigEditorGeometryCache().getPath(m.getPolygon());
            if (!path.contains(p[0], p[1])) continue;
            return m;
        }
        return null;
    }
}

