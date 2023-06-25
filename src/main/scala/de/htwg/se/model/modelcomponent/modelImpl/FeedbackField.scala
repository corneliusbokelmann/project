package de.htwg.se.model.modelcomponent
package modelImpl

import de.htwg.se.model.modelcomponent.modelImpl.Field
import de.htwg.se.model.modelcomponent.modelImpl.Matrix

import de.htwg.se.model.modelcomponent.FeedbackFieldInterface
import de.htwg.se.model.modelcomponent.FieldInterface
import de.htwg.se.model.modelcomponent.FeedbackInterface

case class FeedbackField(guesslength: Int) extends FeedbackFieldInterface {
  private var feedbackMatrix: Matrix[Feedback] = new Matrix(guesslength, guesslength, Feedback.Nothing)

  def getFeedbackMatrix: Matrix[FeedbackInterface] = feedbackMatrix.asInstanceOf[Matrix[FeedbackInterface]]


  def updateFeedback(field: FieldInterface, x: Int, y: Int): Unit = {
    // Cast the field to Field before use
    val castedField = field.asInstanceOf[Field]
    val updatedMatrix = feedbackMatrix.replaceCell(y, x, calculateFeedback(castedField, x, y))
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

enum Feedback {
  case Nothing, ColorCorrect, PositionCorrect
}


implicit class FeedbackMatrixOps(matrix: Matrix[Feedback]) {
  def replaceCell(x: Int, y: Int, feedback: Feedback): Matrix[Feedback] = {
    val updatedRows = matrix.rows.updated(y, matrix.rows(y).updated(x, feedback))
    matrix.copy(rows = updatedRows)
  }
}