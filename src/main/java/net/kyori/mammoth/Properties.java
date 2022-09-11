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
import org.gradle.api.provider.Provider;
import org.jetbrains.annotations.NotNull;

public final class Properties {
  private Properties() {
  }

  /**
   * Mark a property as being for use at configuration time.
   *
   * <p>Gradle has deprecated this method, but it is required on older Gradle versions.</p>
   *
   * @param <T> the provider value type
   * @param provider provider to get for use at configuration time
   * @return a configuration time-safe view of the provided provider
   * @since 1.1.0
   */
  @SuppressWarnings("deprecation")
  public static <T> @NotNull Provider<T> forUseAtConfigurationTime(final @NotNull Provider<T> provider) {
    if (GradleCompat.HAS_FOR_USE_AT_CONFIGURATION_TIME) {
      return provider.forUseAtConfigurationTime();
    } else {
      return provider;
    }
  }

  /**
   * Touches {@code property} to mark it as {@link HasConfigurableValue#finalizeValue() finalized}.
   *
   * @param property the property
   * @param <T> the type
   * @return the property
   * @since 1.0.0
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
   * @since 1.0.0
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
   * @since 1.0.0
   */
  public static <T extends HasConfigurableValue> @NotNull T changesDisallowed(final @NotNull T property) {
    property.disallowChanges();
    return property;
  }
}
