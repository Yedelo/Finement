import jdk.jfr.internal.JVM.exclude
import org.gradle.api.tasks.Copy
import org.gradle.internal.Actions.set
import org.gradle.kotlin.dsl.invoke
import sun.tools.jar.resources.jar
import kotlin.reflect.KProperty

// in stonecutter.gradle.kts
class CommonProperty<T> {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = (rootProject.extra[sc.current.project] as Map<String, Any?>)[property.name] as T
}
val modName by CommonProperty<String>()
val modId by CommonProperty<String>()
val modDescription by CommonProperty<String>()
val modIcon by CommonProperty<String>()
val fabricLoaderVersion by CommonProperty<String>()
val oneconfigVersion by CommonProperty<String>()
val rangedVersion by CommonProperty<Boolean>()
val maxMc by CommonProperty<String?>()
val finalFileName by CommonProperty<String>()
val license: String by project
val javaVersion = JavaVersion.VERSION_25

repositories {
    fun scopedMaven(url: String, vararg groups: String, includeSubgroups: Boolean = false) = maven(url) {
        content { for (group in groups) if (!includeSubgroups) includeGroup(group) else includeGroupAndSubgroups(group) }
    }

    mavenCentral()
    gradlePluginPortal()
    google()
    maven("https://repo.polyfrost.cc/releases")
    maven("https://repo.polyfrost.org/releases")
    maven("https://repo.polyfrost.org/snapshots")
    maven("https://maven.terraformersmc.com/releases")
    maven("https://repo.hypixel.net/repository/Hypixel/")
    maven("https://maven.fabricmc.net/releases")
    scopedMaven("https://central.sonatype.com/repository/maven-snapshots/", "net.kyori")
    maven("https://api.modrinth.com/maven") {
        content { includeGroup("maven.modrinth") }
    }
    maven("https://maven.ornithemc.net/releases")
    maven("https://maven.ornithemc.net/snapshots")
    maven("https://maven.cloverclient.com/releases") {
        content { includeGroup("pl.tomgirl") }
    }

    fun strictMaven(url: String, alias: String, vararg groups: String) = exclusiveContent {
        forRepository { maven(url) { name = alias } }
        filter { groups.forEach(::includeGroup) }
    }

    mavenCentral()
    google()
    maven("https://repo.polyfrost.org/releases") { name = "Polyfrost Releases" }
    maven("https://repo.polyfrost.org/snapshots") { name = "Polyfrost Snapshots" }
    maven("https://central.sonatype.com/repository/maven-snapshots") {
        name = "Sonatype Snapshots"
        content { includeGroup("net.kyori") }
    }
    maven("https://maven.cloverclient.com/releases") {
        content { includeGroup("pl.tomgirl") }
    }
    strictMaven("https://maven.deftu.dev/releases", "Deftu", "dev.deftu")
    strictMaven("https://maven.terraformersmc.com/", "TerraformersMC", "com.terraformersmc")
    strictMaven("https://maven.fabricmc.net/", "FabricMC", "net.fabricmc")
    strictMaven("https://www.cursemaven.com", "CurseForge", "curse.maven")
    strictMaven("https://api.modrinth.com/maven", "Modrinth", "maven.modrinth")
}

plugins {
    id("net.fabricmc.fabric-loom-remap") version "1.16-SNAPSHOT"
    id("ploceus") version "1.16-SNAPSHOT"
    id("dev.deftu.gradle.tools.bloom") version "2.73.0"
}

dependencies {
    minecraft("com.mojang:minecraft:${sc.current.version}")
    mappings(ploceus.mcpMappings("stable", "1.8.9", "22"))
    implementation("net.fabricmc:fabric-loader:$fabricLoaderVersion")
    implementation("org.polyfrost.oneconfig:${sc.current.version}-ornithe:$oneconfigVersion")
    ploceus.dependOsl(sc.properties["versions.osl"])
    compileOnly("net.fabricmc:sponge-mixin:0.17.4+mixin.0.8.7")
}

loom {
    runConfigs.remove(runConfigs["server"])

    runConfigs.all {
        runDir = "../../run"
        val resourcePackDir: String? = System.getenv("minecraft.resourcePackDir")
        if (!resourcePackDir.isNullOrBlank()) {
            println("Using resource pack directory $resourcePackDir from environment variable minecraft.resourcePackDir")
            programArgs("--resourcePackDir", resourcePackDir)
        }
    }
}

bloom {
    replacement("@MC_VERSION@", sc.current.version)
    replacement("@MOD_LOADER@", "ornithe")
    replacement("@FORMATTED_MOD_LOADER@", "Ornithe")
}

tasks {
    processResources {
        fun MutableMap<String, String>.register(key: String, value: String) {
            inputs.property(key, value)
            set(key, value)
        }
        fun target(version: String) = ">=$version"

        exclude("mcmod.info")
        val props = buildMap {
            register("modName", modName)
            register("modId", modId)
            register("modDescription", modDescription)
            register("modIcon", modIcon)
            register("license", license)
            register("version", version.toString())
            register("java", target(javaVersion.majorVersion))
            register("fabricLoader", target(fabricLoaderVersion))
            val minecraftDependency =
                if (rangedVersion) ">=${sc.current.version} <=${maxMc}" else sc.current.version
            register("minecraft", minecraftDependency)
            register("oneconfigv1", target(oneconfigVersion))
            register("mixinJava", "JAVA_${javaVersion.majorVersion}")
            register("mixinMin", "0.8")
        }
        filesMatching(listOf("fabric.mod.json", "mixins.$modId.json")) { expand(props) }

        outputs.upToDateWhen { false }
    }

    register<Copy>("buildAndCollect") {
        group = "build"

        from(jar.map { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs"))
        dependsOn("build")
    }

    jar {
        archiveFileName = finalFileName
        // manifest.attributes(mapOf())
    }
}

java {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}