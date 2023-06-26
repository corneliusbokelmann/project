package de.htwg.se.controler.controlercomponent.controlerImpl

import de.htwg.se.controler.controlercomponent.ControllerInterface
import de.htwg.se.model.modelcomponent.modelImpl.Point
import de.htwg.se.controler.controlercomponent.InputStrategy

class StandardInput extends InputStrategy {
  private var pointsBuffer: List[(Point, Int, Int)] = Nil
  private var currentLine = 0

  override def handleInput(input: String, controller: ControllerInterface): Unit = {
    if (currentLine >= controller.getMaxGuesses) {
      println("Game Over! You cannot make any more moves.")
    } else {
      input match {
        case "u" => controller.undoLastMove()
        case _ =>
          val trimmedInput = input.trim
          if (trimmedInput.length == controller.getGuessLength) {
            val chars = trimmedInput.toCharArray
            chars.zipWithIndex.foreach { case (c, i) => handleSingleInput(c.toString, i, controller) }
            controller.makeMove(pointsBuffer)
            pointsBuffer = Nil
            currentLine += 1
          } else {
            handleSingleInput(trimmedInput, pointsBuffer.length, controller)
            if (pointsBuffer.length == controller.getGuessLength) {
              controller.makeMove(pointsBuffer)
              pointsBuffer = Nil
              currentLine += 1
            }
          }
      }
    }
  }

  private def handleSingleInput(input: String, index: Int, controller: ControllerInterface): Unit = {
    val validSymbols = Set("R", "G", "B", "Y", "O", "P", "C", "M")
    val color = input.toUpperCase
    if (validSymbols.contains(color)) {
      val pointOption = Point.valueFromSymbol(color)
      pointOption match {
        case Some(point) =>
          pointsBuffer = pointsBuffer :+ ((point, currentLine, index)) // swapped index with currentLine
        case None =>
          println(s"Unexpected error: $color was recognized as a valid symbol but could not be converted to a Point.")
      }
    } else {
      println(s"Invalid point symbol: $color. Please enter a valid symbol (G, R, B, Y, O, P, C or M).")
    }
  }



}
