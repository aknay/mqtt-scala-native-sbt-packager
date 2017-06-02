enablePlugins(JavaServerAppPackaging, SystemdPlugin)

name := "mqtt-sbt"
version := "1.0"

mainClass in Compile := Some("MainApp")

// package settings
maintainer in Linux := "aknay <aknay@outlook.com>"

packageDescription := "Mqtt SBT Message Exchanger"

libraryDependencies += "org.eclipse.paho" % "mqtt-client" % "0.4.0"

resolvers += "MQTT Repository" at "https://repo.eclipse.org/content/repositories/paho-releases/"

