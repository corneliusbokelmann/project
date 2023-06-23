package de.htwg.se

import de.htwg.se.model.{Field, FeedbackField, Matrix, Point, PointFactory}
import de.htwg.se.aview.{TUI, StandardInput, GUI}
import de.htwg.se.controler.{Controller, AddCommand, RemoveCommand}


object Mastermind {
  def main(args: Array[String]): Unit = {
    println("Welcome to Mastermind!")

    val field = Field(Matrix(Vector.fill(10, 4)(Some(PointFactory.createPoint(" ")))))
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
