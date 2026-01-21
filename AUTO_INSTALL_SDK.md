# 🚀 Automatic SDK Installation Guide

## ✅ Quick Steps to Install SDK Automatically

### Method 1: Setup Wizard (Easiest - Do This First!)

1. **In Android Studio, if you see a Setup Wizard:**
   - Click **"Next"** through all screens
   - Choose **"Standard"** installation
   - Accept all license agreements
   - Click **"Finish"**
   - **SDK downloads automatically** (~10 minutes, ~2-5 GB)
   - Android Studio restarts
   - Project opens automatically

2. **If Setup Wizard doesn't appear:**
   - **Close the SDK path dialog** (click "Cancel")
   - **Close Android Studio completely** (Quit)
   - **Reopen Android Studio**
   - Setup Wizard should appear automatically
   - Follow step 1 above

### Method 2: SDK Manager (If Setup Wizard Not Available)

1. **Close the SDK path dialog** (click "Cancel")

2. **Open SDK Manager:**
   - **Tools → SDK Manager**
   - Or: **File → Settings → Appearance & Behavior → System Settings → Android SDK**

3. **Set SDK Location:**
   - At the top, you'll see "Android SDK Location"
   - Enter this path:
     ```
     /Users/santosh/Library/Android/sdk
     ```
   - Or click **"Edit"** and browse to that folder

4. **Install SDK Components:**
   
   **In "SDK Platforms" tab:**
   - ✅ Check **"Android 14.0 (API 34)"**
   - ✅ Check **"Show Package Details"** (optional, to see all packages)
   
   **In "SDK Tools" tab:**
   - ✅ **Android SDK Build-Tools**
   - ✅ **Android SDK Platform-Tools**
   - ✅ **Android Emulator** (optional, for testing)
   - ✅ **Google Play services** (for Firebase)

5. **Click "Apply"**
   - SDK components will download and install automatically
   - Progress shown in bottom status bar
   - Wait ~10 minutes (~2-5 GB download)
   - This is automatic - no manual steps needed!

6. **After Installation:**
   - Click **"OK"**
   - **Restart Android Studio**
   - **File → Open** → Select your project folder
   - Gradle sync starts automatically

## 📍 SDK Installation Location

SDK will be installed to:
```
/Users/santosh/Library/Android/sdk
```

This is the standard location and will be set automatically.

## ⏱️ Installation Time

- **Download time**: ~10 minutes (depends on internet speed)
- **Total size**: ~2-5 GB
- **One-time setup**: Yes, only needed once

## ✅ After SDK Installation

1. **Gradle sync starts automatically** (2-5 minutes)
2. **Project opens**
3. **Build your APK:**
   - **Build → Build APK(s)**
   - Wait 1-3 minutes
   - APK ready!

## 🔍 Verify Installation

After installation, verify:
- **File → Project Structure → SDK Location**
- Should show: `/Users/santosh/Library/Android/sdk`
- **Tools → SDK Manager**
- Should show installed components

## 🆘 Troubleshooting

**"Setup Wizard not appearing"**
- Close Android Studio completely
- Reopen Android Studio
- Setup Wizard should appear

**"SDK download failed"**
- Check internet connection
- Try again
- May need to download manually (rare)

**"SDK Manager not opening"**
- File → Settings → Android SDK
- Or: Configure → SDK Manager

---

**The Setup Wizard is the easiest method - it does everything automatically!** 🚀
