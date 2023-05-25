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
  }

  def undoLastMove(): Unit = {
    if (commandHistory.nonEmpty) {
      val command = commandHistory.pop()
      val result = command.undo()
      result match {
        case Success(_) =>
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

class Receiver(var field: Field) {
  def add(point: Point, x: Int, y: Int): Unit = {
    field = field.put(point, x, y)
  }

  def remove(x: Int, y: Int): Option[Point] = {
    val point = field.matrix.cell(x, y)
    field = field.put(PointFactory.createPoint(" "), x, y)
    Some(point)
  }

  def get(x: Int, y: Int): Option[Point] = {
    field.matrix.cell(x, y) match {
      case p if p == PointFactory.createPoint(" ") => None
      case point => Some(point)
    }
  }
}

case class AddCommand(receiver: Receiver, point: Point, x: Int, y: Int) extends Command {
  override def execute(): Try[Unit] = Try(receiver.add(point, x, y))
  override def undo(): Try[Unit] = Try(receiver.remove(x, y))
}

case class RemoveCommand(receiver: Receiver, x: Int, y: Int) extends Command {
  private var removedPoint: Option[Point] = None

  override def execute(): Try[Unit] = {
    receiver.get(x, y) match {
      case Some(point) =>
        removedPoint = Some(point)
        Try(receiver.remove(x, y))
      case None => Failure(new IllegalStateException("No element at the specified position."))
    }
  }

  override def undo(): Try[Unit] = {
    removedPoint match {
      case Some(point) => Try(receiver.add(point, x, y))
      case None => Failure(new IllegalStateException("No element was previously removed."))
    }
  }
}
