package org.apache.samoa.moa.options;

/*
 * #%L
 * SAMOA
 * %%
 * Copyright (C) 2014 - 2015 Apache Software Foundation
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.File;

import org.apache.samoa.moa.core.ObjectRepository;
import org.apache.samoa.moa.tasks.Task;
import org.apache.samoa.moa.tasks.TaskMonitor;

import com.github.javacliparser.AbstractOption;
import com.github.javacliparser.SerializeUtils;

/**
 * Abstract class option.
 * 
 * @author Richard Kirkby (rkirkby@cs.waikato.ac.nz)
 * @version $Revision$
 */
public abstract class AbstractClassOption extends AbstractOption {

  private static final long serialVersionUID = 1L;

  /** The prefix text to use to indicate file. */
  public static final String FILE_PREFIX_STRING = "file:";

  /** The prefix text to use to indicate inmem. */
  public static final String INMEM_PREFIX_STRING = "inmem:";

  /** The current object */
  protected Object currentValue;

  /** The class type */
  protected Class<?> requiredType;

  /** The default command line interface text. */
  protected String defaultCLIString;

  /** The null text. */
  protected String nullString;

  /**
   * Creates a new instance of an abstract option given its class name, command line interface text, its purpose, its
   * class type and its default command line interface text.
   * 
   * @param name
   *          the name of this option
   * @param cliChar
   *          the command line interface text
   * @param purpose
   *          the text describing the purpose of this option
   * @param requiredType
   *          the class type
   * @param defaultCLIString
   *          the default command line interface text
   */
  public AbstractClassOption(String name, char cliChar, String purpose,
      Class<?> requiredType, String defaultCLIString) {
    this(name, cliChar, purpose, requiredType, defaultCLIString, null);
  }

  /**
   * Creates a new instance of an abstract option given its class name, command line interface text, its purpose, its
   * class type, default command line interface text, and its null text.
   * 
   * @param name
   *          the name of this option
   * @param cliChar
   *          the command line interface text
   * @param purpose
   *          the text describing the purpose of this option
   * @param requiredType
   *          the class type
   * @param defaultCLIString
   *          the default command line interface text
   * @param nullString
   *          the null text
   */
  public AbstractClassOption(String name, char cliChar, String purpose,
      Class<?> requiredType, String defaultCLIString, String nullString) {
    super(name, cliChar, purpose);
    this.requiredType = requiredType;
    this.defaultCLIString = defaultCLIString;
    this.nullString = nullString;
    resetToDefault();
  }

  /**
   * Sets current object.
   * 
   * @param obj
   *          the object to set as current.
   */
  public void setCurrentObject(Object obj) {
    if (((obj == null) && (this.nullString != null))
        || this.requiredType.isInstance(obj)
        || (obj instanceof String)
        || (obj instanceof File)
        || ((obj instanceof Task) && this.requiredType.isAssignableFrom(((Task) obj).getTaskResultType()))) {
      this.currentValue = obj;
    } else {
      throw new IllegalArgumentException("Object not of required type.");
    }
  }

  /**
   * Returns the current object.
   * 
   * @return the current object
   */
  public Object getPreMaterializedObject() {
    return this.currentValue;
  }

  /**
   * Gets the class type of this option.
   * 
   * @return the class type of this option
   */
  public Class<?> getRequiredType() {
    return this.requiredType;
  }

  /**
   * Gets the null string of this option.
   * 
   * @return the null string of this option
   */
  public String getNullString() {
    return this.nullString;
  }

  /**
   * Gets a materialized object of this option.
   * 
   * @param monitor
   *          the task monitor to use
   * @param repository
   *          the object repository to use
   * @return the materialized object
   */
  public Object materializeObject(TaskMonitor monitor,
      ObjectRepository repository) {
    if ((this.currentValue == null)
        || this.requiredType.isInstance(this.currentValue)) {
      return this.currentValue;
    } else if (this.currentValue instanceof String) {
      if (repository != null) {
        Object inmemObj = repository.getObjectNamed((String) this.currentValue);
        if (inmemObj == null) {
          throw new RuntimeException("No object named "
              + this.currentValue + " found in repository.");
        }
        return inmemObj;
      }
      throw new RuntimeException("No object repository available.");
    } else if (this.currentValue instanceof Task) {
      Task task = (Task) this.currentValue;
      Object result = task.doTask(monitor, repository);
      return result;
    } else if (this.currentValue instanceof File) {
      File inputFile = (File) this.currentValue;
      Object result = null;
      try {
        result = SerializeUtils.readFromFile(inputFile);
      } catch (Exception ex) {
        throw new RuntimeException("Problem loading "
            + this.requiredType.getName() + " object from file '"
            + inputFile.getName() + "':\n" + ex.getMessage(), ex);
      }
      return result;
    } else {
      throw new RuntimeException(
          "Could not materialize object of required type "
              + this.requiredType.getName() + ", found "
              + this.currentValue.getClass().getName()
              + " instead.");
    }
  }

  @Override
  public String getDefaultCLIString() {
    return this.defaultCLIString;
  }

  /**
   * Gets the command line interface text of the class.
   * 
   * @param aClass
   *          the class
   * @param requiredType
   *          the class type
   * @return the command line interface text of the class
   */
  public static String classToCLIString(Class<?> aClass, Class<?> requiredType) {
    String className = aClass.getName();
    String packageName = requiredType.getPackage().getName();
    if (className.startsWith(packageName)) {
      // cut off package name
      className = className.substring(packageName.length() + 1, className.length());
    } else if (Task.class.isAssignableFrom(aClass)) {
      packageName = Task.class.getPackage().getName();
      if (className.startsWith(packageName)) {
        // cut off task package name
        className = className.substring(packageName.length() + 1,
            className.length());
      }
    }
    return className;
  }

  @Override
  public abstract String getValueAsCLIString();

  @Override
  public abstract void setValueViaCLIString(String s);

  // @Override
  // public abstract JComponent getEditComponent();

  /**
   * Gets the class name without its package name prefix.
   * 
   * @param className
   *          the name of the class
   * @param expectedType
   *          the type of the class
   * @return the class name without its package name prefix
   */
  public static String stripPackagePrefix(String className, Class<?> expectedType) {
    if (className.startsWith(expectedType.getPackage().getName())) {
      return className.substring(expectedType.getPackage().getName().length() + 1);
    }
    return className;
  }
}
