plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

fun registerExtractSoTask(taskName: String, abiFolder: String, soPathInJar: String) {
    tasks.register<Copy>(taskName) {
        val dependencyJar = configurations["debugRuntimeClasspath"]
            .incoming
            .artifactView {
                attributes {
                    attribute(Attribute.of("artifactType", String::class.java), "jar")
                }
            }
            .files
            .find { it.name.contains("h3") }
            ?: throw GradleException("h3 JAR not found in debugRuntimeClasspath")

        from(zipTree(dependencyJar)) {
            include(soPathInJar)
            eachFile { path = name }
        }
        into("$projectDir/src/main/jniLibs/$abiFolder")
    }
}

registerExtractSoTask("extractArm64LibFromJar", "arm64-v8a", "android-arm64/libh3-java.so")
registerExtractSoTask("extractArmLibFromJar", "armeabi-v7a", "android-arm/libh3-java.so")

tasks.named("preBuild") {
    dependsOn("extractArm64LibFromJar", "extractArmLibFromJar")
}

tasks.named("clean") {
    doLast {
        delete("$projectDir/src/main/jniLibs/arm64-v8a/libh3-java.so")
        delete("$projectDir/src/main/jniLibs/armeabi-v7a/libh3-java.so")
    }
}

android {
    namespace = "com.isaacbrodsky.h3helloworld"
    compileSdk = 34
    // Must be at least 28 for 16kb alignment to happen by default
    ndkVersion = "28.2.13676358"

    defaultConfig {
        applicationId = "com.isaacbrodsky.h3helloworld"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            abiFilters.add("armeabi-v7a")
            abiFilters.add("arm64-v8a")
        }

        externalNativeBuild {
            cmake {
                // Native build exists purely to force the STL to be the shared one,
                // so that libh3-java can find lroundl, etc.
                arguments.add("-DANDROID_STL=c++_shared")
                cppFlags += ""
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

    splits { abi { isEnable = false } }
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
}

dependencies {
    implementation(libs.h3)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
