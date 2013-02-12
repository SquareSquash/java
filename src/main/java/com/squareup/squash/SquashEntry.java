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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/** An exception to be tracked in Squash (the replacement for Hoptoad, because Hoptoad is lame). */
// All of the fields are only used for gson serialization, and so can't be made local or removed.
@SuppressWarnings({ "FieldCanBeLocal", "UnusedDeclaration" })
public class SquashEntry {
  private static final String DATE_RFC_2822 = "EEE, dd MMM yyyy HH:mm:ss Z";
  private static final ThreadLocal<DateFormat> DATE_FORMAT_THREAD_LOCAL = new ThreadLocal<DateFormat>() {
    @Override protected DateFormat initialValue() {
      return new SimpleDateFormat(DATE_RFC_2822);
    }
  };

  // Things that do not change per entry but should still be gson'd.
  private final String client;

  // Things that change per entry.
  private final String api_key;
  private final String environment;
  private final String endpoint;
  private final String user_id;
  private final String version;
  private final String revision;
  private final String build;
  private final String occurred_at;

  // Used in tests.
  final List<SquashBacktrace.SquashException> backtraces;
  final Map<String, Object> ivars;
  final List<SquashBacktrace.NestedException> parent_exceptions;
  final String class_name;
  final String message;
  final String log_message;

  public SquashEntry(String client, String apiKey, String logMessage, Throwable error,
      String appVersion, int versionCode, String buildSha, String deviceId, String endpoint,
      String userId, String environment) {
    this.client = client;
    this.log_message = logMessage;
    this.version = appVersion;
    this.revision = buildSha;
    this.build = "" + versionCode;
    this.environment = environment;
    this.endpoint = endpoint;
    this.backtraces = SquashBacktrace.getBacktraces(error);
    this.parent_exceptions = new ArrayList<SquashBacktrace.NestedException>();
    SquashBacktrace.populateNestedExceptions(parent_exceptions, error);
    this.ivars = SquashBacktrace.getIvars(error);
    this.class_name = error == null ? null : error.getClass().getName();
    this.message = createMessage(error, logMessage);
    this.api_key = apiKey;
    this.user_id = userId;
    this.occurred_at = DATE_FORMAT_THREAD_LOCAL.get().format(new Date());
  }

  // Squash requires a non-empty message field.
  private static String createMessage(Throwable error, String logMessage) {
    String message;
    if (error != null && error.getMessage() != null) {
      message = error.getMessage();
    } else if (logMessage != null) {
      message = logMessage;
    } else {
      message = "No message";
    }
    return message;
  }
}
