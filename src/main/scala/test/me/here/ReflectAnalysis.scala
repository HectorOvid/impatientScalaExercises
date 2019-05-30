package test.me.here

import scala.reflect.runtime.{universe => ru}

object ReflectAnalysis extends App {
  println("Reflection stuff is going to happen")

  val any /*: Any*/ = new DataCookie(n = 8, k = new Shocki())

  println(any)


  val tag = getTypeTag(any)
  println(tag)

  println(tag.toString())

  println(tag.equals(new DataCookie(n = 8, k = new Shocki())))

  println(tag.tpe)


  def getTypeTag[T: ru.TypeTag](obj: T) = ru.typeTag[T]

}


class DataCookie(val n: Int, val k: Shocki) {
  override def toString: String = s"n = $n , schoki = $k"
}

object DataCookie {
  val theN = 2
}

case class Shocki(name: String = "Lindt zartbitter")


object ReflectFromNeighbor extends App {
  println(ru.typeOf[DataCookie].companion)

  val runMirror = ru.runtimeMirror(getClass.getClassLoader)

  // Construction
  val cookieCutterMirror: scala.reflect.runtime.universe.ClassMirror = runMirror.reflectClass(ru.typeOf[DataCookie].typeSymbol.asClass)

  val cookieCutterBlueprint = ru.typeOf[DataCookie].decl(ru.termNames.CONSTRUCTOR).asMethod

  val cookieCutter = cookieCutterMirror.reflectConstructor(cookieCutterBlueprint)

  val universeCookie = cookieCutter(1, Shocki("dd"))

  println(universeCookie)


  // members
  val aCookie = new DataCookie(n = 42, k = Shocki("white"))

  val firstMemberTerm = ru.typeOf[DataCookie].decl(ru.TermName("n")).asTerm

  val instanceMirror = runMirror.reflect(aCookie)
  val fieldMirror = instanceMirror.reflectField(firstMemberTerm)

  println(s"Mirrored value = n: ${fieldMirror.get}")
  fieldMirror.set(666)

  println(s"Tampered cookie now looks like: $aCookie")

  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  println("\n\nClass data cookie members:")
  ru.typeOf[DataCookie].members.foreach(println)

  println("\n\nmore:\n")
  val x = ru.typeOf[DataCookie].member(ru.TermName("n"))
  println(x)


  val methodList = ru.typeOf[DataCookie].members.collect {
    case m: ru.MethodSymbol if m.isGetter => m
  }.toList


  println(methodList)
  println(methodList.map(_.returnType))


  println("now only int valued ones:")
  methodList.filter(x => x.returnType == ru.typeOf[Int]).foreach(println)
}
