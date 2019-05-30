import scala.collection.mutable

// How do you rearrange the elements of an Array[Int]
// so that they appear in teverse sorted order?
// How do you do the same with an ArrayBuffer[Int]
val arr = Array(1, -2, 2, 7, 0, 3, 4, 5, -3)

arr.sorted.reverse

arr.sortBy { x => -x }

val buff = mutable.ArrayBuffer.empty[Int]
arr.copyToBuffer(buff)
buff.sorted.reverse



