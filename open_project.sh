#!/bin/bash
# Script to open Android project

PROJECT_PATH="/Users/santosh/GPS Camera With ID and name"

# Try to find Android Studio
AS_PATHS=(
    "/Applications/Android Studio.app"
    "$HOME/Applications/Android Studio.app"
    "/usr/local/android-studio/bin/studio.sh"
)

for path in "${AS_PATHS[@]}"; do
    if [ -e "$path" ]; then
        echo "Found Android Studio at: $path"
        if [[ "$path" == *.app ]]; then
            open -a "$path" "$PROJECT_PATH"
        else
            "$path" "$PROJECT_PATH"
        fi
        exit 0
    fi
done

echo "Android Studio not found. Please:"
echo "1. Install Android Studio from https://developer.android.com/studio"
echo "2. Or manually open: $PROJECT_PATH"
echo ""
echo "Alternative: Open Android Studio and use File → Open → Select project folder"
