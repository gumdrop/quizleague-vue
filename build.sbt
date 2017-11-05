
enablePlugins(ScalaJSPlugin)

name := "quizleague-vue"

version := "2.2.4"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.1",
  "com.github.lukajcb" %%% "rxscala-js" % "0.15.0"
)

scalaJSUseMainModuleInitializer := true


