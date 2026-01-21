# Quick Start - Build and Run

## ⚡ Fastest Way: Android Studio

1. **Open Android Studio**
   - If not installed: https://developer.android.com/studio

2. **Open Project**
   ```
   File → Open → Select "GPS Camera With ID and name" folder
   ```

3. **Wait for Gradle Sync** (2-5 minutes first time)
   - Watch bottom status bar
   - Let it download dependencies

4. **Add Firebase Config** (Required!)
   - Get `google-services.json` from Firebase Console
   - Place in: `app/google-services.json`
   - ⚠️ Without this, build will fail

5. **Build**
   - `Build → Make Project` (or `Cmd+B` / `Ctrl+B`)

6. **Run**
   - Connect device or start emulator
   - Click green ▶️ Run button
   - Select device
   - App launches!

## 🚨 Before First Build

**You MUST add `google-services.json`:**
1. Go to https://console.firebase.google.com/
2. Create/select project
3. Add Android app (package: `com.gpscamera.app`)
4. Download `google-services.json`
5. Place in `app/` folder

## 📱 Testing Without Firebase

The app can run with limited functionality:
- Username login works (creates local user)
- Camera and GPS work
- Offline storage works
- Firebase upload will fail (expected)
- Google Sheets won't work (expected)

## 🔧 Troubleshooting

**Build fails?**
- Check internet connection
- File → Invalidate Caches → Restart
- Ensure `google-services.json` is in `app/` folder

**App crashes?**
- Check Logcat in Android Studio
- Verify permissions are granted (Camera, Location)

**Need help?**
- See BUILD_INSTRUCTIONS.md for detailed steps
- See SETUP_GUIDE.md for Firebase/Sheets setup
