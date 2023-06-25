package de.htwg.se.controler.controlercomponent
package controlerImpl

import de.htwg.se.model.modelcomponent.modelImpl.Point
import de.htwg.se.model.modelcomponent.FieldInterface
import de.htwg.se.model.modelcomponent.FeedbackFieldInterface
import de.htwg.se.model.modelcomponent.FeedbackInterface
import de.htwg.se.util.{Observable, Observer}
import de.htwg.se.aview.GUI

import scala.collection.mutable.Stack
import scala.util.{Failure, Success, Try}
import de.htwg.se.controler.controlercomponent.controlerImpl.AddCommand
import de.htwg.se.controler.controlercomponent.controlerImpl.Command
import de.htwg.se.controler.controlercomponent.controlerImpl.Receiver
import de.htwg.se.controler.controlercomponent.controlerImpl.RemoveCommand

import de.htwg.se.controler.controlercomponent.ControllerInterface
import de.htwg.se.controler.controlercomponent.GameStateInterface
import de.htwg.se.controler.controlercomponent.ReceiverInterface
import de.htwg.se.controler.controlercomponent.CommandInterface


trait GameState extends GameStateInterface{
  def makeMove(controller: ControllerInterface, point: Point, x: Int, y: Int): Unit
}

class PlayState extends GameState {
  override def makeMove(controller: ControllerInterface, point: Point, x: Int, y: Int): Unit = {
    val receiver = controller.getReceiver
    val command = AddCommand(receiver, point, x, y)
    executeCommand(controller, command)
    controller.feedbackfield.updateFeedback(controller.field, x, y)
    controller.notifyObservers()
  }

  private def executeCommand(controller: ControllerInterface, command: CommandInterface): Unit = {
    val result = command.execute()
    result match {
      case Success(_) =>
        controller.addToCommandHistory(command)
      case Failure(ex) =>
        println(s"Command execution failed: ${ex.getMessage}")
    }
  }
}


class GameOverState extends GameState {
  override def makeMove(controller: ControllerInterface, point: Point, x: Int, y: Int): Unit = {
    println("Game Over! You cannot make any more moves.")
  }
}

case class Controller(var field: FieldInterface, var feedbackField: FeedbackFieldInterface, var gui: Option[GUI] = None) extends ControllerInterface {
  private var gameState: GameStateInterface = new PlayState()
  private val receiver: ReceiverInterface = new Receiver(field)
  private val commandHistory: Stack[CommandInterface] = Stack()

  def setGui(newGui: GUI): Unit = {
    gui = Some(newGui)
  }

  def makeMove(point: Point, x: Int, y: Int): Unit = {
    gameState.makeMove(this, point, x, y)
    field = receiver.field // Update the field with the new field after executing the command
    notifyObservers()
  }

  def undoLastMove(): Unit = {
    if (commandHistory.nonEmpty) {
      val command = commandHistory.pop()
      val result = command.undo()
      result match {
        case Success(_) =>
          command match {
            case AddCommand(_, point, x, y) =>
              field = field.put(Point.valueFromSymbol(" "), x, y)
              feedbackField.updateFeedback(field, x, y)
            case RemoveCommand(_, x, y) =>
              val removedPoint = command.asInstanceOf[RemoveCommand].removedPoint.getOrElse(throw new IllegalStateException("Error: No point to add during undo."))
              field = field.put(Some(removedPoint), x, y)
              feedbackField.updateFeedback(field, x, y)
          }
          gui.foreach(_.update()) // update GUI after undoing the last move
          notifyObservers()
        case Failure(ex) =>
          println(s"Undo failed: ${ex.getMessage}")
      }
    } else {
      println("No move to undo.")
    }
  }

  override def setGameState(gameState: GameStateInterface): Unit = {
    this.gameState = gameState
    notifyObservers()
  }

  override def addToCommandHistory(command: CommandInterface): Unit = {
    commandHistory.push(command)
  }

  override def feedbackCell(row: Int, col: Int): Option[FeedbackInterface] = feedbackField.getFeedbackMatrix.cell(row, col)

  override def getGuesslength: Int = field.matrix.guesslength

  override def getPointslength: Int = field.matrix.pointslength

  override def pointCell(row: Int, col: Int): Option[Point] = field.getCell(row, col)

  override def toString: String = field.toString

  def getReceiver: ReceiverInterface = receiver

  def getGameState: GameStateInterface = gameState

  def feedbackfield: FeedbackFieldInterface = feedbackField
}

