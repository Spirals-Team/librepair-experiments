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
package spoon.reflect.meta.impl;


/**
 * Provides a {@link RoleHandler} implementation for the pair of {@link CtElement} implementation and {@link CtRole}
 * The returned {@link RoleHandler} can be then used to manipulate value of attribute represented by {@link CtRole} on the {@link CtElement} instance
 */
public class RoleHandlerHelper {
    private RoleHandlerHelper() {
    }

    private static java.util.Map<java.lang.Class<?>, java.util.List<spoon.reflect.meta.RoleHandler>> roleHandlersByClass = new java.util.HashMap<>();

    @java.lang.SuppressWarnings("unchecked")
    private static final java.util.List<spoon.reflect.meta.RoleHandler>[] roleHandlers = new java.util.List[spoon.reflect.path.CtRole.values().length];

    static {
        for (int i = 0; i < (spoon.reflect.meta.impl.RoleHandlerHelper.roleHandlers.length); i++) {
            spoon.reflect.meta.impl.RoleHandlerHelper.roleHandlers[i] = new java.util.ArrayList<>();
        }
        for (spoon.reflect.meta.RoleHandler rh : spoon.reflect.meta.impl.ModelRoleHandlers.roleHandlers) {
            spoon.reflect.meta.impl.RoleHandlerHelper.roleHandlers[rh.getRole().ordinal()].add(rh);
        }
        java.util.Comparator<spoon.reflect.meta.RoleHandler> cmp = ( a, b) -> a.getTargetType().isAssignableFrom(b.getTargetType()) ? 1 : -1;
        for (spoon.reflect.meta.RoleHandler rh : spoon.reflect.meta.impl.ModelRoleHandlers.roleHandlers) {
            spoon.reflect.meta.impl.RoleHandlerHelper.roleHandlers[rh.getRole().ordinal()].sort(cmp);
        }
    }

    /**
     *
     *
     * @param targetClass
     * 		the class of the to be manipulated node
     * @param role
     * 		defines the to be manipulated attribute
     * @return {@link RoleHandler} implementation which knows how to manipulate the attribute of {@link CtRole} on `targetClass`
     * or throws exception if such role doesn't exist on the `targetClass`
     */
    public static spoon.reflect.meta.RoleHandler getRoleHandler(java.lang.Class<? extends spoon.reflect.declaration.CtElement> targetClass, spoon.reflect.path.CtRole role) {
        spoon.reflect.meta.RoleHandler rh = spoon.reflect.meta.impl.RoleHandlerHelper.getOptionalRoleHandler(targetClass, role);
        if (rh == null) {
            throw new spoon.SpoonException(((("The element of class " + targetClass) + " does not have CtRole.") + (role.name())));
        }
        return rh;
    }

    /**
     *
     *
     * @param targetClass
     * 		the Class of the to be manipulated node
     * @param role
     * 		defines the to be manipulated attribute
     * @return {@link RoleHandler} implementation, which knows how to manipulate the attribute of {@link CtRole} on `targetClass`
     * or returns null if such role doesn't exist on the `targetClass`
     */
    public static spoon.reflect.meta.RoleHandler getOptionalRoleHandler(java.lang.Class<? extends spoon.reflect.declaration.CtElement> targetClass, spoon.reflect.path.CtRole role) {
        java.util.List<spoon.reflect.meta.RoleHandler> handlers = spoon.reflect.meta.impl.RoleHandlerHelper.roleHandlers[role.ordinal()];
        for (spoon.reflect.meta.RoleHandler ctRoleHandler : handlers) {
            if (ctRoleHandler.getTargetType().isAssignableFrom(targetClass)) {
                return ctRoleHandler;
            }
        }
        return null;
    }

    /**
     *
     *
     * @param targetClass
     * 		a Class whose handlers we are looking for
     * @return all {@link RoleHandler}s available for the `targetClass`
     */
    public static java.util.List<spoon.reflect.meta.RoleHandler> getRoleHandlers(java.lang.Class<? extends spoon.reflect.declaration.CtElement> targetClass) {
        java.util.List<spoon.reflect.meta.RoleHandler> handlers = spoon.reflect.meta.impl.RoleHandlerHelper.roleHandlersByClass.get(targetClass);
        if (handlers == null) {
            java.util.List<spoon.reflect.meta.RoleHandler> modifiableHandlers = new java.util.ArrayList<>();
            for (spoon.reflect.path.CtRole role : spoon.reflect.path.CtRole.values()) {
                spoon.reflect.meta.RoleHandler roleHandler = spoon.reflect.meta.impl.RoleHandlerHelper.getOptionalRoleHandler(targetClass, role);
                if (roleHandler != null) {
                    modifiableHandlers.add(roleHandler);
                }
            }
            handlers = java.util.Collections.unmodifiableList(modifiableHandlers);
            spoon.reflect.meta.impl.RoleHandlerHelper.roleHandlersByClass.put(targetClass, handlers);
        }
        return handlers;
    }

    /**
     *
     *
     * @param consumer
     * 		is called for each {@link RoleHandler} of SpoonModel
     */
    public static void forEachRoleHandler(java.util.function.Consumer<spoon.reflect.meta.RoleHandler> consumer) {
        for (java.util.List<spoon.reflect.meta.RoleHandler> list : spoon.reflect.meta.impl.RoleHandlerHelper.roleHandlers) {
            for (spoon.reflect.meta.RoleHandler roleHandler : list) {
                consumer.accept(roleHandler);
            }
        }
    }

    /**
     *
     *
     * @param element
     * 		the {@link CtElement} whose relation from `element.getParent()` to `element` is needed.
     * @return {@link RoleHandler} handling relation from `element.getParent()` to `element`
     */
    public static spoon.reflect.meta.RoleHandler getRoleHandlerWrtParent(spoon.reflect.declaration.CtElement element) {
        if ((element.isParentInitialized()) == false) {
            return null;
        }
        spoon.reflect.declaration.CtElement parent = element.getParent();
        spoon.reflect.path.CtRole roleInParent = element.getRoleInParent();
        if (roleInParent == null) {
            return null;
        }
        return spoon.reflect.meta.impl.RoleHandlerHelper.getRoleHandler(parent.getClass(), roleInParent);
    }
}

