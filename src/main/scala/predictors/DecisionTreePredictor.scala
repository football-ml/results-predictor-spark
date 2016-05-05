package predictors

import org.apache.spark.SparkContext
import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.mllib.tree.model.DecisionTreeModel

case class DecisionTreePredictor(dataSet: String, sc: SparkContext) extends GenericPredictor {
  def buildModel(saveModel: Boolean = false): DecisionTreeModel = {
    val trainData = loadData(dataSet, sc)

    val splits = trainData.randomSplit(Array(0.8, 0.2), seed = 11L)
    val training = splits(0)
    val test = splits(1)

    val numClasses = 10
    val categoricalFeaturesInfo = Map[Int, Int]()
    val impurity = "gini"
    val maxDepth = 5
    val maxBins = 32

    val model = DecisionTree.trainClassifier(training, numClasses, categoricalFeaturesInfo, impurity, maxDepth, maxBins)
    val predictionAndLabel = test.map(p => (model.predict(p.features), p.label))

    val accuracy = 1.0 * predictionAndLabel.filter(x => x._1 == x._2).count() / test.count()
    val roundedAccuracy = accuracy - (accuracy % 0.0001)

    if (saveModel) {
      model.save(sc, s"models/NaiveBayes/${roundedAccuracy}-${dataSet}")
    }
    println(s"Decision Tree predicts test data with ${roundedAccuracy} accuracy")
    model
  }
}
