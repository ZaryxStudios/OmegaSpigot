plugins {
    id("omegaspigot.conventions")
    id("paperclip")
    id("com.gradleup.shadow") version "9.6.1"
}

dependencies {
    implementation("io.sigpipe:jbsdiff:1.0")
}

sourceSets {
    create("java9")
}

tasks {
    jar {
        archiveClassifier.set("original")
        manifest {
            val agentClass = "dev.zaryxstudios.escanergang.omegaspigot.paperclip.Agent"
            attributes(
                "Main-Class" to "dev.zaryxstudios.escanergang.omegaspigot.paperclip.Paperclip",
                "Multi-Release" to true,
                "Launcher-Agent-Class" to agentClass,
                "Premain-Class" to agentClass,
            )
        }
    }

    shadowJar {
        val prefix = "dev.zaryxstudios.escanergang.omegaspigot.paperclip.libs"
        arrayOf("org.apache", "org.tukaani", "io.sigpipe").forEach { pack ->
            relocate(pack, "$prefix.$pack")
        }

        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/NOTICE.txt")

        into("META-INF/versions/9") {
            from(sourceSets["java9"].output)
        }

        from(file("license.txt")) {
            into("META-INF/license")
            rename { "paperclip-LICENSE.txt" }
        }
    }
}
