import dev.deftu.gradle.utils.GameSide

val oneconfigVersion: String by project
val oneconfigWrapperVersion: String by project
val devAuthVersion: String by project

repositories {
    gradlePluginPortal()
    mavenCentral()
    maven("https://repo.polyfrost.cc/releases")
    maven("https://repo.spongepowered.org/repository/maven-public")
}

plugins {
    java
    val dgt = "2.73.0"
    id("dev.deftu.gradle.tools") version dgt
    for (tool in listOf(
        "java",
        "minecraft.loom",
        "bloom",
        "resources",
        "shadow"
    )) id("dev.deftu.gradle.tools.$tool") version dgt
}

dependencies {
    compileOnly("cc.polyfrost:oneconfig-${mcData.version}-${mcData.loader}:$oneconfigVersion")
    listOf(
        "implementation",
        "shade"
    ).forEach { it("cc.polyfrost:oneconfig-wrapper-launchwrapper:$oneconfigWrapperVersion") }

    compileOnly("org.spongepowered:mixin:0.7.11-SNAPSHOT")
}

toolkitLoomHelper {
    disableRunConfigs(GameSide.SERVER)

    // Remove this in the run config if you're using other OneConfig mods, since those mods will not load with the tweaker argument.
    useTweaker("cc.polyfrost.oneconfig.loader.stage0.LaunchWrapperTweaker")

    if (mcData.isLegacyForge) {
        useForgeMixin("finement")
        useMixinRefMap("finement")
    }

    useDevAuth(devAuthVersion)
    useArgument("--version", "Finement", GameSide.BOTH)
    val resourcePackDir: String? = System.getenv("minecraft.resourcePackDir")
    if (!resourcePackDir.isNullOrBlank()) {
        println("Using resource pack directory $resourcePackDir from environment variable minecraft.resourcePackDir")
        useArgument("--resourcePackDir", resourcePackDir, GameSide.BOTH)
    }
}

tasks {
    jar {
        manifest.attributes(mapOf("ModSide" to "CLIENT"))
    }
}