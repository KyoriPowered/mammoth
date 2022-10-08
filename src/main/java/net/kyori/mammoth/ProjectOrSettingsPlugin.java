/*
 * This file is part of mammoth, licensed under the MIT License.
 *
 * Copyright (c) 2021-2022 KyoriPowered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.kyori.mammoth;

import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.initialization.Settings;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.util.GradleVersion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A plugin that can be applied to either a {@link Project} or {@link Settings}.
 *
 * @since 1.3.0
 */
public interface ProjectOrSettingsPlugin extends Plugin<Object> {
  @Override
  default void apply(final @NotNull Object target) {
    if (target instanceof Project) {
      final Project project = (Project) target;
      GradleCompat.requireMinimumVersion(this.minimumGradleVersion(), this, project.getDisplayName());
      this.applyToProject(project, project.getPlugins(), project.getExtensions(), project.getTasks());
    } else if (target instanceof Settings) {
      final Settings settings = (Settings) target;
      GradleCompat.requireMinimumVersion(this.minimumGradleVersion(), this, "settings");
      this.applyToSettings(settings, settings.getPlugins(), settings.getExtensions());
      settings.getGradle().getPlugins().apply(this.getClass());
    } else if (!(target instanceof Gradle)) {
      throw new GradleException(
        "Plugin " + this.getClass() + " target '" + target
          + "' is of unexpected type " + target.getClass()
          + ". We were expecting a Project or Settings instance."
      );
    }
  }

  /**
   * Called when this plugin is applied to a {@link Project} instance.
   *
   * @param target the project this plugin is being applied to
   * @param plugins the plugin container
   * @param extensions the extension container
   * @param tasks the task container
   * @since 1.3.0
   */
  default void applyToProject(
    final @NotNull Project target,
    final @NotNull PluginContainer plugins,
    final @NotNull ExtensionContainer extensions,
    final @NotNull TaskContainer tasks
  ) {
  }

  /**
   * Called when this plugin is applied to a {@link Settings} instance.
   *
   * @param target the settings this plugin is being applied to
   * @param plugins the plugin container
   * @param extensions the extension container
   * @since 1.3.0
   */
  void applyToSettings(
    final @NotNull Settings target,
    final @NotNull PluginContainer plugins,
    final @NotNull ExtensionContainer extensions
  );

  /**
   * Return a minimum Gradle version required to use this plugin.
   *
   * @return the minimum required version
   * @since 1.2.0
   */
  default @Nullable GradleVersion minimumGradleVersion() {
    return null;
  }

  /**
   * Determine if this plugin has been applied to the {@link Settings} instance associated with a particular project.
   *
   * @param project the project to test
   * @return whether the Settings that created the provided project has this plugin applied
   * @since 1.3.0
   */
  default boolean isAppliedToSettingsOf(final @NotNull Project project) {
    return project.getGradle().getPlugins().hasPlugin(this.getClass());
  }
}
