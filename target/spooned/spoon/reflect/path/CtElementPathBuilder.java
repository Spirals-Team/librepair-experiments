package spoon.reflect.path;


/**
 * This builder allow to create some CtPath from CtElements
 *
 * Created by nharrand on 21/02/2018.
 */
public class CtElementPathBuilder {
    /**
     * Build path to a CtElement el, from one of its parent.
     *
     * @throws CtPathException
     * 		is thrown when root is not a parent of el.
     * @param el
     * 		: the element to which the CtPath leads to
     * @param root
     * 		: Starting point of the CtPath
     * @return CtPath from root to el
     */
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
                    // Element needs to be differentiated from its brothers
                    java.util.List list = roleHandler.asList(parent);
                    // Assumes that List's order is deterministic.
                    // Can't be replaced by list.indexOf(cur)
                    // Because objects must be the same (and not just equals)
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

