/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.util.grid;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import org.fleen.forsythia.app.grammarEditor.util.grid.Grid;

public class CursorCircle {
    private static final String NAME = "gridroundcursor";
    private static final double CURSOR_INNER_RADIUS = 0.15;
    private static final double CURSOR_OUTER_RADIUS = 0.48;
    private static BufferedImage image = null;

    static {
        image = new BufferedImage(32, 32, 2);
        int rgb = new Color(255, 255, 255, 255).getRGB();
        int ri = 4;
        int ro = 15;
        for (int x = 0; x < 32; ++x) {
            for (int y = 0; y < 32; ++y) {
                int dx = x - 16;
                int dy = y - 16;
                int d = (int)Math.sqrt(dx * dx + dy * dy);
                if (d <= ri || d >= ro || (x + y) % 2 != 0) continue;
                image.setRGB(x, y, rgb);
            }
        }
    }

    static void setCursor(Grid grid) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension cdim = tk.getBestCursorSize(32, 32);
        Point hotspot = new Point(cdim.width / 2, cdim.height / 2);
        Cursor c = tk.createCustomCursor(image, hotspot, NAME);
        grid.setCursor(c);
    }
}

