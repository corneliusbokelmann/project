package de.htwg.se.controler

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.controler.controlercomponent.controlerImpl._
import de.htwg.se.model.modelcomponent.modelImpl._
import de.htwg.se.model.modelcomponent.FeedbackInterface
import com.google.inject.Guice
import de.htwg.se.MastermindModule
import scala.util.{Success, Try, Failure}

class RemoveCommandSpec extends AnyWordSpec with Matchers {
  "RemoveCommand " when {
    "filled" should {
      val injector = Guice.createInjector(new MastermindModule)
      val field = Matrix[Point](Vector.fill(2, 3)(Point.RedPoint))
      val feedbackMatrix = Matrix[FeedbackInterface](Vector.fill(2, 3)(FeedbackInterface.Nothing))
      val feedbackField = FeedbackField(feedbackMatrix, 2)
      val fieldInterface = Field(field, feedbackField)
      val receiver = new Receiver(fieldInterface)
      val removeCommand = new RemoveCommand(receiver, 0, 0)
      "execute and undo" in {
        removeCommand.execute() should be(Success(()))
        receiver.get(0, 0) should be(Some(Point.EmptyPoint))
        removeCommand.undo() should be(Success(()))
        receiver.get(0, 0) should be(Some(Point.RedPoint))
      }
    }
    "empty" should {
      val injector = Guice.createInjector(new MastermindModule)
      val field = Matrix[Point](Vector.fill(2, 3)(Point.EmptyPoint))
      val feedbackMatrix = Matrix[FeedbackInterface](Vector.fill(2, 3)(FeedbackInterface.Nothing))
      val feedbackField = FeedbackField(feedbackMatrix, 2)
      val fieldInterface = Field(field, feedbackField)
      val receiver = new Receiver(fieldInterface)
      val removeCommand = new RemoveCommand(receiver, 0, 0)
      "execute and undo" in {
        removeCommand.execute() should be(Failure(new IllegalArgumentException))
        removeCommand.undo() should be(Failure(new IllegalStateException))
      }
    }
  }
}
