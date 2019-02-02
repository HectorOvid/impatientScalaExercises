package impatient.ch19structuraltypes

import impatient.ch19structuraltypes.FluentDocuments.{Author, Document, Title}

import scala.collection.mutable


object FluentDocuments {

  sealed trait Keys

  object Title extends Keys

  object Author extends Keys

  class Document {
    val props = mutable.Map(Title -> "", Author -> "")
    var next: Keys = Title

    def set(x: Keys): this.type = {
      next = x
      this
    }

    def to(value: String): this.type = {
      props(next) = value
      this
    }

  }

}

object DocuExample extends App {
  val book = new Document()

  book set Title to "Scala for the Impotion" set Author to "Bilbo Baudling"

  println(book.props)
}
