/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.editor_Generator;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.fleen.forsythia.app.grammarEditor.GE;
import org.fleen.forsythia.app.grammarEditor.UIMain;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.Generator;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.ImageExporter;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.ui.PanDetailFloor;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.ui.PanExportImageSize;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.ui.PanInterval;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.ui.PanViewer;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.ui.UI_Generator;
import org.fleen.forsythia.app.grammarEditor.editor_Grammar.Editor_Grammar;
import org.fleen.forsythia.app.grammarEditor.util.Editor;
import org.fleen.forsythia.core.composition.FPolygon;
import org.fleen.forsythia.core.composition.ForsythiaComposition;

public class Editor_Generator
extends Editor {
    private static final long serialVersionUID = 7503985176169949671L;
    public static final String NAME = "GENERATOR";
    private static final long NUMERICTEXTBOXBUTTONREFRESHDELAY = 2000L;
    private static final ScheduledExecutorService SCHEDULEDEXECUTOR = Executors.newSingleThreadScheduledExecutor();
    private transient ScheduledFuture scheduledrefresh = null;
    public Generator generator = new Generator();
    public ImageExporter imageexporter = new ImageExporter();

    public Editor_Generator() {
        super(NAME);
    }

    @Override
    public void configureForOpen() {
        this.generator.startControlThread();
        this.refreshUI();
    }

    @Override
    public void configureForClose() {
        this.generator.stopControlThread();
    }

    @Override
    protected JPanel createUI() {
        return new UI_Generator();
    }

    @Override
    public void refreshUI() {
        this.refreshViewer();
        this.refreshButtons();
    }

    void refreshViewer() {
        UI_Generator ui = (UI_Generator)this.getUI();
        ui.panviewer.repaint();
    }

    private void refreshButtons() {
        this.refreshStopGoButton();
        this.refreshModeButton();
        this.refreshIntervalButton();
        this.refreshDetailFloorButton();
        this.refreshExportDirButton();
        this.refreshExportSizeButton();
        this.refreshInfo();
    }

    private void refreshStopGoButton() {
        UI_Generator ui = (UI_Generator)this.getUI();
        if (this.generator.isStop()) {
            ui.btngeneratestopgo.setText("Go");
        } else {
            ui.btngeneratestopgo.setText("Stop");
        }
    }

    private void refreshModeButton() {
        UI_Generator ui = (UI_Generator)this.getUI();
        if (this.generator.isContinuous()) {
            ui.btngeneratemode.setText("Mode = Continuous");
        } else {
            ui.btngeneratemode.setText("Mode = Intermittant");
        }
    }

    private void refreshIntervalButton() {
        UI_Generator ui = (UI_Generator)this.getUI();
        ui.pangenerateinterval.txtinterval.setText(String.valueOf(this.generator.getInterval()));
    }

    private void refreshDetailFloorButton() {
        UI_Generator ui = (UI_Generator)this.getUI();
        ui.pandetailfloor.txtfloor.setText(String.valueOf(this.generator.getDetailFloor()));
    }

    private void refreshExportDirButton() {
        UI_Generator ui = (UI_Generator)this.getUI();
        ui.btnexportdir.setText("Export Image Dir = " + this.imageexporter.getExportDirectory().getAbsolutePath());
    }

    private void refreshExportSizeButton() {
        UI_Generator ui = (UI_Generator)this.getUI();
        ui.panexportsize.txtsize.setText(String.valueOf(this.imageexporter.getImageSize()));
    }

    public void refreshInfo() {
        UI_Generator ui = (UI_Generator)this.getUI();
        if (this.generator.composition == null) {
            ui.lblinfo.setText("---");
        } else {
            ui.lblinfo.setText("Leaf Polygon Count = " + this.generator.composition.getLeafPolygons().size());
        }
    }

    public void refreshPalettes() {
    	UI_Generator ui = (UI_Generator)this.getUI();
    	ui.paletteMenu  = new JComboBox( UI_Generator.paletteMenuString);
    	System.out.println("In UI_Generator fetchPalettes()");
     }
    
    private void requestNumericTextboxButtonsRefresh() {
        if (this.scheduledrefresh != null) {
            this.scheduledrefresh.cancel(false);
        }
        this.scheduledrefresh = SCHEDULEDEXECUTOR.schedule(new NumericTextboxButtonsRefresh(), 2000L, TimeUnit.MILLISECONDS);
    }

    public void setForFirstInit() {
        //this.generator.setModeContinuous();
    	this.generator.setModeIntermittant();//Ellen Changed
        this.generator.go();
        this.refreshButtons();
    }

    public void toggleStopGo() {
        this.generator.toggleStopGo();
        this.refreshButtons();
    }

    public void toggleMode() {
        this.generator.toggleMode();
        this.refreshButtons();
    }

    public void setInterval(String interval) {
        try {
            long a = Long.valueOf(interval);
            this.generator.setInterval(a);
        }
        catch (Exception a) {
            // empty catch block
        }
        this.requestNumericTextboxButtonsRefresh();
    }

    public void setDetailFloor(String detailfloor) {
        try {
            double a = Double.valueOf(detailfloor);
            this.generator.setDetailFloor(a);
        }
        catch (Exception a) {
            // empty catch block
        }
        this.requestNumericTextboxButtonsRefresh();
    }

    public void exportImage() {
        System.out.println("export image");
        BufferedImage i = this.generator.renderCompositionForImageExport(this.imageexporter.getImageSize());
        this.imageexporter.writePNGImageFile(i);
    }

    public void setExportDir() {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(GE.getLocalDir());
        fc.setDialogTitle("Specify the image export directory.");
        fc.setFileSelectionMode(1);
        fc.setAcceptAllFileFilterUsed(false);
        int r = fc.showOpenDialog(GE.ge.getUIMain());
        if (r == 0) {
            this.imageexporter.setExportDirectory(fc.getSelectedFile());
            this.refreshButtons();
        }
    }

    public void setExportSize(String size) {
        try {
            int a = Integer.valueOf(size);
            this.imageexporter.setImageSize(a);
        }
        catch (Exception a) {
            // empty catch block
        }
        this.requestNumericTextboxButtonsRefresh();
    }

    public void openGrammarEditor() {
        GE.ge.setEditor(GE.ge.editor_grammar);
    }

    public void openAboutPopup() {
        JOptionPane.showMessageDialog(GE.ge.getUIMain(), "Fleen Forsythia Grammar Editor V2017_10_19\n\nCreate shape grammars for use in Forsythia geometry production processes.\nSample compositions based on grammars.\nExport pretty images.\n\nAuthor : John Greene john@fleen.org\nProject (including docs and source for this app) : fleen.org\n\nCopyright 2017 John Greene\n\nThis program is free software: you can redistribute it and/or modify\nit under the terms of the GNU General Public License as published by\nthe Free Software Foundation, either version 3 of the License, or\n(at your option) any later version.\n\nThis program is distributed in the hope that it will be useful,\nbut WITHOUT ANY WARRANTY; without even the implied warranty of\nMERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\nGNU General Public License for more details.\n\nYou should have received a copy of the GNU General Public License\nalong with this program.  If not, see <http://www.gnu.org/licenses/>.", "About this application", -1);
    }

    private class NumericTextboxButtonsRefresh
    extends Thread
    implements Serializable {
        private static final long serialVersionUID = 7803848373595383644L;

        private NumericTextboxButtonsRefresh() {
        }

        @Override
        public void run() {
            UI_Generator ui = (UI_Generator)Editor_Generator.this.getUI();
            ui.pangenerateinterval.txtinterval.setText(String.valueOf(Editor_Generator.this.generator.getInterval()));
            ui.pangenerateinterval.txtinterval.setCaretPosition(ui.pangenerateinterval.txtinterval.getText().length());
            ui.pandetailfloor.txtfloor.setText(String.valueOf(Editor_Generator.this.generator.getDetailFloor()));
            ui.pandetailfloor.txtfloor.setCaretPosition(ui.pandetailfloor.txtfloor.getText().length());
            ui.panexportsize.txtsize.setText(String.valueOf(Editor_Generator.this.imageexporter.getImageSize()));
            ui.panexportsize.txtsize.setCaretPosition(ui.panexportsize.txtsize.getText().length());
        }
    }

}

