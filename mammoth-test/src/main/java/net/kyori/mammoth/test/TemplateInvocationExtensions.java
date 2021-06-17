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
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

// Template-specific context information
class TemplateInvocationExtensions implements AfterEachCallback, ParameterResolver {
  private final TestContext context;

  TemplateInvocationExtensions(final TestContext context) {
    this.context = context;
  }

  @Override
  public void afterEach(final ExtensionContext context) throws Exception {
    try {
      Files.walkFileTree(this.context.outputDirectory(), DeletingFileVisitor.INSTANCE);
    } catch (final IOException ex) {
      // Don't fail the tests for failing to delete temporary directories
      // Windows can be a bit needy with file locking...
      // Just warn instead
      // And of course, we can't depend on a logging system (and we're not J9+)
      System.err.println("Failed to delete temporary directory for test " + this.getClass().getSimpleName());
      ex.printStackTrace();
    }
  }

  @Override
  public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) throws ParameterResolutionException {
    return TestContext.class.isAssignableFrom(parameterContext.getParameter().getType());
  }

  @Override
  public Object resolveParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) throws ParameterResolutionException {
    return this.context;
  }

  static final class DeletingFileVisitor extends SimpleFileVisitor<Path> {
    static final DeletingFileVisitor INSTANCE = new DeletingFileVisitor();

    private DeletingFileVisitor() {
    }

    @Override
    public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
      Files.deleteIfExists(file);
      return super.visitFile(file, attrs);
    }

    @Override
    public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
      Files.deleteIfExists(dir);
      return super.postVisitDirectory(dir, exc);
    }
  }
}
