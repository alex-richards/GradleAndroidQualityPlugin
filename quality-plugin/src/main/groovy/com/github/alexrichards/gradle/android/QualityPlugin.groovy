package com.github.alexrichards.gradle.android

import com.android.build.gradle.AppExtension
import com.github.alexrichards.gradle.android.plugins.FindBugsPluginConfiguration
import com.github.alexrichards.gradle.android.plugins.PluginConfiguration
import org.gradle.api.Plugin
import org.gradle.api.Project

class QualityPlugin implements Plugin<Project> {

  final PluginConfiguration[] pluginConfigurations = [
      new FindBugsPluginConfiguration(),
  ]

  @Override
  void apply(final Project project) {
    project.configure(project) {
      pluginConfigurations.each { PluginConfiguration configuration ->
        project.apply plugin: configuration.name
      }

      afterEvaluate {
        final AppExtension android = project.android;
        assert android != null

        pluginConfigurations.each { PluginConfiguration configuration ->
          configuration.apply project, android
        }
      }
    }
  }
}