package de.htwg.se.aview

import de.htwg.se.controler.controlercomponent.ControllerInterface
import de.htwg.se.model.modelcomponent.modelImpl.Point
import de.htwg.se.util.Observer

import scala.io.StdIn.readLine
import de.htwg.se.controler.controlercomponent.controlerImpl.AddCommand
import de.htwg.se.controler.controlercomponent.controlerImpl.Command
import de.htwg.se.controler.controlercomponent.controlerImpl.Controller
import de.htwg.se.controler.controlercomponent.controlerImpl.RemoveCommand
import de.htwg.se.controler.controlercomponent.CommandInterface


// Define event trait and case classes
trait TuiEvent
case class InputReceived(input: String) extends TuiEvent
case object UndoRequested extends TuiEvent
case object QuitRequested extends TuiEvent

trait InputStrategy {
  def handleInput(input: String, controller: ControllerInterface): Unit
}

class TUI(controller: Controller, inputStrategy: InputStrategy) extends Observer {
  controller.add(this)

  def run(): Unit = {
    println(controller.toString)
    var event: TuiEvent = InputReceived(readLine())
    while (event != QuitRequested) {
      event match {
        case InputReceived(input) =>
          if (input.toLowerCase == "u") {
            event = UndoRequested
          } else if (input.toLowerCase == "q") {
            event = QuitRequested
          } else {
            inputStrategy.handleInput(input, controller)
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
    println(controller.field)
    println(controller.feedbackField)
  }

  // Assuming you want to keep these functions
  def executeCommand(command: CommandInterface): Unit = {
    command.execute() match {
      case util.Success(_) =>
        controller.notifyObservers()
      case util.Failure(exception) =>
        println(s"Command execution failed: ${exception.getMessage}")
    }
  }

  def createAddCommand(point: Point, x: Int, y: Int): Unit = {
    val command = AddCommand(controller.getReceiver, point, x, y)
    executeCommand(command)
  }

  def createRemoveCommand(x: Int, y: Int): Unit = {
    val command = RemoveCommand(controller.getReceiver, x, y)
    executeCommand(command)
  }
}
