package de.htwg.se

import de.htwg.se.model.modelcomponent.modelImpl.Point
import de.htwg.se.model.modelcomponent.modelImpl.Matrix
import de.htwg.se.aview.{TUI, GUI}
import de.htwg.se.model.modelcomponent.modelImpl.Field
import de.htwg.se.model.modelcomponent.modelImpl.FeedbackField
import de.htwg.se.controler.controlercomponent.controlerImpl.Controller
import de.htwg.se.controler.controlercomponent.controlerImpl.StandardInput
import de.htwg.se.model.modelcomponent.FeedbackInterface

object Mastermind {
  def main(args: Array[String]): Unit = {
    println("Welcome to Mastermind!")

    val fieldMatrix = Matrix(Vector.fill(10, 4)(Point.EmptyPoint))
    val feedbackMatrix = Matrix(Vector.fill(10, 4)(FeedbackInterface.Nothing)) // Adjust sizes to match your requirements
    val feedbackField = FeedbackField(feedbackMatrix, 4)
    val field = Field(fieldMatrix, feedbackField)

    val controller = Controller(field, feedbackField)

    val tui = TUI(controller, new StandardInput())
    val gui = GUI(controller)

    // Start the TUI
    tui.run()

    // Show the GUI
    gui.visible = true
  }
}

