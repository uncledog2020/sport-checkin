# Android 工程说明

这是一个 **WebView 壳工程**，用来把 `likexuan-checkin/` 那套网页版打卡程序
打包成真正的安卓 APK。

**核心：网页代码一行都没改。** 打卡逻辑、统计、结算全在 `assets/` 里。

---

## 目录结构

```
android-wrapper/
├── build.gradle.kts              根构建脚本（AGP 8.7.3 / Kotlin 2.0.21）
├── settings.gradle.kts           仓库配置
├── gradle.properties            JVM 内存等
└── app/
    ├── build.gradle.kts          应用模块配置
    └── src/main/
        ├── AndroidManifest.xml
        ├── java/com/likexuan/checkin/
        │   └── MainActivity.kt   WebView 壳（约 90 行）
        ├── assets/                 ← 网页版原封不动放这
        │   ├── index.html
        │   ├── manifest.json
        │   ├── sw.js
        │   └── icon.svg
        └── res/
            ├── values/          字符串、主题、颜色
            ├── mipmap-*/        各密度图标
            └── mipmap-anydpi-v26/  自适应图标
```

---

## 怎么用（需先装 Android Studio）

1. 装好 Android Studio（见 `../APK打包指南.md`）
2. Android Studio → **Open** → 选 `android-wrapper` 这个文件夹
3. 等待 Gradle 同步（首次会自动下载依赖，约几分钟）
4. 菜单 **Build → Build Bundle(s) / APK(s) → Build APK(s)**
5. 产物：`app/build/outputs/apk/debug/app-debug.apk`
6. 传到手机安装（需允许「安装未知来源应用」）

---

## 改了网页代码怎么办

改的是 `../` 目录下的源文件。改完要同步到 assets：

```bash
cp ../index.html ../manifest.json ../sw.js ../icon.svg app/src/main/assets/
```

然后重新 Build。

> 建议直接在 Android Studio 里改 `assets/index.html`，
> 改完重新 Build 就行，省得同步。

---

## 关键配置说明

| 配置 | 值 | 为什么 |
|---|---|---|
| `minSdk` | 24 | Android 7.0，覆盖率足够 |
| `compileSdk` | 35 | 需要 36+ 的 AS 版本才支持，这里留余量 |
| `jvmTarget` | 17 | AGP 8.x 强制要求，装 JDK 8/11 会直接失败 |
| `domStorageEnabled` | true | **必须开**，localStorage 靠它，打卡数据全存在这 |
| `FORCE_DARK_OFF` | — | 防止系统深色模式把界面染黑 |

---

## 已知限制

- **不需要网络**：所有文件在 assets 里，装完断网也能用
- **不需要任何权限**：AndroidManifest 里没声明任何 permission
- **数据存 WebView 的 localStorage**：和 PWA 版一样存在浏览器存储里
  - 清 App 数据会清掉打卡记录
  - 设置里有「导出备份」，定期存一份
- **debug 签名**：debug 版用 Android Studio 自动生成的调试证书，
  个人装机完全够用。要上架应用商店才需要正式签名。
