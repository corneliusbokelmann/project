package de.htwg.se

import de.htwg.se.model.modelcomponent.modelImpl.Point
import de.htwg.se.model.modelcomponent.modelImpl.Matrix
import de.htwg.se.aview.{TUI, StandardInput, GUI}
import de.htwg.se.model.modelcomponent.modelImpl.Field
import de.htwg.se.model.modelcomponent.modelImpl.FeedbackField
import de.htwg.se.controler.controlercomponent.controlerImpl.Controller

object Mastermind {
  def main(args: Array[String]): Unit = {
    println("Welcome to Mastermind!")

    val field = Field(Matrix(Vector.fill(10, 4)(Point.EmptyPoint)))
    val feedbackField = FeedbackField(guesslength = 10)
    val controller = Controller(field, feedbackField)

    val tui = TUI(controller, new StandardInput())
    val gui = GUI(controller)

    // Start the TUI
    tui.run()

    // Show the GUI
    gui.visible = true
  }
}
