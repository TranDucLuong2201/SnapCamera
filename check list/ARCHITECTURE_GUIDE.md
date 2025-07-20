# 🏗️ Architecture Guide - SnapCamera

## 🎯 Architecture Overview

SnapCamera follows **Clean Architecture** principles with **MVVM** pattern, ensuring:
- **Separation of Concerns**: Clear boundaries between layers
- **Testability**: Easy to unit test each component
- **Maintainability**: Code is organized and easy to understand
- **Scalability**: Easy to add new features
- **Offline-First**: Works without internet connection

## 📐 Architecture Layers

```
┌─────────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                       │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐          │
│  │   Screens   │  │ ViewModels  │  │   States    │          │
│  │ (Compose)   │  │             │  │             │          │
│  └─────────────┘  └─────────────┘  └─────────────┘          │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                     DOMAIN LAYER                            │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐          │
│  │  Use Cases  │  │  Entities   │  │ Repositories│          │
│  │             │  │             │  │  (Interfaces)│         │
│  └─────────────┘  └─────────────┘  └─────────────┘          │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                      DATA LAYER                             │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐          │
│  │Repositories │  │ Data Sources│  │   Models    │          │
│  │(Implementation)│             │  │             │          │
│  └─────────────┘  └─────────────┘  └─────────────┘          │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                  INFRASTRUCTURE LAYER                       │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐          │
│  │   Room DB   │  │   Firebase  │  │   CameraX   │          │
│  │             │  │             │  │             │          │ 
│  └─────────────┘  └─────────────┘  └─────────────┘          │
└─────────────────────────────────────────────────────────────┘
```

## 🗂️ Package Structure

```
com.snapcamera/
├── core/
│   ├── common/
│   │   ├── constants/
│   │   ├── extensions/
│   │   ├── utils/
│   │   └── di/
│   ├── data/
│   │   ├── local/
│   │   │   ├── dao/
│   │   │   ├── entities/
│   │   │   └── database/
│   │   ├── remote/
│   │   │   ├── api/
│   │   │   ├── dto/
│   │   │   └── services/
│   │   ├── repositories/
│   │   └── datasources/
│   ├── domain/
│   │   ├── entities/
│   │   ├── repositories/
│   │   ├── usecases/
│   │   └── models/
│   └── presentation/
│       ├── components/
│       ├── theme/
│       ├── navigation/
│       └── common/
├── features/
│   ├── auth/
│   │   ├── presentation/
│   │   │   ├── screens/
│   │   │   ├── viewmodels/
│   │   │   └── states/
│   │   ├── domain/
│   │   │   ├── entities/
│   │   │   ├── repositories/
│   │   │   └── usecases/
│   │   └── data/
│   │       ├── repositories/
│   │       └── datasources/
│   ├── onboarding/
│   ├── timetable/
│   ├── camera/
│   ├── gallery/
│   ├── dashboard/
│   ├── exam/
│   └── export/
└── shared/
    ├── ui/
    │   ├── components/
    │   ├── theme/
    │   └── utils/
    ├── utils/
    └── resources/
```

## 🎨 Design Patterns

### 1. MVVM (Model-View-ViewModel)
```kotlin
// ViewModel Example
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getCurrentCourseUseCase: GetCurrentCourseUseCase,
    private val capturePhotoUseCase: CapturePhotoUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()
    
    init {
        loadCurrentCourse()
    }
    
    fun capturePhoto() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val result = capturePhotoUseCase()
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        lastPhotoPath = result.photoPath
                    )
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }
}

// UI State
data class DashboardUiState(
    val currentCourse: Course? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val lastPhotoPath: String? = null
)
```

### 2. Repository Pattern
```kotlin
// Repository Interface (Domain Layer)
interface CourseRepository {
    suspend fun getCourses(): Flow<List<Course>>
    suspend fun getCourseById(id: String): Course?
    suspend fun addCourse(course: Course): Result<Course>
    suspend fun updateCourse(course: Course): Result<Course>
    suspend fun deleteCourse(id: String): Result<Unit>
    suspend fun getCurrentCourse(): Course?
}

// Repository Implementation (Data Layer)
@Singleton
class CourseRepositoryImpl @Inject constructor(
    private val localDataSource: CourseLocalDataSource,
    private val remoteDataSource: CourseRemoteDataSource,
    private val networkBoundResource: NetworkBoundResource
) : CourseRepository {
    
    override suspend fun getCourses(): Flow<List<Course>> {
        return networkBoundResource.query(
            fetchFromLocal = { localDataSource.getCourses() },
            shouldFetchFromRemote = { true },
            fetchFromRemote = { remoteDataSource.getCourses() },
            saveRemoteData = { courses -> localDataSource.saveCourses(courses) },
            onFetchFailed = { throwable -> 
                // Handle error
            }
        )
    }
}
```

