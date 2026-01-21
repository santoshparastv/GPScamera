#!/bin/bash
echo "🔧 Building APK with fixed configuration..."
echo ""

# Find Java from Android Studio
JAVA_CMD="/Applications/Android Studio.app/Contents/jbr/Contents/Home/bin/java"
export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
export ANDROID_HOME="$HOME/Library/Android/sdk"

if [ ! -x "$JAVA_CMD" ]; then
    echo "❌ Java not found. Please build from Android Studio GUI:"
    echo "   Build → Build APK(s)"
    exit 1
fi

echo "✅ Using Java from Android Studio"
echo ""

cd "/Users/santosh/GPS Camera With ID and name"

# Clean first
echo "🧹 Cleaning previous build..."
"$JAVA_CMD" -jar gradle/wrapper/gradle-wrapper.jar clean --no-daemon > /dev/null 2>&1

# Build APK
echo "📦 Building APK (this may take 2-5 minutes)..."
echo ""

"$JAVA_CMD" -jar gradle/wrapper/gradle-wrapper.jar assembleDebug --no-daemon 2>&1 | tee build_output.log

BUILD_EXIT=$?

if [ $BUILD_EXIT -eq 0 ] && [ -f "app/build/outputs/apk/debug/app-debug.apk" ]; then
    APK_SIZE=$(ls -lh "app/build/outputs/apk/debug/app-debug.apk" | awk '{print $5}')
    APK_PATH="$(pwd)/app/build/outputs/apk/debug/app-debug.apk"
    
    echo ""
    echo "╔══════════════════════════════════════════════════════════╗"
    echo "║     ✅ ✅ ✅ BUILD SUCCESSFUL! ✅ ✅ ✅                 ║"
    echo "╚══════════════════════════════════════════════════════════╝"
    echo ""
    echo "📱 APK Details:"
    echo "   Location: app/build/outputs/apk/debug/app-debug.apk"
    echo "   Full Path: $APK_PATH"
    echo "   Size: $APK_SIZE"
    echo ""
    echo "🎯 Next Steps:"
    echo "   1. Transfer APK to your phone"
    echo "   2. Enable 'Install from unknown sources'"
    echo "   3. Install and test!"
    echo ""
    
    open "app/build/outputs/apk/debug" 2>/dev/null && echo "✅ APK folder opened in Finder!" || echo "📂 Navigate to: $APK_PATH"
    
    exit 0
else
    echo ""
    echo "❌ Build failed. Check build_output.log for details"
    echo ""
    echo "💡 Alternative: Build from Android Studio GUI:"
    echo "   Build → Build APK(s)"
    echo ""
    echo "Common fixes:"
    echo "   - File → Invalidate Caches → Invalidate and Restart"
    echo "   - File → Sync Project with Gradle Files"
    exit 1
fi
