// For an array of integers produce an array
// that contains first all positive numbers (in old order) and then
// afterwards all {negative numbers or 0} (in old order)

val inputArray = Array(1,-2,13,12,-4,-8,7,-5,1,0,2,7,-7,-7,7,7,-7)
val expectedArray = Array(1,13,12,7,1,2,7,7,7,-2,-4,-8,-5,0,-7,-7,-7)

def separate(a: Array[Int]): Array[Int] = {
  val positives = a.filter(_ > 0)
  val negatives = a.filter(_ <= 0)
  positives ++ negatives
}

expectedArray
separate(inputArray)

assert(assertion = expectedArray sameElements separate(inputArray))


def separateInOneGo(a: Array[Int]): Array[Int] = {
  import scala.collection.mutable.ArrayBuffer
  type AggregatePosAndNeg = (ArrayBuffer[Int], ArrayBuffer[Int])
  val scanIntermediates = a.scanLeft((ArrayBuffer.empty[Int], ArrayBuffer.empty[Int]))(
    op = (buffer: AggregatePosAndNeg, nextNumber: Int) => {
      if (nextNumber > 0) {
        buffer._1 += nextNumber
      } else {
        buffer._2 += nextNumber
      }
      buffer
    }
  )

  if (scanIntermediates.isEmpty) {
    Array.empty
  } else {
    (scanIntermediates.last._1 ++= scanIntermediates.last._2).toArray[Int]
  }
}

val receivedInOneGo = separateInOneGo(inputArray)

assert(expectedArray sameElements receivedInOneGo)