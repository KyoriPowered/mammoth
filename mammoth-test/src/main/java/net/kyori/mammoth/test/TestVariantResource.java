/*
 * This file is part of mammoth, licensed under the MIT License.
 *
 * Copyright (c) 2021-2024 KyoriPowered
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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A resource to load test variants from.
 *
 * <p>The resource path is relative to the test class. Prefix it with a {@code /} to make it absolute.</p>
 *
 * <p>The file should have one variant per line, colon-separated {@code <gradle version>:<args...>}.
 * Arguments will automatically be split on spaces.</p>
 *
 * <p>This annotation can be used as a composable meta-annotation.</p>
 *
 * @since 1.1.0
 */
@Documented
@Repeatable(TestVariantResources.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
public @interface TestVariantResource {
  /**
   * A resource on the classpath to load test variants from.
   *
   * @return the resource
   * @since 1.1.0
   */
  String value();

  /**
   * Whether to allow testing to proceed if no classpath resource was present.
   *
   * @return whether this resource is optional
   * @since 1.1.0
   */
  boolean optional() default false;

  /**
   * Minimum runtime version to execute the resource's variants on.
   *
   * <p>By default, permits any version.</p>
   *
   * @return the minimum runtime version
   * @since 1.4.0
   */
  int minimumRuntimeVersion() default -1;

  /**
   * Maximum runtime version to execute the resource's variants on.
   *
   * <p>By default, permits any version. This must be greater than or equal to {@link #minimumRuntimeVersion()}</p>
   *
   * @return the maximum runtime version
   * @since 1.4.0
   */
  int maximumRuntimeVersion() default Integer.MAX_VALUE;
}
