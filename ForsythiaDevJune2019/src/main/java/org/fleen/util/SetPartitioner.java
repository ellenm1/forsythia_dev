/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.util;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SetPartitioner {
    Map<Integer, List<int[]>> schemelistbyelementcount = new Hashtable<Integer, List<int[]>>();

    public List<int[]> getPartitioningSchemes(Integer ec) {
        List<int[]> sl = this.schemelistbyelementcount.get(ec);
        if (sl == null) {
            sl = this.initPartitioningSchemes(ec);
            this.schemelistbyelementcount.put(ec, sl);
        }
        return sl;
    }

    private List<int[]> initPartitioningSchemes(int ec) {
        Scheme initscheme = new Scheme();
        for (int i = 0; i < ec; ++i) {
            initscheme.add(new Group(i));
        }
        HashSet<Scheme> newschemes = new HashSet<Scheme>();
        HashSet<Scheme> schemes = new HashSet<Scheme>();
        newschemes.add(initscheme);
        while (!newschemes.isEmpty()) {
            Scheme s = (Scheme)newschemes.iterator().next();
            newschemes.remove(s);
            schemes.add(s);
            Set<Scheme> gschemes = this.generateSchemes(s);
            if (gschemes == null) continue;
            newschemes.addAll(this.generateSchemes(s));
        }
        System.out.println("schemescount=" + schemes.size());
        for (Scheme a : schemes) {
            System.out.println(a);
        }
        System.out.println("foo");
        return null;
    }

    private Set<Scheme> generateSchemes(Scheme scheme) {
        if (scheme.size() == 1) {
            return null;
        }
        HashSet<Scheme> gschemes = new HashSet<Scheme>();
        for (Group g0 : scheme) {
            for (Group g1 : scheme) {
                if (g0.equals(g1)) continue;
                Scheme snew = new Scheme(scheme);
                snew.remove(g0);
                snew.remove(g1);
                snew.add(new Group(g0, g1));
                gschemes.add(snew);
            }
        }
        return gschemes;
    }

    public static final void main(String[] a) {
        SetPartitioner p = new SetPartitioner();
        List<int[]> b = p.getPartitioningSchemes(8);
        System.out.println("schemecount=" + b.size());
        for (int[] ia : b) {
            StringBuffer sb = new StringBuffer();
            sb.append("[");
            for (int i : ia) {
                sb.append(String.valueOf(i) + ",");
            }
            sb.append("]");
            System.out.println(sb.toString());
        }
    }

    private class Group
    extends HashSet<Integer> {
        Group(int initelement) {
            this.add(initelement);
        }

        Group(Group g0, Group g1) {
            this.addAll(g0);
            this.addAll(g1);
        }

        @Override
        public int hashCode() {
            return this.size();
        }

        @Override
        public boolean equals(Object a) {
            return this.size() == ((Group)a).size() && this.containsAll((Group)a);
        }
    }

    class NoDupe
    extends ArrayList<int[]> {
        NoDupe() {
        }

        void addArray(int[] a) {
            if (!this.containsArray(a)) {
                this.add(a);
            }
        }

        boolean containsArray(int[] a) {
            for (int[] b : this) {
                if (!this.arraysAreEqual(a, b)) continue;
                return true;
            }
            return false;
        }

        boolean arraysAreEqual(int[] a, int[] b) {
            for (int i = 0; i < a.length; ++i) {
                if (a[i] == b[i]) continue;
                return false;
            }
            return true;
        }
    }

    class Scheme
    extends HashSet<Group> {
        Scheme() {
        }

        Scheme(Scheme scheme) {
            super(scheme);
        }

        int getGroupIndex(Integer element) {
            throw new IllegalArgumentException("element not in scheme");
        }

        @Override
        public int hashCode() {
            return this.size();
        }

        @Override
        public boolean equals(Object a) {
            return this.size() == ((Scheme)a).size() && this.containsAll((Scheme)a);
        }

        @Override
        public String toString() {
            StringBuffer a = new StringBuffer();
            for (Group g : this) {
                a.append("(");
                for (Integer i : g) {
                    a.append(i + ",");
                }
                a.append(")");
            }
            return a.toString();
        }
    }

}

