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
 * A variant of a test to execute.
 *
 * <p>At least one of {@link #gradleVersion()} or {@link #extraArguments()} must be provided.</p>
 *
 * <p>This annotation can be used as a composable meta-annotation.</p>
 *
 * @since 1.1.0
 */
@Documented
@Repeatable(TestVariants.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
public @interface TestVariant {
  /**
   * A gradle version ID.
   *
   * <p>This is only validated at test execution time.</p>
   *
   * @return the gradle version to test against
   * @since 1.1.0
   */
  String gradleVersion() default "";
  /**
   * Extra arguments to provide.
   *
   * @return the extra arguments
   * @since 1.1.0
   */
  String[] extraArguments() default {};

  /**
   * Minimum runtime version to execute this variant on.
   *
   * <p>By default, permits any version.</p>
   *
   * @return the minimum runtime version
   * @since 1.4.0
   */
  int minimumRuntimeVersion() default -1;

  /**
   * Maximum runtime version to execute this variant on.
   *
   * <p>By default, permits any version. This must be greater than or equal to {@link #minimumRuntimeVersion()}</p>
   *
   * @return the maximum runtime version
   * @since 1.4.0
   */
  int maximumRuntimeVersion() default Integer.MAX_VALUE;
}
