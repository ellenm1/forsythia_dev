/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.util.grid;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.URL;
import javax.imageio.ImageIO;
import org.fleen.forsythia.app.grammarEditor.util.grid.Grid;

public class CursorX {
    private static final String NAME = "gridxcursor";
    private static BufferedImage image = null;
    private static final int WHITE = -1;
    private static final int TRANSPARENT = new Color(0, 0, 0, 0).getRGB();

    static {
        try {
            image = ImageIO.read(CursorX.class.getResource("cursorx.png"));
        }
        catch (Exception x) {
            x.printStackTrace();
        }
        BufferedImage image2 = new BufferedImage(32, 32, 2);
        image2.createGraphics().drawImage(image, null, null);
        image = image2;
        for (int x = 0; x < 32; ++x) {
            for (int y = 0; y < 32; ++y) {
                int rgb = image.getRGB(x, y);
                if (rgb == -1) {
                    image.setRGB(x, y, TRANSPARENT);
                    continue;
                }
                if ((x + y) % 2 == 0) {
                    image.setRGB(x, y, TRANSPARENT);
                    continue;
                }
                image.setRGB(x, y, -1);
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

