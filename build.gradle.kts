plugins {
    java
    `maven-publish`
}

val javadoc by tasks.existing(Javadoc::class)
val jar by tasks.existing

group = "gg.solarmc.paperlib" // Solar
version = "1.0.7-SNAPSHOT"

val mcVersion = "1.16.5-R0.1-SNAPSHOT"

repositories {
    mavenCentral()
/* Solar start
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
*/
    maven("https://repo.solarmc.gg/mvn")
// Solar end
}

dependencies {
/* Solar start
    compileOnly("com.google.code.findbugs:jsr305:1.3.9")
    compileOnly("com.destroystokyo.paper:paper-api:$mcVersion")
*/
    implementation("gg.solarmc.paper:solarpaper-api:1.2.1")
// Solar end
}

java {
// Solar start
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
// Solar end
}

val sourcesJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets["main"].allSource)
}

javadoc {
    isFailOnError = false
}

val javadocJar by tasks.registering(Jar::class) {
    dependsOn(javadoc)
    classifier = "javadoc"
    from(javadoc)
}

// A couple aliases just to simplify task names
tasks.register("install") {
    group = "publishing"
    description = "Alias for publishToMavenLocal"
    dependsOn(tasks.named("publishToMavenLocal"))
}

tasks.register("deploy") {
    group = "publishing"
    description = "Alias for publish"
    dependsOn(tasks.named("publish"))
}

artifacts {
    add("archives", sourcesJar)
    add("archives", javadocJar)
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(sourcesJar.get())
            artifact(javadocJar.get())

// Solar start - modify details
            pom {
                name.set("PaperLib")
                description.set("Plugin library for interfacing with Paper specific APIs with graceful fallback that maintains Spigot compatibility.")
                url.set("https://github.com/SolarMC-Dev/PaperLib")
                scm {
                    url.set("https://github.com/SolarMC-Dev/PaperLib")
                }
                issueManagement {
                    system.set("github")
                    url.set("https://github.com/SolarMC-Dev/PaperLib/issues")
                }
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                    license {
                        name.set("GNU Lesser General Public License, Version 3")
                        url.set("https://www.gnu.org/licenses/")
                    }
                }
            }
// Solar end
        }
    }

    if (project.hasProperty("papermcRepoUser") && project.hasProperty("papermcRepoPass")) {
        val papermcRepoUser: String by project
        val papermcRepoPass: String by project

        val repoUrl = if (version.toString().endsWith("-SNAPSHOT")) {
            "https://papermc.io/repo/repository/maven-snapshots/"
        } else {
            "https://papermc.io/repo/repository/maven-releases/"
        }

        repositories {
            maven {
                url = uri(repoUrl)
                name = "Paper"
                credentials {
                    username = papermcRepoUser
                    password = papermcRepoPass
                }
            }
        }
    }
// Solar start
    repositories.maven {
        credentials {
            username = System.getenv("REPO_USER")
            password = System.getenv("REPO_PASS")
        }

        name = "solar-repo"
        url = uri("https://maven.cloudsmith.io/solarmc/oss-lesser-gpl3")
    }
// Solar end
}
