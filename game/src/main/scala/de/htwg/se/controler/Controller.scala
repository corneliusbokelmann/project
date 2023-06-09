package de.htwg.se.controler

import de.htwg.se.model.{Field, FeedbackField, Point, PointFactory}
import de.htwg.se.util.{Observable, Observer}

import scala.collection.mutable.Stack
import scala.util.{Failure, Success, Try}

trait GameState {
  def makeMove(controller: Controller, point: Point, x: Int, y: Int): Unit
}

class PlayState extends GameState {
  override def makeMove(controller: Controller, point: Point, x: Int, y: Int): Unit = {
    val receiver = controller.getReceiver
    val command = AddCommand(receiver, point, x, y)
    executeCommand(controller, command)
    controller.feedbackField.updateFeedback(controller.field, x, y)
    controller.notifyObservers()
  }

  private def executeCommand(controller: Controller, command: Command): Unit = {
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
  override def makeMove(controller: Controller, point: Point, x: Int, y: Int): Unit = {
    println("Game Over! You cannot make any more moves.")
  }
}

case class Controller(var field: Field, var feedbackField: FeedbackField) extends Observable {
  private var gameState: GameState = new PlayState()
  private val receiver: Receiver = new Receiver(field)
  private val commandHistory: Stack[Command] = Stack()

  def makeMove(point: Point, x: Int, y: Int): Unit = {
    gameState.makeMove(this, point, x, y)
    field = receiver.field // Update the field with the new field after executing the command
  }


  def undoLastMove(): Unit = {
    if (commandHistory.nonEmpty) {
      val command = commandHistory.pop()
      val result = command.undo()
      result match {
        case Success(_) =>
          command match {
            case AddCommand(_, point, x, y) =>
              field = field.put(PointFactory.createPoint(" "), x, y)
              feedbackField.updateFeedback(field, x, y)
            case RemoveCommand(_, x, y) =>
              val removedPoint = command.asInstanceOf[RemoveCommand].removedPoint.getOrElse(throw new IllegalStateException("Error: No point to add during undo."))
              field = field.put(removedPoint, x, y)
              feedbackField.updateFeedback(field, x, y)
          }
          notifyObservers()
        case Failure(ex) =>
          println(s"Undo failed: ${ex.getMessage}")
      }
    } else {
      println("No move to undo.")
    }
  }





  def setGameState(gameState: GameState): Unit = {
    this.gameState = gameState
  }

  override def toString: String = field.toString

  def getReceiver: Receiver = receiver

  def addToCommandHistory(command: Command): Unit = {
    commandHistory.push(command)
  }
}
