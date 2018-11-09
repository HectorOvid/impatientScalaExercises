
/**
  * @return Return an array of n random integers.
  */
def getRandomArray(n: Int): Array[Int] = {
  val arr = new Array[Int](n)
  for (i <- arr.indices) arr(i) = scala.util.Random.nextInt(n-1)
  arr
}

val a = getRandomArray(5)


/**
  * Task 3) Repeat with for and yield
  * @return Return an array of n random integers.
  */
def yieldRandomArray(n: Int): Array[Int] = {
  val arr = new Array[Int](n)
  val nextA = for (i <- arr) yield scala.util.Random.nextInt(n-1)
  nextA
}

val a2 = yieldRandomArray(7)

def yieldOnlyRandomIndexedSeq(n: Int): IndexedSeq[Int] = {
  for (_ <- 0 until n) yield scala.util.Random.nextInt(n-1)
}

val a3 = yieldOnlyRandomIndexedSeq(8)


def yieldOnlyRandomArray(n: Int): Array[Int] = {
  val seq = for (_ <- 0 until n) yield scala.util.Random.nextInt(n - 1)
  seq.toArray
}

val a4 = yieldOnlyRandomArray(12)
