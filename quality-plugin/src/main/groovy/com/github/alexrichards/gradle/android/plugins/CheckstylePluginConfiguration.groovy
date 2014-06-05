package com.github.alexrichards.gradle.android.plugins

import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.Project
import org.gradle.api.plugins.quality.Checkstyle

class CheckstylePluginConfiguration extends PluginConfiguration {

  CheckstylePluginConfiguration() {
    super(Checkstyle, 'checkstyle', 'Checkstyle', '/config/checkstyle.xml')
  }

  @Override
  Closure configureTask(final Project project, final ApplicationVariant variant) {
    return {
      configFile getConfigFile(project, '/config/checkstyle.xml')

      source variant.javaCompile.source.files
      classpath = variant.javaCompile.classpath

      include '**/*.java'
      exclude '**/R.java'

      ignoreFailures false
      showViolations false
    }
  }
}
