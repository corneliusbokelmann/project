package de.htwg.se

import de.htwg.se.model.modelcomponent._
import de.htwg.se.aview.{TUI, StandardInput, GUI}
import de.htwg.se.controler.controlercomponent._



object Mastermind {
  def main(args: Array[String]): Unit = {
    println("Welcome to Mastermind!")

    val field = FieldInterface(MatrixInterface(Vector.fill(10, 4)(Some(PointFactoryInterface.createPoint(" ")))))
    val feedbackField = FeedbackFieldInterface(guesslength = 10)
    val controller = ControllerInterface(field, feedbackField)

    val tui = TUI(controller, new StandardInput())
    val gui = GUI(controller)

    // Start the TUI
    tui.run()

    // Show the GUI
    gui.visible = true
  }
}
