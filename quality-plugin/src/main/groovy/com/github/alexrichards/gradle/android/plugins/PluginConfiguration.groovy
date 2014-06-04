package com.github.alexrichards.gradle.android.plugins

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.quality.FindBugs

abstract class PluginConfiguration {

  final String name;
  final String formalName;

  PluginConfiguration(final String name, final String formalName) {
    this.name = name
    this.formalName = formalName;
  }

  void apply(final Project project, final AppExtension android) {
    project.task(name, type: Task) { Task task ->
      project.tasks.check.dependsOn task

      task.group 'Verification'
      task.description "Runs ${formalName} on all configurations."
    }

    android.getApplicationVariants().each { ApplicationVariant variant ->
      final String taskName = variant.name.capitalize()

      project.task("${name}${taskName}", type: FindBugs) { Task task ->
        task.dependsOn variant.javaCompile

        task.group 'Verification'
        task.description "Runs ${formalName} on the ${taskName} configuration."

        task.configure configureTask(project, variant)
      }
    }
  }

  abstract Closure configureTask(Project project, ApplicationVariant variant);
}
