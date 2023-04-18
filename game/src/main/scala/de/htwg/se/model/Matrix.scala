
package de.htwg.se.model 

case class Matrix[T](rows: Vector[Vector[T]]) {
  def this(pointslength: Int, guesslegth: Int, filling: T) = this(Vector.tabulate(guesslegth, pointslength) {(row, col) => filling})
  val guesslegth: Int = rows.length
  val pointslength: Int = rows(0).length 
  def cell(row: Int, col: Int): T = rows(row)(col)
  def row(row : Int) = rows(row)
  def fill(filling: T): Matrix[T] = copy(Vector.tabulate(guesslegth, pointslength) { (row, col) => filling })
  def replaceCell(row: Int, col: Int, cell: T): Matrix[T] = copy(rows.updated(row, rows(row).updated(col, cell)))
}
 