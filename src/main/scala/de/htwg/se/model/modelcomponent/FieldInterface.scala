package de.htwg.se.model.modelcomponent


trait FeedbackFieldInterface {
  def getFeedbackMatrix: MatrixInterface[Feedback]
  def updateFeedback(field: FieldInterface, x: Int, y: Int): Unit
  override def toString: String
}

sealed trait Feedback

trait FieldInterface {
  def put(point: Point, x: Int, y: Int): FieldInterface
  override def toString: String
}

trait MatrixInterface[T] {
  def cell(row: Int, col: Int): T
  def row(row: Int): Vector[T]
  def fill(filling: T): MatrixInterface[T]
  def replaceCell(row: Int, col: Int, cell: T): MatrixInterface[T]
  override def toString: String
}

sealed trait Point

trait PointFactoryInterface {
  def createPoint(color: String): Point
}
