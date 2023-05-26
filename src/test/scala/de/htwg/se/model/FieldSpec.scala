package de.htwg.se.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class FieldSpec extends AnyWordSpec {
    "A Field in Mastermind game" when {
        "empty" should {
            val emptyField = new Field(4, 6, PointFactory.createPoint(" "))
            "be initially filled with Point.e" in {
                for(i <- 0 until 6; j <- 0 until 4) {
                    emptyField.matrix.cell(i, j) should be(Some(PointFactory.createPoint(" ")))
                }
            }
        }
        "filled with points" should {
            val filledField = new Field(4, 6, PointFactory.createPoint("R"))
            "be filled with given Point" in {
                for(i <- 0 until 6; j <- 0 until 4) {
                    filledField.matrix.cell(i, j) should be(Some(PointFactory.createPoint("R")))
                }
            }
            "allow to put a point and return a new field" in {
                val point = PointFactory.createPoint("R")
                val newField = filledField.put(point, 1, 1)
                newField.matrix.cell(1, 1) should be(Some(point))
            }
        }
    }
}
