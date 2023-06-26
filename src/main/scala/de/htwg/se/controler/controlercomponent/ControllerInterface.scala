package de.htwg.se.controler.controlercomponent

import de.htwg.se.model.modelcomponent.modelImpl.Point
import de.htwg.se.model.modelcomponent.{FieldInterface, FeedbackFieldInterface}
import de.htwg.se.util.Observable
import de.htwg.se.model.modelcomponent.FeedbackInterface
import scala.util.{Failure, Success, Try}
import de.htwg.se.aview.GUI

trait ControllerInterface extends Observable {
  def setGui(newGui: GUI): Unit
  def field: FieldInterface
  def feedbackField: FeedbackFieldInterface
  def makeMove(point: (Point, Int, Int)): Unit
  def makeMove(points: List[(Point, Int, Int)]): Unit
  def undoLastMove(): Unit
  def setGameState(gameState: GameStateInterface): Unit
  def getCurrentLine: Int
  def setCurrentLine(line: Int): Unit
  override def toString: String
  def getReceiver: ReceiverInterface
  def getGameState: GameStateInterface
  def getGuessLength: Int
  def getPointslength: Int
  def pointCell(row: Int, col: Int): Option[Point]
  def feedbackCell(row: Int, col: Int): Option[FeedbackInterface]
  def addToCommandHistory(command: CommandInterface): Unit
  def processInput(input: String): Unit

  // Additional methods
  def getMaxGuesses: Int
  def getField: FieldInterface
  def getFeedbackField: FeedbackFieldInterface
  def getCommandHistory: Seq[CommandInterface]

  // New members
  def setFeedbackField(feedbackField: FeedbackFieldInterface): Unit
  def calculateFeedback(guess: List[(Point, Int, Int)]): List[FeedbackInterface]
}


trait InputStrategy {
  def handleInput(input: String, controller: ControllerInterface): Unit
}

trait ReceiverInterface {
  def field: FieldInterface
  def add(point: Point, x: Int, y: Int): Try[Unit]
  def remove(x: Int, y: Int): Try[Point]
  def get(x: Int, y: Int): Option[Point]
}

trait GameStateInterface {
  def makeMove(controller: ControllerInterface, point: (Point, Int, Int)): Unit
}

trait CommandInterface {
  def execute(): Try[Unit]
  def undo(): Try[Unit]
}