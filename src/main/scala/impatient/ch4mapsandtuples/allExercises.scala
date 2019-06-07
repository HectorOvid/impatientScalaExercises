package impatient.ch4mapsandtuples

import java.util.function.BiConsumer

import scala.collection.{SortedMap, mutable}

object allExercises extends App {
  // 1 set up a map of prices for a number of gizmos that you covet.
  // Then produce a second map with the same keys and the prices
  // at a 10 percent discount.
  val myGizmos = Map("Batman cup" -> 9.99, "Curry Shoes" -> 154.66, "Flash Blitzableiter" -> 10.0)
  val discounted = myGizmos.mapValues(_ * 0.9)
  println(discounted)


  // 2 Write a program that reads words from a file. Use a mutable map to count how often each wod appears.
  // To read the words simply use a java.util.Scanner

  // a) mutable map + java way
  val filepath = "src/main/resources/my-file.txt"
  val in = new java.util.Scanner(new java.io.File(filepath))
  val wordCounter = scala.collection.mutable.Map.empty[String, Int].withDefaultValue(0)
  while (in.hasNext()) {
    val line = in.nextLine()
    for (word <- line.split(" ")) {
      val textonly = word.filter(_.isLetter)
      wordCounter(textonly) += 1
    }
  }
  wordCounter.take(20).foreach(println)
  println("===============")

  // 3) immutable map + scala way
  var allWordsCounter = Map.empty[String, Int].withDefaultValue(0)
  val source = scala.io.Source.fromFile(filepath, "UTF-8")
  for (line <- source.getLines) {
    for (word <- line.split(" ")) {
      val textonly = word.filter(_.isLetter)
      // UGLY:
      // allWordsCounter = allWordsCounter + (textonly -> (allWordsCounter(textonly) + 1))
      //
      // LITTLE NICER:
      allWordsCounter += textonly -> (allWordsCounter(textonly) + 1)
    }
  }
  allWordsCounter.take(20).foreach(println)

  assert(wordCounter.keySet.diff(allWordsCounter.keySet).isEmpty)
  for ((key, mut, imu) <- wordCounter.map { case (k, v) => (k, v, allWordsCounter(k)) }) {
    assert(mut == imu, s"$key ==> #mutable: $mut  vs. #immu: $imu")
  }
  assert(allWordsCounter == wordCounter.toMap)
  println("~~~~~~~~sorting~~~~~~~~~~~~~~~~~~~~~~~~~")

  // 4) now sorted
  SortedMap(wordCounter.toSeq: _*).take(5).foreach(println)

  // 5) repeat with java.util.TreeMap, that you adapt to the scala api
  var jTree = new java.util.TreeMap[String, Int]() {
    def <-+(word: String): Unit = {
      if (this.containsKey(word)) {
        this.put(word, this.get(word) + 1)
      } else {
        this.put(word, 1)
      }
    }
  }

  val jSource = scala.io.Source.fromFile(filepath, "UTF-8")
  for (line <- jSource.getLines) {
    for (word <- line.split(" ")) {
      val textonly = word.filter(_.isLetter)
      jTree <-+ textonly
    }
  }
  jTree.forEach(new BiConsumer[String, Int] {
    override def accept(t: String, u: Int): Unit = println(s"Tree: $t \t --> $u")
  })

  // ...... or you do it the boring way with import JavaConverters._ and asScala ... asJava :D


  //
  //
  // 6 Define a linked hash map that maps the day of the week to java.util.Calender
  // Show how the insertion order is preserved
  val theDays = mutable.LinkedHashMap("Tuesday" -> java.util.Calendar.TUESDAY, "Monday" -> java.util.Calendar.MONDAY)
  theDays.foreach(println)
  theDays("Saturday") = java.util.Calendar.SATURDAY
  println("now with another day: ")
  theDays.foreach(println)
}

object PrintingMore extends App {

  // 7 print java properties, with correct column size
  case class Val(padding: Int, propValue: String)

  import scala.collection.JavaConverters._

  val props = java.lang.System.getProperties.asScala
  val longestKey = props.keys.map(_.length).max
  val smartProps = props.map { case (key, value) => (key, Val(longestKey - key.length, value)) }

  for ((key, Val(pad, value)) <- smartProps) {
    println(s"$key${" " * pad} | $value")
  }

  // 8 Write a function that  returns the minimum and the maximum of an array
  println("\n\nGetting those extrema ...")

  case class Extrema(mini: Int, maxi: Int)

  def minmax(a: Array[Int]): Extrema = {
    a.aggregate(Extrema(Int.MaxValue, Int.MinValue))(
      seqop = (extrema, num) => {
        val Extrema(mi, ma) = extrema
        Extrema(math.min(mi, num), math.max(ma, num))
      },
      combop = (lex, rex) => {
        val Extrema(lmi, lma) = lex
        val Extrema(rmi, rma) = rex
        Extrema(math.min(lmi, rmi), math.max(lma, rma))
      })
  }

  val arrrr = Array(1, 4, 8, 2, 3)
  println(s"[${arrrr.mkString(",")}] ======> ${minmax(arrrr)}")

  // 9 Return triple of how many are less than given and how many are greater than given
  println("\n\nNow dividing into larger or bigger thatn some")

  def lteqgt(values: Array[Int], v: Int): (Int, Int, Int) = {
    values.aggregate((0, 0, 0))(
      (triple, value) =>
        triple match {
          case (l, m, r) if v < value => (l, m, r + 1)
          case (l, m, r) if value < v => (l + 1, m, r)
          case (l, m, r) => (l, m + 1, r)
        },
      (ltrip, rtrip) => {
        (ltrip._1 + rtrip._1, ltrip._2 + rtrip._2, ltrip._3 + rtrip._3)
      }
    )
  }

  val brrrr = Array(-3, 1, 4, 8, 2, 3, 9, 11, -1, 0, 0, 0, 0)
  println(s"[${brrrr.mkString(",")} ~~> ${lteqgt(brrrr, 2)}")

  // 10 Zipping two strings, what happens?
  println("~~10 Zipping two strings, what happens?")
  println("Hel".zip("Wor").toVector)
  assert("Hel".zip("Wor").toVector == Vector(('H', 'W'), ('e', 'o'), ('l', 'r')))
  println("Could be useful for computing word edit distance, similarity factor, ...")
  println(s"Or for interleaving: ${"hai".zip("__?").map(t => s"${t._1}${t._2}").mkString("")}")
}
