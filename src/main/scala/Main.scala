import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import predictors.{DecisionTreePredictor, NaiveBayesPredictor}

object Main {
  def main(args: Array[String]) {

    Logger.getLogger("org").setLevel(Level.WARN)
    Logger.getLogger("akka").setLevel(Level.WARN)

    val config = new SparkConf().setMaster("local[1]").setAppName("Test App")
    val sc = new SparkContext(config)

    val naiveBayesPredictor = new NaiveBayesPredictor("1455823226384_2015-16_de_1_train.csv", sc)
    val decisionTreePredictor = new DecisionTreePredictor("1455823226384_2015-16_de_1_train.csv", sc)
    naiveBayesPredictor.buildModel()
    decisionTreePredictor.buildModel()
  }
}