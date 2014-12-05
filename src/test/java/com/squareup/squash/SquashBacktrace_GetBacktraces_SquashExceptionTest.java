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
  private String expectedName;
  private String expectedFileName;
  private String expectedClassName;
  private String expectedMethodName;

  @Before public void setUp(){
    expectedName = this.getClass().getName() + "_UnitTestThread";
    Thread.currentThread().setName(expectedName);
   
    expectedFileName = "UnitTestFile.java";
    expectedClassName = this.getClass().getName();
    expectedMethodName = "aUnitTestMethod()";

    fixture = SquashBacktrace.getBacktraces(new Throwable()).get(0);
  }

  @Test public void testGetBacktraces_WhenGivenThrowable_ReturnsSquashExceptionWithCorrectAttributes(){
    assertSquashExceptionFaultedFlag();
    assertSquashExceptionName();
    assertSquashExceptionBacktrace();
   }

  private void assertSquashExceptionFaultedFlag(){
    assertThat(fixture.faulted).isEqualTo(true);
  }

  private void assertSquashExceptionName(){
    fixture = SquashBacktrace.getBacktraces(new Throwable()).get(0);
    assertThat(fixture.name).isEqualTo(expectedName);
  }  
 
  private void assertSquashExceptionBacktrace(){
    assertThat(fixture.backtrace).isNotNull();
    assertThat(fixture.backtrace.size()).isNotEqualTo(0);
  }

  @Test public void testGetBacktraces_WhenGivenThrowableWithStacktrace_StacktraceIsCorrectlyTransformedIntoBacktrace(){
    int expectedTraceSize = 10;
    
    Throwable throwable = new Throwable();
    throwable.setStackTrace(setUpFixtureStackTrace(expectedTraceSize, expectedFileName, expectedClassName, expectedMethodName));
    assertBacktrace(throwable, expectedTraceSize);
   }

  private StackTraceElement[] setUpFixtureStackTrace(int traceSize, String fileName, String className, String methodName) {
    StackTraceElement[] expectedStacktrace = new StackTraceElement[traceSize];
    for(int i = 0; i < traceSize; i++)
      expectedStacktrace[i] = new StackTraceElement(className, methodName, fileName, i);
    return expectedStacktrace;
  }

  private void assertBacktrace(Throwable throwable, int expectedTraceSize){
    assertThat(SquashBacktrace.getBacktraces(throwable).get(0).backtrace).hasSize(expectedTraceSize);

    int lineNumber = 0;
    for(SquashBacktrace.StackElement stackElement : SquashBacktrace.getBacktraces(throwable).get(0).backtrace)
      assertStackElementAttributes(stackElement, expectedTraceSize, lineNumber++);
  }

  private void assertStackElementAttributes(SquashBacktrace.StackElement stackElement, int expectedTraceSize, int lineNumber){
    assertThat(stackElement.class_name).isEqualTo(expectedClassName);
    assertThat(stackElement.symbol).isEqualTo(expectedMethodName);
    assertThat(stackElement.file).isEqualTo(expectedFileName);
    assertThat(stackElement.line).isEqualTo(lineNumber++);
  }
}
