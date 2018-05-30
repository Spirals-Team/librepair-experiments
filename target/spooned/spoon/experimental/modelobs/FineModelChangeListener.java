/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.experimental.modelobs;


/**
 * Can be subclassed by clients who want to be notified on all changes in AST nodes
 */
public interface FineModelChangeListener {
    /**
     * a field corresponding to the role is being set in the AST node
     */
    void onObjectUpdate(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, spoon.reflect.declaration.CtElement newValue, spoon.reflect.declaration.CtElement oldValue);

    /**
     * a field corresponding to the role is being set in the AST node
     */
    void onObjectUpdate(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.lang.Object newValue, java.lang.Object oldValue);

    /**
     * a field corresponding to the role is being set to null
     */
    void onObjectDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, spoon.reflect.declaration.CtElement oldValue);

    /**
     * a newValue is appended to the list corresponding to the role in the AST node
     */
    void onListAdd(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.List field, spoon.reflect.declaration.CtElement newValue);

    /**
     * a newValue is appended to the list corresponding to the role in the AST node
     */
    void onListAdd(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.List field, int index, spoon.reflect.declaration.CtElement newValue);

    /**
     * an oldValue is deleted in the list corresponding to the role in the AST node
     */
    void onListDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.List field, java.util.Collection<? extends spoon.reflect.declaration.CtElement> oldValue);

    /**
     * an oldValue is deleted in the list corresponding to the role in the AST node
     */
    void onListDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.List field, int index, spoon.reflect.declaration.CtElement oldValue);

    /**
     * a list corresponding to the role in the AST node is emptied
     */
    void onListDeleteAll(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.List field, java.util.List oldValue);

    /**
     * a newValue is appended to the map corresponding to the role in the AST node
     */
    <K, V> void onMapAdd(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Map<K, V> field, K key, spoon.reflect.declaration.CtElement newValue);

    /**
     * a map corresponding to the role in the AST node is emptied
     */
    <K, V> void onMapDeleteAll(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Map<K, V> field, java.util.Map<K, V> oldValue);

    /**
     * a newValue is appended to the set corresponding to the role in the AST node
     */
    void onSetAdd(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, spoon.reflect.declaration.CtElement newValue);

    /**
     * a newValue is appended to the set corresponding to the role in the AST node
     */
    <T extends java.lang.Enum> void onSetAdd(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, T newValue);

    /**
     * an oldValue is deleted in the set corresponding to the role in the AST node
     */
    void onSetDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, spoon.reflect.declaration.CtElement oldValue);

    /**
     * an oldValue is deleted in the set corresponding to the role in the AST node
     */
    void onSetDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, java.util.Collection<spoon.reflect.declaration.ModifierKind> oldValue);

    /**
     * an oldValue is deleted in the set corresponding to the role in the AST node
     */
    void onSetDelete(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, spoon.reflect.declaration.ModifierKind oldValue);

    /**
     * a set corresponding to the role in the AST node is emptied
     */
    void onSetDeleteAll(spoon.reflect.declaration.CtElement currentElement, spoon.reflect.path.CtRole role, java.util.Set field, java.util.Set oldValue);
}

