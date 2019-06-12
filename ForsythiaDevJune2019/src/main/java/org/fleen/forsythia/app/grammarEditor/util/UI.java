/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Path2D;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;

public class UI
implements Serializable {
    private static final long serialVersionUID = 8554677034789624933L;
    public static final HashMap<RenderingHints.Key, Object> RENDERING_HINTS = new HashMap();
    public static final double VERTEX_CLOSENESS_MARGIN = 22.0;
    public static final Color BUTTON_RED;
    public static final Color BUTTON_ORANGE;
    public static final Color BUTTON_YELLOW;
    public static final Color BUTTON_GREEN;
    public static final Color BUTTON_BLUE;
    public static final Color BUTTON_PURPLE;
    public static final int[] RAINBOW;
    public static final int ELEMENTMENU_OVERVIEWMETAGONSROWS = 3;
    public static final int ELEMENTMENU_OVERVIEWJIGSROWS = 1;
    public static final int ELEMENTMENU_ARRAYEDGEMARGIN = 8;
    public static final int ELEMENTMENU_ICONMARGIN = 6;
    public static final int ELEMENTMENU_ICONGEOMETRYINSET = 8;
    public static final int ELEMENTMENU_ICON_OUTLINEWIDTH = 6;
    public static final float ELEMENTMENU_ICONPATHSTROKETHICKNESS = 3.0f;
    public static final Color ELEMENTMENU_BACKGROUND;
    public static final Color ELEMENTMENU_ICONBACKGROUND;
    public static final Color ELEMENTMENU_ICON_FILL;
    public static final Color ELEMENTMENU_METAGONWITHOUTJIGS_ICON_FILL;
    public static final Color ELEMENTMENU_METAGONWITHJIGS_ICON_FILL;
    public static final Color ELEMENTMENU_ICON_STROKE;
    public static final Color ELEMENTMENU_ICON_OUTLINEDEFAULT;
    public static final Color ELEMENTMENU_ICON_OUTLINEFOCUS;
    public static final Color[] GRAMMAR_EDITOR_METAGON_ICONS_FILLCOLOR;
    public static final Color GRID_KGRIDBACKGROUNDCOLOR;
    public static final Color GRID_KGRIDLINECOLOR;
    public static final Color GRID_DRAWINGSTROKECOLOR;
    public static final float GRID_KGRIDLINETHICKNESS = 1.0f;
    public static final int GRID_CURSORCIRCLESIZE = 32;
    public static final int GRID_CURSORSQUARESIZE = 28;
    public static final int GRID_CURSORXSIZE = 32;
    public static final int GRID_CENTERANDFITVIEWMARGIN = 36;
    public static final float GRID_DRAWINGSTROKETHICKNESS = 4.0f;
    public static final Stroke GRID_DRAWINGSTROKE;
    public static final int GRID_DEFAULTVERTEXSPAN = 12;
    public static final Color EDITORCREATEMETAGON_FINISHEDMETAGONFILLCOLOR;
    public static final Color EDITJIG_EDITGEOMETRY_STROKECOLOR;
    public static final Color EDITJIG_EDITGEOMETRY_HOSTMETAGONFILLCOLOR;
    public static final Color EDITJIG_EDITGEOMETRY_CONNECTEDHEADVERTEXDECORATIONCOLOR;
    public static final Color EDITJIG_EDITGEOMETRY_UNCONNECTEDHEADVERTEXDECORATIONCOLOR;
    public static final int EDITJIG_EDITGEOMETRY_HEADVERTEXDECORATIONSPAN = 32;
    public static final float EDITJIG_EDITGEOMETRY_HEADVERTEXDECORATIONSTROKETHICKNESS = 8.0f;
    public static final Stroke EDITJIG_EDITGEOMETRY_HEADVERTEXDECORATIONSTROKE;
    public static final Color EDITJIG_EDITSECTIONS_UNFOCUSSTROKECOLOR;
    public static final Color EDITJIG_EDITSECTIONS_FOCUSSTROKECOLOR;
    private static final int EDITJIG_EDITSECTIONS_SECTIONFILLALPHA = 130;
    private static final float EDITJIG_EDITSECTIONS_SECTIONFILLBRIGHTNESS = 1.0f;
    private static final float EDITJIG_EDITSECTIONS_SECTIONFILLSATURATION = 0.5f;
    public static Color[] EDITJIG_EDITSECTIONS_SECTIONFILL;
    public static final double EDITJIG_EDITSECTIONS_GLYPHINSET = 12.0;
    public static final double EDITJIG_EDITSECTIONS_GLYPHV0DOTRADIUS = 0.6;
    public static final double EDITJIG_EDITSECTIONS_GLYPHARROWLENGTH = 2.5;
    public static final double EDITJIG_EDITSECTIONS_GLYPHARROWWIDTH = 1.2;
    public static final Font POPUPMENUFONT;

    static {
        RENDERING_HINTS.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        RENDERING_HINTS.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        RENDERING_HINTS.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DEFAULT);
        RENDERING_HINTS.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        RENDERING_HINTS.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        RENDERING_HINTS.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        RENDERING_HINTS.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        BUTTON_RED = new Color(255, 128, 128);
        BUTTON_ORANGE = new Color(255, 178, 0);
        BUTTON_YELLOW = new Color(255, 255, 0);
        BUTTON_GREEN = new Color(0, 255, 0);
        BUTTON_BLUE = new Color(144, 144, 255);
        BUTTON_PURPLE = new Color(255, 0, 255);
        RAINBOW = new int[]{new Color(255, 0, 0).getRGB(), new Color(255, 128, 0).getRGB(), new Color(255, 210, 1).getRGB(), new Color(255, 255, 0).getRGB(), new Color(128, 255, 0).getRGB(), new Color(0, 255, 255).getRGB(), new Color(0, 128, 255).getRGB(), new Color(57, 57, 255).getRGB(), new Color(171, 89, 255).getRGB(), new Color(255, 0, 255).getRGB()};
        ELEMENTMENU_BACKGROUND = new Color(220, 220, 220);
        ELEMENTMENU_ICONBACKGROUND = new Color(220, 220, 220);
        ELEMENTMENU_ICON_FILL = new Color(148, 212, 148);
        ELEMENTMENU_METAGONWITHOUTJIGS_ICON_FILL = new Color(255, 255, 255);
        ELEMENTMENU_METAGONWITHJIGS_ICON_FILL = new Color(255, 128, 255);
        ELEMENTMENU_ICON_STROKE = new Color(110, 110, 110);
        ELEMENTMENU_ICON_OUTLINEDEFAULT = new Color(220, 220, 220);
        ELEMENTMENU_ICON_OUTLINEFOCUS = new Color(240, 240, 240);
        GRAMMAR_EDITOR_METAGON_ICONS_FILLCOLOR = new Color[]{new Color(255, 255, 255), new Color(225, 81, 81), new Color(231, 184, 117), new Color(229, 231, 117), new Color(99, 211, 110), new Color(126, 201, 254), new Color(207, 141, 235)};
        GRID_KGRIDBACKGROUNDCOLOR = new Color(197, 197, 197);
        GRID_KGRIDLINECOLOR = new Color(175, 175, 175);
        GRID_DRAWINGSTROKECOLOR = new Color(32, 32, 32);
        GRID_DRAWINGSTROKE = new BasicStroke(4.0f, 2, 1, 0.0f, null, 0.0f);
        EDITORCREATEMETAGON_FINISHEDMETAGONFILLCOLOR = new Color(255, 255, 255, 64);
        EDITJIG_EDITGEOMETRY_STROKECOLOR = new Color(32, 32, 32);
        EDITJIG_EDITGEOMETRY_HOSTMETAGONFILLCOLOR = new Color(255, 255, 255, 64);
        EDITJIG_EDITGEOMETRY_CONNECTEDHEADVERTEXDECORATIONCOLOR = new Color(255, 255, 64);
        EDITJIG_EDITGEOMETRY_UNCONNECTEDHEADVERTEXDECORATIONCOLOR = new Color(255, 64, 64);
        EDITJIG_EDITGEOMETRY_HEADVERTEXDECORATIONSTROKE = new BasicStroke(8.0f, 2, 1, 0.0f, null, 0.0f);
        EDITJIG_EDITSECTIONS_UNFOCUSSTROKECOLOR = new Color(244, 244, 244);
        EDITJIG_EDITSECTIONS_FOCUSSTROKECOLOR = new Color(48, 48, 48);
        float[] hsbvals = new float[3];
        EDITJIG_EDITSECTIONS_SECTIONFILL = new Color[RAINBOW.length];
        for (int i = 0; i < RAINBOW.length; ++i) {
            Color c = new Color(RAINBOW[i]);
            Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsbvals);
            hsbvals[1] = 0.5f;
            hsbvals[2] = 1.0f;
            c = new Color(Color.HSBtoRGB(hsbvals[0], hsbvals[1], hsbvals[2]));
            UI.EDITJIG_EDITSECTIONS_SECTIONFILL[i] = new Color(c.getRed(), c.getGreen(), c.getBlue(), 130);
        }
        POPUPMENUFONT = new Font("Dialog", 1, 16);
    }

    public static final Path2D.Double getClosedPath(DPolygon points) {
        Path2D.Double path = new Path2D.Double();
        DPoint p = (DPoint)points.get(0);
        path.moveTo(p.x, p.y);
        for (int i = 1; i < points.size(); ++i) {
            p = (DPoint)points.get(i);
            path.lineTo(p.x, p.y);
        }
        path.closePath();
        return path;
    }

    public static final Path2D.Double getClosedPath(List<double[]> points) {
        Path2D.Double path = new Path2D.Double();
        double[] p = points.get(0);
        path.moveTo(p[0], p[1]);
        for (int i = 1; i < points.size(); ++i) {
            p = points.get(i);
            path.lineTo(p[0], p[1]);
        }
        path.closePath();
        return path;
    }

    public static final Path2D.Double getOpenPath(List<double[]> points) {
        Path2D.Double path = new Path2D.Double();
        double[] p = points.get(0);
        path.moveTo(p[0], p[1]);
        for (int i = 1; i < points.size(); ++i) {
            p = points.get(i);
            path.lineTo(p[0], p[1]);
        }
        return path;
    }
}

