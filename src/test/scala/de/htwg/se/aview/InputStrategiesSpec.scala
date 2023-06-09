

package de.htwg.se.aview

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.model.*
import de.htwg.se.controler.*

class InputStrategiesSpec extends AnyWordSpec {
    "The InputStrategie " when {
        val field = new Field(2, 3, PointFactory.createPoint(" "))
        val feedback = new FeedbackField(3)
        val controller = Controller(field, feedback)
        val input = new StandardInput()
        "a point is choosen" should {
            input.handleInput("R00",controller)
            "put the point in the field" in {
                field.matrix.cell(0,0) should be(Some(PointFactory.createPoint(" ")))
            }
        }
        "undo is choosen" should {
            field.put(PointFactory.createPoint("B"),0,0)
            input.handleInput("u",controller)
            "undo the last move" in {
                field.matrix.cell(0,0) should be(Some(PointFactory.createPoint(" ")))
            }
        }
    }
}


