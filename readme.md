# mammoth

![GitHub Workflow Status (branch)](https://img.shields.io/github/workflow/status/KyoriPowered/mammoth/build/master) [![MIT License](https://img.shields.io/badge/license-MIT-blue)](license.txt)

## Plugin Utilities

The main component of `mammoth` is utility functions for developing Gradle plugins in java, plus `ProjectPlugin`, an interface for plugins for `Project` instances.

## Functional Testing

Mammoth provides test fixtures to enable functional testing of Gradle plugins. This architecture is designed for Java tests using JUnit 5, with the buildscripts for tested builds stored as classpath resources. This stands in contrast to Groovy functional tests, where the buildscript is written as a string directly in the test class.

<details>
<summary>Example</summary>

Assuming there are files at `src/test/resources/com/example/myplugin/simpleBuild/in/build.gradle.kts` and `src/test/resources/com/example/myplugin/simpleBuild/in/settings.gradle.kts`, the following sets up a simple test that will run on both Gradle 6.9 and 7.1:

**com/example/myplugin/MyPluginTest.java**:

```java
/**
 * A <em>meta-annotation containing our test configuration.</em>
 */
@GradleFunctionalTest
@GradleParameters({"--warning-mode", "all", "--stacktrace"}) // parameters for all variants
@TestVariant(gradleVersion = "6.9")
@TestVariant(gradleVersion = "7.1")
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
public @interface MyPluginTest {
}
```

**com/example/myplugin/MyPluginFunctionalTest.java**
```java

class MyPluginFunctionalTest {

  @SpongeGradleFunctionalTest
  void simpleBuild(final TestContext ctx) {
    ctx.copyInput("build.gradle.kts");
    ctx.copyInput("settings.gradle.kts");

    final BuildResult result = ctx.build("build"); // or anoher

    assertEquals(TaskOutcome.SUCCESS, result.task(":build").getOutcome());
    
    // Use any other methods on TestContext to help validate output.
  }
}
```
</details>
