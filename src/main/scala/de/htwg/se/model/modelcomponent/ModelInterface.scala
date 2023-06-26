package de.htwg.se.model.modelcomponent

import de.htwg.se.model.modelcomponent.modelImpl.{Matrix, Point}

trait FieldInterface {
  def matrix: Matrix[Point]
  def put(point: Option[Point], x: Int, y: Int): FieldInterface
  def getCell(x: Int, y: Int): Option[Point]
  override def toString: String
}

trait FeedbackFieldInterface {
  def getFeedbackMatrix: Matrix[FeedbackInterface]
  override def toString: String
  def put(feedback: FeedbackInterface, row: Int, col: Int): FeedbackFieldInterface
}

enum FeedbackInterface {
  case Nothing, ColorCorrect, PositionCorrect
}
