import dev.architectury.pack200.java.Pack200Adapter

plugins {
    kotlin("jvm")
    id("gg.essential.loom")
    id("io.github.juuxel.loom-quiltflower")
    id("dev.architectury.architectury-pack200")
    id("net.kyori.blossom") version "1.3.1"
}

version = properties["version"]!!
val devAuthVersion: String by project

val embed: Configuration by configurations.creating
configurations.implementation.get().extendsFrom(embed)

repositories {
    maven("https://repo.spongepowered.org/repository/maven-public")
    maven("https://repo.polyfrost.cc/releases")
    maven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1")
}

dependencies {
    minecraft("com.mojang:minecraft:1.8.9")
    mappings("de.oceanlabs.mcp:mcp_stable:22-1.8.9")
    forge("net.minecraftforge:forge:1.8.9-11.15.1.2318-1.8.9")

    compileOnly("cc.polyfrost:oneconfig-1.8.9-forge:0.2.2-alpha+")
    embed("cc.polyfrost:oneconfig-wrapper-launchwrapper:1.0.0-beta+")

    compileOnly("org.spongepowered:mixin:0.7.11-SNAPSHOT")
    annotationProcessor("org.spongepowered:mixin:0.8.5-SNAPSHOT:processor")

    modRuntimeOnly("me.djtheredstoner:DevAuth-forge-legacy:$devAuthVersion")
}

blossom {
    replaceTokenIn("src/main/java/at/yedel/finement/Finement.java")
    replaceToken("#version#", version)
}

loom {
    runConfigs {
        named("client") {
            ideConfigGenerated(true)
        }
    }

    launchConfigs {
        getByName("client") {
            arg("--tweakClass", "cc.polyfrost.oneconfig.loader.stage0.LaunchWrapperTweaker")
            arg("--mixin", "mixins.finement.json")
        }
    }

    forge {
        pack200Provider.set(Pack200Adapter())
        mixinConfig("mixins.finement.json")
    }
}

tasks {
    jar {
        from(embed.files.map { zipTree(it) })

        manifest.attributes(
            mapOf(
                "ModSide" to "CLIENT",
                "ForceLoadAsMod" to true,
                "MixinConfigs" to "mixins.finement.json",
                "TweakClass" to "cc.polyfrost.oneconfig.loader.stage0.LaunchWrapperTweaker"
            )
        )
    }

    processResources {
        filesMatching("mcmod.info") {
            expand("version" to version)
        }
    }

    withType<JavaCompile> {
        options.release.set(8)
    }
}
