
package de.htwg.se.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class MatrixSpec extends AnyWordSpec {
    "A Matrix contains a immutable two dimensional Vektor of something. A Matrix " when {
        "empty" should {
            "be created by points, guess length and a filling" in {
                val matrix = new Matrix[Point](2, 4, Point.e);
                matrix.pointslength should be(5)
                matrix.guesslegth should be(4)
            }
            "for test purposes only be created with a Vector of Vectors" in {
                val testMatrix = Matrix(Vector(Vector(Point.e, Point.e)))
                testMatrix.pointslength should be(2)
                testMatrix.guesslegth should be(1)
            }
        }
        "filled" should {
            val matrix = new Matrix[Point](3, 2, Point.r)
            "give access to its cells" in {
                matrix.cell(1, 2) should be(Point.r)
            }
            "replace a cell and return new data structure" in {
                val newmatrix = matrix.replaceCell(1, 2, Point.g)
                matrix.cell(1, 2) should be(Point.r)
                newmatrix.cell(1, 2) should be(Point.g)
            }
            "be completely filled with given filling by fill operation" in {
                val newmatrix = matrix.fill(Point.w)
                newmatrix.cell(1, 2) should be (Point.w)
            }
            "give access to its rows" in {
                matrix.row(0) should be (matrix.rows(0))
            }
        }
    }
}
