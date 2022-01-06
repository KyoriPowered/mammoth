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

import org.gradle.api.provider.HasConfigurableValue;
import org.jetbrains.annotations.NotNull;

public final class Properties {
  private Properties() {
  }

  /**
   * Touches {@code property} to mark it as {@link HasConfigurableValue#finalizeValue() finalized}.
   *
   * @param property the property
   * @param <T> the type
   * @return the property
   * @since 2.0.0
   */
  public static <T extends HasConfigurableValue> @NotNull T finalized(final @NotNull T property) {
    property.finalizeValue();
    return property;
  }

  /**
   * Touches {@code property} to mark it as {@link HasConfigurableValue#finalizeValueOnRead() finalized on read}.
   *
   * @param property the property
   * @param <T> the type
   * @return the property
   * @since 2.0.0
   */
  public static <T extends HasConfigurableValue> @NotNull T finalizedOnRead(final @NotNull T property) {
    property.finalizeValueOnRead();
    return property;
  }

  /**
   * Touches {@code property} to {@link HasConfigurableValue#disallowChanges() finalize} it.
   *
   * @param property the property
   * @param <T> the type
   * @return the property
   * @since 2.0.0
   */
  public static <T extends HasConfigurableValue> @NotNull T changesDisallowed(final @NotNull T property) {
    property.disallowChanges();
    return property;
  }
}
