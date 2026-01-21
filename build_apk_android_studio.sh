#!/bin/bash
# Script to build APK using Android Studio's Gradle

echo "🔨 Building GPS Camera App APK..."
echo ""

PROJECT_DIR="/Users/santosh/GPS Camera With ID and name"
cd "$PROJECT_DIR"

# Check if gradlew exists
if [ ! -f "gradlew" ]; then
    echo "❌ gradlew not found. Please open project in Android Studio first to generate it."
    exit 1
fi

# Check for Java (Android Studio's Java)
if [ -d "$HOME/Library/Android/sdk" ]; then
    export ANDROID_HOME="$HOME/Library/Android/sdk"
fi

# Try to find Java from Android Studio
JAVA_PATHS=(
    "/Applications/Android Studio.app/Contents/jbr/Contents/Home/bin/java"
    "$HOME/Library/Android/sdk/jre/bin/java"
    "/usr/bin/java"
    "$(which java)"
)

JAVA_CMD=""
for path in "${JAVA_PATHS[@]}"; do
    if [ -x "$path" ] 2>/dev/null; then
        JAVA_CMD="$path"
        break
    fi
done

if [ -z "$JAVA_CMD" ]; then
    echo "⚠️  Java not found. Please:"
    echo "   1. Install Android Studio"
    echo "   2. Or install Java JDK 8+"
    echo ""
    echo "Then run: ./gradlew assembleDebug"
    exit 1
fi

echo "✅ Found Java: $JAVA_CMD"
echo ""

# Build APK
echo "🏗️  Building debug APK (this may take a few minutes)..."
echo ""

./gradlew assembleDebug --no-daemon

if [ $? -eq 0 ]; then
    APK_PATH="app/build/outputs/apk/debug/app-debug.apk"
    if [ -f "$APK_PATH" ]; then
        APK_SIZE=$(du -h "$APK_PATH" | cut -f1)
        echo ""
        echo "✅ ✅ ✅ APK BUILT SUCCESSFULLY! ✅ ✅ ✅"
        echo ""
        echo "📱 APK Details:"
        echo "   Location: $APK_PATH"
        echo "   Size: $APK_SIZE"
        echo ""
        echo "📲 To install on your phone:"
        echo "   1. Transfer APK to your phone (USB, email, cloud)"
        echo "   2. Enable 'Install from unknown sources' in phone settings"
        echo "   3. Open APK file on phone and tap Install"
        echo ""
        
        # Try to open the folder
        open "$(dirname "$APK_PATH")" 2>/dev/null && echo "   📂 Opened APK folder in Finder" || echo "   📂 Navigate to: $(pwd)/$APK_PATH"
    else
        echo "❌ APK not found at: $APK_PATH"
        echo "   Check build output above for errors"
    fi
else
    echo ""
    echo "❌ Build failed. Common solutions:"
    echo ""
    echo "   1. Open project in Android Studio first:"
    echo "      - File → Open → Select project folder"
    echo "      - Wait for Gradle sync to complete"
    echo ""
    echo "   2. Then try building again:"
    echo "      ./gradlew assembleDebug"
    echo ""
    echo "   3. Or build from Android Studio:"
    echo "      Build → Build APK(s)"
fi
