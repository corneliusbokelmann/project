package de.htwg.se.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

/* class FeedbackFieldSpec extends AnyWordSpec {
  "A FeedbackField" when {
    "empty" should {
      val emptyField = FeedbackField(4)
      "be initially filled with Feedback.Nothing" in {
        val emptyMatrix = Matrix[Feedback](Vector.fill(4, 4)(Feedback.Nothing))
        emptyField.feedbackMatrix = emptyMatrix
        for (i <- 0 until 4; j <- 0 until 4) {
          emptyField.feedbackMatrix.cell(i, j) should be(Feedback.Nothing)
        }
      }
    }
  }
} */

class FeedbackSpec extends AnyWordSpec {
  "The Feedback trait" should {
    "be convertible to string" in {
      Feedback.Nothing.toString should be("Nothing")
      Feedback.ColorCorrect.toString should be("ColorCorrect")
      Feedback.PositionCorrect.toString should be("PositionCorrect")
    }
  }
}
