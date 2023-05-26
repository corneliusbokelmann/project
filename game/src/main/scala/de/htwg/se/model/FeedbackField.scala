package de.htwg.se.model

case class FeedbackField(guesslength: Int) {
  private var feedbackMatrix: Matrix[Feedback] = new Matrix(guesslength, guesslength, Feedback.Nothing)

  def updateFeedback(field: Field, x: Int, y: Int): Unit = {
    val updatedMatrix = feedbackMatrix.replaceCell(y, x, calculateFeedback(field, x, y))
    feedbackMatrix = updatedMatrix
  }


  private def calculateFeedback(field: Field, x: Int, y: Int): Feedback = {
    val guess = field.matrix.row(y)
    val solution = field.matrix.row(x)
    val minLength = Math.min(solution.length, guess.length)
    for (i <- 0 until minLength) {
      if (solution(i) == guess(i)) {
        return Feedback.PositionCorrect
      } else if (solution.contains(guess(i))) {
        return Feedback.ColorCorrect
      }
    }
    Feedback.Nothing
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
  def replaceCell(x: Int, y: Int, feedback: Feedback): Matrix[Feedback] = {
    val updatedRows = matrix.rows.updated(y, matrix.rows(y).updated(x, feedback))
    matrix.copy(rows = updatedRows)
  }
}