package org.nelis.securechat;

import groovy.lang.Closure;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.plugin.use.PluginDependenciesSpec;
import org.gradle.plugin.use.PluginDependencySpec;

public class BasicApplicationPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getPlugins().apply("java");
        project.getPlugins().apply("groovy");
        project.getPlugins().apply("application");

        DependencyHandler dependencies = project.getDependencies();
        String logbackVersion = (String)project.getProperties().get("logBackVersion");

        dependencies.add("implementation", "ch.qos.logback:logback-classic:" + logbackVersion);
        dependencies.add("implementation", "org.codehaus.groovy:groovy:3.0.7");
        dependencies.add("implementation", "org.spockframework:spock-core:2.0-M4-groovy-3.0");
    }
}

