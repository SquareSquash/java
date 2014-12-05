// Copyright 2012 Square Inc.
// //
// //    Licensed under the Apache License, Version 2.0 (the "License");
// //    you may not use this file except in compliance with the License.
// //    You may obtain a copy of the License at
// //
// //        http://www.apache.org/licenses/LICENSE-2.0
// //
// //    Unless required by applicable law or agreed to in writing, software
// //    distributed under the License is distributed on an "AS IS" BASIS,
// //    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// //    See the License for the specific language governing permissions and
// //    limitations under the License.

package com.squareup.squash;

import java.util.List;
import java.util.ArrayList;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat; 

public class SquashBacktrace_PopulateNestedExceptionsTest{

  @Test public void populateNestedException_WhenGivenNullThrowable_NestedExceptionListRemainsUnchanged(){
    List<SquashBacktrace.NestedException> emptyList = listOfNestedExceptions(0);
    SquashBacktrace.populateNestedExceptions(emptyList, null);
    assertThat(emptyList).isEmpty();

    List<SquashBacktrace.NestedException> listOfOne = listOfNestedExceptions(1);
    SquashBacktrace.populateNestedExceptions(listOfOne, null);
    assertThat(listOfOne).hasSize(1);

    List<SquashBacktrace.NestedException> listOfTwo = listOfNestedExceptions(2);
    SquashBacktrace.populateNestedExceptions(listOfTwo, null);
    assertThat(listOfTwo).hasSize(2);

    List<SquashBacktrace.NestedException> listOfThree = listOfNestedExceptions(3);
    SquashBacktrace.populateNestedExceptions(listOfThree, null);
    assertThat(listOfThree).hasSize(3);
  }

  private List<SquashBacktrace.NestedException> listOfNestedExceptions(int count){
    List<SquashBacktrace.NestedException> nestedExceptions = new ArrayList<SquashBacktrace.NestedException>();
    for(int i = 0; i < count; i++)
      nestedExceptions.add(new SquashBacktrace.NestedException(this.getClass().getName(), "Nested Exception 1", null, null));
    return nestedExceptions;
  }

  @Test public void populateNestedException_WhenGivenThrowableWithOneNestedException_ReturnsItsNestedException() {
    String expectedClassName = Throwable.class.getName();
    String expectedExceptionMessage = "Nested Exception";
    Throwable expectedCause = new Throwable(expectedExceptionMessage);
    Throwable parent = new Throwable(expectedCause);

    List<SquashBacktrace.NestedException> actualNestedExceptions = listOfNestedExceptions(0);
    SquashBacktrace.populateNestedExceptions(actualNestedExceptions, parent);
    assertNestedExceptionAttributes(actualNestedExceptions.get(0), expectedExceptionMessage, expectedClassName);
  }

  private void assertNestedExceptionAttributes(SquashBacktrace.NestedException fixture, String expectedExceptionMessage, String expectedClassName){
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
  }
}
