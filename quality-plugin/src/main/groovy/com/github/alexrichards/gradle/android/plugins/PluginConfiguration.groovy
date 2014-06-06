package com.github.alexrichards.gradle.android.plugins

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.Project
import org.gradle.api.Task

public abstract class PluginConfiguration<T extends Task> {

  final Class<T> type
  final String name
  final String formalName
  final String configFile

  public PluginConfiguration(final Class<T> type, final String name,
                      final String formalName, final String configFile) {
    this.type = type
    this.name = name
    this.formalName = formalName
    this.configFile = configFile
  }

  public void apply(final Project project, final AppExtension android) {
    final Task checkTask = project.tasks.check

    final Task configTask = project.task("init${name.capitalize()}", type: Task) << {
        getFile(project, configFile).withOutputStream { final OutputStream out ->
        out << getClass().getResourceAsStream(configFile)
      }
    }

    final Task parentTask = project.task(name, type: Task) { final Task task ->
      checkTask.dependsOn task

      task.group = 'Verification'
      task.description = "Runs ${formalName} on all configurations."
    }

    android.getApplicationVariants().each { final ApplicationVariant variant ->
      final String taskName = variant.name.capitalize()

      final Task variantTask = project.task("${name}${taskName}", type: type) { final Task task ->
        task.dependsOn variant.javaCompile
        task.dependsOn configTask

        task.group = 'Verification'
        task.description = "Runs ${formalName} on the ${taskName} configuration."

        task.configure configureTask(project, variant)
      }

      parentTask.dependsOn variantTask
    }
  }

  public abstract Closure getDependencies()

  protected abstract Closure configureTask(Project project, ApplicationVariant variant)

  protected static File getFile(final Project project, final String name) {
    final File f = new File(project.buildDir, 'androidQualityConfigs' + name);
    f.parentFile.mkdirs()
    f.createNewFile()
    return f
  }
}
