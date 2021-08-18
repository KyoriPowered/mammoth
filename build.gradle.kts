plugins {
  val indraVersion = "2.0.6"
  id("net.kyori.indra") version indraVersion
  id("net.kyori.indra.license-header") version indraVersion
  id("net.kyori.indra.publishing.sonatype") version indraVersion
}

group = "net.kyori"
version = "1.1.0-SNAPSHOT"
description = "Helpful API for writing Gradle plugins"

repositories {
  mavenCentral()
}

dependencies {
  compileOnlyApi("org.checkerframework:checker-qual:3.13.0")
  implementation(gradleApi())
}

indra {
  github("KyoriPowered", "mammoth") {
    ci(true)
    publishing(true)
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
