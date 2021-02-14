plugins {
  id("java-gradle-plugin")
}

group = "net.kyori"
version = "1.0.0-SNAPSHOT"

repositories {
  mavenLocal()
  mavenCentral()
  gradlePluginPortal()
}

dependencies {
  compileOnlyApi("org.checkerframework:checker-qual:3.10.0")
  implementation(gradleApi())
}
