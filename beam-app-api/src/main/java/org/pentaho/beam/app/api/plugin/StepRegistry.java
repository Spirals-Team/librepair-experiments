package org.pentaho.beam.app.api.plugin;

import org.pentaho.beam.app.api.step.BaseStep;
import org.pentaho.beam.app.api.step.Step;
import org.pentaho.beam.app.api.stepmeta.StepMeta;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Step Registry
 * <p>
 * Scans the classpath for all classes that extend the BaseStep implementation.  Using reflection we create a map to
 * lookup the logic class based on the meta class.
 * <p>
 * TODO Revisit this; is there a cleaner way that uses annotations.
 * <p>
 * Created by ccaspanello on 1/29/18.
 */
public class StepRegistry implements Serializable {

  private static final Logger LOG = LoggerFactory.getLogger( StepRegistry.class );

  private Map<Class<? extends StepMeta>, Class<? extends Step>> registry;

  public StepRegistry() {
    registry = new HashMap<>();
  }

  public void init() {
    Plugin csvInputPlugin = loadPlugin( "CsvInput" );
    registry.putAll( csvInputPlugin.steps().getSteps() );

    Plugin csvOutputPlugin = loadPlugin( "CsvOutput" );
    registry.putAll( csvOutputPlugin.steps().getSteps() );
  }

  public <T extends Step> T createStep( StepMeta stepMeta ) {
    Class<? extends Step> clazz = registry.get( stepMeta.getClass() );
    try {
      Constructor constructor = clazz.getConstructor( stepMeta.getClass() );
      return (T) constructor.newInstance( stepMeta );
    } catch ( NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e ) {
      throw new RuntimeException( "Unexpected error trying to instantiate Step: ", e );
    }
  }

  private Plugin loadPlugin( String pluginName ) {
    URL jar = lookupPlugin( pluginName );
    URL[] jars = new URL[] { jar };
    try ( URLClassLoader classLoader = new URLClassLoader( jars, ClassLoader.getSystemClassLoader() );
          JarFile jarFile = new JarFile( jar.getFile() ) ) {

      Enumeration<JarEntry> e = jarFile.entries();
      while ( e.hasMoreElements() ) {
        JarEntry je = e.nextElement();
        if ( !je.isDirectory() && je.getName().endsWith( ".class" ) ) {
          String className = je.getName().substring( 0, je.getName().length() - 6 );
          className = className.replace( '/', '.' );
          LOG.debug( "Loading Class: {}", className );
          classLoader.loadClass( className );
        }
      }

      String pluginClass = jarFile.getManifest().getMainAttributes().getValue( "Plugin-Class" );
      return (Plugin) Class.forName( pluginClass, true, classLoader ).newInstance();
    } catch ( Exception e ) {
      throw new RuntimeException( "Unexpected error loading class.", e );
    }

  }

  private URL lookupPlugin( String pluginName ) {
    try {
      File pluginProject = new File( "/Users/ccaspanello/Desktop/beam-prototype/plugins" );
      Map<String, File> map = new HashMap<>();
      map.put( "CsvInput", new File( pluginProject, "csv-input/target/csv-input-1.0-SNAPSHOT.jar" ) );
      map.put( "CsvOutput", new File( pluginProject, "csv-output/target/csv-output-1.0-SNAPSHOT.jar" ) );
      return map.get( pluginName ).toURI().toURL();
    } catch ( MalformedURLException e ) {
      throw new RuntimeException( "Unexpected error trying to lookup plugin.", e );
    }
  }

  public Class<? extends StepMeta> getStepMeta( String className ) {
    return registry.keySet().stream()
      .filter( c -> c.getCanonicalName().equals( className ) )
      .findFirst().get();
  }
}
