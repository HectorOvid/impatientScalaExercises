package impatient.ch19structuraltypes

import scala.collection.mutable.ArrayBuffer

class Network {

  class Member(val name: String) {
    val contacts = new ArrayBuffer[Member]

    override def toString: String =
      s"$name => with contacts:\n${contacts.map(_.name).mkString(",\n")}"

    override def equals(obj: Any): Boolean = {
      obj match {
        case that: Member => that.name == this.name
        case _ => false
      }
    }

    def equalsInAnyNetworkMember(obj: Any): Boolean = {
      obj match {
        case that: Network#Member => that.name == this.name
        case _ => false
      }
    }
  }

  val members = new ArrayBuffer[Member]

  def join(name: String) = {
    val m = new Member(name)
    members += m
    m
  }
}


object NestedEqualityExample extends App {
  val chatter = new Network()
  val visagebook = new Network()

  val fred = chatter.join("Fried")
  val barney = visagebook.join("Bayrney")

  val fred2 = visagebook.join("Fried")
  val fred11 = chatter.join("Fried")

  println(fred)
  println(chatter.members)
  println(visagebook.members)

  println("\n\n****************************\n")

  assert(fred == fred, "same instance is equal")
  assert(fred == fred11, "same network same value is equal")
  assert(fred != fred2, "diff net but same value is NOT equal")
  assert(fred11 != fred2, "diff not 2 => not equal")
  assert(barney != fred2, "same network, different value => not equal")

  println("\n --- and if independent of network:\n")

  assert(fred equalsInAnyNetworkMember fred, "same instance is equal")
  assert(fred equalsInAnyNetworkMember fred11, "same network same value is equal")
  assert(fred equalsInAnyNetworkMember fred2, "diff net but same value is equal while disregarding network instance projection")
  assert(fred11 equalsInAnyNetworkMember fred2, "-\"-")
  assert(!(barney equalsInAnyNetworkMember fred2), "same network, different value => not equal")
}