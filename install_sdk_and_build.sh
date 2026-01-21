#!/bin/bash
set -e

cd "/Users/santosh/GPS Camera With ID and name"

export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
export ANDROID_HOME="$HOME/Library/Android/sdk"
export PATH="$JAVA_HOME/bin:$ANDROID_HOME/platform-tools:$PATH"

echo "🔧 Installing SDK components and building APK..."
echo ""

# Find sdkmanager
SDKMANAGER=$(find "$ANDROID_HOME" -path "*/cmdline-tools/*/bin/sdkmanager" -type f 2>/dev/null | head -1)

if [ -n "$SDKMANAGER" ] && [ -x "$SDKMANAGER" ]; then
    echo "✅ Found SDK Manager: $SDKMANAGER"
    echo ""
    echo "📥 Installing Build-Tools 34..."
    "$SDKMANAGER" --install "build-tools;34.0.0" --sdk_root="$ANDROID_HOME" 2>&1 | grep -E "(Installing|done|complete)" || echo "Installation in progress..."
    echo ""
else
    echo "⚠️  SDK Manager not found in command line tools"
    echo "   Will try to build anyway - Android Studio may have installed it"
    echo ""
fi

# Try building
echo "📦 Building APK..."
echo ""

if ./gradlew assembleDebug --no-daemon 2>&1 | tee build_final.log; then
    if [ -f "app/build/outputs/apk/debug/app-debug.apk" ]; then
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
    fi
fi

echo ""
echo "⚠️  Build still has issues. Please use Android Studio GUI:"
echo ""
echo "📋 In Android Studio:"
echo "   1. Tools → SDK Manager"
echo "   2. SDK Tools tab → Check 'Android SDK Build-Tools 34'"
echo "   3. Click 'Apply' and wait"
echo "   4. File → Sync Project with Gradle Files"
echo "   5. Build → Build APK(s)"
exit 1
