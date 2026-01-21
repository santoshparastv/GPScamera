# 🚀 Build APK - Step by Step

## ✅ Build from Android Studio (Recommended)

### Step 1: Make Sure Gradle Sync is Complete
- Check bottom status bar
- Should say: "Gradle sync finished"
- If not, wait for it to complete

### Step 2: Build APK

1. **Click "Build" menu** (top toolbar)
2. **Select "Build Bundle(s) / APK(s)"**
3. **Select "Build APK(s)"**
4. **Wait for build** (~1-3 minutes)
   - Progress shown in bottom status bar
   - "Build" tab shows progress
   - Watch for "BUILD SUCCESSFUL"

### Step 3: Get Your APK

When build completes:
- **Notification appears**: "APK(s) generated successfully"
- **Click "locate"** in the notification
- **OR navigate to**: `app/build/outputs/apk/debug/app-debug.apk`

### Step 4: Verify APK

- **Location**: `app/build/outputs/apk/debug/app-debug.apk`
- **Size**: ~10-50 MB (depends on dependencies)
- **Type**: Debug APK (for testing)

## 📱 Install on Phone

1. **Transfer APK to phone:**
   - USB: Connect phone, drag APK
   - Email: Send to yourself
   - Cloud: Upload to Drive/Dropbox
   - AirDrop: Share via AirDrop

2. **Enable installation:**
   - Android 7-: Settings → Security → Unknown sources
   - Android 8+: Settings → Apps → Special access → Install unknown apps

3. **Install:**
   - Open APK on phone
   - Tap "Install"
   - Done!

## 🆘 Troubleshooting

**"Build failed"**
- Check "Build" tab for errors
- Common: Missing dependencies (should auto-download)
- Try: Build → Clean Project, then Build → Rebuild Project

**"Gradle sync failed"**
- File → Invalidate Caches → Invalidate and Restart
- Sync again

**"APK not found"**
- Check build output in "Build" tab
- Ensure build completed successfully
- Look for "BUILD SUCCESSFUL" message

## ✅ Quick Checklist

- [ ] Gradle sync finished
- [ ] Build → Build APK(s)
- [ ] Build completed successfully
- [ ] APK found at: `app/build/outputs/apk/debug/app-debug.apk`
- [ ] APK transferred to phone
- [ ] App installed!

---

**Ready? Go to Android Studio → Build → Build APK(s)!** 🚀
