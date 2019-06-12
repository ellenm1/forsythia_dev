/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.util;

import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.io.Serializable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.swing.JPanel;

public abstract class Editor
implements Serializable {
    private static final long serialVersionUID = 1108647719698212710L;
    private String name;
    private transient JPanel ui;
    private boolean isopen = false;
    private static final long RESIZE_REFRESH_DELAY = 300L;
    private static final ScheduledExecutorService SCHEDULEDEXECUTOR = Executors.newSingleThreadScheduledExecutor();
    ScheduledFuture scheduledresizerefresh = null;

    public Editor(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public boolean isOpen() {
        return this.isopen;
    }

    public JPanel getUI() {
        if (this.ui == null) {
            this.ui = this.createUI();
            this.ui.addHierarchyBoundsListener(new HBL0());
        }
        return this.ui;
    }

    protected abstract JPanel createUI();

    public void open() {
        this.getUI().setVisible(true);
        this.configureForOpen();
        this.isopen = true;
    }

    public void close() {
        this.getUI().setVisible(false);
        this.configureForClose();
        this.isopen = false;
    }

    protected abstract void configureForOpen();

    protected abstract void configureForClose();

    public abstract void refreshUI();

    private class HBL0
    implements HierarchyBoundsListener,
    Serializable {
        private static final long serialVersionUID = 6250653239681551733L;

        private HBL0() {
        }

        @Override
        public void ancestorMoved(HierarchyEvent e) {
        }

        @Override
        public void ancestorResized(HierarchyEvent e) {
            if (Editor.this.isOpen()) {
                if (Editor.this.scheduledresizerefresh != null) {
                    Editor.this.scheduledresizerefresh.cancel(false);
                }
                Editor.this.scheduledresizerefresh = SCHEDULEDEXECUTOR.schedule(new ScheduleResizeRefresh(), 300L, TimeUnit.MILLISECONDS);
            }
        }
    }

    private class ScheduleResizeRefresh
    extends Thread
    implements Serializable {
        private static final long serialVersionUID = -7548194239693359381L;

        private ScheduleResizeRefresh() {
        }

        @Override
        public void run() {
            Editor.this.refreshUI();
            Editor.this.scheduledresizerefresh = null;
        }
    }

}

