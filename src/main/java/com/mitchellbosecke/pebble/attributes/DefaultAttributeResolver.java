package com.mitchellbosecke.pebble.attributes;

public class DefaultAttributeResolver implements AttributeResolver {
  private final AttributeResolver mapResolver = new MapResolver();
  private final AttributeResolver arrayResolver = new ArrayResolver();
  private final AttributeResolver listResolver = new ListResolver();
  private final AttributeResolver memberResolver = new MemberResolver();

  @Override
  public ResolvedAttribute resolve(Object instance,
                                   Object attribute,
                                   Object[] argumentValues,
                                   boolean isStrictVariables,
                                   String filename,
                                   int lineNumber) {
    if (instance != null) {
      ResolvedAttribute resolved = mapResolver.resolve(instance, attribute, argumentValues, isStrictVariables, filename, lineNumber);
      if (resolved != null) {
        return resolved;
      }

      resolved = arrayResolver.resolve(instance, attribute, argumentValues, isStrictVariables, filename, lineNumber);
      if (resolved != null) {
        return resolved;
      }

      resolved = listResolver.resolve(instance, attribute, argumentValues, isStrictVariables, filename, lineNumber);
      if (resolved != null) {
        return resolved;
      }

      resolved = memberResolver.resolve(instance, attribute, argumentValues, isStrictVariables, filename, lineNumber);
      if (resolved != null) {
        return resolved;
      }
    }
    return null;
  }
}
