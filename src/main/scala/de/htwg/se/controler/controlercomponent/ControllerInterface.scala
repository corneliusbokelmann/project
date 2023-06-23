package de.htwg.se.controler.controlercomponent

import de.htwg.se.model.modelcomponent.{FieldInterface, Point, PointFactoryInterface}
import de.htwg.se.util.Observable

trait ControllerInterface extends Observable {
  def makeMove(point: Point, x: Int, y: Int): Unit
  def undoLastMove(): Unit
  def setGameState(gameState: GameStateInterface): Unit
  def toString: String
  def getReceiver: ReceiverInterface
  def getGameState: GameStateInterface
  def addToCommandHistory(command: CommandInterface): Unit
}

trait ReceiverInterface {
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
