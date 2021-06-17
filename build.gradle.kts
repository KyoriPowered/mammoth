plugins {
  val indraVersion = "2.0.4"
  id("net.kyori.indra") version indraVersion
  id("net.kyori.indra.license-header") version indraVersion
  id("net.kyori.indra.publishing.sonatype") version indraVersion
}

group = "net.kyori"
version = "1.1.0-SNAPSHOT"
description = "Helpful API for writing Gradle plugins"

dependencies {
  compileOnlyApi("org.checkerframework:checker-qual:3.13.0")
  implementation(gradleApi())
}

allprojects {
  apply(plugin="net.kyori.indra")
  apply(plugin="net.kyori.indra.license-header")
  apply(plugin="net.kyori.indra.publishing")

  repositories {
    // The 'gradle libs' repository added for plugin development prevents us from using central declaration
    mavenCentral()
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
}
