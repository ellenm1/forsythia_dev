/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.editor_Generator;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.PrintStream;
import java.io.Serializable;
import javax.swing.JPanel;
import org.fleen.forsythia.app.grammarEditor.GE;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.Composer;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.Editor_Generator;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.Renderer;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.ui.PanViewer;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.ui.UI_Generator;
import org.fleen.forsythia.app.grammarEditor.project.ProjectGrammar;
import org.fleen.forsythia.core.composition.FPolygon;
import org.fleen.forsythia.core.composition.ForsythiaComposition;
import org.fleen.forsythia.core.grammar.ForsythiaGrammar;
import org.fleen.geom_2D.DPolygon;

public class Generator
implements Serializable {
    private static final long serialVersionUID = -4830057832022895569L;
    private static final boolean STOPGO_STOP = false;
    private static final boolean STOPGO_GO = true;
    private boolean stopgo = false;
    private static final boolean MODE_INTERMITTANT = false;
    private static final boolean MODE_CONTINUOUS = true;
    private boolean mode = false;
    private static final long INTERVAL_MAX = 999999L;
    private static final long INTERVAL_MIN = 100L;
    private static final long INTERVAL_DEFAULT = 1000L;
    private long interval = 1000L;
    private static final double DETAILFLOOR_DEFAULT = 0.05;
    private static final double DETAILFLOOR_MIN = 0.006;
    private static final double DETAILFLOOR_MAX = 0.8;
    private double detailfloor = 0.05;
    private static final long CONTROLCHECKINTERVAL = 200L;
    private boolean runcontrolthread;
    private boolean requestgeneratecomposition = false;
    private long compositiongenerationtime = -1L;
    private transient Composer composer = null;
    private transient Renderer renderer = null;
    public transient ForsythiaComposition composition = null;
    public transient BufferedImage viewerimage = null;

    void toggleStopGo() {
        if (this.isStop()) {
            this.go();
        } else {
            this.stop();
        }
    }

    public void stop() {
        this.stopgo = false;
    }

    public void go() {
        this.stopgo = true;
        this.requestgeneratecomposition = true;
    }

    boolean isStop() {
        return !this.stopgo;
    }

    boolean isGo() {
        return this.stopgo;
    }

    void toggleMode() {
        this.mode = !this.mode;
    }

    void setModeIntermittant() {
        this.mode = false;
    }

    void setModeContinuous() {
        this.mode = true;
    }

    boolean isIntermittant() {
        return !this.mode;
    }

    boolean isContinuous() {
        return this.mode;
    }

    void setInterval(long i) {
        if (i < 100L) {
            i = 100L;
        }
        if (i > 999999L) {
            i = 999999L;
        }
        this.interval = i;
    }

    long getInterval() {
        return this.interval;
    }

    void setDetailFloor(double f) {
        if (f < 0.006) {
            f = 0.006;
        }
        if (f > 0.8) {
            f = 0.8;
        }
        this.detailfloor = f;
    }

    double getDetailFloor() {
        return this.detailfloor;
    }

    public void startControlThread() {
        this.runcontrolthread = true;
        new Thread(){

            @Override
            public void run() {
                while (Generator.this.runcontrolthread) {
                    if (Generator.this.isGo()) {
                        if (Generator.this.isIntermittant()) {
                            if (Generator.this.requestgeneratecomposition) {
                                Generator.access$2(Generator.this, false);
                                Generator.this.generateComposition();
                                Generator.this.renderCompositionForViewer();
                                Generator.access$5(Generator.this, false);
                                GE.ge.editor_generator.refreshUI();
                            }
                        } else if (Generator.this.timeToGenerateAnotherCompositionForContinuous()) {
                            Generator.access$7(Generator.this, System.currentTimeMillis());
                            Generator.this.generateComposition();
                            Generator.this.renderCompositionForViewer();
                            GE.ge.editor_generator.refreshInfo();
                            GE.ge.editor_generator.refreshViewer();
                        }
                    }
                    try {
                        Thread.sleep(200L, 0);
                    }
                    catch (Exception x) {
                        x.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void stopControlThread() {
        this.runcontrolthread = false;
    }

    private boolean timeToGenerateAnotherCompositionForContinuous() {
        long t = System.currentTimeMillis();
        return t - this.compositiongenerationtime > this.interval;
    }

    public Composer getComposer() {
        if (this.composer == null) {
            this.composer = new Composer();
        }
        return this.composer;
    }

    private void resetComposer() {
        this.composer = new Composer();
    }

    public Renderer getRenderer() {
        if (this.renderer == null) {
            this.renderer = new Renderer();
        }
        return this.renderer;
    }

    private void resetRenderer(String ps) {
    	 System.out.println("In Generator>resetRenderer185 ps="+ps);
        this.renderer = new Renderer();
        this.renderer.selectedPalette = ps;
        System.out.println("Generator.resetRenderer() 188 this.renderer.selectedPalette= " +this.renderer.selectedPalette);
    }

    private void generateComposition() {
        System.out.println("generate composition");
        this.resetComposer();
        this.composition = this.getComposer().compose(GE.ge.focusgrammar.getForsythiaGrammar(), this.detailfloor);
    }

    private void renderCompositionForViewer() {
        System.out.println("In Generator.renderCompositionsForViewer() line 198");
        PanViewer viewer = ((UI_Generator)GE.ge.editor_generator.getUI()).panviewer;
        String palettestr = ((UI_Generator)GE.ge.editor_generator.getUI()).selectedpalette;
        System.out.println("In Generator.renderCompositionsForViewer() line 201 palettestr ="+palettestr);
        this.resetRenderer(palettestr);
        
        this.viewerimage = this.getRenderer().getImage(viewer.getWidth(), viewer.getHeight(), this.composition);
    }

    public BufferedImage renderCompositionForImageExport(int imagesize) {
        int iwidth;
        double cheight;
        int iheight;
        Rectangle2D.Double bounds = this.composition.getRootPolygon().getDPolygon().getBounds();
        double cwidth = bounds.getWidth();
        if (cwidth > (cheight = bounds.getHeight())) {
            iwidth = imagesize;
            iheight = (int)(cheight / cwidth * (double)imagesize);
        } else {
            iheight = imagesize;
            iwidth = (int)(cwidth / cheight * (double)imagesize);
        }
        return this.getRenderer().getImage(iwidth, iheight, this.composition);
    }

    static /* synthetic */ void access$2(Generator generator, boolean bl) {
        generator.requestgeneratecomposition = bl;
    }

    static /* synthetic */ void access$5(Generator generator, boolean bl) {
        generator.stopgo = bl;
    }

    static /* synthetic */ void access$7(Generator generator, long l) {
        generator.compositiongenerationtime = l;
    }

}

