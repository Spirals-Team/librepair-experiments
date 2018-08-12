package io.symonk.sylenium.location.page_factory;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.Field;

/**
 * Custom extension of the Page Factory in order to use our custom $Element within page(s) And to
 * remove any explicit driver code from within page objects, we want them very simple.
 */
public class SyleniumPageFactory extends PageFactory {

  /**
   * Loop through all classes of a given page, up until the parent Object.class
   *
   * @param decorator -> Custom implementation of FieldDecorator
   * @param thePage -> The page Object to instantiate
   */
  public static void initElements(final FieldDecorator decorator, final Object thePage) {
    Class<?> proxy = thePage.getClass();
    while (proxy.getDeclaringClass() != Object.class) {
      proxyAllPageObjectElements(decorator, thePage, proxy);
      proxy = proxy.getSuperclass();
    }
  }

  /**
   * Loop all fields of the class and initialize them if they are not already initialized.
   *
   * @param decorator -> decorator to use
   * @param thePage -> the page object
   * @param proxy -> the proxy class
   */
  private static void proxyAllPageObjectElements(final FieldDecorator decorator, final Object thePage, final Class<?> proxy) {
    final Field[] proxyFields = proxy.getDeclaredFields();
    for (final Field field : proxyFields) {
      if (isFieldAlreadyInitialized(thePage, field)) continue;
      try {
      final Object result = decorator.decorate(thePage.getClass().getClassLoader(), field);
        field.setAccessible(true);
        field.set(thePage, field);
        } catch(IllegalAccessException exception) {
          throw new RuntimeException("Critical problem when decorating the field: " + field);
      }
    }
  }

    /**
     * Check if a particular field in the page has already been initialized / decorated
     * @param page -> the page object
     * @param field -> the field to initialize
     * @return -> boolean if already initialized
     */
  private static boolean isFieldAlreadyInitialized(final Object page, final Field field) {
    try {
      field.setAccessible(true);
      return field.get(page) == null;
    } catch (final IllegalAccessException exception) {
      throw new RuntimeException(exception);
    }
  }
}
