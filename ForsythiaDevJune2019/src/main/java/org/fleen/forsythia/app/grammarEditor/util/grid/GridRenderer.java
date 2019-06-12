/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.util.grid;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.fleen.forsythia.app.grammarEditor.util.UI;
import org.fleen.geom_2D.GD;

public class GridRenderer
implements Serializable {
    private static final long serialVersionUID = 2408218887813661894L;
    BufferedImage tileimage = null;

    public void render(Graphics2D graphics, double viewwidth, double viewheight, double viewscale, double viewoffsetx, double viewoffsety) {
        int tilewidth = (int)(viewscale * 2.0 * Math.sqrt(3.0)) + 1;
        int tileheight = (int)(viewscale * 6.0);
        double littleoffsetx = viewwidth / 2.0 % (double)tilewidth;
        double littleoffsety = viewheight / 2.0 % (double)tileheight;
        double scaledviewoffsetx = - viewoffsetx * viewscale % (double)tilewidth;
        double scaledviewoffsety = viewoffsety * viewscale % (double)tileheight;
        AffineTransform oldtransform = graphics.getTransform();
        graphics.transform(AffineTransform.getTranslateInstance(scaledviewoffsetx + littleoffsetx, scaledviewoffsety + littleoffsety));
        BufferedImage itile = this.getTileImage(tilewidth, tileheight, viewscale);
        int tilexcount = (int)(viewwidth / (double)tilewidth) + 5;
        int tileycount = (int)(viewheight / (double)tileheight) + 5;
        for (int x = 0; x < tilexcount; ++x) {
            for (int y = 0; y < tileycount; ++y) {
                graphics.drawImage(itile, null, x * tilewidth - tilewidth * 2, y * tileheight - tileheight * 2);
            }
        }
        graphics.setTransform(oldtransform);
    }

    private BufferedImage getTileImage(int tilewidth, int tileheight, double viewscale) {
        if (this.tileimage == null) {
            this.tileimage = this.createTileImage(tilewidth, tileheight, viewscale);
        }
        return this.tileimage;
    }

    public final void invalidateTileImage() {
        this.tileimage = null;
    }

    private BufferedImage createTileImage(int tilewidth, int tileheight, double viewscale) {
        BufferedImage tile = new BufferedImage(tilewidth, tileheight, 1);
        double[] p0 = new double[]{0.0, 0.0};
        double[] p2 = new double[]{tilewidth, 0.0};
        double[] p16 = new double[]{0.0, tileheight};
        double[] p18 = new double[]{tilewidth, tileheight};
        double[] p1 = GD.getPoint_Mid2Points(p0[0], p0[1], p2[0], p2[1]);
        double[] p8 = GD.getPoint_Mid2Points(p0[0], p0[1], p16[0], p16[1]);
        double[] p10 = GD.getPoint_Mid2Points(p2[0], p2[1], p18[0], p18[1]);
        double[] p17 = GD.getPoint_Mid2Points(p16[0], p16[1], p18[0], p18[1]);
        double[] p3 = GD.getPoint_Between2Points(p0[0], p0[1], p8[0], p8[1], 0.6666666666666666);
        double[] p7 = GD.getPoint_Between2Points(p2[0], p2[1], p10[0], p10[1], 0.6666666666666666);
        double[] p11 = GD.getPoint_Between2Points(p8[0], p8[1], p16[0], p16[1], 0.3333333333333333);
        double[] p15 = GD.getPoint_Between2Points(p10[0], p10[1], p18[0], p18[1], 0.3333333333333333);
        Path2D.Double path = new Path2D.Double();
        ((Path2D)path).moveTo(p0[0], p0[1]);
        ((Path2D)path).lineTo(p16[0], p16[1]);
        ((Path2D)path).lineTo(p18[0], p18[1]);
        ((Path2D)path).lineTo(p2[0], p2[1]);
        ((Path2D)path).lineTo(p0[0], p0[1]);
        ((Path2D)path).lineTo(p18[0], p18[1]);
        ((Path2D)path).moveTo(p1[0], p1[1]);
        ((Path2D)path).lineTo(p17[0], p17[1]);
        ((Path2D)path).moveTo(p2[0], p2[1]);
        ((Path2D)path).lineTo(p16[0], p16[1]);
        ((Path2D)path).moveTo(p7[0], p7[1]);
        ((Path2D)path).lineTo(p11[0], p11[1]);
        ((Path2D)path).moveTo(p10[0], p10[1]);
        ((Path2D)path).lineTo(p8[0], p8[1]);
        ((Path2D)path).moveTo(p15[0], p15[1]);
        ((Path2D)path).lineTo(p3[0], p3[1]);
        ((Path2D)path).moveTo(p0[0], p0[1]);
        ((Path2D)path).lineTo(p7[0], p7[1]);
        ((Path2D)path).moveTo(p2[0], p2[1]);
        ((Path2D)path).lineTo(p3[0], p3[1]);
        ((Path2D)path).moveTo(p11[0], p11[1]);
        ((Path2D)path).lineTo(p18[0], p18[1]);
        ((Path2D)path).moveTo(p15[0], p15[1]);
        ((Path2D)path).lineTo(p16[0], p16[1]);
        Graphics2D g = tile.createGraphics();
        g.setPaint(UI.GRID_KGRIDBACKGROUNDCOLOR);
        g.fillRect(0, 0, tilewidth, tileheight);
        g.setRenderingHints(UI.RENDERING_HINTS);
        g.setStroke(new BasicStroke(3.0f));
        g.setPaint(UI.GRID_KGRIDLINECOLOR);
        g.draw(path);
        return tile;
    }
}

