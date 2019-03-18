name := "ElasticSearch_CRUD_Operations"

version := "0.1"

scalaVersion := "2.11.8"

// https://mvnrepository.com/artifact/org.elasticsearch/elasticsearch
libraryDependencies += "org.elasticsearch" % "elasticsearch" % "6.3.2"

// https://mvnrepository.com/artifact/org.elasticsearch.client/transport
libraryDependencies += "org.elasticsearch.client" % "transport" % "6.3.2"

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.3.0"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.3.0"


assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}