package com.github.alexrichards.gradle.android.plugins

import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.Project
import org.gradle.api.plugins.quality.FindBugs

public class FindBugsPluginConfiguration extends PluginConfiguration<FindBugs> {

  public FindBugsPluginConfiguration() {
    super(FindBugs.class, 'findbugs', 'FindBugs', '/config/findbugs.xml')
  }

  @Override
  public Closure getDependencies(){
    return {
      findbugs 'com.google.code.findbugs:findbugs:2.0.3'
    }
  }

  @Override
  protected Closure configureTask(final Project project, final ApplicationVariant variant) {
    final File myConfigFile = getFile(project, configFile)
    return { final FindBugs findBugs ->
      findBugs.classes = project.fileTree(variant.javaCompile.destinationDir, {
        include '**/*.class'
        exclude '**/R.class', '**/R$*.class'
      })

      findBugs.source = variant.javaCompile.source.files
      findBugs.classpath = variant.javaCompile.classpath

      findBugs.effort = 'max'
      findBugs.ignoreFailures = false
      findBugs.includeFilter = myConfigFile
    }
  }
}
