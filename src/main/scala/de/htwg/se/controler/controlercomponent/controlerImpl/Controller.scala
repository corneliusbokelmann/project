package de.htwg.se.controler.controlercomponent.controlerImpl

import de.htwg.se.controler.controlercomponent.{ControllerInterface, GameStateInterface, ReceiverInterface, CommandInterface, InputStrategy}
import de.htwg.se.model.modelcomponent.modelImpl.{Field, Matrix, Point, FeedbackField}
import de.htwg.se.model.modelcomponent.FieldInterface
import de.htwg.se.model.modelcomponent.FeedbackFieldInterface
import de.htwg.se.model.modelcomponent.FeedbackInterface
import de.htwg.se.util.{Observable, Observer}
import de.htwg.se.aview.GUI

import scala.collection.mutable.Stack
import scala.util.{Failure, Success, Try}

trait GameState extends GameStateInterface {
  def makeMove(controller: ControllerInterface, point: (Point, Int, Int)): Unit
}

class PlayState extends GameState {
  override def makeMove(controller: ControllerInterface, point: (Point, Int, Int)): Unit = {
    val receiver = controller.getReceiver
    val (p, x, y) = point
    val command = AddCommand(receiver, p, x, y)
    executeCommand(controller, command)

    if (isValidPosition(x, y, controller)) {
      val feedbackList = controller.calculateFeedback(List(point)) // Wrap the point into a list
      val feedback = feedbackList.headOption.getOrElse(FeedbackInterface.Nothing) // Get the first feedback item, or use default if the list is empty

      if (isValidPosition(x, y, controller)) {
        val newFeedbackField = controller.getFeedbackField.put(feedback, x, y)
        controller.setFeedbackField(newFeedbackField)
      }
    }

    controller.notifyObservers()
  }



