plugins {
    id("org.openrewrite.build.language-library")
}

// run manually with `./gradlew rewrite-hcl:generateAntlrSources` when you need to regenerate
// be sure to use `implementation("org.antlr:antlr4:4.13.2")` below when generating
tasks.register<JavaExec>("generateAntlrSources") {
    mainClass.set("org.antlr.v4.Tool")

    args = listOf(
            "-o", "src/main/java/org/openrewrite/hcl/internal/grammar",
            "-package", "org.openrewrite.hcl.internal.grammar",
            "-visitor"
    ) + fileTree("src/main/antlr").matching { include("**/*.g4") }.map { it.path }

    classpath = sourceSets["main"].runtimeClasspath
}

dependencies {
    api(project(":rewrite-core"))
    api("org.jetbrains:annotations:latest.release")
    api("com.fasterxml.jackson.core:jackson-annotations")

    compileOnly(project(":rewrite-test"))

    implementation("org.antlr:antlr4-runtime:4.13.2")
    implementation("io.micrometer:micrometer-core:1.9.+")

    testImplementation(project(":rewrite-test"))
    testImplementation("org.junit-pioneer:junit-pioneer:2.0.0")
}
