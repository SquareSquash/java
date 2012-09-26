// Copyright 2012 Square, Inc.
package com.squareup.squash;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SquashEntryTest {

  private Gson gson = new Gson();
  private EntryFactory factory = new EntryFactory();

  @Test public void testNoException() throws Exception {
    final String message = "I LOVE TACOS";
    final SquashEntry logEntry = factory.create(message, null);
    SquashEntry deserialized = serializeAndDeserialize(logEntry);
    assertThat(deserialized.backtraces).isNull();
    assertThat(deserialized.ivars).isNull();
    assertThat(deserialized.log_message).isEqualTo(message);
    assertThat(deserialized.parent_exceptions).isEmpty();
    assertThat(deserialized.class_name).isNull();
  }

  private SquashEntry serializeAndDeserialize(SquashEntry logEntry) throws IOException {
    return gson.fromJson(gson.toJson(logEntry), SquashEntry.class);
  }

  @Test public void testWithSimpleExceptionNoIvars() throws Exception {
    final String logMessage = "I LOVE TACOS";
    final Throwable exception = mock(Throwable.class);
    StackTraceElement s0 =
        new StackTraceElement("com.taco.Taco", "digest", "core-android/src/com/taco/Taco.java", 50);
    StackTraceElement s1 =
        new StackTraceElement("com.taco.Taco", "eat", "core-android/src/com/taco/Taco.java", 80);
    StackTraceElement s2 =
        new StackTraceElement("com.taco.Dude", "purchase", "core-android/src/com/taco/Dude.java",
            112);
    StackTraceElement[] myLittleStackTrace = new StackTraceElement[] {s0, s1, s2};
    final String message = "ExceptionMessage";
    when(exception.getMessage()).thenReturn(message);
    when(exception.getStackTrace()).thenReturn(myLittleStackTrace);
    final SquashEntry logEntry = factory.create(logMessage, exception);
    SquashEntry deserialized = serializeAndDeserialize(logEntry);
    assertThat(deserialized.backtraces).isNotEmpty();
    final List<Object> backtrace = deserialized.backtraces.get(0);
    assertThat(backtrace.get(0)).isEqualTo(Thread.currentThread().getName());
    assertThat(backtrace.get(1)).isEqualTo(true);
    List<List<Object>> stackElements = (List<List<Object>>) backtrace.get(2);
    assertBacktracesMatch(myLittleStackTrace, stackElements);
    assertThat(deserialized.ivars).isEmpty();
    assertThat(deserialized.log_message).isEqualTo(logMessage);
    assertThat(deserialized.message).isEqualTo(message);
    assertThat(deserialized.parent_exceptions).isEmpty();
    assertThat(deserialized.class_name).isEqualTo(exception.getClass().getName());
  }

  private void assertBacktracesMatch(StackTraceElement[] myLittleStackTrace,
      List<List<Object>> stackElements) {
    for (int i = 0, stackElementsSize = stackElements.size(); i < stackElementsSize; i++) {
      List<Object> stackElement = stackElements.get(i);
      StackTraceElement expected = myLittleStackTrace[i];
      assertThat(stackElement.get(0)).isEqualTo(SquashBacktrace.JAVA_PREFIX);
      assertThat(stackElement.get(1)).isEqualTo(expected.getFileName());
      assertThat(((Double) stackElement.get(2)).intValue()).isEqualTo(expected.getLineNumber());
      assertThat(stackElement.get(3)).isEqualTo(expected.getMethodName());
      assertThat(stackElement.get(4)).isEqualTo(expected.getClassName());
    }
  }

  @Test public void testNestedExceptions() throws Exception {
    final String logMessage = "I LOVE TACOS";
    final Throwable nestedException = mock(Throwable.class);
    final Throwable doublyNestedException = mock(Throwable.class);
    final Throwable exception = mock(Throwable.class);
    StackTraceElement n0 = new StackTraceElement("com.taco.Burrito", "digest",
        "core-android/src/com/burrito/Burrito.java", 45);
    StackTraceElement n1 = new StackTraceElement("com.taco.Burrito", "eat",
        "core-android/src/com/burrito/Burrito.java", 10);
    StackTraceElement n2 =
        new StackTraceElement("com.taco.Dude", "purchase", "core-android/src/com/taco/Dude.java",
            65);
    StackTraceElement[] nestedStackTrace = new StackTraceElement[] {n0, n1, n2};
    StackTraceElement z0 =
        new StackTraceElement("com.taco.Dude", "wheresmycar", "core-android/src/com/taco/Dude.java",
            455);
    StackTraceElement z1 =
        new StackTraceElement("com.bro.Bro", "hollerback", "core-android/src/com/bro/Bro.java",
            105);
    StackTraceElement z2 =
        new StackTraceElement("com.taco.Dude", "holler", "core-android/src/com/taco/Dude.java",
            655);
    StackTraceElement[] doublyNestedStackTrace = new StackTraceElement[] {z0, z1, z2};
    StackTraceElement s0 =
        new StackTraceElement("com.taco.Taco", "digest", "core-android/src/com/taco/Taco.java", 50);
    StackTraceElement s1 =
        new StackTraceElement("com.taco.Taco", "eat", "core-android/src/com/taco/Taco.java", 80);
    StackTraceElement s2 =
        new StackTraceElement("com.taco.Dude", "purchase", "core-android/src/com/taco/Dude.java",
            112);
    StackTraceElement[] myLittleStackTrace = new StackTraceElement[] {s0, s1, s2};
    final String message = "ExceptionMessage";
    when(exception.getMessage()).thenReturn(message);
    when(exception.getStackTrace()).thenReturn(myLittleStackTrace);
    when(exception.getCause()).thenReturn(nestedException);

    final String nestedExceptionMessage = "NestedExceptionMessage";
    when(nestedException.getMessage()).thenReturn(nestedExceptionMessage);
    when(nestedException.getStackTrace()).thenReturn(nestedStackTrace);
    when(nestedException.getCause()).thenReturn(doublyNestedException);

    final String doublyNestedExceptionMessage = "DoublyNestedExceptionMessage";
    when(doublyNestedException.getMessage()).thenReturn(doublyNestedExceptionMessage);
    when(doublyNestedException.getStackTrace()).thenReturn(doublyNestedStackTrace);

    final SquashEntry logEntry = factory.create(logMessage, exception);
    SquashEntry deserialized = serializeAndDeserialize(logEntry);
    assertThat(deserialized.backtraces).isNotEmpty();
    List<Object> backtrace = deserialized.backtraces.get(0);
    assertThat(backtrace.get(0)).isEqualTo(Thread.currentThread().getName());
    assertThat(backtrace.get(1)).isEqualTo(true);
    List<List<Object>> stackElements = (List<List<Object>>) backtrace.get(2);
    assertBacktracesMatch(myLittleStackTrace, stackElements);
    assertThat(deserialized.ivars).isEmpty();
    assertThat(deserialized.log_message).isEqualTo(logMessage);
    assertThat(deserialized.message).isEqualTo(message);
    final List<SquashBacktrace.NestedException> nestedExceptions = deserialized.parent_exceptions;
    assertThat(nestedExceptions).hasSize(2);

    final SquashBacktrace.NestedException nested1 = nestedExceptions.get(0);
    assertThat(nested1.class_name).isEqualTo(nestedException.getClass().getName());
    assertThat(nested1.ivars).isEmpty();
    assertThat(nested1.message).isEqualTo(nestedExceptionMessage);
    backtrace = nested1.backtraces.get(0);
    assertThat(backtrace.get(0)).isEqualTo(Thread.currentThread().getName());
    assertThat(backtrace.get(1)).isEqualTo(true);
    assertBacktracesMatch(nestedStackTrace, (List<List<Object>>) backtrace.get(2));

    final SquashBacktrace.NestedException nested2 = nestedExceptions.get(1);
    assertThat(nested2.class_name).isEqualTo(doublyNestedException.getClass().getName());
    assertThat(nested2.ivars).isEmpty();
    assertThat(nested2.message).isEqualTo(doublyNestedExceptionMessage);
    backtrace = nested1.backtraces.get(0);
    assertThat(backtrace.get(0)).isEqualTo(Thread.currentThread().getName());
    assertThat(backtrace.get(1)).isEqualTo(true);
    assertBacktracesMatch(nestedStackTrace, (List<List<Object>>) backtrace.get(2));
  }

  @Test public void testInfinitelyNestedExceptions() throws Exception {
    final String logMessage = "I LOVE TACOS";
    final Throwable nestedException = mock(Throwable.class);
    final Throwable doublyNestedException = mock(Throwable.class);
    final Throwable exception = mock(Throwable.class);
    StackTraceElement n0 = new StackTraceElement("com.taco.Burrito", "digest",
        "core-android/src/com/burrito/Burrito.java", 45);
    StackTraceElement n1 = new StackTraceElement("com.taco.Burrito", "eat",
        "core-android/src/com/burrito/Burrito.java", 10);
    StackTraceElement n2 =
        new StackTraceElement("com.taco.Dude", "purchase", "core-android/src/com/taco/Dude.java",
            65);
    StackTraceElement[] nestedStackTrace = new StackTraceElement[] {n0, n1, n2};
    StackTraceElement z0 =
        new StackTraceElement("com.taco.Dude", "wheresmycar", "core-android/src/com/taco/Dude.java",
            455);
    StackTraceElement z1 =
        new StackTraceElement("com.bro.Bro", "hollerback", "core-android/src/com/bro/Bro.java",
            105);
    StackTraceElement z2 =
        new StackTraceElement("com.taco.Dude", "holler", "core-android/src/com/taco/Dude.java",
            655);
    StackTraceElement[] doublyNestedStackTrace = new StackTraceElement[] {z0, z1, z2};
    StackTraceElement s0 =
        new StackTraceElement("com.taco.Taco", "digest", "core-android/src/com/taco/Taco.java", 50);
    StackTraceElement s1 =
        new StackTraceElement("com.taco.Taco", "eat", "core-android/src/com/taco/Taco.java", 80);
    StackTraceElement s2 =
        new StackTraceElement("com.taco.Dude", "purchase", "core-android/src/com/taco/Dude.java",
            112);
    StackTraceElement[] myLittleStackTrace = new StackTraceElement[] {s0, s1, s2};
    final String message = "ExceptionMessage";
    when(exception.getMessage()).thenReturn(message);
    when(exception.getStackTrace()).thenReturn(myLittleStackTrace);
    when(exception.getCause()).thenReturn(nestedException);

    final String nestedExceptionMessage = "NestedExceptionMessage";
    when(nestedException.getMessage()).thenReturn(nestedExceptionMessage);
    when(nestedException.getStackTrace()).thenReturn(nestedStackTrace);
    when(nestedException.getCause()).thenReturn(doublyNestedException);

    final String doublyNestedExceptionMessage = "DoublyNestedExceptionMessage";
    when(doublyNestedException.getMessage()).thenReturn(doublyNestedExceptionMessage);
    when(doublyNestedException.getStackTrace()).thenReturn(doublyNestedStackTrace);
    when(doublyNestedException.getCause()).thenReturn(doublyNestedException);

    final SquashEntry logEntry = factory.create(logMessage, exception);
    SquashEntry deserialized = serializeAndDeserialize(logEntry);
    assertThat(deserialized.backtraces).isNotEmpty();
    List<Object> backtrace = deserialized.backtraces.get(0);
    assertThat(backtrace.get(0)).isEqualTo(Thread.currentThread().getName());
    assertThat(backtrace.get(1)).isEqualTo(true);
    List<List<Object>> stackElements = (List<List<Object>>) backtrace.get(2);
    assertBacktracesMatch(myLittleStackTrace, stackElements);
    assertThat(deserialized.ivars).isEmpty();
    assertThat(deserialized.log_message).isEqualTo(logMessage);
    assertThat(deserialized.message).isEqualTo(message);
    final List<SquashBacktrace.NestedException> nestedExceptions = deserialized.parent_exceptions;
    assertThat(nestedExceptions).hasSize(2);

    final SquashBacktrace.NestedException nested1 = nestedExceptions.get(0);
    assertThat(nested1.class_name).isEqualTo(nestedException.getClass().getName());
    assertThat(nested1.ivars).isEmpty();
    assertThat(nested1.message).isEqualTo(nestedExceptionMessage);
    backtrace = nested1.backtraces.get(0);
    assertThat(backtrace.get(0)).isEqualTo(Thread.currentThread().getName());
    assertThat(backtrace.get(1)).isEqualTo(true);
    assertBacktracesMatch(nestedStackTrace, (List<List<Object>>) backtrace.get(2));

    final SquashBacktrace.NestedException nested2 = nestedExceptions.get(1);
    assertThat(nested2.class_name).isEqualTo(doublyNestedException.getClass().getName());
    assertThat(nested2.ivars).isEmpty();
    assertThat(nested2.message).isEqualTo(doublyNestedExceptionMessage);
    backtrace = nested1.backtraces.get(0);
    assertThat(backtrace.get(0)).isEqualTo(Thread.currentThread().getName());
    assertThat(backtrace.get(1)).isEqualTo(true);
    assertBacktracesMatch(nestedStackTrace, (List<List<Object>>) backtrace.get(2));
  }

  private class EntryFactory {
    public SquashEntry create(String logMessage, Throwable exception) {
      return new SquashEntry("testclient", "testAPIKey", logMessage, exception, "testAppVersion",
          42, "testSHA", "testDeviceId", "testEndpoint", "testUserId", "Debug");
    }
  }
}
