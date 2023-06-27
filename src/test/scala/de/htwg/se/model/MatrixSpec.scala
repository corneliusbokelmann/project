package de.htwg.se.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.model.modelcomponent.modelImpl._
import de.htwg.se.model.modelcomponent.modelImpl.Point

class MatrixSpec extends AnyWordSpec {
  "A Matrix contains an immutable two-dimensional Vector of something. A Matrix" when {
    "empty" should {
      "be created by points, guess length, and a filling" in {
        val matrix = Matrix[Option[Point]](Vector.fill(2, 4)(Some(Point.EmptyPoint)))
        matrix.pointslength should be(2)
        matrix.guesslength should be(4)
      }
      "for test purposes only be created with a Vector of Vectors" in {
        val testMatrix = Matrix(Vector(Vector(Some(Point.EmptyPoint), Some(Point.EmptyPoint))))
        testMatrix.pointslength should be(2)
        testMatrix.guesslength should be(1)
      }
    }
    "filled" should {
      val matrix = Matrix[Option[Point]](Vector.fill(3, 2)(Some(Point.RedPoint)))
      "give access to its cells" in {
        matrix.cell(1, 2) should be(Some(Point.RedPoint))
      }
      "replace a cell and return a new data structure" in {
        val newMatrix = matrix.replaceCell(1, 2, Some(Point.GreenPoint))
        matrix.cell(1, 2) should be(Some(Point.RedPoint))
        newMatrix.cell(1, 2) should be(Some(Point.GreenPoint))
      }
      "be completely filled with the given filling by the fill operation" in {
        val newMatrix = matrix.fill(Some(Point.WhitePoint))
        newMatrix.cell(1, 2) should be(Some(Point.WhitePoint))
      }
      "give access to its rows" in {
        matrix.row(0) should be(matrix.rows(0))
      }
    }
  }
}
