import java.io.File
import java.nio.charset.Charset

import org.apache.commons.csv.{CSVFormat, CSVParser}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.recommendation.{ALS, Rating, MatrixFactorizationModel}

object Main {
  def main(args: Array[String]) {

    Logger.getLogger("org.apache.spark").setLevel(Level.OFF)
  }
}