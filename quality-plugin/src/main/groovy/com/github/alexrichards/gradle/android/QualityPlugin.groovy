package com.github.alexrichards.gradle.android

import org.gradle.api.Plugin
import org.gradle.api.Project

class QualityPlugin implements Plugin<Project> {
    @Override
    void apply(final Project project) {
        project.configure(project) {
            project.apply plugin: 'findbugs'

            // TODO
        }
    }
}
