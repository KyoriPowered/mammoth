description = "JUnit extensions for testing Gradle plugins"

dependencies {
  api(gradleApi())
  api(gradleTestKit())
  api("org.junit.jupiter:junit-jupiter-api:5.7.2")
  implementation("org.junit.platform:junit-platform-commons:1.7.2")
}
