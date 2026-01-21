#!/bin/bash
# Script to build APK

echo "🔨 Building GPS Camera App APK..."
echo ""

# Check for Java
if ! command -v java &> /dev/null; then
    echo "❌ Java not found. Please install JDK 8 or higher."
    exit 1
fi

# Check for Android SDK
if [ -z "$ANDROID_HOME" ] && [ ! -d "$HOME/Library/Android/sdk" ]; then
    echo "⚠️  Android SDK not found in standard location."
    echo "   Setting ANDROID_HOME to common location..."
    export ANDROID_HOME="$HOME/Library/Android/sdk"
fi

# Create gradle wrapper if needed
if [ ! -f "gradle/wrapper/gradle-wrapper.jar" ]; then
    echo "📥 Downloading Gradle wrapper..."
    mkdir -p gradle/wrapper
    curl -L -o gradle/wrapper/gradle-wrapper.jar \
        https://github.com/gradle/gradle/raw/v8.0.0/gradle/wrapper/gradle-wrapper.jar || {
        echo "❌ Failed to download Gradle wrapper."
        echo "   Please install Android Studio or Gradle manually."
        exit 1
    }
fi

# Make gradlew executable
chmod +x gradlew 2>/dev/null || {
    echo "Creating gradlew script..."
    cat > gradlew << 'GRADLEW'
#!/bin/sh
exec "$(dirname "$0")/gradle/wrapper/gradle-wrapper.jar" "$@"
GRADLEW
    chmod +x gradlew
}

# Build APK
echo "🏗️  Building debug APK..."
./gradlew assembleDebug

if [ $? -eq 0 ]; then
    APK_PATH="app/build/outputs/apk/debug/app-debug.apk"
    if [ -f "$APK_PATH" ]; then
        echo ""
        echo "✅ APK built successfully!"
        echo "📱 Location: $(pwd)/$APK_PATH"
        echo ""
        echo "To install on your phone:"
        echo "  1. Transfer the APK to your phone"
        echo "  2. Enable 'Install from unknown sources' in settings"
        echo "  3. Open the APK file and install"
        open "$(dirname "$APK_PATH")" 2>/dev/null || echo "   Open: $(pwd)/$APK_PATH"
    else
        echo "❌ APK not found at expected location"
    fi
else
    echo ""
    echo "❌ Build failed. Common issues:"
    echo "  - Missing Android SDK"
    echo "  - Missing google-services.json (create a dummy one for testing)"
    echo "  - Network issues downloading dependencies"
fi
