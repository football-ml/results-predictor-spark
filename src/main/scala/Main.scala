import java.io.File
import java.nio.charset.Charset

import org.apache.commons.csv.{CSVFormat, CSVParser}
import org.apache.log4j.{Level, Logger}

import org.apache.spark.{rdd, SparkConf, SparkContext}
import org.apache.spark.rdd.{RDD, PairRDDFunctions}

import org.apache.spark.mllib.classification.{NaiveBayes, NaiveBayesModel}
import org.apache.spark.mllib.recommendation.{ALS, Rating, MatrixFactorizationModel}

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._
import util.CSVUtil

object Main {
  def main(args: Array[String]) {

    Logger.getLogger("org").setLevel(Level.WARN)
    Logger.getLogger("akka").setLevel(Level.WARN)

    val config = new SparkConf().setMaster("local[1]").setAppName("Test App")
    val sc = new SparkContext(config)
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

    val trainData = CSVUtil.CSVToRDD("data/1455823226384_2015-16_de_1_train.csv", sc)

    val splits = trainData.randomSplit(Array(0.6, 0.4), seed = 11L)
    val training = splits(0)
    val test = splits(1)

    val model = NaiveBayes.train(training, lambda = 1.0, modelType = "multinomial")
    val predictionAndLabel = test.map(p => (model.predict(p.features), p.label))

    println("PRED" + predictionAndLabel.toString())
    val accuracy = 1.0 * predictionAndLabel.filter(x => x._1 == x._2).count() / test.count()
    println(s"Naive Bayes predicts test data with ${accuracy} accurancy")
  }
}