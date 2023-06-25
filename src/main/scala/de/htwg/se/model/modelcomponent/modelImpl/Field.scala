package de.htwg.se.model.modelcomponent.modelImpl

import de.htwg.se.model.modelcomponent.modelImpl.Matrix
import de.htwg.se.model.modelcomponent.modelImpl.Point

import de.htwg.se.model.modelcomponent.FieldInterface

case class Field(matrix: Matrix[Point]) extends FieldInterface {
  def this(pointslength: Int, guesslength: Int, filling: Point) =
    this(new Matrix(pointslength, guesslength, filling))

  def put(point: Point, x: Int, y: Int): Field = {
    val updatedMatrix = matrix.replaceCell(x, y, point)
    copy(matrix = updatedMatrix)
  }

  def getCell(x: Int, y: Int): Option[Point] = matrix.cell(x, y)

  override def toString: String = {
  val sb = new StringBuilder
  for (row <- matrix.rows) {
    for (col <- 0 until row.length) {
      val cell = row(col)
      val cellStr = cell.symbol
      sb.append(cellStr).append(" ")
    }
    sb.append("\n")
  }
  sb.toString()
}


}
