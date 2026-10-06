# 运动打卡 · Android

李柯璇的运动打卡程序，打卡 +30 元，漏打 −30 元，每月结算一次，可补打。

这是一个 **WebView 壳工程**：打卡逻辑（HTML/CSS/JS）完整放在
`app/src/main/assets/`，安卓端只有一个约 90 行的 `MainActivity` 负责显示它。

## 怎么拿 APK

1. 顶部 **Actions** 标签页
2. 左侧选 **构建 APK**
3. 点右侧 **Run workflow** → 确认绿色按钮 **Run workflow**
4. 等 3～5 分钟，构建完成后本页出现绿色对勾
5. 在运行记录下方 **Artifacts** 区域下载 `运动打卡-debug.apk`

## 自己改代码

网页逻辑改 `app/src/main/assets/index.html`，改完重新触发一次构建即可。
安卓壳改 `app/src/main/java/com/likexuan/checkin/MainActivity.kt`。

## 规则说明

| 项目 | 规则 |
|---|---|
| 打卡单位 | 每个自然日一次，当天重复打不重复计 |
| 打卡 / 漏打 | +30 / −30 元 |
| 补打 | 可以，漏掉的日子随时点回去补 |
| 结算 | 每月一次，结算后该月锁定不可改 |
| 月净额 | （打卡天数 − 漏打天数）× 30 |
| 今天未打 | 算「待打卡」，当天不扣钱 |
| 起始日期 | 默认当天，可在设置里改，起始日之前不计 |

## 注意事项

- **数据存本地**：WebView 的 localStorage，卸载 App 或清除数据会丢，
  记得用设置里的「导出备份」存一份
- **无网络依赖**：所有文件打包在 assets 里，装完断网可用
- **零权限**：不申请任何系统权限
- debug 签名，仅供个人安装，不能上架应用商店

## 技术栈

- AGP 8.7.3 / Kotlin 2.0.21 / Gradle 8.11.1
- JDK 17（`jvmTarget = 17`）
- minSdk 24（Android 7.0）/ compileSdk 35
- 无任何第三方依赖

## 许可

仅供个人使用。
