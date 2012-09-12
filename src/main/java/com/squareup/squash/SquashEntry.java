// Copyright 2012 Square, Inc.
package com.squareup.squash;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/** An exception to be tracked in Squash (the replacement for Hoptoad, because Hoptoad is lame). */
// All of the fields are only used for gson serialization, and so can't be made local or removed.
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class SquashEntry {
  private static final String DATE_RFC_2822 = "EEE, dd MMM yyyy HH:mm:ss Z";
  private static final ThreadLocal<DateFormat> dateFormatThreadLocal =
      new ThreadLocal<DateFormat>();

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
  final List<List<Object>> backtraces;
  final Map<String, Object> ivars;
  final List<SquashBacktrace.NestedException> parent_exceptions;
  final String class_name;
  final String message;
  final String log_message;

  public SquashEntry(String client, String apiKey, String message, Throwable error,
      String appVersion, int versionCode, String buildSha, String deviceId, String endpoint,
      String userId, String environment) {
    this.client = client;
    this.log_message = message;
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
    this.message = error == null ? null : error.getMessage();
    this.api_key = apiKey;
    this.user_id = userId;
    DateFormat dateFormat = dateFormatThreadLocal.get();
    if (dateFormat == null) {
      dateFormat = new SimpleDateFormat(DATE_RFC_2822);
      dateFormatThreadLocal.set(dateFormat);
    }
    this.occurred_at = dateFormat.format(new Date());
  }
}
