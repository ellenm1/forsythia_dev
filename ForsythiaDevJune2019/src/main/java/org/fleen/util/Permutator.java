/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Permutator {
    public static <T> List<List<T>> getPermutations(List<List<T>> collections) {
        if (collections == null || collections.isEmpty()) {
            return new LinkedList<List<T>>();
        }
        LinkedList<List<T>> res = new LinkedList<List<T>>();
        Permutator.permutationsImpl(collections, res, 0, new LinkedList());
        return res;
    }

    private static <T> void permutationsImpl(List<List<T>> ori, List<List<T>> res, int d, List<T> current) {
        if (d == ori.size()) {
            res.add(current);
            return;
        }
        Collection currentCollection = ori.get(d);
        for (Object element : currentCollection) {
            LinkedList<T> copy = new LinkedList<T>(current);
            copy.add(element);
            Permutator.permutationsImpl(ori, res, d + 1, copy);
        }
    }
}

