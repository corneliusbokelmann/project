package de.htwg.se.controler

import de.htwg.se.controler.controlercomponent.controlerImpl._
import de.htwg.se.model.modelcomponent.modelImpl._
import de.htwg.se.model.modelcomponent.FeedbackInterface
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import com.google.inject.Guice
import de.htwg.se.MastermindModule
import scala.util.{Failure, Success, Try}

class ReceiverSpec extends AnyWordSpec with Matchers {
  "The Receiver in Mastermind game" when {
    "empty" should {
      val injector = Guice.createInjector(new MastermindModule)
      val field = Matrix[Point](Vector.fill(2, 3)(Point.EmptyPoint))
      val feedbackMatrix = Matrix[FeedbackInterface](Vector.fill(2, 3)(FeedbackInterface.Nothing))
      val feedbackField = FeedbackField(feedbackMatrix, 2)
      val fieldInterface = Field(field, feedbackField)
      val receiver = new Receiver(fieldInterface)
      "add a point" in {
        receiver.add(Point.RedPoint, 0, 0) should be(Success(()))
        receiver.get(0, 0) should be(Some(Point.RedPoint))
      }
    }
    "filled" should {
      val injector = Guice.createInjector(new MastermindModule)
      val field = Matrix[Point](Vector.fill(2, 3)(Point.RedPoint))
      val feedbackMatrix = Matrix[FeedbackInterface](Vector.fill(2, 3)(FeedbackInterface.Nothing))
      val feedbackField = FeedbackField(feedbackMatrix, 2)
      val fieldInterface = Field(field, feedbackField)
      val receiver = new Receiver(fieldInterface)
      "remove a point" in {
        receiver.remove(0, 0) should be(Success(Point.RedPoint))
        receiver.get(0, 0) should be(Some(Point.EmptyPoint))
      }
    }
  }
}
