package com.github.alexrichards.gradle.android.plugins

import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.Project
import org.gradle.api.plugins.quality.Pmd

class PMDPluginConfiguration extends PluginConfiguration {

  PMDPluginConfiguration() {
    super(Pmd, 'pmd', 'PMD', '/config/pmd.xml')
  }

  @Override
  Closure configureTask(final Project project, final ApplicationVariant variant) {
    return {
//      ruleSetFiles = project.files(getConfigFile(project, '/config/pmd.xml'))

      ruleSets = [
          "basic",
          "braces",
          "naming",
          "android",
          "clone",
          "codesize",
          "controversial",
          "design",
          "finalizers",
          "imports",
          "j2ee",
          "javabeans",
          "junit",
          "logging-jakarta-commons",
          "logging-java",
          "migrating",
          "optimizations",
          "strictexception",
          "strings",
          "sunsecure",
          "typeresolution",
          "unusedcode"
      ]
      source = variant.javaCompile.source
      ignoreFailures = true

      exclude '**/R.java', '**/BuildConfig.java'
    }
  }
}
