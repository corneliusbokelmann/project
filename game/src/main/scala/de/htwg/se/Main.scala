package de.htwg.se

import de.htwg.se.aview.{TUI, StandardInput}
import de.htwg.se.controler.Controller
import de.htwg.se.model.{Field, FeedbackField, Matrix, Point, PointFactory}
import de.htwg.se.util.Observer

object Mastermind {
  def main(args: Array[String]): Unit = {
    println("Welcome to Mastermind!")
    val field = Field(Matrix(Vector.fill(10, 4)(PointFactory.createPoint(" "))))
    val feedbackField = FeedbackField(guesslength = 10)
    val controller = Controller(field, feedbackField)
    val tui = new TUI(controller, new StandardInput())
    tui.run()
  }
}
