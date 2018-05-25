package org.objectweb.carol.jndi.spi;


public class CmiContext extends org.objectweb.carol.jndi.spi.AbsContext implements javax.naming.Context {
    public CmiContext(javax.naming.Context cmiContext) {
        super(cmiContext);
    }

    protected java.lang.Object unwrapObject(java.lang.Object o, javax.naming.Name name) throws javax.naming.NamingException {
        return super.defaultUnwrapObject(o, name);
    }

    protected java.lang.Object wrapObject(java.lang.Object o, javax.naming.Name name, boolean replace) throws javax.naming.NamingException {
        try {
            if ((!(o instanceof java.rmi.Remote)) && (o instanceof javax.naming.Referenceable)) {
                return new org.objectweb.carol.jndi.wrapping.UnicastJNDIReferenceWrapper(((javax.naming.Referenceable) (o)).getReference(), getObjectPort());
            }else
                if ((!(o instanceof java.rmi.Remote)) && (o instanceof javax.naming.Reference)) {
                    return new org.objectweb.carol.jndi.wrapping.UnicastJNDIReferenceWrapper(((javax.naming.Reference) (o)), getObjectPort());
                }else
                    if ((((!(o instanceof java.rmi.Remote)) && (!(o instanceof javax.naming.Referenceable))) && (!(o instanceof javax.naming.Reference))) && (o instanceof java.io.Serializable)) {
                        org.objectweb.carol.jndi.wrapping.JNDIResourceWrapper irw = new org.objectweb.carol.jndi.wrapping.JNDIResourceWrapper(((java.io.Serializable) (o)));
                        javax.rmi.CORBA.PortableRemoteObjectDelegate proDelegate = org.objectweb.carol.util.configuration.ConfigurationRepository.getCurrentConfiguration().getProtocol().getPortableRemoteObject();
                        proDelegate.exportObject(irw);
                        java.rmi.Remote oldObj = ((java.rmi.Remote) (addToExported(name, irw)));
                        if (oldObj != null) {
                            if (replace) {
                                proDelegate.unexportObject(oldObj);
                            }else {
                                proDelegate.unexportObject(irw);
                                addToExported(name, oldObj);
                                throw new javax.naming.NamingException((((("Object '" + o) + "' with name '") + name) + "' is already bind"));
                            }
                        }
                        return irw;
                    }else {
                        return o;
                    }


        } catch (java.lang.Exception e) {
            throw org.objectweb.carol.rmi.exception.NamingExceptionHelper.create(((((("Cannot wrap object '" + o) + "' with name '") + name) + "' : ") + (e.getMessage())), e);
        }
    }
}

