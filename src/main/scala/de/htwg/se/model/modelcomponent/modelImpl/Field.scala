package de.htwg.se.model.modelcomponent
package modelImpl

import de.htwg.se.model.modelcomponent.modelImpl.Matrix
import de.htwg.se.model.modelcomponent.modelImpl.Point
import de.htwg.se.model.modelcomponent.modelImpl.FeedbackField
import de.htwg.se.model.modelcomponent.FieldInterface
import de.htwg.se.model.modelcomponent.FeedbackFieldInterface

case class Field(matrix: Matrix[Point], feedbackField: FeedbackField) extends FieldInterface {
  def this(rows: Int, columns: Int, filling: Point, feedbackFilling: FeedbackInterface) =
    this(new Matrix(rows, columns, filling), FeedbackField(new Matrix(rows, columns, feedbackFilling), columns))

  def put(pointOption: Option[Point], x: Int, y: Int): Field = {
    pointOption match {
      case Some(point) =>
        val updatedMatrix = matrix.replaceCell(x, y, point)
        copy(matrix = updatedMatrix)
      case None =>
        this
    }
  }

  def getCell(x: Int, y: Int): Option[Point] = matrix.cell(x, y)

  override def toString: String = {
    val sb = new StringBuilder
    for (row <- 0 until matrix.guesslength) {
      for (col <- 0 until matrix.pointslength) {
        matrix.cell(row, col) match {
          case Some(cell) => sb.append(cell.symbol).append(" ")
          case None => sb.append(" ")
        }
      }
      sb.append(" | ")
      for (col <- 0 until feedbackField.getFeedbackMatrix.guesslength) {
        feedbackField.getFeedbackMatrix.cell(row, col) match {
          case Some(feedback) => sb.append(feedback.toString).append(" ")
          case None => sb.append(" ")
        }
      }
      sb.append("\n")
    }
    sb.toString()
  }
}
