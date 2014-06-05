package com.github.alexrichards.gradle.android.plugins

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.Project
import org.gradle.api.Task

abstract class PluginConfiguration {

  final Class type
  final String name
  final String formalName
  final String[] configFiles

  PluginConfiguration(final Class type, final String name,
                      final String formalName, final String[] configFiles) {
    this.type = type
    this.name = name
    this.formalName = formalName
    this.configFiles = configFiles
  }

  void apply(final Project project, final AppExtension android) {
    project.task(name, type: Task) { Task task ->
      project.tasks.check.dependsOn task

      task.group 'Verification'
      task.description "Runs ${formalName} on all configurations."
    }

    android.getApplicationVariants().each { ApplicationVariant variant ->
      final String taskName = variant.name.capitalize()

      project.task("${name}${taskName}", type: type) { Task task ->
        task.dependsOn variant.javaCompile

        task.group 'Verification'
        task.description "Runs ${formalName} on the ${taskName} configuration."

        task.configure configureTask(project, variant)
      }
    }
  }

  abstract Closure configureTask(Project project, ApplicationVariant variant);

  File getConfigFile(final Project project, final String name) {
    File f = new File(project.buildDir, ('androidQualityConfigs' + name).replaceAll('/', File.separator));
    f.parentFile.mkdirs();

    if (f.exists()) {
      f.delete();
    }

    f.createNewFile()
    f.withOutputStream { OutputStream out ->
      out << getClass().getResourceAsStream(name)
    }

    return f;
  }
}
