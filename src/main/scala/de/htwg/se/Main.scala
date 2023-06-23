package de.htwg.se

import de.htwg.se.model.{Field, FeedbackField, Matrix, Point, PointFactory}
import de.htwg.se.aview.{TUI, StandardInput, GUI}
import de.htwg.se.controler.{Controller, AddCommand, RemoveCommand}

object Mastermind {
  def main(args: Array[String]): Unit = {
    println("Welcome to Mastermind!")
    val field = Field(Matrix(Vector.fill(10, 4)(Some(PointFactory.createPoint(" ")))))
    val feedbackField = FeedbackField(guesslength = 10)
    val controller = Controller(field, feedbackField) // Gui not passed

    val tui = new TUI(controller, new StandardInput())

    val threadTui = new Thread {
      override def run(): Unit = {
        tui.run()
      }
    }

    val threadGui = new Thread {
      override def run(): Unit = {
        val gui = new GUI(controller)
        gui.visible = true
      }
    }

    threadTui.start()
    threadGui.start()
  }
}
