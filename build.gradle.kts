plugins {
  val indraVersion = "1.3.1"
  id("net.kyori.indra") version indraVersion
  id("net.kyori.indra.license-header") version indraVersion
  id("net.kyori.indra.publishing.sonatype") version indraVersion
}

group = "net.kyori"
version = "1.0.0-SNAPSHOT"
description = "Helpful API for writing Gradle plugins"

repositories {
  mavenCentral()
  // gradlePluginPortal()
}

dependencies {
  compileOnlyApi("org.checkerframework:checker-qual:3.10.0")
  implementation(gradleApi())
}

indra {
  github("KyoriPowered", "mammoth") {
    ci = true
    publishing = true
  }
  mitLicense()

  configurePublications {
    pom {
      organization {
        name.set("KyoriPowered")
        url.set("https://kyori.net")
      }

      developers {
        developer {
          id.set("kashike")
          timezone.set("America/Vancouver")
        }
        developer {
          id.set("zml")
          email.set("zml at stellardrift [.] ca")
          timezone.set("America/Vancouver")
        }
      }
    }
  }
}
