/*
  * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
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
import java.net.URL;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import javax.swing.JPanel;
import org.fleen.forsythia.app.grammarEditor.GrammarImportExport;
import org.fleen.forsythia.app.grammarEditor.UIMain;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.Editor_Generator;
import org.fleen.forsythia.app.grammarEditor.editor_Grammar.Editor_Grammar;
import org.fleen.forsythia.app.grammarEditor.editor_Jig.Editor_Jig;
import org.fleen.forsythia.app.grammarEditor.editor_Metagon.Editor_Metagon;
import org.fleen.forsythia.app.grammarEditor.project.ProjectGrammar;
import org.fleen.forsythia.app.grammarEditor.project.jig.ProjectJig;
import org.fleen.forsythia.app.grammarEditor.project.metagon.ProjectMetagon;
import org.fleen.forsythia.app.grammarEditor.sampleGrammars.SampleGrammars;
import org.fleen.forsythia.app.grammarEditor.util.Editor;

public class GE
implements Serializable {
    private static final long serialVersionUID = 4971596440965502787L;
    public static final String VERSIONNAME = "V2017_10_19";
    public static final String APPNAME = "Fleen Forsythia Grammar Editor ";
    public static final String ABOUT = "Fleen Forsythia Grammar Editor V2017_10_19\n\nCreate shape grammars for use in Forsythia geometry production processes.\nSample compositions based on grammars.\nExport pretty images.\n\nAuthor : John Greene john@fleen.org\nProject (including docs and source for this app) : fleen.org\n\nCopyright 2017 John Greene\n\nThis program is free software: you can redistribute it and/or modify\nit under the terms of the GNU General Public License as published by\nthe Free Software Foundation, either version 3 of the License, or\n(at your option) any later version.\n\nThis program is distributed in the hope that it will be useful,\nbut WITHOUT ANY WARRANTY; without even the implied warranty of\nMERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\nGNU General Public License for more details.\n\nYou should have received a copy of the GNU General Public License\nalong with this program.  If not, see <http://www.gnu.org/licenses/>.";
    private transient UIMain uimain = null;
    public Editor presenteditor = null;
    public Editor_Grammar editor_grammar;
    public Editor_Metagon editor_metagon;
    public Editor_Jig editor_jig;
    public Editor_Generator editor_generator;
    public Editor[] editors = null;
    public ProjectGrammar focusgrammar = null;
    public ProjectMetagon focusmetagon = null;
    public ProjectJig focusjig = null;
    public GrammarImportExport grammarimportexport = new GrammarImportExport();
    public static String importdirectory = null;
    static final String GEINSTANCEFILENAME = "GE.instance";
    public static final boolean DEBUG = false;
    public static GE ge = null;

    private void init() {
        this.initFocusGrammar();
        this.initEditors();
        this.initUI();
    }

    public UIMain getUIMain() {
        if (this.uimain == null) {
            this.createUI();
        }
        return this.uimain;
    }

    private void initUI() {
        UIMain uimain = this.getUIMain();
        uimain.setVisible(true);
        this.setEditor(this.editor_generator);
        this.editor_generator.setForFirstInit();
    }

    private void createUI() {
        this.uimain = new UIMain();
        for (Editor a : this.editors) {
            this.uimain.paneditor.add((Component)a.getUI(), a.getName());
        }
    }

    private void initEditors() {
        if (this.editors == null) {
            this.editor_grammar = new Editor_Grammar();
            this.editor_metagon = new Editor_Metagon();
            this.editor_jig = new Editor_Jig();
            this.editor_generator = new Editor_Generator();
            this.editors = new Editor[]{this.editor_grammar, this.editor_metagon, this.editor_jig, this.editor_generator};
        }
    }

    public void setEditor(Editor editor) {
        if (this.presenteditor != null) {
            this.presenteditor.close();
        }
        this.presenteditor = editor;
        CardLayout a = (CardLayout)this.uimain.paneditor.getLayout();
        String n = editor.getName();
        a.show(this.uimain.paneditor, n);
        this.uimain.setTitle("Fleen Forsythia Grammar Editor  V2017_10_19 :: " + n);
        this.presenteditor.open();
    }

    private void initFocusGrammar() {
        if (this.focusgrammar == null) {
            SampleGrammars sg = new SampleGrammars();
            sg.init();
        }
    }

    public void term() {
        System.out.println("GE TERMINATE");
        GE.saveInstance(this);
        System.exit(0);
    }

    public static final File getLocalDir() {
        String decodedpath;
        String path = GE.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        try {
            decodedpath = URLDecoder.decode(path, "UTF-8");
        }
        catch (Exception x) {
            throw new IllegalArgumentException(x);
        }
        File f = new File(decodedpath);
        if (!f.isDirectory()) {
            f = f.getParentFile();
        }
        return f;
    }

    private static final GE loadInstance() {
        String pathtoconfig = String.valueOf(GE.getLocalDir().getPath()) + "/" + GEINSTANCEFILENAME;
        System.out.println("Loading instance : " + pathtoconfig);
        GE instance = null;
        try {
            FileInputStream a = new FileInputStream(pathtoconfig);
            ObjectInputStream b = new ObjectInputStream(a);
            instance = (GE)b.readObject();
            b.close();
        }
        catch (Exception e) {
            System.out.println("Load instance failed.");
        }
        return instance;
    }

    private static final void saveInstance(GE instance) {
        String pathtoconfig = String.valueOf(GE.getLocalDir().getPath()) + "/" + GEINSTANCEFILENAME;
        System.out.println("Saving instance : " + pathtoconfig);
        File file = new File(pathtoconfig);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(instance);
            oos.close();
        }
        catch (IOException x) {
            System.out.println("Save instance failed.");
            x.printStackTrace();
        }
    }
  
    
    public static final void main(String[] a){
        ge = GE.loadInstance();
        String ps;
        if (ge != null) {
            System.out.println("loaded serialized instance of GE");
            ge.init();
        } else {
            System.out.println("constructed instance of GE");
            ge = new GE();
            ge.init();
        }
    }
}

