ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.1"
ThisBuild / scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-feature"
)

ThisBuild / testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")

val zioVersion        = "2.0.19"
val tapirVersion      = "1.4.0"
val zioLoggingVersion = "2.1.12"
val zioConfigVersion  = "3.0.7"
val sttpVersion       = "3.8.13"
val javaMailVersion   = "1.6.2"
val stripeVersion     = "24.3.0"

val dependencies = Seq(
  "com.softwaremill.sttp.tapir"   %% "tapir-sttp-client"                 % tapirVersion,
  "com.softwaremill.sttp.tapir"   %% "tapir-json-zio"                    % tapirVersion,
  "com.softwaremill.sttp.client3" %% "zio"                               % sttpVersion,
  "com.softwaremill.sttp.tapir"   %% "tapir-zio"                         % tapirVersion, // Brings in zio, zio-streams
  "com.softwaremill.sttp.tapir"   %% "tapir-zio-http-server"             % tapirVersion, // Brings in zhttp
  "com.softwaremill.sttp.tapir"   %% "tapir-swagger-ui-bundle"           % tapirVersion,
  "com.softwaremill.sttp.tapir"   %% "tapir-sttp-stub-server"            % tapirVersion % "test",
  "com.softwaremill.sttp.tapir"   %% "tapir-sttp-client"                 % tapirVersion,
  "com.softwaremill.sttp.tapir"   %% "tapir-json-zio"                    % tapirVersion,
  "com.softwaremill.sttp.client3" %% "zio"                               % sttpVersion,
  "dev.zio"                       %% "zio-logging"                       % zioLoggingVersion,
  "dev.zio"                       %% "zio-logging-slf4j"                 % zioLoggingVersion,
  "ch.qos.logback"                 % "logback-classic"                   % "1.4.12",
  "dev.zio"                       %% "zio-test"                          % zioVersion,
  "dev.zio"                       %% "zio-test-junit"                    % zioVersion % "test",
  "dev.zio"                       %% "zio-test-sbt"                      % zioVersion % "test",
  "dev.zio"                       %% "zio-test-magnolia"                 % zioVersion % "test",
  "dev.zio"                       %% "zio-mock"                          % "1.0.0-RC9" % "test",
  "dev.zio"                       %% "zio-config"                        % zioConfigVersion,
  "dev.zio"                       %% "zio-config-magnolia"               % zioConfigVersion,
  "dev.zio"                       %% "zio-config-typesafe"               % zioConfigVersion,
  "io.getquill"                   %% "quill-jdbc-zio"                    % "4.7.3",
  "org.postgresql"                 % "postgresql"                        % "42.5.4",
  "org.flywaydb"                   % "flyway-core"                       % "9.16.0",
  "io.github.scottweaver"         %% "zio-2-0-testcontainers-postgresql" % "0.10.0",
  "dev.zio"                       %% "zio-prelude"                       % "1.0.0-RC16",
  "com.auth0"                      % "java-jwt"                          % "4.3.0",
  "com.sun.mail"                   % "javax.mail"                        % javaMailVersion,
  "com.stripe"                     % "stripe-java"                       % stripeVersion
)

lazy val root = (project in file("."))
  .settings(
    name := "megan-and",
    libraryDependencies ++= dependencies
  )