/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import javax.swing.JFileChooser;
import org.fleen.forsythia.app.grammarEditor.GE;
import org.fleen.forsythia.app.grammarEditor.UIMain;
import org.fleen.forsythia.app.grammarEditor.project.ProjectGrammar;
import org.fleen.forsythia.app.grammarEditor.project.jig.ProjectJig;
import org.fleen.forsythia.app.grammarEditor.project.metagon.ProjectMetagon;
import org.fleen.forsythia.core.grammar.ForsythiaGrammar;

public class GrammarImportExport
implements Serializable {
    private static final long serialVersionUID = 796695767401225914L;
    private File importexportdir = null;

    private File getImportExportDir() {
        if (this.importexportdir == null || !this.importexportdir.isDirectory()) {
            this.importexportdir = GE.getLocalDir();
        }
        return this.importexportdir;
    }

    public void importGrammar() {
        File path = this.getGrammarFile();
        this.importGrammar(path);
    }

    public void importGrammar(File path) {
        System.out.println("importing grammar from file:" + path);
        if (path == null) {
            return;
        }
        ForsythiaGrammar fg = null;
        try {
            fg = this.extractForsythiaGrammarFromFile(path);
        }
        catch (Exception x) {
            x.printStackTrace();
        }
        GE.ge.focusgrammar = new ProjectGrammar(fg, path.getName());
        this.initFocusElementsForNewGrammar();
    }

    private void initFocusElementsForNewGrammar() {
        if (GE.ge.focusgrammar.hasMetagons()) {
            GE.ge.focusmetagon = GE.ge.focusgrammar.getMetagon(0);
            if (GE.ge.focusmetagon != null) {
                GE.ge.focusjig = GE.ge.focusmetagon.getJig(0);
            }
        } else {
            GE.ge.focusmetagon = null;
            GE.ge.focusjig = null;
        }
    }

    private File getGrammarFile() {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(this.getImportExportDir());
        int r = fc.showOpenDialog(GE.ge.getUIMain());
        if (r != 0) {
            return null;
        }
        File selectedfile = fc.getSelectedFile();
        this.importexportdir = fc.getCurrentDirectory();
        return selectedfile;
    }

    private ForsythiaGrammar extractForsythiaGrammarFromFile(File file) {
        ForsythiaGrammar fg = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            fg = (ForsythiaGrammar)ois.readObject();
            ois.close();
        }
        catch (Exception e) {
            System.out.println("#^#^# EXCEPTION IN EXTRACT GRAMMAR FROM FILE FOR IMPORT #^#^#");
            e.printStackTrace();
            return null;
        }
        return fg;
    }

    public void exportGrammar() {
        this.exportGrammar(GE.ge.focusgrammar);
    }

    public void exportGrammar(ProjectGrammar grammar) {
        File path = this.getExportFile();
        if (path == null) {
            return;
        }
        this.exportGrammar(grammar, path);
    }

    public void exportGrammar(ProjectGrammar grammar, File path) {
        ForsythiaGrammar g = grammar.getForsythiaGrammar();
        if (g == null) {
            return;
        }
        this.writeExportFile(g, path);
    }

    private File getExportFile() {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(this.getImportExportDir());
        if (fc.showSaveDialog(GE.ge.getUIMain()) != 0) {
            return null;
        }
        File path = fc.getSelectedFile();
        this.importexportdir = fc.getCurrentDirectory();
        return path;
    }

    private void writeExportFile(Serializable grammar, File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oot = new ObjectOutputStream(fos);
            oot.writeObject(grammar);
            oot.close();
        }
        catch (IOException ex) {
            System.out.println("%-%-% EXCEPTION IN EXPORT GRAMMAR %-%-%");
            ex.printStackTrace();
        }
    }
}