  private def isValidPosition(x: Int, y: Int, controller: ControllerInterface): Boolean = {
    x >= 0 && x < controller.getMaxGuesses && y >= 0 && y < controller.getGuessLength
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
  override def makeMove(controller: ControllerInterface, point: (Point, Int, Int)): Unit = {
    println("Game Over! You cannot make any more moves.")
  }
}

class GameWonState extends GameState {
  override def makeMove(controller: ControllerInterface, point: (Point, Int, Int)): Unit = {
    println("Game has already been won! No more moves allowed.")
  }
}

case class Controller(
  var field: FieldInterface = createField(10, 4), // Adjust the parameters according to your desired grid size
  var feedbackField: FeedbackFieldInterface = createFeedbackField(10, 4), // Adjust the parameters according to your desired grid size
  var gui: Option[GUI] = None
) extends ControllerInterface {
  private var gameState: GameStateInterface = new PlayState()
  private val receiver: ReceiverInterface = new Receiver(field)
  private val commandHistory: Stack[CommandInterface] = Stack()
  private var currentLine: Int = 0
  private val guessLength: Int = 4
  var solution: Vector[Point] = generateRandomCombination(guessLength)

  def setGui(newGui: GUI): Unit = {
    gui = Some(newGui)
  }

  def makeMove(point: (Point, Int, Int)): Unit = {
    gameState.makeMove(this, point)
    field = receiver.field // Update the field with the new field after executing the command(s)
    notifyObservers()
  }

  def makeMove(points: List[(Point, Int, Int)]): Unit = {
    points.foreach(point => {
      gameState.makeMove(this, point)
      field = receiver.field // Update the field with the new field after executing the command(s)
    })
    // Calculate feedback for the entire guess here
    val feedback = calculateFeedback(points)
    // Then, set the feedback for each point in the feedbackField
    feedback.zipWithIndex.foreach { case (fb, index) =>
      val (_, x, y) = points(index)
      val newFeedbackField = feedbackField.put(fb, x, y)
      setFeedbackField(newFeedbackField)
    }
    if (feedback.forall(_ == FeedbackInterface.PositionCorrect)) {
      setGameState(new GameWonState())
    }
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
              field = field.put(Some(Point.EmptyPoint), x, y)
              feedbackField = feedbackField.put(FeedbackInterface.Nothing, x, y)
            case RemoveCommand(_, x, y) =>
              val removedPoint =
                command.asInstanceOf[RemoveCommand].removedPoint.getOrElse(
                  throw new IllegalStateException("Error: No point to add during undo.")
                )
              field = field.put(Some(removedPoint), x, y)
              feedbackField = feedbackField.put(FeedbackInterface.Nothing, x, y)
          }
          gui.foreach(_.update()) // Update GUI after undoing the last move
          notifyObservers()
        case Failure(ex) =>
          println(s"Undo failed: ${ex.getMessage}")
      }
    } else {
      println("No move to undo.")
    }
  }

  def setFeedbackField(feedbackField: FeedbackFieldInterface): Unit = {
    this.feedbackField = feedbackField
    notifyObservers()
  }
  override def setGameState(gameState: GameStateInterface): Unit = {
    this.gameState = gameState
    notifyObservers()
  }

  override def addToCommandHistory(command: CommandInterface): Unit = {
    commandHistory.push(command)
  }

  override def feedbackCell(row: Int, col: Int): Option[FeedbackInterface] =
    feedbackField.getFeedbackMatrix.cell(row, col)

  override def getGuessLength: Int = guessLength

  override def getPointslength: Int = field.matrix.rows.headOption.map(_.length).getOrElse(0)

  override def pointCell(row: Int, col: Int): Option[Point] = field.matrix.cell(row, col)

  override def toString: String = field.toString

  def getReceiver: ReceiverInterface = receiver

  def getGameState: GameStateInterface = gameState

  def getCurrentLine: Int = currentLine

  def setCurrentLine(line: Int): Unit = {
    currentLine = line
  }

  private val inputStrategy: InputStrategy = new StandardInput()

  def processInput(input: String): Unit = {
    inputStrategy.handleInput(input, this)
  }

  def getMaxGuesses: Int = 10

  def getField: FieldInterface = field

  def getFeedbackField: FeedbackFieldInterface = feedbackField

  def getCommandHistory: Seq[CommandInterface] = commandHistory.toSeq

  def calculateFeedback(guess: List[(Point, Int, Int)]): List[FeedbackInterface] = {
    // Convert guess to only a list of Points, remove the position information
    val guessPoints: List[Point] = guess.map(_._1)

    // We need to calculate feedback for each point in the guess
    val feedback = guessPoints.zipWithIndex.map { case (p, i) =>
      if (solution(i) == p) {
        FeedbackInterface.PositionCorrect
      } else if (solution.contains(p)) {
        FeedbackInterface.ColorCorrect
      } else {
        FeedbackInterface.Nothing
      }
    }
    feedback
  }




  private def generateRandomCombination(size: Int): Vector[Point] = {
    val colors = Vector(Point.GreenPoint, Point.RedPoint, Point.BluePoint, Point.YellowPoint, Point.OrangePoint, Point.PinkPoint, Point.PurplePoint, Point.BrownPoint)
    scala.util.Random.shuffle(colors).take(size)
  }
}

object Controller {
  private def createField(guessLength: Int, maxLength: Int): FieldInterface = {
    val matrix = Matrix[Point](Vector.fill(guessLength, maxLength)(Point.EmptyPoint))
    val feedbackMatrix = Matrix[FeedbackInterface](Vector.fill(guessLength, maxLength)(FeedbackInterface.Nothing))
    val feedbackField = FeedbackField(feedbackMatrix, guessLength)
    Field(matrix, feedbackField)
  }

  private def createFeedbackField(guessLength: Int, maxLength: Int): FeedbackFieldInterface = {
    val matrix = Matrix[FeedbackInterface](Vector.fill(guessLength, maxLength)(FeedbackInterface.Nothing))
    FeedbackField(matrix, guessLength)
  }
}
