# 📍 Fix: Android Studio Asking for SDK Path

## Quick Solution

**In the SDK path dialog that's open in Android Studio:**

### Step 1: Enter This Path

Copy and paste this exact path:
```
/Users/santosh/Library/Android/sdk
```

OR click "..." (Browse) and navigate to:
- Go to: `/Users/santosh/Library/Android/`
- Select: `sdk` folder
- Click "OK"

### Step 2: If Folder Doesn't Exist or Is Empty

1. **Click "Cancel"** in the SDK path dialog

2. **Install SDK via SDK Manager:**
   - **Tools → SDK Manager**
   - (Or: **File → Settings → Android SDK**)

3. **Set SDK Location:**
   - At the top, you'll see "Android SDK Location"
   - Click **"Edit"** or enter: `/Users/santosh/Library/Android/sdk`
   - Click **"Apply"**

4. **Install SDK Components:**
   - **SDK Platforms** tab:
     - ✅ Check **"Android 14.0 (API 34)"**
   - **SDK Tools** tab:
     - ✅ **Android SDK Build-Tools**
     - ✅ **Android SDK Platform-Tools**
   - Click **"Apply"**
   - Wait for download (~10 minutes, ~2-5 GB)

5. **After Installation:**
   - Click **"OK"**
   - Restart Android Studio
   - Open your project: **File → Open**

## Alternative: Let Setup Wizard Handle It

If you see a Setup Wizard:

1. **Click "Next"** through screens
2. Choose **"Standard"** installation
3. Accept licenses
4. Click **"Finish"**
5. SDK downloads automatically to: `~/Library/Android/sdk`
6. No need to set path manually!

## Exact Paths to Try

Try these in order:

1. `/Users/santosh/Library/Android/sdk`
2. `~/Library/Android/sdk`
3. `/Users/santosh/Library/Android/sdk` (with browse button)

## Verify SDK Location

After setting path, verify:
- **File → Project Structure → SDK Location**
- Should show: `/Users/santosh/Library/Android/sdk`

## Still Having Issues?

1. **Close Android Studio completely**
2. **Reopen Android Studio**
3. **Setup Wizard should appear**
4. Choose **"Standard"** installation
5. Let it install SDK automatically

---

**The easiest way: Let the Setup Wizard install the SDK automatically!** 🚀
