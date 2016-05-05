package test

import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._

/**
  * Created by mazi on 05.05.16.
  */
object SparkTests {
  def testSQLContext(sc: SparkContext): Unit = {
    val sqlContext = new SQLContext(sc)

    val df = sqlContext.read
      .format("com.databricks.spark.csv")
      .option("header", "true") // Use first line of all files as header
      .option("inferSchema", "true") // Automatically infer data types
      .load("data/1455823226384_2015-16_de_1_train.csv")

    df.registerTempTable("football")

    df.show(5)

    val selectedData = df.select("team_value_ratio", "winner").show(100)
    //    df.filter(df("winner") === "H").show()
    //    df.groupBy(df("winner")).count().show()
    //    sqlContext.sql("select winner, count(*) as count from football group by winner").show()
    df.groupBy("winner").agg(count("*").as("Count"), sum("team_value_ratio").as("SUM")).show()
  }

}
