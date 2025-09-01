plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("jacoco")
}

android {
    namespace = "com.flab.domain"
    compileSdk = 35

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")

    reports {
        xml.required.set(true)
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco/jacocoTestReport/html"))
    }

    val fileFilter = listOf(
        "**/R.class", "**/R$*.class", "**/BuildConfig.*", "**/Manifest*.*",
        "**/com/android/**/*.class", "**/*\$ViewInjector*.*", "**/*\$ViewBinder*.*"
    )

    val debugTree = fileTree(layout.buildDirectory.dir("intermediates/javac/debug/classes")) {
        exclude(fileFilter)
    }
    val kotlinDebugTree = fileTree(layout.buildDirectory.dir("tmp/kotlin-classes/debug")) {
        exclude(fileFilter)
    }

    classDirectories.setFrom(files(debugTree, kotlinDebugTree))
    sourceDirectories.setFrom(files("src/main/java", "src/main/kotlin"))
    executionData.setFrom(fileTree(layout.buildDirectory.dir("jacoco")).include("**/*.exec"))
}

dependencies {

    implementation(libs.javax.inject)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // testCode
    testImplementation(libs.junit)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlin.test.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
