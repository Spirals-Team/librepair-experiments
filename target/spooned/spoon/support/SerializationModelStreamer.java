package spoon.support;


public class SerializationModelStreamer implements spoon.reflect.ModelStreamer {
    public SerializationModelStreamer() {
    }

    public void save(spoon.reflect.factory.Factory f, java.io.OutputStream out) throws java.io.IOException {
        java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(out);
        oos.writeObject(f);
        oos.flush();
        oos.close();
    }

    public spoon.reflect.factory.Factory load(java.io.InputStream in) throws java.io.IOException {
        try {
            java.io.ObjectInputStream ois = new java.io.ObjectInputStream(in);
            final spoon.reflect.factory.Factory f = ((spoon.reflect.factory.Factory) (ois.readObject()));
            f.createQuery(f.Module().getAllModules().toArray()).filterChildren(new spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtElement>() {
                @java.lang.Override
                public boolean matches(spoon.reflect.declaration.CtElement e) {
                    e.setFactory(f);
                    return false;
                }
            }).list();
            ois.close();
            return f;
        } catch (java.lang.ClassNotFoundException e) {
            spoon.Launcher.LOGGER.error(e.getMessage(), e);
            throw new java.io.IOException(e.getMessage());
        }
    }
}

