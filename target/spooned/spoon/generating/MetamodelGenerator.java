package spoon.generating;


public class MetamodelGenerator {
    public static void main(java.lang.String[] args) {
        spoon.test.metamodel.SpoonMetaModel mm = new spoon.test.metamodel.SpoonMetaModel(new java.io.File("src/main/java"));
        mm.getFactory().getEnvironment().useTabulations(true);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (spoon.test.metamodel.MetamodelConcept type : mm.getConcepts()) {
            if ((type.getKind()) == (spoon.test.metamodel.MMTypeKind.LEAF)) {
                sb.append(spoon.generating.MetamodelGenerator.printType(mm.getFactory(), type));
            }
        }
        java.lang.System.out.println(sb.toString());
    }

    private static java.lang.String printType(spoon.reflect.factory.Factory factory, spoon.test.metamodel.MetamodelConcept type) {
        java.util.Map<java.lang.String, java.lang.String> valuesMap = new java.util.HashMap<>();
        valuesMap.put("typeName", type.getName());
        valuesMap.put("ifaceName", type.getModelInterface().getQualifiedName());
        valuesMap.put("implName", type.getModelClass().getQualifiedName());
        valuesMap.put("fields", spoon.generating.MetamodelGenerator.printFields(factory, type));
        org.apache.commons.lang3.text.StrSubstitutor strSubst = new org.apache.commons.lang3.text.StrSubstitutor(valuesMap);
        return strSubst.replace(("types.add(new Type(\"${typeName}\", ${ifaceName}.class, ${implName}.class, fm -> fm\n" + ("${fields}\n" + "));\n\n")));
    }

    private static java.lang.String printFields(spoon.reflect.factory.Factory factory, spoon.test.metamodel.MetamodelConcept type) {
        java.util.Map<spoon.reflect.path.CtRole, spoon.test.metamodel.MetamodelProperty> allFields = new java.util.LinkedHashMap<>(type.getRoleToProperty());
        java.util.List<spoon.reflect.path.CtRole> rolesByScanner = spoon.generating.MetamodelGenerator.getRoleScanningOrderOfType(factory, ((java.lang.Class) (type.getModelInterface().getActualClass())));
        java.util.List<java.lang.String> elementFields = new java.util.ArrayList<>();
        for (spoon.reflect.path.CtRole ctRole : rolesByScanner) {
            spoon.test.metamodel.MetamodelProperty field = allFields.remove(ctRole);
            elementFields.add(spoon.generating.MetamodelGenerator.printField(field));
        }
        java.util.List<java.lang.String> primitiveFields = new java.util.ArrayList<>();
        new java.util.ArrayList(allFields.keySet()).stream().sorted().forEach(( role) -> {
            spoon.test.metamodel.MetamodelProperty field = allFields.remove(role);
            primitiveFields.add(spoon.generating.MetamodelGenerator.printField(field));
        });
        if ((allFields.isEmpty()) == false) {
            throw new spoon.SpoonException("There remained some fields?");
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        primitiveFields.addAll(elementFields);
        primitiveFields.forEach(( s) -> sb.append(s).append('\n'));
        return sb.toString();
    }

    private static java.lang.String printField(spoon.test.metamodel.MetamodelProperty field) {
        java.util.Map<java.lang.String, java.lang.String> valuesMap = new java.util.HashMap<>();
        valuesMap.put("role", field.getRole().name());
        valuesMap.put("derived", java.lang.String.valueOf(field.isDerived()));
        valuesMap.put("unsetable", java.lang.String.valueOf(field.isUnsettable()));
        org.apache.commons.lang3.text.StrSubstitutor strSubst = new org.apache.commons.lang3.text.StrSubstitutor(valuesMap);
        return strSubst.replace("\t.field(CtRole.${role}, ${derived}, ${unsetable})");
    }

    private static java.util.List<spoon.reflect.path.CtRole> getRoleScanningOrderOfType(spoon.reflect.factory.Factory factory, java.lang.Class<? extends spoon.reflect.declaration.CtElement> iface) {
        java.util.List<spoon.reflect.path.CtRole> roles = new java.util.ArrayList<>();
        spoon.reflect.declaration.CtElement ele = factory.Core().create(iface);
        ele.accept(new spoon.reflect.visitor.CtScanner() {
            @java.lang.Override
            public void scan(spoon.reflect.path.CtRole role, spoon.reflect.declaration.CtElement element) {
                roles.add(role);
            }

            @java.lang.Override
            public void scan(spoon.reflect.path.CtRole role, java.util.Collection<? extends spoon.reflect.declaration.CtElement> elements) {
                roles.add(role);
            }

            @java.lang.Override
            public void scan(spoon.reflect.path.CtRole role, java.util.Map<java.lang.String, ? extends spoon.reflect.declaration.CtElement> elements) {
                roles.add(role);
            }
        });
        return roles;
    }
}

