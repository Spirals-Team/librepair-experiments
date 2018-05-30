package spoon.reflect.path;


public class CtElementPathBuilder {
    public spoon.reflect.path.CtPath fromElement(spoon.reflect.declaration.CtElement el, spoon.reflect.declaration.CtElement root) throws spoon.reflect.path.CtPathException {
        spoon.reflect.path.impl.CtPathImpl path = new spoon.reflect.path.impl.CtPathImpl();
        spoon.reflect.declaration.CtElement cur = el;
        while (cur != root) {
            spoon.reflect.declaration.CtElement parent = cur.getParent();
            spoon.reflect.path.CtRole role = cur.getRoleInParent();
            if (role == null) {
                throw new spoon.reflect.path.CtPathException();
            }
            spoon.reflect.meta.RoleHandler roleHandler = spoon.reflect.meta.impl.RoleHandlerHelper.getOptionalRoleHandler(parent.getClass(), role);
            if (roleHandler == null) {
                throw new spoon.reflect.path.CtPathException();
            }
            spoon.reflect.path.impl.CtPathElement pathElement = new spoon.reflect.path.impl.CtRolePathElement(role);
            switch (roleHandler.getContainerKind()) {
                case SINGLE :
                    break;
                case LIST :
                    java.util.List list = roleHandler.asList(parent);
                    int index = 0;
                    for (java.lang.Object o : list) {
                        if (o == cur) {
                            break;
                        }
                        index++;
                    }
                    pathElement.addArgument("index", (index + ""));
                    break;
                case SET :
                    java.lang.String name;
                    if (cur instanceof spoon.reflect.declaration.CtNamedElement) {
                        name = ((spoon.reflect.declaration.CtNamedElement) (cur)).getSimpleName();
                    }else
                        if (cur instanceof spoon.reflect.reference.CtReference) {
                            name = ((spoon.reflect.reference.CtReference) (cur)).getSimpleName();
                        }else {
                            throw new spoon.reflect.path.CtPathException();
                        }

                    pathElement.addArgument("name", name);
                    break;
                case MAP :
                    java.util.Map map = roleHandler.asMap(parent);
                    java.lang.String key = null;
                    for (java.lang.Object o : map.keySet()) {
                        if ((map.get(o)) == cur) {
                            key = ((java.lang.String) (o));
                            break;
                        }
                    }
                    if (key == null) {
                        throw new spoon.reflect.path.CtPathException();
                    }else {
                        pathElement.addArgument("key", key);
                    }
                    break;
            }
            cur = parent;
            path.addFirst(pathElement);
        } 
        return path;
    }
}

