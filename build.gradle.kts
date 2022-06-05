import Build_gradle.Constants.dl4jMasterVersion

plugins {
    id("java")
}

group = "com.valb3r.idr"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

object Constants {
    const val dl4jMasterVersion = "1.0.0-M1.1" // Do not use M2 as there is a regression https://community.konduit.ai/t/computing-gradient-without-backpropagation/1887?u=valb3r
}

dependencies {
    implementation("org.bytedeco:javacv-platform:1.5.5")
    implementation("ch.qos.logback:logback-classic:1.1.7")
    implementation("org.apache.httpcomponents:httpclient:4.3.5")
    implementation("org.jfree:jcommon:1.0.23")
    implementation("jfree:jfreechart:1.0.13")
//    implementation("org.deeplearning4j:deeplearning4j-zoo:${dl4jMasterVersion}")
    implementation("org.deeplearning4j:deeplearning4j-ui:${dl4jMasterVersion}")
    implementation("org.deeplearning4j:deeplearning4j-core:${dl4jMasterVersion}")
    implementation("org.deeplearning4j:deeplearning4j-datasets:${dl4jMasterVersion}")
//    implementation("org.datavec:datavec-local:${dl4jMasterVersion}")
//    implementation("org.datavec:datavec-data-image:${dl4jMasterVersion}")
//    implementation("org.datavec:datavec-api:${dl4jMasterVersion}")

    runtimeOnly("org.nd4j:nd4j-native-platform:${dl4jMasterVersion}")
    runtimeOnly("org.nd4j:nd4j-native:${dl4jMasterVersion}:macosx-x86_64")

    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.13.0")
    
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

//configurations.all {
//    exclude("com.google.flatbuffers", "flatbuffers-java")
//}