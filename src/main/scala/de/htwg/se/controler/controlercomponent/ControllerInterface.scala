package de.htwg.se.controler.controlercomponent

import de.htwg.se.model.modelcomponent.modelImpl.Point
import de.htwg.se.model.modelcomponent.{FieldInterface, FeedbackFieldInterface}
import de.htwg.se.util.Observable
import de.htwg.se.model.modelcomponent.FeedbackInterface

trait ControllerInterface extends Observable {
  def field: FieldInterface
  def feedbackfield: FeedbackFieldInterface
  def makeMove(point: Point, x: Int, y: Int): Unit
  def undoLastMove(): Unit
  def setGameState(gameState: GameStateInterface): Unit
  def toString: String
  def getReceiver: ReceiverInterface
  def getGameState: GameStateInterface
  def getGuesslength: Int
  def getPointslength: Int
  def pointCell(row: Int, col: Int): Option[Point]
  def feedbackCell(row: Int, col: Int): Option[FeedbackInterface]
  def addToCommandHistory(command: CommandInterface): Unit
}

trait ReceiverInterface {
  def field: FieldInterface
  def add(point: Point, x: Int, y: Int): Unit
  def remove(x: Int, y: Int): Option[Point]
  def get(x: Int, y: Int): Option[Point]
}

trait GameStateInterface {
  def makeMove(controller: ControllerInterface, point: Point, x: Int, y: Int): Unit
}

trait CommandInterface {
  def execute(): scala.util.Try[Unit]
  def undo(): scala.util.Try[Unit]
}
