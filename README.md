## Information
This is a Kotlin Multiplatform project targeting Android, Web, Desktop.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),
[Kotlin/Wasm](https://kotl.in/wasm/)…

## Installation 
Clone project
```bash
git clone https://github.com/DragonKEME/home2sec.git
```

Download AndroidStudio.
Open project in android studio and sync gradle project.

## Launch
Android: Connect your device (whit ADB debugging enabled) or use AndroidStudio emulator.
Launch `composeApp` in configuration.

JavaApp: Lunch `Home2SEC - JavaApp` configuration

Web: Lunch `Home2SEC - Web` configuration