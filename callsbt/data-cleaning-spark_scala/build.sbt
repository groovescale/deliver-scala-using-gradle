name := "data-cleaning-spark"

scalaVersion := "2.10.5"

version := "1.0"

mainClass := Some("org.dataclean1.Main")

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.5.1" % "provided"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "1.5.1" % "provided"

libraryDependencies += "com.databricks" %% "spark-csv" % "1.3.0"

