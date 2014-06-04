package com.github.alexrichards.gradle.android.plugins

import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.Project
import org.gradle.api.Task

class FindBugsPluginConfiguration extends PluginConfiguration {

  FindBugsPluginConfiguration() {
    super('findbugs', 'FindBugs')
  }

  @Override
  Closure configureTask(final Project project, final ApplicationVariant variant) {
    return { Task task ->
      classes = project.fileTree(variant.javaCompile.destinationDir, {
        include '**/*.class'
        exclude '**/R.class', '**/R$*.class'
      })
      source = variant.javaCompile.source.files
      classpath = variant.javaCompile.classpath

      effort = 'max'
      ignoreFailures = true
      includeFilter = getClass().getResource('config/findbugs.xml')

      reports {
        xml.enabled = false
        html.enabled = true
      }
    }
  }
}
