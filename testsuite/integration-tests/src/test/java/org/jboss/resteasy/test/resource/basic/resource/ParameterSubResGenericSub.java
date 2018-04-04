package org.jboss.resteasy.test.resource.basic.resource;

import javax.ws.rs.Path;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

@Path("generic")
public class ParameterSubResGenericSub {
    @SuppressWarnings(value = "unchecked")
    @Path("sub")
    public ParameterSubResDoubleInterface doit() {
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                List<Double> list = (List<Double>) args[0];
                return list.get(0).toString();
            }
        };
        Class[] intfs = {ParameterSubResDoubleInterface.class};
        return (ParameterSubResDoubleInterface) Proxy.newProxyInstance(ParameterSubResGenericSub.class.getClassLoader(), intfs, handler);
    }
}
