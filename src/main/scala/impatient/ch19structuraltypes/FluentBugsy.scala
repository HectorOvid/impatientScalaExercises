package impatient.ch19structuraltypes

import impatient.ch19structuraltypes.FluentBugsy.Command.{BugCommand, around, show, then}
import impatient.ch19structuraltypes.FluentBugsy.FluentBugsy

object FluentBugsy {

  object Command {

    sealed trait BugCommand

    object then extends BugCommand

    object show extends BugCommand

    object around

  }

  class FluentBugsy {
    private var heading = 1
    private var steps = 0

    def move(i: Int): this.type = {
      steps += (heading * i)
      this
    }

    def show: this.type = {
      print(s"$steps ")
      this
    }

    def and(x: BugCommand): this.type = {
      x match {
        case _: Command.show.type => this.show
        case _: Command.then.type => this
      }
    }

    def turn(x: Command.around.type): this.type = {
      heading *= -1
      this
    }

  }

}


object BugDemo extends App {
  val bugsy = new FluentBugsy()

  println("How does the bug say:")
  bugsy move 4 and show and then move 6 and show turn around move 5 and show
  println("\n done.")
}
