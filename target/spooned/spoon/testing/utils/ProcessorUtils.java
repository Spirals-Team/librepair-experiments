package spoon.testing.utils;


public final class ProcessorUtils {
    private static final com.fasterxml.jackson.databind.ObjectMapper converter = new com.fasterxml.jackson.databind.ObjectMapper();

    private ProcessorUtils() {
        throw new java.lang.AssertionError();
    }

    public static void process(spoon.reflect.factory.Factory factory, java.util.Collection<spoon.processing.Processor<?>> processors) {
        final spoon.support.compiler.jdt.JDTBasedSpoonCompiler compiler = ((spoon.support.compiler.jdt.JDTBasedSpoonCompiler) (new spoon.Launcher().createCompiler(factory)));
        compiler.process(processors);
    }

    public static void initProperties(spoon.processing.Processor<?> p, spoon.processing.ProcessorProperties properties) {
        if (properties != null) {
            for (java.lang.reflect.Field f : spoon.support.util.RtHelper.getAllFields(p.getClass())) {
                if (f.isAnnotationPresent(spoon.processing.Property.class)) {
                    java.lang.Object obj = properties.get(f.getType(), f.getName());
                    if (obj != null) {
                        f.setAccessible(true);
                        try {
                            f.set(p, obj);
                        } catch (java.lang.Exception e) {
                            throw new spoon.SpoonException(e);
                        }
                    }else {
                        obj = properties.get(java.lang.String.class, f.getName());
                        if (obj != null) {
                            try {
                                obj = spoon.testing.utils.ProcessorUtils.converter.readValue(((java.lang.String) (obj)), f.getType());
                                f.setAccessible(true);
                                f.set(p, obj);
                            } catch (java.lang.Exception e) {
                                throw new spoon.SpoonException(("Error while assigning the value to " + (f.getName())), e);
                            }
                        }else {
                            p.getFactory().getEnvironment().report(p, org.apache.log4j.Level.WARN, ((("No value found for property '" + (f.getName())) + "' in processor ") + (p.getClass().getName())));
                        }
                    }
                }
            }
        }
    }
}

