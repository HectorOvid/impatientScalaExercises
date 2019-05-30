import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
// array buffer of int
// remove all but the first negative number

// given attempt was:
def removeSecondaryNegatives_FromExercise(a: mutable.Buffer[Int]) = {
  var first = true
  var n = a.length
  var i = 0
  while (i < n) {
    if (a(i) >= 0) i += 1
    else {
      if (first) {
        first = false;
        i += 1
      }
      else {
        a.remove(i);
        n -= 1
      }
    }
  }
  a
}

val buff = ArrayBuffer(1, 2, 3, -3, 4, 5, -2, 7, 8, -12, 0, 3, -1, -3, 6, 7)
val buffBefore = ArrayBuffer.empty[Int]
buff.copyToBuffer(buffBefore)
removeSecondaryNegatives_FromExercise(buffBefore)


def retryRemoveSecondaryNegatives(a: mutable.Buffer[Int]) = {
  val negativeIndices = a.zipWithIndex.filter {
    case (x, _) => x < 0
  }.map {
    case (_, index) => index
  }.tail.reverse

  println(negativeIndices.toVector)

  for (toRemoveAt <- negativeIndices)
    a.remove(toRemoveAt)

  a
}

retryRemoveSecondaryNegatives(buff)

