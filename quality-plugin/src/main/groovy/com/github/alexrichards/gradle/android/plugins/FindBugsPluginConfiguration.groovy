package com.github.alexrichards.gradle.android.plugins

import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.Project
import org.gradle.api.plugins.quality.FindBugs

class FindBugsPluginConfiguration extends PluginConfiguration {

  FindBugsPluginConfiguration() {
    super(FindBugs, 'findbugs', 'FindBugs', '/config/findbugs.xml')
  }

  @Override
  Closure configureTask(final Project project, final ApplicationVariant variant) {
    return {
      classes = project.fileTree(variant.javaCompile.destinationDir, {
        include '**/*.class'
        exclude '**/R.class', '**/R$*.class'
      })
      source = variant.javaCompile.source.files
      classpath = variant.javaCompile.classpath

      effort = 'max'
      ignoreFailures = false
      includeFilter = getConfigFile(project, '/config/findbugs.xml')
    }
  }
}
