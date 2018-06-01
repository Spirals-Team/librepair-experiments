package com.economizate.loader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.economizate.servicios.impl.Propiedad;

public class MyClassLoader extends ClassLoader{

    public MyClassLoader(ClassLoader parent) {
        super(parent);
    }

    public Class loadClass(String name) throws ClassNotFoundException {
    	
    	
        if(!Propiedad.getInstance().getPropiedad("nubeSeleccionada").equals(name) 
        		&& !Propiedad.getInstance().getPropiedad("nubes").equals(name)
        		&& !Propiedad.getInstance().getPropiedad("propiedadesExtra").equals(name))
                return super.loadClass(name);
       
        try {
            String url = "file:./src/test/resources/META-INF/services/" + name + ".class";
            URL myUrl = new URL(url);
            URLConnection connection = myUrl.openConnection();
            InputStream input = connection.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int data = input.read();

            while(data != -1){
                buffer.write(data);
                data = input.read();
            }

            input.close();

            byte[] classData = buffer.toByteArray();

            if(name.equals("com.economizate.servicios.INube")) {
            	return defineClass(name,
                        classData, 0, classData.length);
            }
            
            return defineClass("com.economizate.nubeManager." + name,
                    classData, 0, classData.length);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}