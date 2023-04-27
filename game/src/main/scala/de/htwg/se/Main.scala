package de.htwg.se

// import model.Field
// import model.Point
import model.Point
import model.Field
import scala.io.StdIn.readLine

@main def mastermind: Unit =
  println("Willkommen zu Mastermind!")
  val field = new Field(pointslength = 2, guesslegth = 3, Point.e)
  println(field.toString)
  getInputAndPrintLoop(field)

  def getInputAndPrintLoop(field: Field): Unit = 
    println("Enter your guess <Colour><Position> (q to quit):")
    val input = readLine
    parseInput(input) match
      case None => field
      case Some(newfield) =>
        println(newfield)
        getInputAndPrintLoop(newfield)

    def parseInput(input: String): Option[Field] = 
      input match
        case "q" => None
        case _ => {
          val chars = input.toCharArray
            val point = chars(0) match
              case 'W' => Point.w
              case 'B' => Point.b
              case 'G' => Point.g
              case 'R' => Point.r
              case _  => Point.e
            val xpos = chars(2).toString.toInt
            val ypos = chars(1).toString.toInt
            Some(field.put((point), ypos, xpos))
        }