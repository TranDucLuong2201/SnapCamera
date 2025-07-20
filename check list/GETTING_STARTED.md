# 🚀 Getting Started - SnapCamera

## 📋 Prerequisites

### Required Software
- **Android Studio Hedgehog | 2023.1.1** or later
- **JDK 17** (OpenJDK or Oracle JDK)
- **Git** for version control
- **Android SDK** API 34 (Android 14)
- **Gradle** 8.2+ (bundled with Android Studio)

### System Requirements
- **Windows**: Windows 10/11 (64-bit)
- **macOS**: macOS 10.15 or later
- **Linux**: Ubuntu 18.04+ or equivalent
- **RAM**: 8GB minimum, 16GB recommended
- **Storage**: 10GB free space

## 🛠️ Initial Setup

### Step 1: Clone Repository
```bash
git clone https://github.com/your-username/SnapCamera.git
cd SnapCamera
```

### Step 2: Open in Android Studio
1. Launch Android Studio
2. Select "Open an existing Android Studio project"
3. Navigate to the SnapCamera folder and select it
4. Wait for Gradle sync to complete

### Step 3: Configure Firebase
1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Create a new project named "SnapCamera"
3. Add Android app with package name: `com.snapcamera`
4. Download `google-services.json` and place it in the `app/` directory
5. Enable the following Firebase services:
   - Authentication (Email/Password, Google Sign-In)
   - Firestore Database
   - Firebase Storage
   - Cloud Messaging
   - Analytics
   - Crashlytics

### Step 4: Configure Signing
```bash
# Generate debug keystore (if not exists)
keytool -genkey -v -keystore debug.keystore -storepass android -alias androiddebugkey -keypass android -keyalg RSA -keysize 2048 -validity 10000 -dname "CN=Android Debug,O=Android,C=US"
```

## 🏗️ Project Structure Setup

### Step 1: Create Package Structure
```bash
# Create core packages
mkdir -p app/src/main/java/com/snapcamera/core/{common,data,domain,presentation}
mkdir -p app/src/main/java/com/snapcamera/core/common/{constants,extensions,utils,di}
mkdir -p app/src/main/java/com/snapcamera/core/data/{local,remote,repositories,datasources}
mkdir -p app/src/main/java/com/snapcamera/core/data/local/{dao,entities,database}
mkdir -p app/src/main/java/com/snapcamera/core/data/remote/{api,dto,services}
mkdir -p app/src/main/java/com/snapcamera/core/domain/{entities,repositories,usecases,models}
mkdir -p app/src/main/java/com/snapcamera/core/presentation/{components,theme,navigation,common}

# Create feature packages
mkdir -p app/src/main/java/com/snapcamera/features/{auth,onboarding,timetable,camera,gallery,dashboard,exam,export}
mkdir -p app/src/main/java/com/snapcamera/features/auth/{presentation,domain,data}
mkdir -p app/src/main/java/com/snapcamera/features/auth/presentation/{screens,viewmodels,states}
mkdir -p app/src/main/java/com/snapcamera/features/auth/domain/{entities,repositories,usecases}
mkdir -p app/src/main/java/com/snapcamera/features/auth/data/{repositories,datasources}

# Create shared packages
mkdir -p app/src/main/java/com/snapcamera/shared/{ui,utils,resources}
mkdir -p app/src/main/java/com/snapcamera/shared/ui/{components,theme,utils}
```

### Step 2: Create Resource Directories
```bash
# Create resource directories
mkdir -p app/src/main/res/{values,drawable,layout,menu,navigation}
mkdir -p app/src/main/res/values-{en,vi}
mkdir -p app/src/main/res/drawable-{mdpi,hdpi,xhdpi,xxhdpi,xxxhdpi}
```

## 📱 First Run Setup

### Step 1: Build Configuration
1. Open `app/build.gradle.kts`
2. Verify all dependencies are correctly configured
3. Sync project with Gradle files

### Step 2: Run on Device/Emulator
1. Connect Android device or start emulator
2. Enable Developer Options and USB Debugging (for physical device)
3. Click "Run" button in Android Studio
4. Select target device
5. Wait for app to install and launch

### Step 3: Verify Installation
- App should launch to login screen
- Check logcat for any errors
- Verify Firebase connection

## 🔧 Development Environment

