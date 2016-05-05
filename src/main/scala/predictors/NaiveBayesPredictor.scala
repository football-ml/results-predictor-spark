package predictors

import org.apache.spark.SparkContext
import org.apache.spark.mllib.classification.{NaiveBayes, NaiveBayesModel}

case class NaiveBayesPredictor(dataSet: String, sc: SparkContext) extends GenericPredictor {
  def buildModel(saveModel: Boolean = false): NaiveBayesModel = {
    val trainData = loadData(dataSet, sc)

    val splits = trainData.randomSplit(Array(0.8, 0.2), seed = 11L)
    val training = splits(0)
    val test = splits(1)

    val model = NaiveBayes.train(training, lambda = 1.0, modelType = "multinomial")
    val predictionAndLabel = test.map(p => (model.predict(p.features), p.label))

    val accuracy = 1.0 * predictionAndLabel.filter(x => x._1 == x._2).count() / test.count()
    val roundedAccuracy = accuracy - (accuracy % 0.0001)

    if (saveModel) {
      model.save(sc, s"models/NaiveBayes/${roundedAccuracy}-${dataSet}")
    }
    println(s"Naive Bayes predicts test data with ${roundedAccuracy} accuracy")
    model
  }
}
