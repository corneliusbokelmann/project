package de.htwg.se.model.modelcomponent.modelImpl

import de.htwg.se.model.modelcomponent.modelImpl.Matrix
import de.htwg.se.model.modelcomponent.modelImpl.Point


case class Field(matrix: Matrix[Option[Point]]) {
  def this(pointslength: Int, guesslength: Int, filling: Point) =
    this(new Matrix(pointslength, guesslength, Some(filling)))

  def put(point: Point, x: Int, y: Int): Field = {
    val updatedMatrix = matrix.replaceCell(x, y, Some(point))
    copy(matrix = updatedMatrix)
  }

  override def toString: String = {
    val sb = new StringBuilder
    for (row <- matrix.rows) {
      for (col <- 0 until row.length) {
        val cell = row(col)
        val cellStr = cell match {
          case Some(value) => value.toString
          case None => " "
        }
        sb.append(cellStr).append(" ")
      }
      sb.append("\n")
    }
    sb.toString()
  }

}
