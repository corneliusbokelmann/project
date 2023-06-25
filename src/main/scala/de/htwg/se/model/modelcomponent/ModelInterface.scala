package de.htwg.se.model.modelcomponent

import de.htwg.se.model.modelcomponent.modelImpl.{Matrix, Point}

trait FieldInterface {
  def matrix: Matrix[Point]
  def put(point: Point, x: Int, y: Int): FieldInterface
  def getCell(x: Int, y: Int): Option[Point]
  override def toString: String
}

trait FeedbackFieldInterface {
  def getFeedbackMatrix: Matrix[FeedbackInterface]
  def updateFeedback(field: FieldInterface, x: Int, y: Int): Unit
  override def toString: String
}

enum FeedbackInterface {
  case Nothing, ColorCorrect, PositionCorrect
}
