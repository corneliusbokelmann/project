package de.htwg.se.controler

import de.htwg.se.controler.controlercomponent.controlerImpl._
import de.htwg.se.model.modelcomponent.modelImpl._
import de.htwg.se.model.modelcomponent.FieldInterface
import de.htwg.se.model.modelcomponent.FeedbackInterface
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import com.google.inject.Guice
import de.htwg.se.MastermindModule
import scala.util.{Failure, Success, Try}

class ControllerSpec extends AnyWordSpec with Matchers {
  "A Controller" when {
    "undo a point" should {
      val injector = Guice.createInjector(new MastermindModule)
      val controller = injector.getInstance(classOf[Controller])
      val field = Matrix[Point](Vector.fill(4, 6)(Point.EmptyPoint))
      val feedbackMatrix = Matrix[FeedbackInterface](Vector.fill(4, 6)(FeedbackInterface.Nothing))
      val feedbackField = FeedbackField(feedbackMatrix, 4)
      val emptyField = Field(field, feedbackField)
      val point = (Point.RedPoint, 0, 0)
      controller.makeMove(point)
      controller.undoLastMove()
      "have the last game state" in {
        controller.getField should be(emptyField)
      }
    }
    "set a gamestate" should {
      val injector = Guice.createInjector(new MastermindModule)
      val controller = injector.getInstance(classOf[Controller])
      val gameState = new PlayState()
      controller.setGameState(gameState)
      "have the given gamestate" in {
        controller.getGameState should be(gameState)
      }
    }
  }
}
