/*
 * This file is part of mammoth, licensed under the MIT License.
 *
 * Copyright (c) 2024 KyoriPowered
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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Iterator;
import java.util.Set;
import org.gradle.api.file.FileCollection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A factory for classloaders that will load classes from themselves rather than its parent where possible.
 *
 * <p>This can be used to create isolated environments for working with Gradle plugins, or providing an alternative to workers.</p>
 *
 * <p>The returned loader from any of these factory methods will be registered as parallel capable.</p>
 *
 * @since 1.4.0
 */
public final class IsolatingClassLoader {
  private IsolatingClassLoader() {
  }

  /**
   * Create a new loader based on a provided set of URLs.
   *
   * @param parent the parent loader
   * @param urls the urls
   * @return the newly created loader
   * @since 1.4.0
   */
  public static @NotNull URLClassLoader isolatingClassLoader(final @Nullable ClassLoader parent, final @NotNull URL @NotNull... urls) {
    return new IsolatingClassLoaderImpl(urls, parent);
  }

  /**
   * Create a new loader based on a provided file collection.
   *
   * @param parent the parent loader
   * @param files the file collection
   * @return the newly created loader
   * @since 1.4.0
   */
  public static @NotNull URLClassLoader isolatingClassLoader(final @Nullable ClassLoader parent, final @NotNull FileCollection files) {
    final Set<File> unwrapped = files.getFiles();
    final URL[] urls = new URL[unwrapped.size()];
    final Iterator<File> it = files.iterator();
    int idx = 0;
    File file;
    while (it.hasNext()) {
      file = it.next();
      try {
        urls[idx++] = file.toURI().toURL();
      } catch (final MalformedURLException ex) {
        throw new IllegalArgumentException("Unable to include file " + file + " in classpath");
      }
    }

    return new IsolatingClassLoaderImpl(urls, parent);
  }
}
