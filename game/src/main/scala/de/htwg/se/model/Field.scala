package de.htwg.se.model

case class Field(matrix: Matrix[Point]) {
  def this(pointslength: Int, guesslength: Int, filling: Point) =
    this(new Matrix(pointslength, guesslength, filling))

  def put(point: Point, x: Int, y: Int): Field = {
    val updatedMatrix = matrix.replaceCell(x, y, point)
    copy(matrix = updatedMatrix)
  }

  override def toString: String = matrix.toString
}

case class FeedbackField(guesslength: Int) {
  private var feedbackMatrix: Matrix[Feedback] = new Matrix(guesslength, guesslength, Feedback.Nothing)

  def updateFeedback(field: Field, x: Int, y: Int): Unit = {
    val guess = field.matrix.row(y)
    val solution = field.matrix.rows(x)
    val updatedMatrix = feedbackMatrix.replaceCell(solution, guess)
    feedbackMatrix = updatedMatrix
  }

  override def toString: String = feedbackMatrix.toString
}

sealed trait Feedback
object Feedback {
  case object Nothing extends Feedback
  case object ColorCorrect extends Feedback
  case object PositionCorrect extends Feedback
}

implicit class FeedbackMatrixOps(matrix: Matrix[Feedback]) {
  def replaceCell(solution: Vector[Point], guess: Vector[Point]): Matrix[Feedback] = {
    val minLength = Math.min(solution.length, guess.length)
    val updatedRows = matrix.rows.zipWithIndex.map { case (row, i) =>
      if (i < minLength) {
        if (solution(i) == guess(i)) {
          row.updated(i, Feedback.PositionCorrect)
        } else if (solution.contains(guess(i))) {
          row.updated(i, Feedback.ColorCorrect)
        } else {
          row.updated(i, Feedback.Nothing)
        }
      } else {
        row
      }
    }
    matrix.copy(rows = updatedRows)
  }
}
