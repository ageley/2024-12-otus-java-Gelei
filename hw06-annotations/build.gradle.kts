dependencies {
    implementation ("ch.qos.logback:logback-classic")
    compileOnly ("org.projectlombok:lombok")
    annotationProcessor ("org.projectlombok:lombok")
}

plugins {
    id("application")
}

application {
    mainClass = "ru.otus.homework.test.util.Application"
}