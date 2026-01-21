# 🔧 Fix: Android SDK Not Specified

## Error Message
"Unable to continue until an Android SDK is specified"

## ✅ Solution: Complete Android Studio Setup

This error appears because Android Studio needs to download and configure the Android SDK. Follow these steps:

### Step 1: Complete Setup Wizard

1. **If Setup Wizard is open:**
   - Click **"Next"** through the welcome screens
   - Choose **"Standard"** installation type
   - Accept license agreements
   - Click **"Finish"**
   - **Wait for SDK download** (~5-10 minutes, ~2-5 GB)
   - This is a one-time setup

2. **If Setup Wizard closed:**
   - **Tools → SDK Manager** (or **Configure → SDK Manager**)
   - Or restart Android Studio - it should show setup wizard again

### Step 2: Install SDK Components

1. **Open SDK Manager:**
   - **Tools → SDK Manager**
   - Or: **File → Settings → Appearance & Behavior → System Settings → Android SDK**

2. **Install Required Components:**
   - ✅ **Android SDK Platform** (API 34 - Android 14)
   - ✅ **Android SDK Platform-Tools**
   - ✅ **Android SDK Build-Tools**
   - ✅ **Android Emulator** (optional, for testing)
   - ✅ **Google Play services** (for Firebase)

3. **Click "Apply" or "OK"**
   - Wait for download and installation
   - This may take 5-10 minutes

### Step 3: Set SDK Location

If SDK is installed but not detected:

1. **File → Project Structure** (or **File → Settings**)
2. Go to **SDK Location**
3. Set **Android SDK location** to:
   ```
   ~/Library/Android/sdk
   ```
   Or click "..." and browse to SDK folder

4. Click **"Apply"** and **"OK"**

### Step 4: Verify SDK

1. **Tools → SDK Manager**
2. Check **"SDK Platforms"** tab
3. Ensure **Android 14.0 (API 34)** is installed
4. Check **"SDK Tools"** tab
5. Ensure **Android SDK Build-Tools** is installed

## 🚀 Quick Fix (Recommended)

**Easiest way - Let Android Studio do it:**

1. **Close Android Studio** (if open)
2. **Reopen Android Studio**
3. **Setup Wizard should appear**
4. Choose **"Standard"** installation
5. **Wait for SDK download** (~10 minutes)
6. **Done!**

## 📋 Manual SDK Installation

If setup wizard doesn't appear:

```bash
# Check if SDK exists
ls ~/Library/Android/sdk

# If not, Android Studio will create it during setup
# Or manually:
mkdir -p ~/Library/Android/sdk
```

Then in Android Studio:
1. **Tools → SDK Manager**
2. Select components to install
3. Click **"Apply"**

## ✅ After SDK is Installed

1. **Restart Android Studio**
2. **Open your project:**
   - File → Open
   - Select: `/Users/santosh/GPS Camera With ID and name`
3. **Wait for Gradle sync** (2-5 minutes)
4. **Build APK!**

## 🔍 Check SDK Location

In Android Studio:
- **File → Project Structure → SDK Location**
- Should show: `~/Library/Android/sdk` or `/Users/santosh/Library/Android/sdk`

## ⚠️ Common Issues

**"SDK download failed"**
- Check internet connection
- Try again
- May need to download manually

**"SDK location not found"**
- Let Android Studio create it automatically
- Or specify path manually in SDK Manager

**"Setup wizard not appearing"**
- Close and reopen Android Studio
- Or: **Help → Find Action → "Setup Wizard"**

---

**The easiest fix: Let the Setup Wizard complete! It will download everything automatically.** 🚀
