#!/bin/bash
set -e

cd "/Users/santosh/GPS Camera With ID and name"

export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
export ANDROID_HOME="$HOME/Library/Android/sdk"
export PATH="$JAVA_HOME/bin:$ANDROID_HOME/platform-tools:$PATH"

echo "🔧 Building APK..."
echo "Java: $JAVA_HOME/bin/java"
echo "SDK: $ANDROID_HOME"
echo ""

# Sync first
echo "📥 Syncing Gradle..."
./gradlew tasks --no-daemon > /dev/null 2>&1 || true

# Build APK
echo "📦 Building APK (this may take 2-5 minutes)..."
echo ""

if ./gradlew assembleDebug --no-daemon 2>&1 | tee build.log; then
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
echo "❌ Build failed. Check build.log for details"
echo ""
echo "💡 Building from Android Studio GUI instead..."
echo "   1. File → Sync Project with Gradle Files"
echo "   2. Build → Build APK(s)"
exit 1
