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

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class SquashBacktraceTest{

  @Test public void testGetBacktraces_WhenErrorIsNull_ReturnsNull(){
    assertThat(SquashBacktrace.getBacktraces(null)).isNull();
  }

  @Test public void testGetBacktraces_WhenGivenThrowable_ReturnsIsNotNull(){
    assertThat(SquashBacktrace.getBacktraces(new Throwable())).isNotNull();
    assertThat(SquashBacktrace.getBacktraces(new Exception())).isNotNull();
    assertThat(SquashBacktrace.getBacktraces(new Error())).isNotNull();
  }






}

