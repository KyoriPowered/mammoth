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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Indicate that the annotated test method will be a Gradle functional test.
 *
 * <p>This annotation can be used as a <em>meta-annotation</em> to create a project-specific annotation specifying all customizable parameters.</p>
 *
 * <p>The annotated method can receive a {@link TestContext} instance in its parameters to support a standard directory layout.</p>
 *
 * <p>This directory layout follows a pattern of {@code <test class package>/<test name>/in/} for test input files,
 * and {@code <test class package>/<test name>/out/} for expected output files. Test output will be written to a temporary
 * directory, and deleted after test execution.</p>
 *
 * @see GradleParameters
 * @see TestVariant
 * @since 1.1.0
 */
@Documented
@TestTemplate
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@ExtendWith(GradleFunctionalTestExtension.class)
public @interface GradleFunctionalTest {
}
