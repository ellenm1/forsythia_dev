/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.project.jig;

import java.io.Serializable;
import java.util.List;
import org.fleen.forsythia.app.grammarEditor.project.metagon.ProjectMetagon;
import org.fleen.geom_Kisrhombille.KAnchor;

class MetagonAndAnchors
implements Serializable {
    private static final long serialVersionUID = 3890810848394785357L;
    ProjectMetagon metagon;
    List<KAnchor> anchors;

    MetagonAndAnchors(ProjectMetagon metagon, List<KAnchor> anchors) {
        this.metagon = metagon;
        this.anchors = anchors;
    }
}

