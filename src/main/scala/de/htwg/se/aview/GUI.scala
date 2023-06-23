package de.htwg.se.aview

import scala.swing._
import de.htwg.se.controler.Controller
import de.htwg.se.util.Observer

class GUI(controller: Controller) extends MainFrame with Observer {
  title = "Mastermind"
  preferredSize = new Dimension(800, 600)

  // Create a label for each position in the game board
  val gameBoard = new GridPanel(controller.field.matrix.guesslength, controller.field.matrix.pointslength) {
    for {
      x <- 0 until controller.field.matrix.guesslength
      y <- 0 until controller.field.matrix.pointslength
    } {
      contents += new Label {
        text = controller.field.matrix.cell(x, y).toString
        font = new Font("Arial", 1, 24)
      }
    }
  }

  // Combine the labels into one layout
  contents = new GridPanel(1, 1) {
    contents += gameBoard
  }

  // Update the GUI when the game state changes
  controller.add(this)

  override def update(): Unit = {
    for {
      x <- 0 until controller.field.matrix.guesslength
      y <- 0 until controller.field.matrix.pointslength
    } {
      val label = gameBoard.contents(y * controller.field.matrix.guesslength + x).asInstanceOf[Label]
      label.text = controller.field.matrix.cell(x, y).toString
    }
  }

  visible = true
}
