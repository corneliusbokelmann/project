package de.htwg.se.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class MatrixSpec extends AnyWordSpec {
    "A Matrix contains a immutable two dimensional Vector of something. A Matrix " when {
        "empty" should {
            "be created by points, guess length and a filling" in {
                val matrix = new Matrix[Option[Point]](2, 4, Some(PointFactory.createPoint(" ")));
                matrix.pointslength should be(2)
                matrix.guesslength should be(4)
            }
            "for test purposes only be created with a Vector of Vectors" in {
                val testMatrix = Matrix(Vector(Vector(Some(PointFactory.createPoint(" ")), Some(PointFactory.createPoint(" ")))))
                testMatrix.pointslength should be(2)
                testMatrix.guesslength should be(1)
            }
        }
        "filled" should {
            val matrix = new Matrix[Option[Point]](3, 2, Some(PointFactory.createPoint("R")))
            "give access to its cells" in {
                matrix.cell(1, 2) should be(Some(PointFactory.createPoint("R")))
            }
            "replace a cell and return new data structure" in {
                val newmatrix = matrix.replaceCell(1, 2, Some(PointFactory.createPoint("G")))
                matrix.cell(1, 2) should be(Some(PointFactory.createPoint("R")))
                newmatrix.cell(1, 2) should be(Some(PointFactory.createPoint("G")))
            }
            "be completely filled with given filling by fill operation" in {
                val newmatrix = matrix.fill(Some(PointFactory.createPoint("W")))
                newmatrix.cell(1, 2) should be (Some(PointFactory.createPoint("W")))
            }
            "give access to its rows" in {
                matrix.row(0) should be (matrix.rows(0))
            }
        }
    }
}
