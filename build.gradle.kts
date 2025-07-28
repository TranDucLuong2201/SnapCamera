import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt
import nl.littlerobots.vcu.plugin.versionCatalogUpdate
import org.jetbrains.kotlin.gradle.dsl.KotlinBaseExtension

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.google.gms.google.services) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.gradle.spotless) apply false
    alias(libs.plugins.ben.manes)
    alias(libs.plugins.littlerobots)
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.kotlin.serialization) apply false
}
detekt {
    toolVersion = "1.23.8"
    config.setFrom(file("config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
}

// Kotlin DSL
tasks.withType<Detekt>().configureEach {
    reports {
        xml.required.set(true)
        html.required.set(true)
        sarif.required.set(true)
        md.required.set(true)
    }
}
allprojects {

    // Apply spotless to all projects including root (if root has Kotlin files)
    plugins.withId("com.diffplug.spotless") {
        configure<com.diffplug.gradle.spotless.SpotlessExtension> {
            val ktlintVersion = rootProject.libs.versions.ktlint.get()
            kotlin {
                ktlint(ktlintVersion).editorConfigOverride(
                    mapOf(
                        "ktlint_standard_function-naming" to "disabled" // avoid function naming
                    )
                )
                target("**/*.kt")
                targetExclude(
                    "**/Res.kt",
                    "**/build/**/*.kt",
                )
                trimTrailingWhitespace()
                leadingSpacesToTabs()
                endWithNewline()
            }
            kotlinGradle {
                target("**/*.gradle.kts", "*.gradle.kts")
                targetExclude("**/build/**/*.kts")
                ktlint(ktlintVersion)
                trimTrailingWhitespace()
                leadingSpacesToTabs()
                endWithNewline()
            }
        }
    }


    afterEvaluate {
        extensions.findByType<KotlinBaseExtension>()?.let {
            it.jvmToolchain {
                languageVersion.set(JavaLanguageVersion.of(21))
                vendor.set(JvmVendorSpec.AZUL)
            }
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
            freeCompilerArgs.set(
                listOf(
                    "-opt-in=kotlin.RequiresOptIn",
                    "-opt-in=kotlin.ExperimentalStdlibApi",
                    "-opt-in=kotlin.time.ExperimentalTime",
                    "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                    "-opt-in=kotlinx.coroutines.FlowPreview"
                )
            )
        }
    }
}

subprojects {
    // Apply spotless plugin to all subprojects
    apply(plugin = "com.diffplug.spotless")

    // Apply versions plugin to subprojects to check dependency updates
    apply(plugin = "com.github.ben-manes.versions")
}
versionCatalogUpdate {
    sortByKey.set(true)
    keep {
        keepUnusedVersions.set(true)
    }
}

// Reject non-stable versions for dependency updates
fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = Regex("^[0-9,.v-]+(-r)?$")
    return !stableKeyword && !regex.matches(version)
}

tasks.withType<DependencyUpdatesTask>().configureEach {
    resolutionStrategy {
        componentSelection {
            all {
                rejectVersionIf {
                    val candidateIsNonStable = isNonStable(candidate.version)
                    val currentIsStable = !isNonStable(currentVersion)
                    candidateIsNonStable && currentIsStable
                }
            }
        }
    }
}
// ---- Thêm task tạo file cấu hình detekt.yml tự động ----
tasks.register("generateDetektConfig") {
    doLast {
        val configFile = file("$rootDir/config/detekt/detekt.yml")
        if (!configFile.exists()) {
            configFile.parentFile.mkdirs()
            configFile.writeText("""
                # Detekt default configuration
                build:
                  maxIssues: 10
                  weights:
                    complexity: 2
                    LongParameterList: 1
                style:
                  WildcardImport:
                    active: true
                # Thêm các rules khác theo ý bạn...
            """.trimIndent())
            println("Detekt config generated at ${configFile.path}")
        } else {
            println("Detekt config already exists at ${configFile.path}")
        }
    }
}

