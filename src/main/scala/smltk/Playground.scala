import breeze.linalg._
import smltk.linearmodel._
import smltk.metrics.ClassificationMetrics._

object Playground extends App {

  var X = DenseMatrix.rand[Double](200, 10)
  val y = DenseVector.rand[Double](200)

  val linReg = new LinearRegression()
  println (linReg.fit(X, y))
  println (linReg.score(X, y))

  val ridgeReg = new RidgeRegression(0.005)
  println (ridgeReg.fit(X, y))
  println (ridgeReg.score(X, y))

  println("========== Accuracy ==========")
  println(accuracy(DenseVector(1,2,3,4,5,6,7), DenseVector(1,2,3,4,5,6,7)))
  println(accuracy(DenseVector(1,2,3,4,5,6,7), DenseVector(1,2,3,4,5,6,12)))
  println(accuracy(DenseVector(1,2,3,4,5,6,7), DenseVector(1,2,3,4,5,6,12), normalize=false))
  // println(accuracy(DenseVector(), DenseVector()))
  val yTrue = DenseVector(1,1,1,0,0,0,1)
  val yPreds = DenseVector(1,1,1,1,0,0,1)
  var (tp, tn, fp, fn) = confusions(yTrue, yPreds)
  println("******************")
  println(accuracy(yTrue, yPreds))
  var acc  = (tp+tn) / (tp+tn+fp+fn)
  println(acc)
  println("******************")
  //
  println("==================== CLustering ====================")

  import smltk.cluster.Kmeans
  // X = DenseMatrix.rand[Double](200, 10)
  X = loadIris()
  val pca = princomp(X)
  // val model = pca.loadings(0 until 2, ::)
  // val XNew = (model.t * model) * pca.center

  val cl = new Kmeans(3, maxIterations=1000, nRuns=20)
  cl.fit(pca.scores)
  println(cl.centroids)
  println(s"Objective: ${cl.objective}")
  println(cl.labels)

  def loadIris(): DenseMatrix[Double] = {
    import com.github.tototoshi.csv._
    import java.io.File
    val reader = CSVReader.open(new File("/Users/fayimora/Misc/iris.csv"))
    val ls = reader.all.map(_.dropRight(1).map(_.toDouble).toArray)
    DenseMatrix(ls:_*)
  }


}