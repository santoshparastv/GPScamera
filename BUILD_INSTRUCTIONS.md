# Build and Run Instructions

## Prerequisites

1. **Android Studio** (Hedgehog 2023.1.1 or later)
   - Download from: https://developer.android.com/studio
   
2. **Android SDK**
   - API Level 24 (Android 7.0) minimum
   - API Level 34 (Android 14) target
   - Install via Android Studio SDK Manager

3. **JDK 8 or higher**
   - Usually included with Android Studio

## Build Steps

### Option 1: Using Android Studio (Recommended)

1. **Open Project**
   - Launch Android Studio
   - Click "Open" or "File → Open"
   - Navigate to: `/Users/santosh/GPS Camera With ID and name`
   - Click "OK"

2. **Wait for Gradle Sync**
   - Android Studio will automatically sync Gradle
   - This may take a few minutes on first open
   - Watch the bottom status bar for progress

3. **Fix Any Sync Issues**
   - If sync fails, check:
     - Internet connection (for downloading dependencies)
     - File → Invalidate Caches → Invalidate and Restart
     - Check `app/build.gradle` for any errors

4. **Add Firebase Configuration** (Required before building)
   - Get `google-services.json` from Firebase Console
   - Place it in `app/` directory (same level as `app/build.gradle`)
   - File should be: `app/google-services.json`

5. **Build Project**
   - Click "Build" → "Make Project" (or press `Cmd+B` on Mac / `Ctrl+B` on Windows/Linux)
   - Wait for build to complete
   - Check "Build" tab at bottom for any errors

6. **Run on Device/Emulator**
   - Connect Android device via USB (enable USB debugging)
   - OR start an Android emulator (API 24+)
   - Click green "Run" button (or press `Shift+F10`)
   - Select device/emulator
   - App will install and launch

### Option 2: Using Command Line (Advanced)

If you have Gradle installed:

```bash
cd "/Users/santosh/GPS Camera With ID and name"

# Create Gradle wrapper (first time only)
gradle wrapper

# Build debug APK
./gradlew assembleDebug

# Install on connected device
./gradlew installDebug

# Or build and run
./gradlew installDebug && adb shell am start -n com.gpscamera.app/.ui.login.LoginActivity
```

## Common Build Issues

### Issue: "google-services.json not found"
**Solution**: 
- Download from Firebase Console
- Place in `app/` directory
- File name must be exactly `google-services.json`

### Issue: "Failed to resolve: androidx.camera..."
**Solution**:
- Check internet connection
- File → Invalidate Caches → Invalidate and Restart
- Sync project again

### Issue: "Room database annotation processor error"
**Solution**:
- Ensure `kotlin-kapt` plugin is applied (already added)
- Clean and rebuild: Build → Clean Project, then Build → Rebuild Project

### Issue: "SDK location not found"
**Solution**:
- File → Project Structure → SDK Location
- Set Android SDK location (usually auto-detected)

### Issue: "Minimum supported Gradle version is X.X"
**Solution**:
- Android Studio will prompt to update
- Click "Update" when prompted
- Or manually update in `gradle/wrapper/gradle-wrapper.properties`

## Testing the App

1. **First Launch**
   - App opens to Login screen
   - Try username login (no Firebase required for basic test)
   - Or set up Firebase Phone Auth for OTP

2. **Permissions**
   - Grant Camera permission when prompted
   - Grant Location permission when prompted
   - These are required for app functionality

3. **Capture Flow**
   - Click "Capture Photo"
   - Wait for GPS to fetch (may take a few seconds)
   - Take photo
   - Enter alphanumeric ID
   - Review preview
   - Save (will upload if online, save offline if not)

4. **Offline Mode**
   - Turn off WiFi/Mobile data
   - Capture photo
   - Data saves locally
   - Turn on internet
   - Click "Sync Pending Data" from home screen

## Debugging

### View Logs
- In Android Studio: View → Tool Windows → Logcat
- Filter by package: `com.gpscamera.app`

### Common Runtime Issues

**Camera not opening:**
- Check camera permission granted
- Test on physical device (emulator may have issues)

**GPS not working:**
- Enable location services on device
- Test in open area (better GPS signal)
- Check location permission granted

**Firebase errors:**
- Verify `google-services.json` is correct
- Check Firebase project settings
- Verify internet connection

## Next Steps After Build

1. **Configure Firebase** (if not done)
   - See SETUP_GUIDE.md for detailed instructions

2. **Set up Google Sheets** (optional)
   - See SETUP_GUIDE.md for instructions
   - App works without Sheets, but won't export

3. **Test All Features**
   - Login (username/OTP)
   - Camera capture
   - GPS location
   - Offline save
   - Online sync
   - Records view

## Build Output

- **Debug APK**: `app/build/outputs/apk/debug/app-debug.apk`
- **Release APK**: `app/build/outputs/apk/release/app-release.apk` (after signing)

## Need Help?

- Check ACTIVITY_LOG.md for recent changes
- Review SETUP_GUIDE.md for configuration
- Check Android Studio's "Build" tab for specific errors
