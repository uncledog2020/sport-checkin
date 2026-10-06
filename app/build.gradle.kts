plugins {
    id("com.android.application")
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

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // 只用系统自带的 WebView，不需要任何第三方库
}
