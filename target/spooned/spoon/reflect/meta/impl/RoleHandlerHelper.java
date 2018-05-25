package spoon.reflect.meta.impl;


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

    public static spoon.reflect.meta.RoleHandler getRoleHandler(java.lang.Class<? extends spoon.reflect.declaration.CtElement> targetClass, spoon.reflect.path.CtRole role) {
        spoon.reflect.meta.RoleHandler rh = spoon.reflect.meta.impl.RoleHandlerHelper.getOptionalRoleHandler(targetClass, role);
        if (rh == null) {
            throw new spoon.SpoonException(((("The element of class " + targetClass) + " does not have CtRole.") + (role.name())));
        }
        return rh;
    }

    public static spoon.reflect.meta.RoleHandler getOptionalRoleHandler(java.lang.Class<? extends spoon.reflect.declaration.CtElement> targetClass, spoon.reflect.path.CtRole role) {
        java.util.List<spoon.reflect.meta.RoleHandler> handlers = spoon.reflect.meta.impl.RoleHandlerHelper.roleHandlers[role.ordinal()];
        for (spoon.reflect.meta.RoleHandler ctRoleHandler : handlers) {
            if (ctRoleHandler.getTargetType().isAssignableFrom(targetClass)) {
                return ctRoleHandler;
            }
        }
        return null;
    }

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

    public static void forEachRoleHandler(java.util.function.Consumer<spoon.reflect.meta.RoleHandler> consumer) {
        for (java.util.List<spoon.reflect.meta.RoleHandler> list : spoon.reflect.meta.impl.RoleHandlerHelper.roleHandlers) {
            for (spoon.reflect.meta.RoleHandler roleHandler : list) {
                consumer.accept(roleHandler);
            }
        }
    }

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

