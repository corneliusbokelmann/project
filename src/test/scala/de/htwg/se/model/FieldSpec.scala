package de.htwg.se.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.model.modelcomponent.modelImpl._
import de.htwg.se.model.modelcomponent.FeedbackInterface

class FieldSpec extends AnyWordSpec with Matchers {
    "A Field in Mastermind game" when {
        "empty" should {
            val emptyMatrix = Matrix[Point](Vector.fill(6, 4)(Point.EmptyPoint))
            val feedbackMatrix = Matrix[FeedbackInterface](Vector.fill(4, 4)(FeedbackInterface.Nothing))
            val guessLength = 4
            val emptyFeedbackField = FeedbackField(feedbackMatrix, guessLength)
            val emptyField = Field(emptyMatrix, emptyFeedbackField)

            "be initially filled with Point.EmptyPoint" in {
                for (i <- 0 until 6; j <- 0 until 4) {
                emptyField.matrix.cell(i, j) should be(Some(Point.EmptyPoint))
                }
            }

             val stringRep =
                "         | Nothing Nothing Nothing Nothing\n" +
                "         | Nothing Nothing Nothing Nothing\n" +
                "         | Nothing Nothing Nothing Nothing\n" +
                "         | Nothing Nothing Nothing Nothing \n" +
                "         |     \n" +
                "         |     \n"

            "have a String representation" in {
                emptyField.toString should be(stringRep)
            }
        }
        "filled with points" should {
            val filledMatrix = Matrix[Point](Vector.fill(6, 4)(Point.RedPoint))
            val feedbackMatrix = Matrix[FeedbackInterface](Vector.fill(4, 4)(FeedbackInterface.Nothing))
            val guessLength = 4
            val filledFeedbackField = FeedbackField(feedbackMatrix, guessLength)
            val filledField = Field(filledMatrix, filledFeedbackField)
            "be filled with given Point.RedPoint" in {
                for (i <- 0 until 6; j <- 0 until 4) {
                    filledField.matrix.cell(i, j) should be(Some(Point.RedPoint))
                }
            }
            val stringRep = ("R " * 4 + "\n") * 6
            "have a String representation" in {
                filledField.toString should be(stringRep)
            }
            "allow to put a point and return a new field" in {
                val point = Point.RedPoint
                val newField = filledField.put(Some(point), 1, 1)
                newField.matrix.cell(1, 1) should be(Some(point))
            }
        }
    }
}
