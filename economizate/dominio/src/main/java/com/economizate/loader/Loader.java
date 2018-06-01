package com.economizate.loader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.ServiceLoader;

import com.economizate.servicios.IAlertas;
import com.economizate.servicios.INube;

public class Loader {
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
	      ServiceLoader<IAlertas> serviceLoader =
	              ServiceLoader.load(IAlertas.class);
	      
	      for (IAlertas alerta : serviceLoader) {
	  		Object obj = alerta;
	        System.out.println(obj.toString());
	      }
	      
	      
	   /*// Comprobamos que en el classpath no existe el driver de mysql.
	        try {
	            Class.forName("com.economizate.nubeManager.ConnectorDrive");
	        } catch (ClassNotFoundException e) {
	            System.out.println("no se encuentra com.mysql.jdbc.Driver");
	        }

	        // Cambiamos el classpath, anadiendo un nuevo jar al ClassLoader.
	        try {
	            // Se obtiene el ClassLoader y su metodo addURL()
	            URLClassLoader classLoader = ((URLClassLoader) ClassLoader
	                    .getSystemClassLoader());
	            Method metodoAdd = URLClassLoader.class.getDeclaredMethod("addURL",
	                    new Class[] { URL.class });
	            metodoAdd.setAccessible(true);

	            // La URL del jar que queremos anadir
	            URL url = new URL(
	                    "file:///META-INF/services/ConnectorDrive.class");

	            // Se invoca al metodo addURL pasando esa url del jar
	            metodoAdd.invoke(classLoader, new Object[] { url });
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        // Se comprueba que ahora si se puede acceder a esa clase.
	        try {
	            Class.forName("com.economizate.dominio.ConnectorDrive");
	            System.out.println("ya se encuentra com.mysql.jdbc.Driver");
	        } catch (ClassNotFoundException e) {
	            System.out.println("Pues no, sigue sin estar accesible");
	        }
	      
	      ClassLoader ldr = Thread.currentThread().getContextClassLoader();
	      Enumeration<URL> e = ldr.getResources("META-INF/services/" + "ConnectorDrive.class");*/
	      ClassLoader parentClassLoader = MyClassLoader.class.getClassLoader();
	      MyClassLoader classLoader = new MyClassLoader(parentClassLoader);
	      Class myObjectClass = classLoader.loadClass("ConnectorDrive");
	      classLoader.loadClass("NubeEnum");
	      classLoader.loadClass("NubePropiedades");
	      //classLoader.loadClass("ConnectorDrive");
	      //classLoader.loadClass("ConnectorDrive");

	      INube       object1 =
	              (INube) myObjectClass.newInstance();


	      System.out.println(object1.getTipo());
	      //create new class loader so classes can be reloaded.
	      /*classLoader = new MyClassLoader(parentClassLoader);
	      myObjectClass = classLoader.loadClass("ConnectorDrive");*/
	      try {
	    	  object1.conectar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	      

	      /*ServiceLoader<INube> serviceLoaderNube =
	              ServiceLoader.load(INube.class);
	      
	      for (INube alerta : serviceLoaderNube) {
	  		Object obj = alerta;
	        System.out.println(obj.toString());
	      }*/
	  }
	
	

}
