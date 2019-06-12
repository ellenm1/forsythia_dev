/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.sampleGrammars;

import java.io.File;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.io.Serializable;
import org.fleen.forsythia.app.grammarEditor.GE;
import org.fleen.forsythia.app.grammarEditor.GrammarImportExport;
import org.fleen.forsythia.app.grammarEditor.project.ProjectGrammar;
import org.fleen.forsythia.core.grammar.ForsythiaGrammar;

public class SampleGrammars
implements Serializable {
    private static final long serialVersionUID = -5614469697980598416L;
    private static final String[] GRAMMARNAMES = new String[]{"nice.grammar", "boxy.grammar", "triangley.grammar", "fancy.grammar", "fancy2.grammar", "fancy3.grammar", "fancy4.grammar", "nuther003.grammar", "precise009.grammar"};

    public void init() {
        for (String name : GRAMMARNAMES) {
            this.loadAndExportResourceGrammar(name);
        }
        File path = GE.getLocalDir();
        path = new File(String.valueOf(path.getAbsolutePath()) + "/" + GRAMMARNAMES[6]);
        GE.ge.grammarimportexport.importGrammar(path);
    }

    private ForsythiaGrammar loadAndExportResourceGrammar(String name) {
        ForsythiaGrammar g = null;
        try {
            InputStream a = SampleGrammars.class.getResourceAsStream(name);
            ObjectInputStream b = new ObjectInputStream(a);
            g = (ForsythiaGrammar)b.readObject();
            b.close();
        }
        catch (Exception e) {
            System.out.println("Load sample grammar failed.");
            e.printStackTrace();
        }
        ProjectGrammar pg = new ProjectGrammar(g, name);
        File path = GE.getLocalDir();
        path = new File(String.valueOf(path.getAbsolutePath()) + "/" + pg.name);
        GE.ge.grammarimportexport.exportGrammar(pg, path);
        return g;
    }
}

