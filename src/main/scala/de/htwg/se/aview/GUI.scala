package de.htwg.se.aview

import javax.inject.Inject

import scala.swing._
import de.htwg.se.util.Observer
import de.htwg.se.model.modelcomponent.modelImpl.Point
import de.htwg.se.model.modelcomponent.modelImpl.Point._
import de.htwg.se.model.modelcomponent.FeedbackInterface
import java.awt.Color
import javax.swing.BorderFactory
import de.htwg.se.controler.controlercomponent.ControllerInterface
import de.htwg.se.controler.controlercomponent.controlerImpl.{GameWonState, GameOverState}


class GUI @Inject() (controller: ControllerInterface) extends MainFrame with Observer {
  title = "Mastermind"
  preferredSize = new Dimension(800, 600)

  val playingFieldPanel = new GridPanel(controller.getMaxGuesses, controller.getGuessLength) {
    for (x <- 0 until controller.getMaxGuesses) {
      for (y <- 0 until controller.getGuessLength) {
        val label = createPointLabel(x, y)
        contents += label
      }
    }
  }

  val feedbackPanel = new GridPanel(controller.getMaxGuesses, controller.getGuessLength) {
    for (x <- 0 until controller.getMaxGuesses) {
      for (y <- 0 until controller.getGuessLength) {
        val label = createFeedbackLabel(x, y)
        contents += label
      }
    }
  }

  // Create input panel for colors and undo
  val inputPanel = new FlowPanel {
    val colors = List("R", "G", "B", "Y", "O", "P", "C", "M") // Modify with your available colorscase WhitePoint extends Point("W")

    colors.zipWithIndex.foreach { case (color, index) =>
      contents += new Button(color) {
        reactions += {
          case event.ButtonClicked(_) =>
            controller.processInput(color)
        }
      }
    }

    contents += new Button("Undo") {
      reactions += {
        case event.ButtonClicked(_) =>
          controller.undoLastMove()
      }
    }

    contents += new Button("Quit") {
      reactions += {
        case event.ButtonClicked(_) =>
          closeOperation()
      }
    }
  }


  contents = new BorderPanel {
    layout(playingFieldPanel) = BorderPanel.Position.Center
    layout(feedbackPanel) = BorderPanel.Position.East
    layout(inputPanel) = BorderPanel.Position.South
  }

  controller.add(this)

  override def update(): Unit = {
  val gameState = controller.getGameState

  
  if (gameState.isInstanceOf[GameOverState]) {
    Dialog.showMessage(
      contents.head,
      "Sorry, you've lost. Try again!",
      title = "Game Over"
    )
  } else {
    val maxGuesses = controller.getMaxGuesses
    val guessLength = controller.getGuessLength

    for (x <- 0 until maxGuesses) {
      for (y <- 0 until guessLength) {
        val colorLabel = playingFieldPanel.contents(x * guessLength + y).asInstanceOf[Label]
        val feedbackLabel = feedbackPanel.contents(x * guessLength + y).asInstanceOf[Label]

        colorLabel.text = controller.pointCell(x, y).toString
        colorLabel.background = getColorForPoint(controller.pointCell(x, y))

        feedbackLabel.text = controller.feedbackCell(x, y).toString
        feedbackLabel.background = getColorForFeedback(controller.feedbackCell(x, y))
      }
    }
    playingFieldPanel.revalidate()
    feedbackPanel.revalidate()
  }
  if (gameState.isInstanceOf[GameWonState]) {
    Dialog.showMessage(
      contents.head,
      "Congratulations, you've won!",
      title = "Game Over"
    )
  }
}



  private def createPointLabel(row: Int, col: Int): Label = {
    new Label {
      text = controller.pointCell(col, row).toString // Swap row and col
      font = new Font("Arial", 1, 24)
      opaque = true
      background = getColorForPoint(controller.pointCell(col, row)) // Swap row and col
      border = BorderFactory.createLineBorder(Color.black)
      preferredSize = new Dimension(60, 60)
      horizontalAlignment = Alignment.Center
      verticalAlignment = Alignment.Center
    }
  }

  private def createFeedbackLabel(row: Int, col: Int): Label = {
    new Label {
      text = controller.feedbackCell(col, row).toString // Swap row and col
      font = new Font("Arial", 1, 24)
      opaque = true
      background = getColorForFeedback(controller.feedbackCell(col, row)) // Swap row and col
      border = BorderFactory.createLineBorder(Color.black)
      preferredSize = new Dimension(60, 60)
      horizontalAlignment = Alignment.Center
      verticalAlignment = Alignment.Center
    }
  }

  private def getColorForPoint(point: Option[Point]): Color = {
    point match {
      case Some(RedPoint)    => Color.red
      case Some(GreenPoint)  => Color.green
      case Some(BluePoint)   => Color.blue
      case Some(YellowPoint) => Color.yellow
      case Some(OrangePoint) => Color.orange
      case Some(PinkPoint)   => Color.pink
      case Some(MagentaPoint) => Color.magenta
      case Some(CyanPoint)   => Color.cyan
      case _                 => Color.lightGray
    }
  }

  private def getColorForFeedback(feedback: Option[FeedbackInterface]): Color = {
    feedback match {
      case Some(FeedbackInterface.PositionCorrect) => Color.black
      case Some(FeedbackInterface.ColorCorrect)    => Color.white
      case _                                       => Color.lightGray
    }
  }

  visible = true
}
