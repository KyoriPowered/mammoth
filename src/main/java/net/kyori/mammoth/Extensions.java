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

import org.gradle.api.plugins.ExtensionContainer;

/**
 * Helpers for working with extensions.
 *
 * @since 1.0.0
 */
public final class Extensions {
  private Extensions() {
  }

  /**
   * Find or create an extension by type from the provided container.
   *
   * @param <E> extension type
   * @param extensions extension container to search
   * @param name extension name
   * @param type type to create the extension under
   * @return the new or existing extension instance
   * @since 1.0.0
   */
  public static <E> E findOrCreate(final ExtensionContainer extensions, final String name, final Class<E> type) {
    E extension = extensions.findByType(type);
    if (extension == null) {
      extension = extensions.create(name, type);
    }
    return extension;
  }

  /**
   * Find or create an extension by type from the provided container.
   *
   * @param <E> extension type
   * @param extensions extension container to search
   * @param name extension name
   * @param publicType type to expose to extension consumers
   * @param implementationType type to use to create the extension itsef
   * @return the new or existing extension instance
   * @since 1.0.0
   */
  @SuppressWarnings("unchecked")
  public static <E> E findOrCreate(final ExtensionContainer extensions, final String name, final Class<? super E> publicType, final Class<E> implementationType) {
    E extension = (E) extensions.findByType(publicType);
    if (extension == null) {
      extension = (E) extensions.create(publicType, name, implementationType);
    }
    return extension;
  }
}
