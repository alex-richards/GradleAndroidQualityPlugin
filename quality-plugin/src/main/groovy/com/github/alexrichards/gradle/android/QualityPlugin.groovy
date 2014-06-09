package com.github.alexrichards.gradle.android

import com.android.build.gradle.AppExtension
import com.github.alexrichards.gradle.android.plugins.CheckstylePluginConfiguration
import com.github.alexrichards.gradle.android.plugins.FindBugsPluginConfiguration
import com.github.alexrichards.gradle.android.plugins.PMDPluginConfiguration
import com.github.alexrichards.gradle.android.plugins.PluginConfiguration
import org.gradle.api.Plugin
import org.gradle.api.Project

public class QualityPlugin implements Plugin<Project> {

  private final PluginConfiguration[] pluginConfigurations = [
      new FindBugsPluginConfiguration(),
      new CheckstylePluginConfiguration(),
      new PMDPluginConfiguration(),
  ]

  @Override
  public void apply(final Project project) {
    project.configure(project) {
      pluginConfigurations.each { final PluginConfiguration configuration ->
        project.apply plugin: configuration.name
        project.dependencies configuration.dependencies
      }

      afterEvaluate {
        final AppExtension android = project.android;
        assert android != null

        pluginConfigurations.each { final PluginConfiguration configuration ->
          configuration.apply project, android
        }
      }
    }
  }
}