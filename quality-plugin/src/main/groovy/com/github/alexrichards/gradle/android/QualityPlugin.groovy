package com.github.alexrichards.gradle.android

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.quality.FindBugs

class QualityPlugin implements Plugin<Project> {

  @Override
  void apply(final Project project) {
    project.configure(project) {
      project.apply plugin: 'findbugs'

      afterEvaluate {
        final AppExtension android = project.android;

        android.getApplicationVariants().each { ApplicationVariant variant ->
          final String taskName = variant.name.capitalize()

          project.task("findbugs${taskName}", type: FindBugs) { Task task ->
            task.dependsOn variant.javaCompile
            project.tasks.check.dependsOn task

            classes = fileTree(variant.javaCompile.destinationDir, {
              include '**/*.class'
              exclude '**/R.class', '**/R$*.class'
            })
            source = variant.javaCompile.source.files
            classpath = variant.javaCompile.classpath

            effort = 'max'
            ignoreFailures = true

            reports {
              xml.enabled = false
              html.enabled = true
            }
          }
        }
      }
    }
  }
}