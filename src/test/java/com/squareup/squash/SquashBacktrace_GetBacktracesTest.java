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

import static org.fest.assertions.Assertions.assertThat;

public class SquashBacktrace_GetBacktracesTest{

  @Test public void testGetBacktraces_WhenErrorIsNull_ReturnsNull(){
    assertThat(SquashBacktrace.getBacktraces(null)).isNull();
  }

  @Test public void testGetBacktraces_WhenGivenThrowable_ReturnsIsNotNull(){
    assertThat(SquashBacktrace.getBacktraces(new Throwable())).isNotNull();
    assertThat(SquashBacktrace.getBacktraces(new Exception())).isNotNull();
    assertThat(SquashBacktrace.getBacktraces(new Error())).isNotNull();
  }

  @Test public void testGetBacktraces_WhenGivenThrowable_ReturnsOnlyOneException(){
    assertThat(SquashBacktrace.getBacktraces(new Throwable()).size()).isEqualTo(1);
  }

  @Test public void testGetBacktraces_WhenGivenThrowable_ReturnsSquashException(){
    assertThat(SquashBacktrace.getBacktraces(new Throwable()).get(0).getClass()).isEqualTo(SquashBacktrace.SquashException.class);
  }

  @Test public void testGetBacktraces_WhenGivenThrowable_SquashExceptionIsFaulted(){
    assertThat(SquashBacktrace.getBacktraces(new Throwable()).get(0).faulted).isEqualTo(true);
  }

  @Test public void testGetBacktraces_WhenGivenThrowable_SquashExceptionNameIsEqlToCurrentThreadName(){
    String expectedName = this.getClass().getName() + "_UnitTestThread";
    Thread.currentThread().setName(expectedName);
    assertThat(SquashBacktrace.getBacktraces(new Throwable()).get(0).name).isEqualTo(expectedName);
  }

  @Test public void testGetBacktraces_WhenGivenThrowable_SquashExceptionBacktraceIsNotEmpty(){
    assertThat(SquashBacktrace.getBacktraces(new Throwable()).get(0).backtrace).isNotNull();
    assertThat(SquashBacktrace.getBacktraces(new Throwable()).get(0).backtrace.size()).isNotEqualTo(0);
  }

  @Test public void testGetBacktraces_WhenGivenThrowableWithStacktrace_StacktraceIsCorrectlyTransformedIntoBacktrace(){
    int expectedTraceSize = 10;
    StackTraceElement[] expectedStacktrace = new StackTraceElement[expectedTraceSize];
    for(int i = 0; i < expectedTraceSize; i++)
      expectedStacktrace[i] = new StackTraceElement(this.getClass().getName(), "aUnitTestMethod()", "UnitTestFile.java", i);
    Throwable throwable =  new Throwable();
    throwable.setStackTrace(expectedStacktrace);

    assertThat(SquashBacktrace.getBacktraces(throwable).get(0).backtrace).hasSize(expectedTraceSize);
    
    int lineNumber = 0;
    for(SquashBacktrace.StackElement stackElement : SquashBacktrace.getBacktraces(throwable).get(0).backtrace) {
      assertThat(stackElement.class_name).isEqualTo(this.getClass().getName());
      assertThat(stackElement.symbol).isEqualTo("aUnitTestMethod()");
      assertThat(stackElement.file).isEqualTo("UnitTestFile.java");
      assertThat(stackElement.line).isEqualTo(lineNumber++);
    }
  }
}

