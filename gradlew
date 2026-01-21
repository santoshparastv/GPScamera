#!/bin/sh
# Gradle wrapper script

# Find Java
JAVA_CMD="java"
if [ -n "$JAVA_HOME" ]; then
    JAVA_CMD="$JAVA_HOME/bin/java"
fi

# Check if wrapper jar exists
WRAPPER_JAR="gradle/wrapper/gradle-wrapper.jar"
if [ ! -f "$WRAPPER_JAR" ]; then
    echo "Gradle wrapper not found. Please install Android Studio or download it manually."
    exit 1
fi

# Run Gradle
exec "$JAVA_CMD" -jar "$WRAPPER_JAR" "$@"