### 3. Use Case Pattern
```kotlin
// Use Case Example
class GetCurrentCourseUseCase @Inject constructor(
    private val courseRepository: CourseRepository,
    private val scheduleRepository: ScheduleRepository
) {
    suspend operator fun invoke(): Course? {
        val currentTime = System.currentTimeMillis()
        val currentDayOfWeek = Calendar.getInstance().apply {
            timeInMillis = currentTime
        }.get(Calendar.DAY_OF_WEEK)
        
        val currentSchedule = scheduleRepository.getScheduleByDayAndTime(
            dayOfWeek = currentDayOfWeek,
            currentTime = currentTime
        )
        
        return currentSchedule?.let { schedule ->
            courseRepository.getCourseById(schedule.courseId)
        }
    }
}
```

### 4. State Management
```kotlin
// Sealed Class for UI States
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

// StateFlow for Reactive UI
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _authState = MutableStateFlow<UiState<User>>(UiState.Loading)
    val authState: StateFlow<UiState<User>> = _authState.asStateFlow()
    
    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = UiState.Loading
            try {
                val user = authRepository.signIn(email, password)
                _authState.value = UiState.Success(user)
            } catch (e: Exception) {
                _authState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
```

## 🔄 Data Flow

### 1. One-Way Data Flow
```
User Action → ViewModel → Use Case → Repository → Data Source
                ↓
            UI State → Compose UI
```

### 2. Offline-First Flow
```kotlin
// NetworkBoundResource Pattern
class NetworkBoundResource<ResultType, RequestType> @Inject constructor() {
    
    fun query(
        fetchFromLocal: () -> Flow<ResultType>,
        shouldFetchFromRemote: (ResultType) -> Boolean,
        fetchFromRemote: suspend () -> RequestType,
        saveRemoteData: suspend (RequestType) -> Unit,
        onFetchFailed: (Throwable) -> Unit
    ): Flow<ResultType> = flow {
        // 1. Load from local first
        val localData = fetchFromLocal().first()
        emit(localData)
        
        // 2. Check if we should fetch from remote
        if (shouldFetchFromRemote(localData)) {
            try {
                // 3. Fetch from remote
                val remoteData = fetchFromRemote()
                // 4. Save to local
                saveRemoteData(remoteData)
                // 5. Emit updated local data
                emitAll(fetchFromLocal())
            } catch (e: Exception) {
                onFetchFailed(e)
            }
        }
    }
}
```

## 🗄️ Database Architecture

### 1. Room Database Setup
```kotlin
@Database(
    entities = [
        StudentEntity::class,
        CourseEntity::class,
        ScheduleEntity::class,
        PhotoEntity::class,
        ExamEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class SnapCameraDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
    abstract fun courseDao(): CourseDao
    abstract fun scheduleDao(): ScheduleDao
    abstract fun photoDao(): PhotoDao
    abstract fun examDao(): ExamDao
    
    companion object {
        const val DATABASE_NAME = "snapcamera.db"
    }
}
```

### 2. DAO Pattern
```kotlin
@Dao
interface CourseDao {
    @Query("SELECT * FROM courses WHERE semesterId = :semesterId AND isActive = 1")
    fun getActiveCoursesBySemester(semesterId: String): Flow<List<CourseEntity>>
    
    @Query("SELECT * FROM courses WHERE id = :courseId")
    suspend fun getCourseById(courseId: String): CourseEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: CourseEntity)
    
    @Update
    suspend fun updateCourse(course: CourseEntity)
    
    @Query("UPDATE courses SET isActive = 0 WHERE id = :courseId")
    suspend fun deactivateCourse(courseId: String)
    
    @Transaction
    @Query("SELECT * FROM courses WHERE id = :courseId")
    suspend fun getCourseWithSchedules(courseId: String): CourseWithSchedules?
}
```

## 🔐 Dependency Injection

