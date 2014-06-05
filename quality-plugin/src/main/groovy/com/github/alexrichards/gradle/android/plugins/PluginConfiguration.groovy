package com.github.alexrichards.gradle.android.plugins

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.Project
import org.gradle.api.Task

abstract class PluginConfiguration {

  final Class type
  final String name
  final String formalName
  final String configFile

  PluginConfiguration(final Class type, final String name,
                      final String formalName, final String configFile) {
    this.type = type
    this.name = name
    this.formalName = formalName
    this.configFile = configFile
  }

  void apply(final Project project, final AppExtension android) {
    Task checkTask = project.tasks.check

    Task configTask = project.task("init${name.capitalize()}", type: Task) << {
      getConfigFile(project, configFile).withOutputStream { OutputStream out ->
        out << getClass().getResourceAsStream(configFile)
      }
    }

    Task parentTask = project.task(name, type: Task) { Task task ->
      checkTask.dependsOn task

      task.group 'Verification'
      task.description "Runs ${formalName} on all configurations."
    }

    android.getApplicationVariants().each { ApplicationVariant variant ->
      final String taskName = variant.name.capitalize()

      Task variantTask = project.task("${name}${taskName}", type: type) { Task task ->
        task.dependsOn variant.javaCompile
        task.dependsOn configTask
        task.group 'Verification'
        task.description "Runs ${formalName} on the ${taskName} configuration."

        task.configure configureTask(project, variant)
      }

      parentTask.dependsOn variantTask
    }
  }

  abstract Closure configureTask(Project project, ApplicationVariant variant);

  File getConfigFile(final Project project, final String name) {
    File f = new File(project.buildDir, 'androidQualityConfigs' + name);
    f.parentFile.mkdirs()
    f.createNewFile()
    return f
  }
}
