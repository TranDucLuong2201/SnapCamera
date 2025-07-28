import java.util.Properties
import kotlin.apply

plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.kotlin.compose)
	alias(libs.plugins.google.gms.google.services)
	alias(libs.plugins.ksp)
	alias(libs.plugins.hilt)
	alias(libs.plugins.detekt)
}
android {
	namespace = "org.android.snap.snapcamera"
	compileSdk = libs.versions.compileSdk.get().toInt()

	defaultConfig {
		applicationId = "org.android.snap.snapcamera"
		minSdk = libs.versions.minSdk.get().toInt()
		targetSdk = libs.versions.targetSdk.get().toInt()
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		debug {
			isDebuggable = true // Cho phép debug
			versionNameSuffix = "-debug" // Thêm suffix để phân biệt version debug
			isMinifyEnabled = false // Tắt minify để debug dễ dàng
			isShrinkResources = false // Tắt shrink resources

			// Có thể bật logging, crashlytics debug, giả lập API test...
			// buildConfigField dùng để bật các flag riêng cho debug
			buildConfigField("boolean", "LOG_DEBUG", "true")
			buildConfigField("boolean", "USE_MOCK_API", "true")
		}

		release {
			isDebuggable = false
			isMinifyEnabled = true
			isShrinkResources = true
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro",
			)
			buildConfigField("boolean", "LOG_DEBUG", "false")
			buildConfigField("boolean", "USE_MOCK_API", "false")
		}
	}

	val localProperties =
		Properties().apply {
			val file = rootProject.file("local.properties")
			if (file.exists()) {
				load(file.inputStream())
			}
		}
	val apiKey = localProperties["API_KEY"] ?: ""
	flavorDimensions += "environment"

	productFlavors {
		create("dev") {
			dimension = "environment"
			applicationIdSuffix = ".dev"
			versionNameSuffix = "-dev"
			resValue("string", "app_name", "SnapCamera Dev")
			buildConfigField("String", "API_KEY", "\"${apiKey}\"")
			buildConfigField("boolean", "USE_MOCK_API", "true") // ví dụ bật mock API cho dev
		}
		create("production") {
			dimension = "environment"
			resValue("string", "app_name", "SnapCamera")
			buildConfigField("String", "API_KEY", "\"${apiKey}\"")
			buildConfigField("boolean", "USE_MOCK_API", "false")
		}
	}

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	kotlin {
		compilerOptions {
			optIn.add("kotlin.RequiresOptIn")
			freeCompilerArgs = listOf("-XXLanguage:+PropertyParamAnnotationDefaultTargetMode")
		}
	}
	buildFeatures {
		compose = true
		buildConfig = true
	}
	packaging {
		resources.excludes +=
			listOf(
				"/META-INF/{AL2.0,LGPL2.1}",
				"/META-INF/gradle/incremental.annotation.processors",
				"/META-INF/DEPENDENCIES",
				"/META-INF/LICENSE*",
				"/META-INF/NOTICE*",
				"/META-INF/ASL2.0",
				"/META-INF/*.kotlin_module",
				"/META-INF/annotations.kotlin_module",
				"/META-INF/androidx/room/room-compiler-processing/LICENSE.txt",
			)
	}
}

ksp {
	arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {

	implementation(projects.core)
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.lifecycle.runtime.ktx)
	implementation(libs.androidx.activity.compose)
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.androidx.ui)
	implementation(libs.androidx.ui.graphics)
	implementation(libs.androidx.ui.tooling.preview)
	implementation(libs.androidx.material3)
	implementation(libs.firebase.auth)
	implementation(libs.androidx.credentials)
	implementation(libs.androidx.credentials.play.services.auth)
	implementation(libs.googleid)
	implementation(libs.androidx.datastore.core.android)
	implementation(libs.firebase.database)
	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(platform(libs.androidx.compose.bom))
	androidTestImplementation(libs.androidx.ui.test.junit4)
	debugImplementation(libs.androidx.ui.tooling)
	debugImplementation(libs.androidx.ui.test.manifest)
	testImplementation(libs.robolectric)
	testImplementation(libs.mockk.android)
	implementation(libs.androidx.datastore.preferences.core)
	implementation(libs.androidx.datastore.preferences)
	// Truth for assertions (optional, but recommended)
	testImplementation(libs.truth)
	detektPlugins(libs.detekt.formatting)
	implementation(libs.bundles.dagger.hilt)
	ksp(libs.dagger.hilt.compiler)
	implementation(libs.androidx.room.runtime)
	testImplementation(libs.mockk) // hoặc version mới nhất
	testImplementation(libs.kotlinx.coroutines.test)
	ksp(libs.androidx.room.compiler) // Cần thêm dòng này nếu app dùng Room Entity/DAO từ module khác

	implementation(libs.bundles.camerax)
	testImplementation(kotlin("test"))
}
