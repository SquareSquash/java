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
    List<SquashBacktrace.NestedException> emptyList = new ArrayList<SquashBacktrace.NestedException>();
    SquashBacktrace.populateNestedExceptions(emptyList, null);
    assertThat(emptyList).isEmpty();

    List<SquashBacktrace.NestedException> listOfOne = new ArrayList<SquashBacktrace.NestedException>();
    listOfOne.add(new SquashBacktrace.NestedException(this.getClass().getName(), "Nested Exception 1", null, null));
    SquashBacktrace.populateNestedExceptions(listOfOne, null);
    assertThat(listOfOne).hasSize(1);

    List<SquashBacktrace.NestedException> listOfTwo = new ArrayList<SquashBacktrace.NestedException>();
    listOfTwo.add(new SquashBacktrace.NestedException(this.getClass().getName(), "Nested Exception 1", null, null));
    listOfTwo.add(new SquashBacktrace.NestedException(this.getClass().getName(), "Nested Exception 2", null, null));
    SquashBacktrace.populateNestedExceptions(listOfTwo, null);
    assertThat(listOfTwo).hasSize(2);

    List<SquashBacktrace.NestedException> listOfThree = new ArrayList<SquashBacktrace.NestedException>();
    listOfThree.add(new SquashBacktrace.NestedException(this.getClass().getName(), "Nested Exception 1", null, null));
    listOfThree.add(new SquashBacktrace.NestedException(this.getClass().getName(), "Nested Exception 2", null, null));
    listOfThree.add(new SquashBacktrace.NestedException(this.getClass().getName(), "Nested Exception 3", null, null));
    SquashBacktrace.populateNestedExceptions(listOfThree, null);
    assertThat(listOfThree).hasSize(3);
  }
}
