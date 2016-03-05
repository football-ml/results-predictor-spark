name := "SparkExperiment"

version := "1.0"

scalaVersion := "2.11.7"

// additional libraries
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.6.0" % "compile",
  "org.apache.spark" %% "spark-sql" % "1.6.0" % "compile",
  "org.apache.spark" %% "spark-mllib" % "1.6.0" % "compile",
  "org.apache.spark" %% "spark-ml" % "1.6.0" % "compile",
  "org.apache.spark" %% "spark-hive" % "1.6.0" % "compile",
  "com.databricks" %% "spark-csv" % "1.4.0" % "compile",
  "mysql" % "mysql-connector-java" % "5.1.+" % "compile",
  "com.sandinh" %% "php-unserializer" % "1.0.3",
  "org.json4s" %% "json4s-native" % "3.3.0",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.2",
  "io.spray" %%  "spray-json" % "1.3.2"
)