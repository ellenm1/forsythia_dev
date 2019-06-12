/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.Serializable;
import java.util.List;
import javax.swing.JPanel;
import org.fleen.forsythia.app.grammarEditor.util.ElementMenuItem;
import org.fleen.forsythia.app.grammarEditor.util.UI;

public abstract class ElementMenu
extends JPanel {
    private static final long serialVersionUID = -1864008707499697749L;
    private static final long MOUSEDRAGSAMPLEPERIOD = 50L;
    private static final int MOUSEDRAG_DX_MIN = 10;
    private int mousesamplex;
    private boolean mousedragging = false;
    private long oldtime = -1L;
    private long newtime;
    private long timediff;
    private static final int SCROLLXOFFSETMAX = 0;
    private int[] iconarraydimensions = null;
    private int scrollxoffsetmin;
    private int scrollxoffset = 0;
    private int rowcount;
    private int iconspan = -1;

    public ElementMenu(int rowcount) {
        this.rowcount = rowcount;
        this.addMouseListener(new ML0());
        this.addMouseMotionListener(new ML0());
    }

    protected abstract void doPopupMenu(int var1, int var2);

    protected abstract List<? extends ElementMenuItem> getItems();

    protected abstract boolean isFocusItem(ElementMenuItem var1);

    protected abstract void setFocusItem(ElementMenuItem var1);

    protected abstract void doDoubleclick(ElementMenuItem var1);

    private ElementMenuItem getItem(int x, int y) {
        this.iconspan = this.getIconSpan();
        int col = (x - this.scrollxoffset - 8) / (this.iconspan + 6);
        int row = (y - 8) / (this.iconspan + 6);
        int iconarrayheight = this.getIconArrayDimensions()[1];
        int index = col * iconarrayheight + row;
        List<? extends ElementMenuItem> items = this.getItems();
        int itemcount = items.size();
        if (index < 0 || index >= itemcount) {
            return null;
        }
        return items.get(index);
    }

    public int[] getIconArrayDimensions() {
        if (this.iconarraydimensions == null) {
            this.calculateIconArrayMetrics();
        }
        return this.iconarraydimensions;
    }

    public void invalidateIconArrayMetrics() {
        this.iconarraydimensions = null;
    }

    private void calculateIconArrayMetrics() {
        int iconcount = this.getItems().size();
        if (iconcount == 0) {
            this.iconarraydimensions = new int[2];
            this.scrollxoffsetmin = 0;
        } else {
            int iconarrayheight = this.rowcount;
            if (iconarrayheight < 1) {
                iconarrayheight = 1;
            }
            int iconarraywidth = iconcount / iconarrayheight;
            if (iconcount % iconarrayheight != 0) {
                ++iconarraywidth;
            }
            if (iconarraywidth < 1) {
                iconarraywidth = 1;
            }
            this.iconarraydimensions = new int[]{iconarraywidth, iconarrayheight};
            this.iconspan = this.getIconSpan();
            this.scrollxoffsetmin = iconarraywidth * this.iconspan;
            this.scrollxoffsetmin += 16;
            this.scrollxoffsetmin += (iconarraywidth - 1) * 6;
            this.scrollxoffsetmin = this.getWidth() - this.scrollxoffsetmin;
        }
    }

    private void modifyScrollXOffset(int delta) {
        this.scrollxoffset += delta;
        if (this.scrollxoffset < this.scrollxoffsetmin) {
            this.scrollxoffset = this.scrollxoffsetmin;
        }
        if (this.scrollxoffset > 0) {
            this.scrollxoffset = 0;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.renderItemIcons((Graphics2D)g);
        this.paintBorder(g);
    }

    private void renderItemIcons(Graphics2D g) {
        g.setPaint(UI.ELEMENTMENU_BACKGROUND);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        List<? extends ElementMenuItem> items = this.getItems();
        if (items == null) {
            return;
        }
        int itemcount = items.size();
        for (int itemindex = 0; itemindex < itemcount; ++itemindex) {
            ElementMenuItem item = items.get(itemindex);
            int[] xy = this.getIconXY(itemcount, itemindex);
            g.drawImage(item.getGrammarEditorIconImage(this.getIconSpan()), xy[0], xy[1], null);
            if (this.isFocusItem(item)) {
                g.setColor(UI.ELEMENTMENU_ICON_OUTLINEFOCUS);
            } else {
                g.setColor(UI.ELEMENTMENU_ICON_OUTLINEDEFAULT);
            }
            int iconspan = this.getIconSpan();
            g.setStroke(new BasicStroke(4.0f));
            g.drawRect(xy[0], xy[1], iconspan, iconspan);
        }
    }

    private int[] getIconXY(int iconcount, int iconindex) {
        int[] ad = this.getIconArrayDimensions();
        int col = iconindex / ad[1];
        int row = iconindex % ad[1];
        int iconspan = this.getIconSpan();
        int x = col * (iconspan + 6) + 8 + this.scrollxoffset;
        int y = row * (iconspan + 6) + 8;
        return new int[]{x, y};
    }

    private int getIconSpan() {
        if (this.iconspan == -1) {
            this.calculateIconSpan();
        }
        return this.iconspan;
    }

    private void calculateIconSpan() {
        int h = this.getHeight();
        this.iconspan = (h - 16 - (this.rowcount - 1) * 6) / this.rowcount;
    }

    public void invalidateIconSpan() {
    }

    static /* synthetic */ void access$0(ElementMenu elementMenu, int n) {
        elementMenu.mousesamplex = n;
    }

    static /* synthetic */ void access$3(ElementMenu elementMenu, boolean bl) {
        elementMenu.mousedragging = bl;
    }

    static /* synthetic */ void access$4(ElementMenu elementMenu, long l) {
        elementMenu.oldtime = l;
    }

    static /* synthetic */ void access$6(ElementMenu elementMenu, long l) {
        elementMenu.newtime = l;
    }

    static /* synthetic */ void access$8(ElementMenu elementMenu, long l) {
        elementMenu.timediff = l;
    }

    private class ML0
    extends MouseAdapter
    implements Serializable {
        private static final long serialVersionUID = 2524868473914370627L;

        private ML0() {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            ElementMenu.access$0(ElementMenu.this, e.getX());
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                e.consume();
                ElementMenuItem i = ElementMenu.this.getItem(e.getX(), e.getY());
                ElementMenu.this.doDoubleclick(i);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int button = e.getButton();
            int x = e.getX();
            int y = e.getY();
            if (button == 1 && !ElementMenu.this.mousedragging) {
                ElementMenuItem i = ElementMenu.this.getItem(x, y);
                if (i != null) {
                    ElementMenu.this.setFocusItem(i);
                    ElementMenu.this.repaint();
                }
            } else if (button == 3) {
                ElementMenu.this.doPopupMenu(x, y);
            }
            ElementMenu.access$3(ElementMenu.this, false);
            ElementMenu.access$4(ElementMenu.this, -1L);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (ElementMenu.this.oldtime == -1L) {
                ElementMenu.access$4(ElementMenu.this, System.currentTimeMillis());
                return;
            }
            ElementMenu.access$6(ElementMenu.this, System.currentTimeMillis());
            ElementMenu.access$8(ElementMenu.this, ElementMenu.this.newtime - ElementMenu.this.oldtime);
            if (ElementMenu.this.timediff > 50L) {
                ElementMenu.access$4(ElementMenu.this, ElementMenu.this.newtime);
                int x = e.getX();
                int dx = x - ElementMenu.this.mousesamplex;
                if (Math.abs(dx) > 10) {
                    ElementMenu.access$3(ElementMenu.this, true);
                    ElementMenu.this.modifyScrollXOffset(dx);
                    ElementMenu.access$0(ElementMenu.this, x);
                    ElementMenu.this.repaint();
                }
            }
        }
    }

}

