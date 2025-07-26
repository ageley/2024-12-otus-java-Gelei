# hw18-jdbc

Build a .jar file:

```shell
../gradlew :hw18-jdbc:build
```

Run a Postgres DB:

```shell
docker compose up -d postgres
```

Run an app in Docker:

```shell
docker compose up -d --build orm
```

Cleanup:

```shell
docker compose stop
```
```shell
docker compose rm --volumes
```

----

Other run options:

Run from a .jar:

```shell
java -jar build/libs/hw18-jdbc.jar
```

Run from a .jar with remote debugging:

```shell
java "-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005" -jar build/libs/hw18-jdbc.jar
```

