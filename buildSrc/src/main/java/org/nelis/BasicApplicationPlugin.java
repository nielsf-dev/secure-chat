package org.nelis;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSetContainer;

public class BasicApplicationPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {

        // kan dus zo
        JavaPluginConvention plugin = project.getConvention().getPlugin(JavaPluginConvention.class);
        SourceSetContainer sourceSets = plugin.getSourceSets();

        DependencyHandler dependencies = project.getDependencies();
        dependencies.add("implementation", "ch.qos.logback:logback-classic:1.2.3");

        project.task("hellofromplugin")
                .doLast(task -> System.out.println("Hello Gradle!"));
    }
}

