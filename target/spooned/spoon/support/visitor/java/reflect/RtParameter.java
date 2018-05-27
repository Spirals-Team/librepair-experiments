package spoon.support.visitor.java.reflect;


public class RtParameter {
    private final java.lang.String name;

    private final java.lang.Class<?> type;

    private final java.lang.reflect.Type genericType;

    private final spoon.support.visitor.java.reflect.RtMethod method;

    private final java.lang.reflect.Constructor constructor;

    private final int index;

    public RtParameter(java.lang.String name, java.lang.Class<?> type, java.lang.reflect.Type genericType, spoon.support.visitor.java.reflect.RtMethod method, java.lang.reflect.Constructor constructor, int index) {
        this.name = name;
        this.type = type;
        this.genericType = genericType;
        this.method = method;
        this.constructor = constructor;
        this.index = index;
    }

    public java.lang.String getName() {
        if (((name) == null) || (name.equals(""))) {
            return "arg" + (index);
        }else {
            return name;
        }
    }

    public java.lang.Class<?> getType() {
        return type;
    }

    public java.lang.reflect.Type getGenericType() {
        return genericType;
    }

    public java.lang.annotation.Annotation[] getDeclaredAnnotations() {
        if ((method) == null) {
            return constructor.getParameterAnnotations()[index];
        }
        return method.getParameterAnnotations()[index];
    }

    public boolean isVarArgs() {
        if ((method) == null) {
            return (constructor.isVarArgs()) && ((index) == ((constructor.getParameterTypes().length) - 1));
        }
        return (method.isVarArgs()) && ((index) == ((method.getParameterTypes().length) - 1));
    }

    public boolean equals(java.lang.Object obj) {
        if (obj instanceof spoon.support.visitor.java.reflect.RtParameter) {
            spoon.support.visitor.java.reflect.RtParameter other = ((spoon.support.visitor.java.reflect.RtParameter) (obj));
            if ((method) == null) {
                return (other.constructor.equals(constructor)) && ((other.index) == (index));
            }
            return (other.method.equals(method)) && ((other.index) == (index));
        }
        return false;
    }

    public int hashCode() {
        if ((method) == null) {
            return (constructor.hashCode()) ^ (index);
        }
        return (method.hashCode()) ^ (index);
    }

    public static spoon.support.visitor.java.reflect.RtParameter[] parametersOf(spoon.support.visitor.java.reflect.RtMethod method) {
        spoon.support.visitor.java.reflect.RtParameter[] parameters = new spoon.support.visitor.java.reflect.RtParameter[method.getParameterTypes().length];
        for (int index = 0; index < (method.getParameterTypes().length); index++) {
            parameters[index] = new spoon.support.visitor.java.reflect.RtParameter(null, method.getParameterTypes()[index], method.getGenericParameterTypes()[index], method, null, index);
        }
        return parameters;
    }

    public static spoon.support.visitor.java.reflect.RtParameter[] parametersOf(java.lang.reflect.Constructor constructor) {
        spoon.support.visitor.java.reflect.RtParameter[] parameters;
        int lengthGenericParameterTypes = constructor.getGenericParameterTypes().length;
        int lengthParameterTypes = constructor.getParameterTypes().length;
        int offset;
        if (lengthGenericParameterTypes == lengthParameterTypes) {
            parameters = new spoon.support.visitor.java.reflect.RtParameter[lengthParameterTypes];
            offset = 0;
        }else
            if (lengthGenericParameterTypes == (lengthParameterTypes - 1)) {
                parameters = new spoon.support.visitor.java.reflect.RtParameter[lengthGenericParameterTypes];
                offset = 1;
            }else
                if ((constructor.getDeclaringClass().isEnum()) && (lengthGenericParameterTypes == (lengthParameterTypes - 2))) {
                    parameters = new spoon.support.visitor.java.reflect.RtParameter[lengthGenericParameterTypes];
                    offset = 2;
                }else {
                    throw new spoon.SpoonException(((((("Error while analyzing parameters of constructor: " + constructor) + ". # of parameters: ") + lengthParameterTypes) + " - # of generic parameter types: ") + lengthGenericParameterTypes));
                }


        for (int index = 0; index < (constructor.getGenericParameterTypes().length); index++) {
            parameters[index] = new spoon.support.visitor.java.reflect.RtParameter(null, constructor.getParameterTypes()[(index + offset)], constructor.getGenericParameterTypes()[index], null, constructor, index);
        }
        return parameters;
    }
}

