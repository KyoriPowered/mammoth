/*
 * This file is part of mammoth, licensed under the MIT License.
 *
 * Copyright (c) 2021 KyoriPowered
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
package net.kyori.mammoth.test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.gradle.util.GradleVersion;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.junit.platform.commons.support.AnnotationSupport;

/**
 * An extension that can be applied to test methods to provide test template invocation context.
 */
class GradleFunctionalTestExtension implements TestTemplateInvocationContextProvider {
  @Override
  public boolean supportsTestTemplate(final ExtensionContext context) {
    final Optional<Method> method = context.getTestMethod();
    if (!method.isPresent()) {
      return false;
    }

    return AnnotationSupport.isAnnotated(method, GradleFunctionalTest.class);
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(final ExtensionContext context) {
    final Optional<GradleParameters> parameters = AnnotationSupport.findAnnotation(context.getElement(), GradleParameters.class);
    final List<TestVariant> variants = AnnotationSupport.findRepeatableAnnotations(context.getElement(), TestVariant.class);
    final String[] commonArgs = parameters.map(GradleParameters::value).orElse(new String[0]);

    // Execute the actual tests
    if (variants.isEmpty()) { // populate with one variant for the current Gradle version
      return Stream.of(this.produce(context, commonArgs, ""));
    } else {
      return variants.stream()
        .map(variant -> this.produce(context, commonArgs, variant.gradleVersion(), variant.extraArguments()));
    }
  }

  private TestTemplateInvocationContext produce(final ExtensionContext context, final String[] commonArgs, final String gradleVersion, final String... extraArguments) {
    final List<String> extraArgs = processArgs(commonArgs, extraArguments);
    final Path tempDirectory;
    try {
      tempDirectory = Files.createTempDirectory(context.getRequiredTestClass().getSimpleName());
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }

    final TestContext testContext = new TestContext(
      context.getRequiredTestClass(),
      context.getDisplayName(),
      tempDirectory,
      gradleVersion.isEmpty() ? GradleVersion.current().getVersion() : gradleVersion,
      extraArgs
    );

    return new TestTemplateInvocationContext() {
      @Override
      public String getDisplayName(final int invocationIndex) {
        return "gradle " + testContext.gradleVersion() + ", args=" + extraArgs;
      }

      @Override
      public List<Extension> getAdditionalExtensions() {
        return Collections.singletonList(new TemplateInvocationExtensions(testContext));
      }
    };
  }

  private static List<String> processArgs(final String[] common, final String[] extra) {
    final List<String> ret = new ArrayList<>(common.length + extra.length);
    Collections.addAll(ret, common);
    Collections.addAll(ret, extra);
    return ret;
  }
}
