package org.dataclean1

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{SaveMode, SQLContext}

object Main {
  def usage() = {
    println("usage:\n")
    println("sbt run <output file>")
  }

  def main(args: Array[String]): Unit =
  {
    // ******** These declarations not applicable for spark-shell. ********
    val sparkConf = new SparkConf()
      .setMaster("local")
      .setAppName("dataclean1")
    val sc = new SparkContext(sparkConf)
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)

    if(args.length < 1) {
      usage()
      System.exit(1)
    }
    val fileOut = args(0)
    println(s"hello main: $fileOut")

    // This is used to implicitly convert an RDD to a DataFrame.
    // If absent, you will get: "value $ is not a member of StringContext"
    import sqlContext.implicits._

    // ******** Begin here for spark-shell.  Stuff above this line not applicable. ********

    import org.apache.spark.sql.types.{StructType, StringType, StructField}
    import org.apache.spark.sql.functions._

    val gfa = sqlContext.read.format("com.databricks.spark.csv").option("header", "true").option("inferSchema", "true").load("gfa25.csv")
    // gfa: org.apache.spark.sql.DataFrame = [
    // Country: string,
    // Commodity: string,
    // Item: string,
    // Unit: string,
    // Year: int,
    // Amount: double]

    val pop = gfa.filter($"Item" === "Total Population - Both Sexes").groupBy("Country","Year").agg(avg("Amount").as("Population"))

    val numExtreme = (pop.filter($"Year" === 1992).count()/10).toInt

    val countriesExtreme = {
      val popOrder = pop.filter($"Year" === 1992).orderBy($"Population".asc)
      val numExtreme = (popOrder.count()/10).toInt
      val tooBig = popOrder.select($"Country").collect().take(numExtreme)
      val tooSmall = popOrder.select($"Country").collect().takeRight(numExtreme)
      sqlContext.createDataFrame(sc.parallelize(tooSmall ++ tooBig), StructType(List(StructField("Country2",StringType,true))))
    }

    val justRight = gfa.join(countriesExtreme, $"Country" === $"Country2", "outer")

    val dfFinal = justRight.filter($"Country2".isNull)
    dfFinal.repartition(1).save("com.databricks.spark.csv",
      SaveMode.ErrorIfExists,
      Map("path" -> args(0), "header" -> "true"))

    println("goodbye main")
  }
}