# mammoth

![GitHub Workflow Status (branch)](https://img.shields.io/github/workflow/status/KyoriPowered/mammoth/build/master) [![MIT License](https://img.shields.io/badge/license-MIT-blue)](license.txt)

## Plugin Utilities

The main component of `mammoth` is utility functions for developing Gradle plugins in java, plus `ProjectPlugin`, an interface for plugins for `Project` instances.

## Functional Testing

Mammoth provides test fixtures to enable functional testing of Gradle plugins. This architecture is designed for Java tests using JUnit 5, with the buildscripts for tested builds stored as classpath resources. This stands in contrast to Groovy functional tests, where the buildscript is written as a string directly in the test class.

### Annotations

Annotation                  | Purpose
--------------------------- | ------------------------------------------
`@GradleFunctionalTest` | Mark a test method as a functional test
`@GradleParameters`      | Pass parameters to every Gradle invocation
`@TestVariant`            | Declare a single variant of a tent, with a specific gradle version and extra parameters. Repeatable
`@TestVariantResource`  | Declare a classpath resource to read additional variants from. Each line in the file is one variant, in the format `<version>[:<args...>]`

### Usage

<details>
<summary>Example</summary>

Assuming there are files at `src/test/resources/com/example/myplugin/simpleBuild/in/build.gradle` and `src/test/resources/com/example/myplugin/simpleBuild/in/settings.gradle`, the following sets up a simple test that will run on both Gradle 6.9 and 7.1:

**com/example/myplugin/MyPluginTest.java**:

```java
/**
 * A <em>meta-annotation</em> containing our test configuration.
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

  @MyPluginTest
  void simpleBuild(final TestContext ctx) {
    ctx.copyInput("build.gradle");
    ctx.copyInput("settings.gradle");

    final BuildResult result = ctx.build("build"); // or anoher

    assertEquals(TaskOutcome.SUCCESS, result.task(":build").getOutcome());
    
    // Use any other methods on TestContext to help validate output.
  }
}
```
</details>
