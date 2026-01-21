#!/bin/bash
echo "🔧 Fixing issues and building APK..."
echo ""

# Check if we can find Java from Android Studio
JAVA_PATHS=(
    "/Applications/Android Studio.app/Contents/jbr/Contents/Home/bin/java"
    "$HOME/Library/Android/sdk/jre/bin/java"
)

JAVA_CMD=""
for path in "${JAVA_PATHS[@]}"; do
    if [ -x "$path" ] 2>/dev/null; then
        JAVA_CMD="$path"
        export JAVA_HOME="$(dirname "$(dirname "$path")")"
        break
    fi
done

if [ -z "$JAVA_CMD" ]; then
    echo "⚠️  Java not found in Android Studio location"
    echo "   Building from Android Studio GUI instead"
    echo ""
    echo "📋 In Android Studio:"
    echo "   1. Build → Build APK(s)"
    echo "   2. Wait for build to complete"
    exit 0
fi

echo "✅ Found Java: $JAVA_CMD"
echo ""

# Set Android SDK path
export ANDROID_HOME="$HOME/Library/Android/sdk"
export PATH="$ANDROID_HOME/platform-tools:$PATH"

echo "📦 Building APK..."
echo ""

# Build APK
cd "/Users/santosh/GPS Camera With ID and name"
"$JAVA_CMD" -jar gradle/wrapper/gradle-wrapper.jar assembleDebug --no-daemon 2>&1 | tee build.log

if [ $? -eq 0 ]; then
    if [ -f "app/build/outputs/apk/debug/app-debug.apk" ]; then
        APK_SIZE=$(ls -lh "app/build/outputs/apk/debug/app-debug.apk" | awk '{print $5}')
        echo ""
        echo "✅ ✅ ✅ BUILD SUCCESSFUL! ✅ ✅ ✅"
        echo ""
        echo "📱 APK Details:"
        echo "   Location: app/build/outputs/apk/debug/app-debug.apk"
        echo "   Size: $APK_SIZE"
        echo ""
        open "app/build/outputs/apk/debug" 2>/dev/null
        echo "✅ APK folder opened in Finder!"
    else
        echo "⚠️  Build completed but APK not found"
        echo "   Check build.log for details"
    fi
else
    echo ""
    echo "❌ Build failed. Check build.log for errors"
    echo ""
    echo "💡 Try building from Android Studio GUI instead:"
    echo "   Build → Build APK(s)"
fi
