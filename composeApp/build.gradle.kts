import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.composeHotReload)
    id("com.google.gms.google-services")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm()

    js {
        browser()
        binaries.executable()
    }


    sourceSets {
        androidMain.dependencies {
            kotlin {
                sourceSets {
                    val androidMain by getting {
                        dependencies {
                            implementation("com.google.android.gms:play-services-auth:21.0.0")
                        }
                    }
                }
            }
            implementation(libs.sqldelight.android.driver)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {

            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)

            implementation(libs.compose.uiToolingPreview)

            implementation(compose.materialIconsExtended)
            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.coroutines)

            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.coroutines)

            implementation("dev.gitlive:firebase-auth:2.4.0")
            implementation("dev.gitlive:firebase-firestore:2.4.0")
            implementation(libs.kotlinx.serialization.json)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }


        jvmMain.dependencies {

            implementation(compose.desktop.currentOs)

            implementation(libs.kotlinx.coroutinesSwing)
            implementation(libs.sqldelight.sqlite.driver)
        }
        iosMain.dependencies {
            implementation(libs.sqldelight.native.driver)
        }
    }
}

android {

    namespace = "org.example.project"

    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {

        applicationId = "org.example.project"

        minSdk = libs.versions.android.minSdk.get().toInt()

        targetSdk = libs.versions.android.targetSdk.get().toInt()

        versionCode = 1
        versionName = "1.0"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {

        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {

        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {

    debugImplementation(libs.compose.uiTooling)
}

compose.desktop {

    application {

        mainClass = "org.example.project.MainKt"

        nativeDistributions {

            targetFormats(
                TargetFormat.Dmg,
                TargetFormat.Msi,
                TargetFormat.Deb
            )

            packageName = "TAAL"
            packageVersion = "1.0.0"
        }
    }
}
sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("org.example.project.database")
        }
    }
}
