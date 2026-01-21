# 🔧 Fix: Build Tools Corrupted

## Error
"Installed Build Tools revision 36.0.0 is corrupted. Remove and install again using the SDK Manager."

## ✅ Solution: Fix in Android Studio

### Step 1: Remove Corrupted Build Tools

1. **Tools → SDK Manager**
   (Or: **File → Settings → Android SDK**)

2. **In "SDK Tools" tab:**
   - Find **"Android SDK Build-Tools 36.0.0"** (or 36.1.0)
   - **UNCHECK** it to remove
   - Click **"Apply"**
   - Wait for removal

### Step 2: Install Correct Build Tools

1. **Still in SDK Tools tab:**
   - Check **"Android SDK Build-Tools 34.0.0"**
   - (Or latest stable version)
   - Click **"Apply"**
   - Wait for download (~2-5 minutes)

2. **Click "OK"** when done

### Step 3: Sync and Build

1. **File → Sync Project with Gradle Files**
   - Wait for sync to complete

2. **Build → Build APK(s)**
   - Wait 1-3 minutes
   - APK ready!

## ✅ Alternative: Use Build Tools 34

The project is configured for API 34, so Build Tools 34 is the correct version.

## 📋 Quick Steps

1. Tools → SDK Manager
2. SDK Tools → Uncheck Build-Tools 36
3. Check Build-Tools 34
4. Apply
5. Sync Gradle
6. Build APK

---

**Fix this in Android Studio SDK Manager, then build!** 🚀