### Step 1: Code Style Configuration
1. Install Spotless plugin
2. Configure code formatting rules
3. Set up pre-commit hooks

### Step 2: Testing Setup
1. Create test directories
2. Configure test dependencies
3. Set up test runners

### Step 3: Debug Configuration
1. Configure debug build variant
2. Set up logging
3. Configure crash reporting

## 📊 Development Workflow

### Daily Development Process
1. **Morning Setup**
   ```bash
   git pull origin develop
   ./gradlew clean build
   ```

2. **Feature Development**
   - Create feature branch: `git checkout -b feature/feature-name`
   - Implement feature following architecture guidelines
   - Write unit tests
   - Update documentation

3. **Code Review**
   - Commit changes: `git commit -m "feat: add feature description"`
   - Push branch: `git push origin feature/feature-name`
   - Create pull request

4. **Testing**
   ```bash
   ./gradlew test
   ./gradlew connectedAndroidTest
   ./gradlew lint
   ```

5. **Code Quality**
   ```bash
   ./gradlew spotlessApply
   ./gradlew detekt
   ./gradlew ktlintCheck
   ```

## 🚨 Troubleshooting

### Common Issues

#### 1. Gradle Sync Failed
```bash
# Clean and rebuild
./gradlew clean
./gradlew build

# Invalidate caches in Android Studio
File → Invalidate Caches and Restart
```

#### 2. Build Errors
```bash
# Check Gradle version
./gradlew --version

# Update Gradle wrapper
./gradlew wrapper --gradle-version 8.2

# Check dependency conflicts
./gradlew app:dependencies
```

#### 3. Firebase Issues
- Verify `google-services.json` is in correct location
- Check Firebase project configuration
- Verify package name matches Firebase app

#### 4. Camera Permissions
- Add camera permissions to `AndroidManifest.xml`
- Request runtime permissions in app
- Handle permission denied gracefully

#### 5. Database Issues
```bash
# Reset database (development only)
adb shell pm clear com.snapcamera

# Check database schema
./gradlew room:exportSchema
```

## 📚 Learning Resources

### Official Documentation
- [Android Developer Guide](https://developer.android.com/guide)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Room Database](https://developer.android.com/training/data-storage/room)
- [Firebase Documentation](https://firebase.google.com/docs)

### Architecture Patterns
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [MVVM Pattern](https://developer.android.com/topic/architecture/ui-layer/patterns/mvvm)
- [Repository Pattern](https://developer.android.com/topic/architecture/data-layer)

### Testing
- [Android Testing Guide](https://developer.android.com/training/testing)
- [Compose Testing](https://developer.android.com/jetpack/compose/testing)
- [Room Testing](https://developer.android.com/training/data-storage/room/testing-db)

## 🎯 Next Steps

### Phase 1: Core Setup (Week 1-2)
1. ✅ Project initialization
2. ✅ Firebase configuration
3. ✅ Basic architecture setup
4. 🔄 Authentication implementation
5. 🔄 Database setup

### Phase 2: Basic Features (Week 3-4)
1. 🔄 Onboarding flow
2. 🔄 Timetable management
3. 🔄 Basic UI components
4. 🔄 Navigation setup

### Phase 3: Camera Integration (Week 5-6)
1. 🔄 Camera permissions
2. 🔄 CameraX implementation
3. 🔄 Photo capture
4. 🔄 File storage

### Phase 4: Advanced Features (Week 7-8)
1. 🔄 Gallery implementation
2. 🔄 Photo management
3. 🔄 Export functionality
4. 🔄 Offline sync

## 📞 Support

### Getting Help
1. **Documentation**: Check project documentation first
2. **Issues**: Create GitHub issue with detailed description
3. **Stack Overflow**: Search for similar problems
4. **Team Chat**: Ask in team communication channel

### Code Review Guidelines
1. **Self Review**: Review your own code before submitting
2. **Peer Review**: Have at least one team member review
3. **Automated Checks**: Ensure all CI checks pass
4. **Documentation**: Update relevant documentation

### Release Process
1. **Feature Complete**: All features implemented and tested
2. **Code Review**: All code reviewed and approved
3. **Testing**: Comprehensive testing completed
4. **Documentation**: Documentation updated
5. **Release**: Create release tag and deploy

---

*Follow this guide to set up your development environment and start contributing to SnapCamera. Remember to follow the coding standards and architecture patterns outlined in the project documentation.* 