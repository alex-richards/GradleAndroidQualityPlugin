package com.github.alexrichards.gradle.android.plugins

import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.Project
import org.gradle.api.plugins.quality.Checkstyle

public class CheckstylePluginConfiguration extends PluginConfiguration<Checkstyle> {

  public CheckstylePluginConfiguration() {
    super(Checkstyle.class, 'checkstyle', 'Checkstyle', '/config/checkstyle.xml')
  }

  @Override
  public Closure getDependencies(){
    return {
      checkstyle 'com.puppycrawl.tools:checkstyle:5.7'
    }
  }

  @Override
  protected Closure configureTask(final Project project, final ApplicationVariant variant) {
    final File myConfigFile = getFile(project, configFile)
    return { final Checkstyle checkstyle ->
      checkstyle.configFile = myConfigFile

      checkstyle.source = variant.javaCompile.source.files
      checkstyle.classpath = variant.javaCompile.classpath

      checkstyle.include '**/*.java'
      checkstyle.exclude '**/R.java', "**/BuildConfig.java"

      checkstyle.ignoreFailures = false
      checkstyle.showViolations = false
    }
  }
}
