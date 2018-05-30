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
package spoon.support;


/**
 * This class provides a regular Java serialization-based implementation of the
 * model streamer.
 */
public class SerializationModelStreamer implements spoon.reflect.ModelStreamer {
    /**
     * Default constructor.
     */
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
            // create query using factory directly
            // because any try to call CtElement#map or CtElement#filterChildren will fail on uninitialized factory
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

