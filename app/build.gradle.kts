import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.android.application")
    // 必须应用：否则 kotlinOptions / kotlin 扩展访问器不存在，脚本编译期直接失败
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.likexuan.checkin"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.likexuan.checkin"
        minSdk = 24            // Android 7.0
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            // 自签名的 debug 配置也能装，够个人用了
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

// Kotlin 2.0+ 用 compilerOptions（kotlinOptions 已废弃）
kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    // 只用系统自带的 WebView，不需要任何第三方库
}
