// Build file for example applications written in ScalaJS using scala-js-dom and scala-js-workbench
lazy val root = (project in file(".")).enablePlugins(ScalaJSPlugin)
workbenchSettings

// Name is a prefix in the object code filename.
name := "A*Star Maze Solver"
scalaVersion := "2.11.7"

// Optional, necessary to sbt run, needs phantomJS to be installed.
jsDependencies += RuntimeDOM
scalaJSStage in Global := FastOptStage

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.8.1",
  "com.lihaoyi" %%% "scalatags" % "0.5.2",
  "be.doeraene" %%% "scalajs-jquery" % "0.8.0",
  "com.lihaoyi" %%% "utest" % "0.3.0" % "test"
)
skip in packageJSDependencies := false // All JavaScript dependencies to be concatenated to a single file

// Workbench has to know how to restart your application.
bootSnippet := "astar.AStarApp().doDynContent();"
// Update without refreshing the page every time fastOptJS completes
updateBrowsers <<= updateBrowsers.triggeredBy(fastOptJS in Compile)

testFrameworks += new TestFramework("utest.runner.Framework")