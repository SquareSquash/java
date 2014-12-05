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

import java.util.List;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;

import static org.fest.assertions.Assertions.assertThat; 

public class SquashBacktrace_PopulateNestedExceptionsTest{

  private String expectedClassName;
  private Throwable parent;

  @Before public void setUp(){
    expectedClassName = Throwable.class.getName();
    parent = new Throwable(); 
  }

  @Test public void populateNestedException_WhenGivenNullThrowable_NestedExceptionsRemainsUnchanged(){
    List<SquashBacktrace.NestedException> emptyList = new ArrayList<SquashBacktrace.NestedException>();
    SquashBacktrace.populateNestedExceptions(emptyList, null);
    assertThat(emptyList).isEmpty();
  }

  @Test public void populateNestedException_WhenGivenThrowableWithOneNestedException_ReturnsItsNestedException() {
    parent.initCause(nestedThrowablesWithDepths(1, null));
    List<SquashBacktrace.NestedException> actualNestedExceptions = new ArrayList<SquashBacktrace.NestedException>();
    SquashBacktrace.populateNestedExceptions(actualNestedExceptions, parent);
    assertNestedExceptions(actualNestedExceptions);
  }

  @Test public void populateNestedException_WhenGivenThrowableWithFiveNestedException_ReturnsAllNestedExceptions() {
    parent.initCause(nestedThrowablesWithDepths(5, null));
    List<SquashBacktrace.NestedException> actualNestedExceptions = new ArrayList<SquashBacktrace.NestedException>();
    SquashBacktrace.populateNestedExceptions(actualNestedExceptions, parent);
    assertNestedExceptions(actualNestedExceptions);
  }

  @Test public void populateNestedException_WhenGivenThrowableWithTenNestedException_ReturnsAllNestedExceptions() {
    parent.initCause(nestedThrowablesWithDepths(10, null));
    List<SquashBacktrace.NestedException> actualNestedExceptions = new ArrayList<SquashBacktrace.NestedException>();
    SquashBacktrace.populateNestedExceptions(actualNestedExceptions, parent);
    assertNestedExceptions(actualNestedExceptions);
  }

  @Test public void populateNestedException_WhenGivenThrowableWithThirtyNestedException_ReturnsAllNestedExceptions() {
    parent.initCause(nestedThrowablesWithDepths(30, null));
    List<SquashBacktrace.NestedException> actualNestedExceptions = new ArrayList<SquashBacktrace.NestedException>();
    SquashBacktrace.populateNestedExceptions(actualNestedExceptions, parent);
    assertNestedExceptions(actualNestedExceptions);
  }

  @Test public void populateNestedException_WhenGivenThrowableWithSixtyNestedException_ReturnsAllNestedExceptions() {
    parent.initCause(nestedThrowablesWithDepths(60, null));
    List<SquashBacktrace.NestedException> actualNestedExceptions = new ArrayList<SquashBacktrace.NestedException>();
    SquashBacktrace.populateNestedExceptions(actualNestedExceptions, parent);
    assertNestedExceptions(actualNestedExceptions);
  }

  private Throwable nestedThrowablesWithDepths(int depth, Throwable cause) {
    if(depth == 0)
      return cause;

    String message = "Level[" + depth + "]: This Is A Nested Exception";
    Throwable throwable = new Throwable(message);
    if (cause != null)
      throwable.initCause(cause);
    return nestedThrowablesWithDepths(depth - 1, throwable);
  }

  private void assertNestedExceptions(List<SquashBacktrace.NestedException> nestedExceptions){
    for(int i = 0; i < nestedExceptions.size(); i++) {
      int depth = i + 1;
      String expectedMessage = "Level[" + depth + "]: This Is A Nested Exception";
      assertNestedExceptionAttributes(nestedExceptions.get(i), expectedMessage);
    }
  }

  private void assertNestedExceptionAttributes(SquashBacktrace.NestedException fixture, String expectedExceptionMessage){
    assertThat(fixture).isNotNull();
    assertThat(fixture.class_name).isEqualTo(expectedClassName);
    assertThat(fixture.message).isEqualTo(expectedExceptionMessage);
    assertThat(fixture.ivars).isNotNull();
    assertNestedExceptionBacktraces(fixture);
  }

  private void assertNestedExceptionBacktraces(SquashBacktrace.NestedException fixture){
    assertThat(fixture.backtraces).isNotEmpty();
    assertThat(fixture.backtraces).hasSize(1);
    assertSquashExceptionAttributes(fixture.backtraces.get(0));
  }

  private void assertSquashExceptionAttributes(SquashBacktrace.SquashException fixture){
    assertThat(fixture).isNotNull();
    assertThat(fixture.getClass()).isEqualTo(SquashBacktrace.SquashException.class);
    assertThat(fixture.name).isNotNull();
    assertThat(fixture.faulted).isEqualTo(true);
    assertThat(fixture.backtrace).isNotNull();
  }
}