### 1. Hilt Modules
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SnapCameraDatabase {
        return Room.databaseBuilder(
            context,
            SnapCameraDatabase::class.java,
            SnapCameraDatabase.DATABASE_NAME
        )
        .fallbackToDestructiveMigration()
        .build()
    }
    
    @Provides
    fun provideStudentDao(database: SnapCameraDatabase): StudentDao {
        return database.studentDao()
    }
    
    @Provides
    fun provideCourseDao(database: SnapCameraDatabase): CourseDao {
        return database.courseDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    
    @Provides
    @Singleton
    fun provideCourseRepository(
        localDataSource: CourseLocalDataSource,
        remoteDataSource: CourseRemoteDataSource
    ): CourseRepository {
        return CourseRepositoryImpl(localDataSource, remoteDataSource)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    
    @Provides
    fun provideGetCurrentCourseUseCase(
        courseRepository: CourseRepository,
        scheduleRepository: ScheduleRepository
    ): GetCurrentCourseUseCase {
        return GetCurrentCourseUseCase(courseRepository, scheduleRepository)
    }
}
```

## 🧪 Testing Strategy

### 1. Unit Testing
```kotlin
@RunWith(MockitoJUnitRunner::class)
class GetCurrentCourseUseCaseTest {
    
    @Mock
    private lateinit var courseRepository: CourseRepository
    
    @Mock
    private lateinit var scheduleRepository: ScheduleRepository
    
    private lateinit var useCase: GetCurrentCourseUseCase
    
    @Before
    fun setup() {
        useCase = GetCurrentCourseUseCase(courseRepository, scheduleRepository)
    }
    
    @Test
    fun `when current time matches schedule, return course`() = runTest {
        // Given
        val mockSchedule = Schedule(id = "1", courseId = "course1")
        val mockCourse = Course(id = "course1", name = "Java Programming")
        
        whenever(scheduleRepository.getScheduleByDayAndTime(any(), any()))
            .thenReturn(mockSchedule)
        whenever(courseRepository.getCourseById("course1"))
            .thenReturn(mockCourse)
        
        // When
        val result = useCase()
        
        // Then
        assertEquals(mockCourse, result)
    }
}
```

### 2. UI Testing
```kotlin
@RunWith(AndroidJUnit4::class)
class DashboardScreenTest {
    
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun dashboard_displaysCurrentCourse() {
        // Given
        val mockCourse = Course(id = "1", name = "Java Programming")
        val mockState = DashboardUiState(currentCourse = mockCourse)
        
        // When
        composeTestRule.setContent {
            DashboardScreen(
                uiState = mockState,
                onCapturePhoto = {},
                onViewPhotos = {}
            )
        }
        
        // Then
        composeTestRule.onNodeWithText("Java Programming").assertIsDisplayed()
    }
}
```

## 🚀 Performance Optimization

### 1. Lazy Loading
```kotlin
@Composable
fun PhotoGallery(
    photos: List<Photo>,
    onPhotoClick: (Photo) -> Unit
) {
    LazyColumn {
        items(
            items = photos,
            key = { it.id }
        ) { photo ->
            PhotoItem(
                photo = photo,
                onClick = { onPhotoClick(photo) }
            )
        }
    }
}
```

### 2. Image Caching
```kotlin
@Composable
fun PhotoItem(
    photo: Photo,
    onClick: () -> Unit
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(photo.localPath)
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        contentScale = ContentScale.Crop
    )
}
```

### 3. Background Processing
```kotlin
class PhotoSyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    
    override suspend fun doWork(): Result {
        return try {
            val photoRepository: PhotoRepository = get()
            val unsyncedPhotos = photoRepository.getUnsyncedPhotos()
            
            unsyncedPhotos.forEach { photo ->
                photoRepository.syncPhoto(photo)
            }
            
            Result.success()
        } catch (e: Exception) {
            if (runAttemptCount < 3) {
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }
}
```

## 🔄 Error Handling

### 1. Result Pattern
```kotlin
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

// Usage in Repository
suspend fun addCourse(course: Course): Result<Course> {
    return try {
        val savedCourse = localDataSource.insertCourse(course)
        Result.Success(savedCourse)
    } catch (e: Exception) {
        Result.Error(e)
    }
}
```

### 2. Error Handling in ViewModel
```kotlin
fun addCourse(course: Course) {
    viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        
        when (val result = courseRepository.addCourse(course)) {
            is Result.Success -> {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        courses = it.courses + result.data
                    )
                }
            }
            is Result.Error -> {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = result.exception.message
                    )
                }
            }
            is Result.Loading -> {
                _uiState.update { it.copy(isLoading = true) }
            }
        }
    }
}
```

## 📱 Navigation Architecture

### 1. Navigation Setup
```kotlin
@Composable
fun SnapCameraApp() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = "auth"
    ) {
        authGraph(navController)
        onboardingGraph(navController)
        mainGraph(navController)
    }
}

fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation(
        startDestination = "dashboard",
        route = "main"
    ) {
        composable("dashboard") {
            DashboardScreen(
                onNavigateToCamera = { navController.navigate("camera") },
                onNavigateToGallery = { navController.navigate("gallery") }
            )
        }
        composable("camera") {
            CameraScreen(
                onPhotoCaptured = { navController.popBackStack() }
            )
        }
        composable("gallery") {
            GalleryScreen()
        }
    }
}
```

---

*This architecture ensures a scalable, maintainable, and testable codebase that follows Android best practices and modern development patterns.* 