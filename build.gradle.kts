import org.jetbrains.intellij.platform.gradle.IntelliJPlatformType

plugins {
    id("java")
    id("org.jetbrains.intellij.platform") version "2.18.1"
}

group = "com.livewire"
version = "1.0.1"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        intellijIdeaCommunity("2025.1.1")
        pluginVerifier()
        zipSigner()
    }
}

intellijPlatform {
    pluginConfiguration {
        id = "com.livewire.theme"
        name = "Livewire Theme"
        version = project.version.toString()
        ideaVersion {
            sinceBuild = "243"
            untilBuild = provider { null }
        }
    }
    instrumentCode = false
    buildSearchableOptions = false

    pluginVerification {
        // Verify against the oldest supported (sinceBuild 243) and current IDE
        // only — recommended() downloads every recommended release and blows
        // past the disk space available on GitHub-hosted runners.
        ides {
            create(IntelliJPlatformType.IntellijIdeaCommunity, "2024.3")
            create(IntelliJPlatformType.IntellijIdeaCommunity, "2025.1.1")
        }
    }

    signing {
        certificateChain = providers.environmentVariable("CERTIFICATE_CHAIN")
        privateKey = providers.environmentVariable("PRIVATE_KEY")
        password = providers.environmentVariable("PRIVATE_KEY_PASSWORD")
    }

    publishing {
        token = providers.environmentVariable("PUBLISH_TOKEN")
    }
}
