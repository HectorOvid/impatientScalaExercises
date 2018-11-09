// compute average of array

def avg(a: Array[Double]): Double = if (a.nonEmpty) a.sum / a.length else 0

val inputA = Array(2.0, 6.0, 4.0)
val itsAvg = avg(inputA)

assert(itsAvg == 4.0)

avg(Array.empty)
