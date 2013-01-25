Squash Client Library: Java
===========================

This client library reports exceptions to Squash, the Squarish exception
reporting and management system.

Documentation
-------------

For an overview of the various components of Squash, see the website
documentation at https://github.com/SquareSquash/web.

Compatibility
-------------

This library is compatible with J2SE 1.6 or newer. All dependencies are handled
by Maven.

Usage
-----

The `SquashEntry` class is meant to be extended and then serialized with your
choice of json library for transmission to your Squash server.  We recommend 
using gson, but any auto-field-name-detecting library will do.

First, extend `SquashEntry` and include any additional occurrence data you wish
to send to Squash. See the `Occurrence` class documentation in the Squash web
code for a list of known properties; you can also supply any arbitrary
properties as well.

```` java
import com.squareup.squash.SquashEntry;

public class AndroidSquashEntry extends SquashEntry implements LogEntry {
    // The API key used for all Android Squash entries.
    private static final String API_KEY = "YOUR_API_KEY";
    private static final String CLIENT_ID = "android";
    private static final String DEBUG = "Debug";
    private static final String RELEASE = "Release";
    // Transient so it doesn't try to serialize itself.
    private transient Gson gson;

    // Device stuff.
    private final String device_id;
    private final String device_type;
    private final String operating_system;
    private final boolean rooted;
    private final String network_operator;
    private final String network_type;
    private final String connectivity;
    private final String orientation;

    // Location stuff.
    private final String lat;
    private final String lon;
    private final String altitude;
    private final String location_precision;
    private final String heading;
    private final String speed;

    // Which app am I?
    private final String app_id;
}
````

Build a constructor or whatever you need to set all these parameters, and then
add the ability for the class to transmit itself to Squash:

```` java
public class AndroidSquashEntry extends SquashEntry implements LogEntry {
    @Override public void writeTo(OutputStream output) throws IOException {
      final String json = gson.toJson(this);
      output.write(Strings.getBytes(json));
    }
}
````

Add an exception handler that will generate these entry instances. (In the below
example we're assuming that you've also built a factory that instantiates Squash
entries, and a `transmit` method that transmits the JSON to Squash.)

```` java
public class SquashUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
	@Inject AndroidSquashEntryFactory squashEntryFactory;
	
	@Override public void uncaughtException(Thread thread, Throwable ex) {
		try {
			transmit(squashEntryFactory.create(message, ex));
	    } catch (Throwable ignored) {
			// write your internal failsafe handler
	    }
	}
}
````

... and install the exception handler.

```` java
final Thread.UncaughtExceptionHandler handler =
	Thread.getDefaultUncaughtExceptionHandler();
Thread.setDefaultUncaughtExceptionHandler(
	new SquashUncaughtExceptionHandler(this, handler));
````

De-Obfuscation and File Paths
-----------------------------

The [Squash Java Deobfuscator](https://github.com/SquareSquash/java_deobfuscator)
Ruby gem can be included into your build-and-release process to upload yGuard
or ProGuard obfuscation maps to Squash.

Even if you are not using code obfuscation, you can still use this gem to map
Java class names to their original file paths, as Java stack traces do not
include the full path to source files, which Squash needs to perform its
Git-blame magic.
