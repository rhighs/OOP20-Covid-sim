plugins {
    java

    application

    id("com.github.johnrengelman.shadow") version "6.1.0"
}

repositories {
    mavenCentral()
}

val jmeModules = listOf(
    "bullet",
    "bullet-native",
    "core",
    "desktop",
    "lwjgl",
    "niftygui"
)

val jmeVersion = "3.3.2-stable"

val jUnitVersion = "5.7.1"

dependencies {

    for (module in jmeModules) {
        implementation("org.jmonkeyengine:jme3-$module:$jmeVersion")
    }

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // JUnit API and testing engine
    //testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnitVersion")
    //testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jUnitVersion")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Test> {
    useJUnitPlatform()
}

application {
    mainClass.set("Main.Main")

    @Suppress("DEPRECATION")
    mainClassName = mainClass.get()
}

sourceSets {
    main {
        java {
            setSrcDirs(listOf("src"))
        }
        resources {
            setSrcDirs(listOf("src"))
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
