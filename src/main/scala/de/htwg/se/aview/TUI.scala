package de.htwg.se.aview

import com.google.inject.Inject

import de.htwg.se.controler.controlercomponent.ControllerInterface
import de.htwg.se.controler.controlercomponent.InputStrategy
import de.htwg.se.model.modelcomponent.modelImpl.Point
import de.htwg.se.util.Observer
import de.htwg.se.controler.controlercomponent.controlerImpl.{GameWonState, GameOverState}

import scala.io.StdIn.readLine

// Define event trait and case classes
sealed trait TuiEvent
case class InputReceived(input: String) extends TuiEvent
case object UndoRequested extends TuiEvent
case object QuitRequested extends TuiEvent

class TUI @Inject() (controller: ControllerInterface, inputStrategy: InputStrategy) extends Observer {
  controller.add(this)

  def run(): Unit = {
    printBoard()
    var event: TuiEvent = InputReceived(readLine())
    while (event != QuitRequested) {
      event match {
        case InputReceived(input) =>
          if (input.toLowerCase == "u") {
            event = UndoRequested
          } else if (input.toLowerCase == "q") {
            event = QuitRequested
          } else {
            controller.processInput(input)
            event = InputReceived(readLine())
          }
        case UndoRequested =>
          controller.undoLastMove()
          event = InputReceived(readLine())
        case QuitRequested => // Do nothing, loop will exit
      }
    }

  }

  override def update(): Unit = {
    printBoard()
    if (controller.getGameState.isInstanceOf[GameWonState]) {
      println("Congratulations, you've won!")
    } else if (controller.getGameState.isInstanceOf[GameOverState]) {
      println("Sorry, you've lost. Try again!")
    }
  }

  private def printBoard(): Unit = {
    val guessLength = 10
    val pointslength = 4

    val builder = new StringBuilder()
    for (row <- 0 until guessLength) {
      for (col <- 0 until pointslength) {
        val point = controller.pointCell(row, col).map(_.toString).getOrElse(" ")
        builder.append(point).append(" ")
      }
      builder.append("| ")
      for (col <- 0 until 4) {
        val feedback = controller.feedbackCell(row, col).map(_.toString).getOrElse(" ")
        builder.append(feedback).append(" ")
      }
      builder.append("\n")
    }

    println(builder.toString())
  }

  private def processInput(input: String): Unit = {
    inputStrategy.handleInput(input, controller)
  }
}