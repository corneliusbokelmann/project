package de.htwg.se.model.modelcomponent.modelImpl

import de.htwg.se.model.modelcomponent.{FeedbackFieldInterface, FieldInterface, FeedbackInterface}

case class FeedbackField(val feedbackMatrix: Matrix[FeedbackInterface], val guessLength: Int) extends FeedbackFieldInterface {
  def getFeedbackMatrix: Matrix[FeedbackInterface] = feedbackMatrix

  def put(feedback: FeedbackInterface, row: Int, col: Int): FeedbackFieldInterface = {
    val newMatrix = feedbackMatrix.replaceCell(row, col, feedback)
    FeedbackField(newMatrix, guessLength)
  }

  override def toString: String = feedbackMatrix.toString
}
