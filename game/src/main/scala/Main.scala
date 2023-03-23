import scala.annotation.main

object Main {
  @main def run(): Unit = {
    // Define a multiline string for the playing field
    val fieldString =
      """+---+---+---+---+
         | - | - | - | - |
         +---+---+---+---+
         | - | - | - | - |
         +---+---+---+---+
         | - | - | - | - |
         +---+---+---+---+
         | - | - | - | - |
         +---+---+---+---+
         | - | - | - | - |
         +---+---+---+---+"""

    // Print out the playing field string
    println(fieldString)

    println(msg)
  }

  def msg = "I was compiled by Scala 3. :)"
}

