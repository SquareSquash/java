// Copyright 2012 Square Inc.
//
//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
package com.squareup.squash;

import java.util.Date;
import org.junit.Test;
import org.junit.Before;

import static org.fest.assertions.Assertions.assertThat;

public class SquashBacktrace_GetBacktraces_SquashExceptionTest {

  private SquashBacktrace.SquashException fixture;

  @Before public void setUp(){
    fixture = SquashBacktrace.getBacktraces(new Throwable()).get(0);
  }

  @Test public void testGetBacktraces_WhenGivenThrowable_ReturnsSquashException(){
    assertThat(fixture.getClass()).isEqualTo(SquashBacktrace.SquashException.class);
  }

  @Test public void testGetBacktraces_WhenGivenThrowable_SquashExceptionIsFaulted(){
    assertThat(fixture.faulted).isEqualTo(true);
  }

  @Test public void testGetBacktraces_WhenGivenThrowable_SquashExceptionNameIsEqlToCurrentThreadName(){
    String expectedName = this.getClass().getName() + "_UnitTestThread";
    Thread.currentThread().setName(expectedName);
    fixture = SquashBacktrace.getBacktraces(new Throwable()).get(0);
    assertThat(fixture.name).isEqualTo(expectedName);
  }

  @Test public void testGetBacktraces_WhenGivenThrowable_SquashExceptionBacktraceIsNotEmpty(){
    assertThat(fixture.backtrace).isNotNull();
    assertThat(fixture.backtrace.size()).isNotEqualTo(0);
  }

  @Test public void testGetBacktraces_WhenGivenThrowableWithStacktrace_StacktraceIsCorrectlyTransformedIntoBacktrace(){
    int expectedTraceSize = 10;
    String expectedTestFileName = "UnitTestFile.java";
    String expectedTestClassName = this.getClass().getName();
    String expectedTestMethodName = "aUnitTestMethod()";

    Throwable throwable = new Throwable();
    throwable.setStackTrace(setUpFixtureStackTrace(expectedTraceSize, expectedTestFileName, expectedTestClassName, expectedTestMethodName));

    assertThat(SquashBacktrace.getBacktraces(throwable).get(0).backtrace).hasSize(expectedTraceSize);

    int lineNumber = 0;
    for(SquashBacktrace.StackElement stackElement : SquashBacktrace.getBacktraces(throwable).get(0).backtrace) {
      assertThat(stackElement.class_name).isEqualTo(expectedTestClassName);
      assertThat(stackElement.symbol).isEqualTo(expectedTestMethodName);
      assertThat(stackElement.file).isEqualTo(expectedTestFileName);
      assertThat(stackElement.line).isEqualTo(lineNumber++);
    }
  }

  private StackTraceElement[] setUpFixtureStackTrace(int traceSize, String fileName, String className, String methodName) {
    StackTraceElement[] expectedStacktrace = new StackTraceElement[traceSize];
    for(int i = 0; i < traceSize; i++)
      expectedStacktrace[i] = new StackTraceElement(className, methodName, fileName, i);
    return expectedStacktrace;
  }
}
