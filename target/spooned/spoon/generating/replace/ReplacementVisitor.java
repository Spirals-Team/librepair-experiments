package spoon.generating.replace;


class ReplacementVisitor extends spoon.reflect.visitor.CtScanner {
    public static void replace(spoon.reflect.declaration.CtElement original, spoon.reflect.declaration.CtElement replace) {
        try {
            new spoon.generating.replace.ReplacementVisitor(original, (replace == null ? spoon.generating.replace.ReplacementVisitor.EMPTY : new spoon.reflect.declaration.CtElement[]{ replace })).scan(original.getParent());
        } catch (spoon.support.visitor.replace.InvalidReplaceException e) {
            throw e;
        } catch (spoon.SpoonException ignore) {
        }
    }

    public static <E extends spoon.reflect.declaration.CtElement> void replace(spoon.reflect.declaration.CtElement original, java.util.Collection<E> replaces) {
        try {
            new spoon.generating.replace.ReplacementVisitor(original, replaces.toArray(new spoon.reflect.declaration.CtElement[replaces.size()])).scan(original.getParent());
        } catch (spoon.support.visitor.replace.InvalidReplaceException e) {
            throw e;
        } catch (spoon.SpoonException ignore) {
        }
    }

    private spoon.reflect.declaration.CtElement original;

    private spoon.reflect.declaration.CtElement[] replace;

    private static final spoon.reflect.declaration.CtElement[] EMPTY = new spoon.reflect.declaration.CtElement[0];

    private ReplacementVisitor(spoon.reflect.declaration.CtElement original, spoon.reflect.declaration.CtElement... replace) {
        this.original = original;
        this.replace = (replace == null) ? spoon.generating.replace.ReplacementVisitor.EMPTY : replace;
    }

    private <K, V extends spoon.reflect.declaration.CtElement> void replaceInMapIfExist(java.util.Map<K, V> mapProtected, spoon.support.visitor.replace.ReplaceMapListener listener) {
        java.util.Map<K, V> map = new java.util.HashMap<>(mapProtected);
        V shouldBeDeleted = null;
        K key = null;
        for (java.util.Map.Entry<K, V> entry : map.entrySet()) {
            if ((entry.getValue()) == (original)) {
                shouldBeDeleted = entry.getValue();
                key = entry.getKey();
                break;
            }
        }
        if (shouldBeDeleted != null) {
            if ((replace.length) > 0) {
                if ((replace.length) > 1) {
                    throw new spoon.support.visitor.replace.InvalidReplaceException(("Cannot replace single value by multiple values in " + (listener.getClass().getSimpleName())));
                }
                V val = ((V) (replace[0]));
                if (val != null) {
                    map.put(key, val);
                    val.setParent(shouldBeDeleted.getParent());
                }else {
                    map.remove(key);
                }
            }else {
                map.remove(key);
            }
            listener.set(map);
        }
    }

    private <T extends spoon.reflect.declaration.CtElement> void replaceInSetIfExist(java.util.Set<T> setProtected, spoon.support.visitor.replace.ReplaceSetListener listener) {
        java.util.Set<T> set = new java.util.HashSet<>(setProtected);
        T shouldBeDeleted = null;
        for (T element : set) {
            if (element == (original)) {
                shouldBeDeleted = element;
                break;
            }
        }
        if (shouldBeDeleted != null) {
            set.remove(shouldBeDeleted);
            for (spoon.reflect.declaration.CtElement ele : replace) {
                if (ele != null) {
                    set.add(((T) (ele)));
                    ele.setParent(shouldBeDeleted.getParent());
                }
            }
            listener.set(set);
        }
    }

    private <T extends spoon.reflect.declaration.CtElement> void replaceInListIfExist(java.util.List<T> listProtected, spoon.support.visitor.replace.ReplaceListListener listener) {
        java.util.List<T> list = new java.util.ArrayList<>(listProtected);
        T shouldBeDeleted = null;
        int index = 0;
        for (int i = 0; i < (list.size()); i++) {
            if ((list.get(i)) == (original)) {
                index = i;
                shouldBeDeleted = list.get(i);
                break;
            }
        }
        if (shouldBeDeleted != null) {
            list.remove(index);
            if ((replace.length) > 0) {
                for (int i = 0; i < (replace.length); i++) {
                    T ele = ((T) (replace[i]));
                    if (ele != null) {
                        list.add(index, ele);
                        ele.setParent(shouldBeDeleted.getParent());
                        index = index + 1;
                    }
                }
            }
            listener.set(list);
        }
    }

    private void replaceElementIfExist(spoon.reflect.declaration.CtElement candidate, spoon.support.visitor.replace.ReplaceListener listener) {
        if (candidate == (original)) {
            spoon.reflect.declaration.CtElement val = null;
            if ((replace.length) > 0) {
                if ((replace.length) > 1) {
                    throw new spoon.support.visitor.replace.InvalidReplaceException(("Cannot replace single value by multiple values in " + (listener.getClass().getSimpleName())));
                }
                val = replace[0];
            }
            if (val != null) {
                val.setParent(candidate.getParent());
            }
            listener.set(val);
        }
    }
}

