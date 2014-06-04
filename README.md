# Gradle Android Quality Plugin

A simple plugin to apply and configure some popular quality tools in Android Gradle Projects.

## Usage

Simply add to your `build.gradle` file as another plugin.

_You'll need to install it into your local Maven repo first though._

```
$ git clone git@github.com:alex-richards/GradleAndroidQualityPlugin.git
$ cd GradleAndroidQualityPlugin
$ gradle install
```

```
buildscript {
  repositories {
    mavenLocal()
    // ...
  }
  dependencies {
    classpath 'com.github.alexrichards.gradle:quality-plugin:1.0.+'
    // ...
  }
}

// ...

apply plugin: 'android-quality'
```

Now ~~loads of popular Java quality tools~~ FindBugs is added and configured automatically for your project.

You can run it through `check` or `findbugs[buildVariant]`.

## TODO

* Maven Central (?)
* FindBugs
  * ~~Apply Plugin~~
  * Android Config
* PMD
  * Apply Plugin
  * Android Config
* CheckStyle
  * Apply Plugin
  * Android Config
