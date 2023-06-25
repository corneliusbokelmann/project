package de.htwg.se.model.modelcomponent.modelImpl

case class Matrix[T](rows: Vector[Vector[T]]) {
  def this(pointslength: Int, guesslength: Int, filling: T) =
    this(Vector.tabulate(guesslength, pointslength) { (row, col) => filling })

  val guesslength: Int = rows.length
  val pointslength: Int = rows(0).length

  def cell(row: Int, col: Int): Option[T] = 
    if (row >= 0 && row < guesslength && col >= 0 && col < pointslength) 
      Some(rows(row)(col)) 
    else 
      None

  def row(row: Int): Vector[T] = rows(row)

  def fill(filling: T): Matrix[T] =
    copy(rows = Vector.tabulate(guesslength, pointslength) { (row, col) => filling })

  def replaceCell(row: Int, col: Int, cell: T): Matrix[T] =
    copy(rows = rows.updated(row, rows(row).updated(col, cell)))

  override def toString: String = rows.map(_.mkString(" ")).mkString("\n")
}