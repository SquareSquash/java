// Copyright 2012 Square, Inc.
package com.squareup.squash;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Creates the Squash stacktrace format for serialization by gson. */
public final class SquashBacktrace {

  public static final String THREAD_0 = "Thread 0";

  private SquashBacktrace() {
    // Should not be instantiated: this is a utility class.
  }

  public static List<List<Object>> getBacktraces(Throwable error) {
    if (error == null) {
      return null;
    }
    final ArrayList<List<Object>> threadList = new ArrayList<List<Object>>();
    final ArrayList<Object> thread0 = new ArrayList<Object>();
    thread0.add(THREAD_0);
    thread0.add(true);
    thread0.add(getStacktraceArray(error));
    threadList.add(thread0);
    return threadList;
  }

  private static List<List<Object>> getStacktraceArray(Throwable error) {
    List<List<Object>> stackElems = new ArrayList<List<Object>>();
    for (StackTraceElement element : error.getStackTrace()) {
      List<Object> elementList = new ArrayList<Object>();
      elementList.add(element.getFileName());
      elementList.add(element.getLineNumber());
      elementList.add(element.getMethodName());
      elementList.add(element.getClassName());
      stackElems.add(elementList);
    }
    return stackElems;
  }

  public static Map<String, Object> getIvars(Throwable error) {
    if (error == null) {
      return null;
    }
    Map<String, Object> ivars = new HashMap<String, Object>();
    final Field[] fields = error.getClass().getDeclaredFields();
    for (Field field : fields) {
      try {
        if (!Modifier.isStatic(field.getModifiers()) // Ignore static fields.
            && !field.getName().startsWith("CGLIB")) { // Ignore mockito stuff in tests.
          if (!field.isAccessible()) {
            field.setAccessible(true);
          }
          Object val = field.get(error);
          ivars.put(field.getName(), val);
        }
      } catch (IllegalAccessException e) {
        ivars.put(field.getName(), "Exception accessing field: " + e);
      }
    }
    return ivars;
  }

  /**
   * Recursive method that follows the "cause" exceptions all the way down the stack, adding them to
   * the passed-in list.
   */
  public static void populateNestedExceptions(List<NestedException> nestedExceptions,
      Throwable error) {
    if (error == null || error.getCause() == null) {
      return;
    }
    final Throwable cause = error.getCause();
    NestedException doc = new NestedException(cause.getClass().getName(), cause.getMessage(),
        getStacktraceArray(cause), getIvars(cause));
    nestedExceptions.add(doc);
    // Exceptions all the way down!
    populateNestedExceptions(nestedExceptions, cause);
  }

  /** Wrapper object for nested exceptions. */
  public static class NestedException {
    final String class_name;
    final String message;
    final List<List<Object>> backtraces;
    final Map<String, Object> ivars;

    public NestedException(String className, String message, List<List<Object>> backtraces,
        Map<String, Object> ivars) {
      this.class_name = className;
      this.message = message;
      this.backtraces = backtraces;
      this.ivars = ivars;
    }
  }
}
