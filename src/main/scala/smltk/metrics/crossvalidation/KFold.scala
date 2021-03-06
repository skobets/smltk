package smltk.metrics.crossvalidation


import breeze.linalg.{shuffle => bshuffle}

object KFold {
  def apply(nSamples: Int, k: Int, shuffle: Boolean = false) = {
    var idxs = (0 until nSamples).toArray
    idxs = if(shuffle) bshuffle(idxs) else idxs
    val part = if(nSamples%k==0) nSamples/k else (nSamples/k)+1
    val idxs2 = idxs.grouped(part).toArray //=> Array[Array[Int]]
    for(i <- 0 until k)
      yield ((idxs2.slice(0, i) ++ idxs2.slice(i+1, idxs2.size)).flatten, idxs2(i))
  }
}
