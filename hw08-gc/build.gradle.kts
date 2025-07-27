dependencies {
    implementation ("ch.qos.logback:logback-classic")
}

plugins {
    id("application")
}

application {
    mainClass = "ru.calculator.CalcDemo"
    applicationDefaultJvmArgs = listOf(
        "-Xms256m",
        "-Xmx256m",
        "-XX:+HeapDumpOnOutOfMemoryError",
        "-XX:HeapDumpPath=./logs/heapdump.hprof",
        "-XX:+UseG1GC",
        "-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m"
    )
}

tasks.named<JavaExec>("run") {
    doFirst {
        mkdir("logs")
    }
}
