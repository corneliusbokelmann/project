package de.htwg.se.aview

import de.htwg.se.controler.{Controller, AddCommand, RemoveCommand, Command}
import de.htwg.se.model.Point
import de.htwg.se.util.Observer

import scala.io.StdIn.readLine
import scala.util.{Failure, Success, Try}

trait InputStrategy {
  def handleInput(input: String, controller: Controller): Unit
}

class TUI(controller: Controller, inputStrategy: InputStrategy) extends Observer {
  private var lastCommand: Option[Command] = None

  controller.add(this)

  def run(): Unit = {
    println(controller.toString)
    var input: String = ""
    while (input != "q") {
      getInputAndPrintLoop()
      input = readLine()
      inputStrategy.handleInput(input, controller)
    }
  }

  override def update(): Unit = {
    println(controller.field)
    println(controller.feedbackField)
  }


  private def getInputAndPrintLoop(): Unit = {
    println("Enter your move (<Color><x><y>, e.g., R02, q to quit, u to undo):")
    val input = readLine()
    if (input.toLowerCase() == "u") {
      undoLastCommand()
    } else {
      inputStrategy.handleInput(input, controller)
    }
  }

  private def undoLastCommand(): Unit = {
    lastCommand match {
      case Some(command) =>
        command.undo() match {
          case Success(_) =>
            lastCommand = None
            controller.notifyObservers()
          case Failure(exception) =>
            println(s"Undo failed: ${exception.getMessage}")
        }
      case None =>
        println("No command to undo.")
    }
  }


  private def executeCommand(command: Command): Unit = {
    val result = Try(command.execute())
    result match {
      case Success(_) =>
        lastCommand = Some(command)
        controller.notifyObservers()
      case Failure(exception) =>
        println(s"Command execution failed: ${exception.getMessage}")
    }
  }


  def createAddCommand(point: Point, x: Int, y: Int): Unit = {
    val receiver = controller.getReceiver
    receiver.get(x, y) match {
      case Some(_) =>
        receiver.remove(x, y)
        receiver.add(point, x, y)
        controller.notifyObservers()
      case None =>
        val command = AddCommand(receiver, point, x, y)
        executeCommand(command)
    }
  }


  def createRemoveCommand(x: Int, y: Int): Unit = {
    val command = RemoveCommand(controller.getReceiver, x, y)
    executeCommand(command)
  }
}
