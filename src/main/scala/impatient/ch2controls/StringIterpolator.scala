package impatient.ch2controls

import java.time.LocalDate

object StringIterpolator extends App {

  // define a java.time.LocalDate as date"$year-$month-$day"
  implicit class DateInterpolator(val sc: StringContext) extends AnyVal {
    def date(args: Any*): LocalDate = {
      if (args.size != 3) throw new IllegalArgumentException("wrong number of arguments")


      var y, m, d : Int = 0

      val text = args match {
        case args: Seq[Integer] =>
          y = args.head
          m = args(1)
          d = args(2)
          "happy input :)"
        case _ => throw new IllegalArgumentException("expect only strings as input")
      }

      println(text)
      LocalDate.of(y, m , d)
    }

  }

  // Goal
  val year = 2010
  val month = 10
  val day = 7
  val dd: LocalDate = date"$year-$month-$day"

  println(dd)
}


object User2MoreSacalaesk extends App {
    println("Hi im a user")

    val tr = (2011, 11, 1)

    // Goal
    import impatient.ch2controls.StringIterpolator.DateInterpolator
    println( date"${tr._1}-${tr._2}-${tr._3}" )
}
