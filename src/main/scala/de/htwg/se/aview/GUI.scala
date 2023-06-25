package de.htwg.se.aview

import scala.swing._
import de.htwg.se.util.Observer
import de.htwg.se.model.modelcomponent.modelImpl.Point
import de.htwg.se.model.modelcomponent.modelImpl.Point.{WhitePoint, BlackPoint, GreenPoint, RedPoint}
import java.awt.Color
import javax.swing.BorderFactory
import de.htwg.se.controler.controlercomponent.ControllerInterface

class GUI(controller: ControllerInterface) extends MainFrame with Observer {
  title = "Mastermind"
  preferredSize = new Dimension(800, 600)

  // Create a panel for the playing field
  val gameBoardPanel = new GridPanel(controller.field.matrix.guesslength, controller.field.matrix.pointslength) {
    for {
      x <- 0 until controller.field.matrix.guesslength
      y <- 0 until controller.field.matrix.pointslength
    } {
      val label = new Label {
        text = controller.field.matrix.cell(x, y).toString
        font = new Font("Arial", 1, 24)
        opaque = true
        background = getColorForPoint(controller.field.matrix.cell(x, y))
        border = BorderFactory.createLineBorder(Color.black)
        preferredSize = new Dimension(60, 60)
        horizontalAlignment = Alignment.Center
        verticalAlignment = Alignment.Center
      }
      contents += label
    }
  }

  // Create a panel for the feedback field
  val feedbackFieldPanel = new GridPanel(controller.field.matrix.guesslength, 1) {
    for {
      x <- 0 until controller.field.matrix.guesslength
    } {
      val label = new Label {
        text = controller.feedbackfield.getFeedbackMatrix.cell(x, 0).toString
        font = new Font("Arial", 1, 24)
        opaque = true
        background = Color.lightGray
        border = BorderFactory.createLineBorder(Color.black)
        preferredSize = new Dimension(60, 60)
        horizontalAlignment = Alignment.Center
        verticalAlignment = Alignment.Center
      }
      contents += label
    }
  }

  // Combine the playing field and feedback field panels into one layout
  contents = new BorderPanel {
    layout(gameBoardPanel) = BorderPanel.Position.Center
    layout(feedbackFieldPanel) = BorderPanel.Position.East
  }

  // Update the GUI when the game state changes
  controller.add(this)

  override def update(): Unit = {
    for {
      x <- 0 until controller.field.matrix.guesslength
      y <- 0 until controller.field.matrix.pointslength
    } {
      val label = gameBoardPanel.contents(y * controller.field.matrix.guesslength + x).asInstanceOf[Label]
      label.text = controller.field.matrix.cell(x, y).toString
      label.background = getColorForPoint(controller.field.matrix.cell(x, y))
    }

    // Update the feedback field labels
    for {
      x <- 0 until controller.field.matrix.guesslength
      label = feedbackFieldPanel.contents(x).asInstanceOf[Label]
    } {
      label.text = controller.feedbackfield.getFeedbackMatrix.cell(x, 0).toString
    }
  }

  private def getColorForPoint(point: Option[Point]): Color = {
    point match {
      case Some(WhitePoint) => Color.white
      case Some(BlackPoint) => Color.black
      case Some(GreenPoint) => Color.green
      case Some(RedPoint)   => Color.red
      case _                => Color.lightGray
    }
  }

  visible = true
}
