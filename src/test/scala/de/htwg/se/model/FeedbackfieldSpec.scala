package de.htwg.se.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.model.modelcomponent.modelImpl._
import de.htwg.se.controler.controlercomponent.controlerImpl._
import de.htwg.se.model.modelcomponent.FeedbackInterface
import com.google.inject.Guice
import de.htwg.se.MastermindModule

class FeedbackFieldSpec extends AnyWordSpec with Matchers {
  "A FeedbackField" when {
    "empty" should {
      val injector = Guice.createInjector(new MastermindModule)
      val emptyField = FeedbackField(Matrix[FeedbackInterface](Vector.fill(4, 4)(FeedbackInterface.Nothing)), 4)
      "be initially filled with Feedback.Nothing" in {
        val emptyMatrix = Matrix[FeedbackInterface](Vector.fill(4, 4)(FeedbackInterface.Nothing))
        for (i <- 0 until 4; j <- 0 until 4) {
          emptyField.getFeedbackMatrix.cell(i, j) should be(FeedbackInterface.Nothing)
        }
      }
    }
  }
} 

class FeedbackSpec extends AnyWordSpec with Matchers {
  "The Feedback trait" should {
    "be convertible to string" in {
      FeedbackInterface.Nothing.toString should be("Nothing")
      FeedbackInterface.ColorCorrect.toString should be("ColorCorrect")
      FeedbackInterface.PositionCorrect.toString should be("PositionCorrect")
    }
  }
}
