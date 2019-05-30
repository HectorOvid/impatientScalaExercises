package impatient.ch19structuraltypes

import scala.language.existentials

object NetAliases {

  type NetworkMember = n.Member forSome {val n: Network}

  def process(m1: NetworkMember, m2: NetworkMember): (NetworkMember, NetworkMember) = (m1, m2)
}

object ReferencePage286ExistentialTypes {
  def old_process[M <: n.Member forSome {val n : Network}](m1: M, m2: M): (M, M) = (m1, m2)
}

import impatient.ch19structuraltypes.ReferencePage286ExistentialTypes.old_process
import org.scalatest.FunSpec

object Differences extends FunSpec with App {
  val chatter = new Network
  val myFace = new Network

  val harry = chatter.join("harry")
  val bob = chatter.join("bob")

  val marcone = myFace.join("marcone")

  old_process(harry, bob) // OK
  assertDoesNotCompile("old_process(harry, marcone)")

  println("========================")

  assertDoesNotCompile("process(harry, harry)")
  assertDoesNotCompile("process(harry, bob)")
  assertDoesNotCompile("process(harry, marcone)")

  // You need the specific type from the type alias:
  //val networkMemberJim = new NetworkMember("jim") {

  //  class Member

  //}

  // val networkMemberBill = new NetworkMember("bill") {
  //   def what = "is going on"
  // }
  // println(networkMemberBill.what)

  //assertDoesNotCompile("process(networkMemberJim, networkMemberBill)")
}

