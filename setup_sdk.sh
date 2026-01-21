#!/bin/bash
# Script to help set up Android SDK

echo "🔧 Android SDK Setup Helper"
echo "============================"
echo ""

# Create SDK directory
mkdir -p ~/Library/Android/sdk
echo "✅ SDK directory created: ~/Library/Android/sdk"
echo ""

# Check if Android Studio is installed
if [ -d "/Applications/Android Studio.app" ]; then
    echo "✅ Android Studio is installed"
    echo ""
    echo "📋 NEXT STEPS (Do this in Android Studio):"
    echo ""
    echo "1. Open Android Studio"
    echo "2. If Setup Wizard appears:"
    echo "   - Click 'Next' through screens"
    echo "   - Choose 'Standard' installation"
    echo "   - Accept licenses"
    echo "   - Click 'Finish'"
    echo "   - Wait for SDK download (~10 minutes)"
    echo ""
    echo "3. If Setup Wizard doesn't appear:"
    echo "   - Tools → SDK Manager"
    echo "   - Install:"
    echo "     • Android SDK Platform (API 34)"
    echo "     • Android SDK Build-Tools"
    echo "     • Android SDK Platform-Tools"
    echo "   - Click 'Apply'"
    echo ""
    echo "4. Set SDK Location (if needed):"
    echo "   - File → Project Structure → SDK Location"
    echo "   - Set to: ~/Library/Android/sdk"
    echo "   - Click 'Apply'"
    echo ""
    echo "5. After SDK installs:"
    echo "   - File → Open → Select project folder"
    echo "   - Wait for Gradle sync"
    echo "   - Build → Build APK(s)"
    echo ""
else
    echo "❌ Android Studio not found"
    echo "   Please install Android Studio first"
fi

echo ""
echo "🚀 Opening Android Studio..."
open -a "Android Studio" "/Users/santosh/GPS Camera With ID and name" 2>/dev/null || open -a "Android Studio" 2>/dev/null

echo ""
echo "✅ Setup script complete!"
echo "   Follow the steps above in Android Studio"
