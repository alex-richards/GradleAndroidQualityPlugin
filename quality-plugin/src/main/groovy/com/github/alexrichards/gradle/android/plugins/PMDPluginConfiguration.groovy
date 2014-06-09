package com.github.alexrichards.gradle.android.plugins

import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.Project
import org.gradle.api.plugins.quality.Pmd

public class PMDPluginConfiguration extends PluginConfiguration<Pmd> {

  public PMDPluginConfiguration() {
    super(Pmd, 'pmd', 'PMD', '/config/pmd.xml')
  }

  @Override
  public Closure getDependencies() {
    return {
      pmd 'net.sourceforge.pmd:pmd:5.1.1'
    }
  }

    @Override
  protected Closure configureTask(final Project project, final ApplicationVariant variant) {
    return { final Pmd pmd ->
      // TODO why can't I configure this ??
      // pmd.ruleSetFiles = project.files(getFile(project, configFile))
      /*pmd.ruleSets = [
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
        "javabeans",
        "junit",
        "migrating",
        "optimizations",
        "strictexception",
        "strings",
        "sunsecure",
        "typeresolution",
        "unusedcode",
      ]*/

      pmd.source = variant.javaCompile.source
      pmd.ignoreFailures = false

      pmd.exclude '**/R.java', '**/BuildConfig.java'

      pmd.pmdClasspath = project.configurations.pmd

      pmd.reports {
        xml.enabled = true
        html.enabled = false
      }
    }
  }
}
