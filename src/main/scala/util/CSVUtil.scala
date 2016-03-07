package util

import org.apache.spark.SparkContext
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD

object CSVUtil {

  def CSVToRDD(path: String, sparkContext: SparkContext): RDD[LabeledPoint] = {
    val csv = sparkContext.textFile("data/1455823226384_2015-16_de_1_train.csv")

    //To find the headers
    val header = csv.first

    //To remove the header
    val data = csv.filter(_ (0) != header(0))

    //To create a RDD of (label, features) pairs
    val rddData = data.map { line =>
      val parts = line.split(',')
      LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).split(' ').map(_.toDouble)))
    }.cache()

    rddData
  }
}
