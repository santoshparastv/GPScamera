# GPS Camera App

A comprehensive Android application for capturing photos with GPS metadata, user input, and automatic cloud synchronization.

## Features

- **User Authentication**: Login via username or mobile OTP
- **Live Camera Capture**: Only live camera capture (gallery upload disabled)
- **GPS Integration**: Automatic GPS capture with reverse geocoding
- **Image Watermarking**: Auto watermark with location, date/time, username, and input ID
- **Cloud Storage**: Firebase Storage for images
- **Database**: Firestore for metadata storage
- **Google Sheets Export**: Automatic export to Google Sheets
- **Offline Support**: Capture and save offline, auto-sync when online

## Setup Instructions

### 1. Firebase Setup

1. Create a Firebase project at [Firebase Console](https://console.firebase.google.com/)
2. Add Android app with package name: `com.gpscamera.app`
3. Download `google-services.json` and place it in `app/` directory
4. Enable the following Firebase services:
   - Authentication (Phone Auth)
   - Firestore Database
   - Storage

### 2. Google Sheets API Setup

1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project or select existing
3. Enable Google Sheets API
4. Create Service Account credentials
5. Download JSON key file and place in `app/src/main/assets/` (or implement OAuth 2.0 flow)
6. Share your Google Sheet with the service account email
7. Update `GoogleSheetsService.kt` to load credentials

### 3. Build Configuration

1. Update `app/build.gradle` if needed
2. Sync Gradle files
3. Build and run the app

## Project Structure

```
app/
├── src/main/
│   ├── java/com/gpscamera/app/
│   │   ├── data/
│   │   │   ├── model/          # Data models
│   │   │   └── database/        # Room database
│   │   ├── repository/         # Firebase repository
│   │   ├── service/            # Location, Sync, Google Sheets services
│   │   ├── ui/                  # Activities and UI components
│   │   │   ├── login/
│   │   │   ├── home/
│   │   │   ├── camera/
│   │   │   ├── input/
│   │   │   ├── preview/
│   │   │   └── records/
│   │   └── util/                # Utility classes
│   └── res/                     # Resources
└── google-services.json         # Firebase config (add your own)
```

## Usage Flow

1. **Login**: Enter username or mobile number with OTP
2. **Home Screen**: Choose to capture photo, view records, or sync data
3. **Camera**: Capture photo with automatic GPS fetch
4. **Input Screen**: Enter alphanumeric ID (max 1KB) and optional remarks
5. **Preview**: Review watermarked image and save
6. **Auto Upload**: Image and metadata uploaded to Firebase and Google Sheets

## Database Schema

### Firestore Collections

**users**
- userId (string)
- name (string)
- mobile/email (string)
- role (string)
- createdAt (timestamp)

**captures**
- captureId (string)
- userId (reference)
- userName (string)
- imageUrl (string)
- latitude (number)
- longitude (number)
- address (string)
- captureDate (string)
- captureTime (string)
- inputId (string)
- remarks (string)
- deviceId (string)
- gpsAccuracy (number)
- createdAt (timestamp)

## Permissions Required

- CAMERA
- ACCESS_FINE_LOCATION
- ACCESS_COARSE_LOCATION
- INTERNET
- READ_EXTERNAL_STORAGE (for Android < 13)
- READ_MEDIA_IMAGES (for Android 13+)

## Configuration

### Google Sheets

To enable Google Sheets export:
1. Set up Google Sheets API credentials
2. Create a Google Sheet with headers: User Name, Image URL, Latitude, Longitude, Address, Date, Time, Input ID, Remarks
3. Share the sheet with your service account
4. Set the sheet ID in SharedPreferences using `saveGoogleSheetId()`

## Notes

- The app requires GPS to be enabled for location capture
- Images are compressed to 85% quality before upload
- Offline captures are automatically synced when network is available
- Input ID validation: alphanumeric, max 1KB

## License

This project is created for GPS Camera App implementation.
