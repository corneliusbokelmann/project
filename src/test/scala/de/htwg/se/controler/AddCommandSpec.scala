package de.htwg.se.controler

import de.htwg.se.controler.controlercomponent.controlerImpl.{AddCommand, Receiver}
import de.htwg.se.model.modelcomponent.FeedbackInterface
import de.htwg.se.model.modelcomponent.modelImpl.{Field, Point, Matrix, FeedbackField}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

import scala.util.{Failure, Success, Try}

class AddCommandSpec extends AnyWordSpec {
  "AddCommand " should {
    val matrix = new Matrix[Point](2, 3, Point.EmptyPoint)
    val feedbackMatrix = new Matrix[FeedbackInterface](2, 3, FeedbackInterface.Nothing)
    val feedbackField = new FeedbackField(feedbackMatrix, 3)
    val field = new Field(matrix, feedbackField)
    val receiver: Receiver = new Receiver(field)
    val addCommand = new AddCommand(receiver, Point.RedPoint, 0, 0)
    "execute and undo" in {
      addCommand.execute() should be(Success(()))
      addCommand.undo() should be(Success(()))
    }
    "fail to overwrite a non-empty point" in {
      receiver.add(Point.RedPoint, 0, 0)
      val failAddCommand = new AddCommand(receiver, Point.BluePoint, 0, 0)
      failAddCommand.execute() should be(Failure(new IllegalStateException("Position already has a point.")))
    }
  }
}
