inThisBuild(
  Seq(
    startYear := Some(2017),
    scalaVersion := "2.12.6",
    sonatypeGithost := (Gitlab, "fommil", "drone-dynamic-agents"),
    sonatypeDevelopers := List("Sam Halliday"),
    licenses := Seq(GPL3),
    scalafmtConfig := Some(file("project/scalafmt.conf")),
    scalafixConfig := Some(file("project/scalafix.conf"))
  )
)

resourcesOnCompilerCp(Compile)

addCommandAlias("cpl", "all compile test:compile")
addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt")
addCommandAlias(
  "check",
  "all headerCheck test:headerCheck scalafmtSbtCheck scalafmtCheck test:scalafmtCheck"
)
addCommandAlias("lint", "all compile:scalafixTest test:scalafixTest")
addCommandAlias("fix", "all compile:scalafixCli test:scalafixCli")

libraryDependencies ++= Seq(
  "com.github.mpilquist" %% "simulacrum"      % "0.13.0",
  "com.chuusai"          %% "shapeless"       % "2.3.3",
  "eu.timepit"           %% "refined-scalaz"  % "0.9.2",
  "org.scalaz"           %% "scalaz-ioeffect" % "2.10.1",
  "com.propensive"       %% "contextual"      % "1.1.0",
  "org.scalatest"        %% "scalatest"       % "3.0.5" % "test"
)

val derivingVersion = "1.0.0-RC8"
libraryDependencies ++= Seq(
  "com.fommil" %% "deriving-macro" % derivingVersion % "provided",
  compilerPlugin("com.fommil" %% "deriving-plugin" % derivingVersion),
  "com.fommil" %% "scalaz-deriving"            % derivingVersion,
  "com.fommil" %% "scalaz-deriving-magnolia"   % derivingVersion,
  "com.fommil" %% "scalaz-deriving-scalacheck" % derivingVersion,
  "com.fommil" %% "jsonformat"                 % derivingVersion
)

scalacOptions ++= Seq(
  "-language:_",
  "-unchecked",
  "-explaintypes",
  "-Ywarn-value-discard",
  "-Ywarn-numeric-widen",
  "-Ypartial-unification",
  "-Xlog-free-terms",
  "-Xlog-free-types",
  "-Xlog-reflective-calls",
  "-Yrangepos",
  "-Yno-imports",
  "-Yno-predef",
  "-Ywarn-unused:explicits,patvars,imports,privates,locals,implicits",
  "-opt:l:method,inline",
  "-opt-inline-from:scalaz.**"
)

addCompilerPlugin(scalafixSemanticdb)
addCompilerPlugin("org.spire-math" %% "kind-projector"     % "0.9.7")
addCompilerPlugin("com.olegpy"     %% "better-monadic-for" % "0.2.4")
addCompilerPlugin(
  ("org.scalamacros" % "paradise" % "2.1.1").cross(CrossVersion.full)
)

scalacOptions in (Compile, console) -= "-Yno-imports"
scalacOptions in (Compile, console) -= "-Yno-predef"
initialCommands in (Compile, console) := Seq(
  "scalaz._, Scalaz._"
).mkString("import ", ",", "")
