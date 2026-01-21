# GPS Camera App - Setup Guide

## Prerequisites

- Android Studio Hedgehog (2023.1.1) or later
- JDK 8 or higher
- Android SDK (API 24 minimum, API 34 target)
- Firebase account
- Google Cloud account (for Sheets API)

## Step-by-Step Setup

### 1. Clone/Download Project

```bash
# If using git
git clone <repository-url>
cd "GPS Camera With ID and name"
```

### 2. Firebase Setup

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Click "Add project" or select existing project
3. Click "Add app" → Select Android
4. Register app:
   - Package name: `com.gpscamera.app`
   - App nickname: GPS Camera (optional)
   - Debug signing certificate SHA-1 (optional for now)
5. Download `google-services.json`
6. Place `google-services.json` in `app/` directory (same level as `build.gradle`)
7. Enable Firebase services:
   - **Authentication**: 
     - Go to Authentication → Sign-in method
     - Enable "Phone" provider
   - **Firestore Database**:
     - Go to Firestore Database → Create database
     - Start in test mode (or production with rules)
   - **Storage**:
     - Go to Storage → Get started
     - Start in test mode (or production with rules)

### 3. Google Sheets API Setup

#### Option A: Service Account (Recommended for automated export)

1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create new project or select existing
3. Enable "Google Sheets API":
   - APIs & Services → Library
   - Search "Google Sheets API"
   - Click Enable
4. Create Service Account:
   - APIs & Services → Credentials
   - Create Credentials → Service Account
   - Fill in details and create
   - Click on the service account → Keys → Add Key → JSON
   - Download the JSON key file
5. Place JSON file in `app/src/main/assets/service_account.json`
6. Update `GoogleSheetsService.kt`:
   ```kotlin
   private fun getCredentials(httpTransport: NetHttpTransport): Credential {
       val inputStream = context.assets.open("service_account.json")
       val credential = GoogleCredential.fromStream(inputStream)
           .createScoped(scopes)
       return credential
   }
   ```
7. Create Google Sheet:
   - Create new Google Sheet
   - Add headers in row 1: User Name, Image URL, Latitude, Longitude, Address, Date, Time, Input ID, Remarks
   - Share the sheet with the service account email (from JSON file)
   - Copy the Sheet ID from URL: `https://docs.google.com/spreadsheets/d/{SHEET_ID}/edit`
   - Set it in app: Use SharedPreferencesHelper.saveGoogleSheetId()

#### Option B: OAuth 2.0 (For user-based access)

1. Follow steps 1-3 from Option A
2. Create OAuth 2.0 credentials:
   - APIs & Services → Credentials
   - Create Credentials → OAuth client ID
   - Application type: Android
   - Package name: `com.gpscamera.app`
   - SHA-1: Get from `keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android`
3. Implement OAuth flow in `GoogleSheetsService.kt`

### 4. Build Configuration

1. Open project in Android Studio
2. Wait for Gradle sync to complete
3. If sync fails:
   - File → Invalidate Caches → Invalidate and Restart
   - Check internet connection
   - Verify all dependencies in `app/build.gradle`

### 5. Run the App

1. Connect Android device or start emulator (API 24+)
2. Enable USB debugging (for physical device)
3. Click Run → Run 'app'
4. Grant permissions when prompted:
   - Camera
   - Location (Fine and Coarse)

### 6. Testing

1. **Login Test**:
   - Try username login
   - Try mobile OTP (requires Firebase Phone Auth setup)

2. **Camera Test**:
   - Click "Capture Photo"
   - Verify GPS is fetched
   - Check accuracy display

3. **Input Test**:
   - Enter alphanumeric ID
   - Test 1KB limit validation
   - Add optional remarks

4. **Upload Test**:
   - Verify image upload to Firebase Storage
   - Check Firestore entry
   - Verify Google Sheets export (if configured)

5. **Offline Test**:
   - Turn off internet
   - Capture photo
   - Verify local save
   - Turn on internet
   - Click "Sync Pending Data"
   - Verify upload

## Troubleshooting

### Firebase Issues
- **Error: "google-services.json not found"**
  - Ensure file is in `app/` directory
  - File name must be exactly `google-services.json`

- **Error: "FirebaseApp not initialized"**
  - Check if Google Services plugin is applied in `app/build.gradle`
  - Rebuild project

### Google Sheets Issues
- **Error: "Credentials not configured"**
  - Ensure service account JSON is in `app/src/main/assets/`
  - Update `getCredentials()` method in `GoogleSheetsService.kt`
  - Verify sheet is shared with service account email

- **Error: "Permission denied"**
  - Share Google Sheet with service account email
  - Check service account has Editor access

### Location Issues
- **GPS not working**
  - Enable location services on device
  - Grant location permissions
  - Test in open area (better GPS signal)

### Camera Issues
- **Camera not opening**
  - Grant camera permission
  - Check if device has camera
  - Test on physical device (emulator may have issues)

## Production Checklist

- [ ] Replace test Firebase rules with production rules
- [ ] Set up proper security rules for Firestore
- [ ] Configure Storage security rules
- [ ] Set up proper error logging
- [ ] Add analytics (Firebase Analytics)
- [ ] Test on multiple devices
- [ ] Optimize image compression
- [ ] Set up crash reporting (Firebase Crashlytics)
- [ ] Configure ProGuard rules
- [ ] Sign APK with release keystore
- [ ] Test offline functionality thoroughly

## Support

For issues or questions:
1. Check Firebase Console for errors
2. Check Android Logcat for runtime errors
3. Verify all configurations are correct
4. Review ACTIVITY_LOG.md for recent changes
