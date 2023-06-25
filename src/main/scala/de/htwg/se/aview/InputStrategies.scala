package de.htwg.se.aview

import de.htwg.se.controler.controlercomponent.ControllerInterface
import de.htwg.se.model.modelcomponent.modelImpl.Point

class StandardInput extends InputStrategy {
  private var currentLine: Int = 0

  override def handleInput(input: String, controller: ControllerInterface): Unit = {
    input match {
      case "q" =>
      case "u" => controller.undoLastMove()
      case _ =>
        val chars = input.toCharArray
        if (chars.length == 4) {
          val validSymbols = Set("W", "B", "G", "R", " ")
          chars.foreach { c =>
            val color = c.toString
            if (validSymbols.contains(color)) {
              val pointOption = Point.valueFromSymbol(color)
              pointOption match {
                case Some(point) =>
                  controller.makeMove(point, chars.indexOf(c), currentLine)
                case None =>
                  println(s"Unexpected error: $color was recognized as a valid symbol but could not be converted to a Point.")
              }
            } else {
              println(s"Invalid point symbol: $color. Please enter a valid symbol (W, B, G, R, or a space).")
            }
          }
          println("Your move has been made. Now you can enter a new line.")
          currentLine += 1
        } else {
          println("Invalid input format. Please enter four valid colors in the format 'RBGR'.")
        }
    }
  }
}
