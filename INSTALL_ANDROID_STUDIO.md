# 📥 Install Android Studio - Step by Step

## 🚀 Quick Installation Guide

### Method 1: Direct Download (Recommended)

1. **Download Android Studio**
   - Visit: https://developer.android.com/studio
   - Click "Download Android Studio"
   - The file will be: `android-studio-*.dmg` (~1GB)

2. **Install**
   - Open the downloaded `.dmg` file
   - Drag "Android Studio" to "Applications" folder
   - Wait for copy to complete

3. **First Launch**
   - Open Applications → Android Studio
   - Click "OK" on setup wizard
   - Choose "Standard" installation
   - Wait for SDK components to download (~2-5 GB)
   - This may take 10-20 minutes

4. **Done!**
   - Android Studio is ready
   - Now you can build your APK!

### Method 2: Using Homebrew (If you have it)

```bash
# Install Homebrew if not installed
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# Install Android Studio
brew install --cask android-studio
```

### Method 3: Command Line Download

```bash
# Download directly
cd ~/Downloads
curl -L -o android-studio.dmg "https://redirector.gvt1.com/edgedl/android/studio/install/2023.1.1.28/android-studio-2023.1.1.28-mac.dmg"

# Mount and install
hdiutil attach android-studio.dmg
cp -R "/Volumes/Android Studio/Android Studio.app" /Applications/
hdiutil detach "/Volumes/Android Studio"
```

## ⚙️ System Requirements

- **macOS**: 10.15 (Catalina) or later
- **RAM**: 8GB minimum, 16GB recommended
- **Disk Space**: ~10GB for Android Studio + SDK
- **Internet**: Required for download and setup

## 📋 Installation Steps (Detailed)

### Step 1: Download
- Go to: https://developer.android.com/studio
- Click "Download Android Studio for Mac"
- File size: ~1GB, download time: 5-15 minutes

### Step 2: Install
1. Open the `.dmg` file (double-click)
2. Drag "Android Studio" icon to "Applications" folder
3. Wait for copy (30 seconds - 2 minutes)

### Step 3: First Launch Setup
1. Open Applications → Android Studio
2. If security warning appears:
   - System Preferences → Security & Privacy → "Open Anyway"
3. Setup Wizard appears:
   - Click "Next" through welcome screens
   - Choose "Standard" installation type
   - Accept license agreements
   - Click "Finish"
4. Android Studio downloads SDK components:
   - This takes 10-20 minutes
   - Requires internet connection
   - ~2-5 GB download

### Step 4: Verify Installation
- Android Studio should open
- Welcome screen appears
- You're ready to build!

## 🎯 After Installation

1. **Open Your Project**
   - File → Open
   - Navigate to: `/Users/santosh/GPS Camera With ID and name`
   - Click OK

2. **Wait for Gradle Sync**
   - First time: 2-5 minutes
   - Downloads dependencies

3. **Build APK**
   - Build → Build APK(s)
   - APK ready in: `app/build/outputs/apk/debug/app-debug.apk`

## 🔧 Troubleshooting

**"Android Studio can't be opened"**
- System Preferences → Security & Privacy
- Click "Open Anyway" next to Android Studio

**"SDK not found"**
- Let Android Studio download it during setup
- Or: Tools → SDK Manager → Install components

**"Installation takes too long"**
- Normal! First install downloads ~5GB
- Be patient, it's a one-time setup

## ✅ Verification

After installation, verify:
```bash
# Check if installed
ls -la /Applications/ | grep -i android

# Should show: Android Studio.app
```

## 📱 Next Steps

Once Android Studio is installed:
1. Open your GPS Camera project
2. Build → Build APK(s)
3. Transfer APK to phone
4. Install and test!

---

**Installation time: 15-30 minutes total**
**Then you can build your APK in 2-3 minutes!** 🚀
