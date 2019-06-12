/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.geom_Kisrhombille;

import java.io.Serializable;
import org.fleen.geom_Kisrhombille.KPoint;

public class KAnchor
implements Serializable {
    private static final long serialVersionUID = -5677563038771003335L;
    public KPoint v0;
    public KPoint v1;
    public boolean twist;

    public KAnchor(KPoint v0, KPoint v1, boolean twist) {
        this.v0 = v0;
        this.v1 = v1;
        this.twist = twist;
    }

    public boolean equals(Object a) {
        KAnchor b = (KAnchor)a;
        return b.v0.equals(this.v0) && b.v1.equals(this.v1) && b.twist == this.twist;
    }

    public String toString() {
        return "[" + this.getClass().getSimpleName() + " v0=" + this.v0 + " v1=" + this.v1 + " twist=" + this.twist + "]";
    }
}

