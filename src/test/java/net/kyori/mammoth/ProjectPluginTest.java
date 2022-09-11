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
import org.gradle.api.internal.plugins.PluginApplicationException;
import org.gradle.testfixtures.ProjectBuilder;
import org.gradle.util.GradleVersion;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProjectPluginTest {

  static class ProjectPluginFailing implements ProjectPlugin {
    @Override
    public @Nullable GradleVersion minimumGradleVersion() {
      return GradleVersion.version("999.9.9");
    }
  }

  static class ProjectPluginSuccessful implements ProjectPlugin {
    @Override
    public @Nullable GradleVersion minimumGradleVersion() {
      return GradleVersion.version("6.9.0");
    }
  }

  @Test
  void testMinimumVersionMismatch() {
    final GradleException ex =  assertThrows(
      PluginApplicationException.class,
      () -> ProjectBuilder.builder().build().getPluginManager().apply(ProjectPluginFailing.class)
    );
    final GradleException wrapped = assertInstanceOf(GradleException.class, ex.getCause());

    assertTrue(wrapped.getMessage().contains("Your Gradle version is too old"));
  }

  @Test
  void testMinimumVersionSuccessful() {
    assertDoesNotThrow(() -> ProjectBuilder.builder().build().getPluginManager().apply(ProjectPluginSuccessful.class));
  }
}
