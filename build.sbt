
enablePlugins(ScalaJSPlugin)

name := "quizleague-vue"

version := "0.0.1"

scalaVersion := "2.12.2"

val circeVersion = "0.8.0"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.1",
  "com.github.lukajcb" %%% "rxscala-js" % "0.15.0"
)

 libraryDependencies ++= Seq(
	  "io.circe" %%% "circe-core",
	  "io.circe" %%% "circe-generic",
	  "io.circe" %%% "circe-parser"
	).map(_ % circeVersion)

libraryDependencies += "io.github.cquiroz" %%% "scala-java-time" % "2.0.0-M12"

scalaJSUseMainModuleInitializer := true


