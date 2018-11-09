import scala.collection.mutable.ArrayBuffer

def swapero(array: Array[Int]) : Array[Int] = {
  var swapped = new ArrayBuffer[Int]
  for (i <- array.indices by 2)
    if (i+1 < array.length)
      swapped ++= ArrayBuffer(array(i+1), array(i))
    else swapped += array(i)
  swapped.toArray
}

swapero(Array(1,2,3,4,5))