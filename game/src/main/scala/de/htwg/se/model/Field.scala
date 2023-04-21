
package de.htwg.se.model

case class Field(matrix: Matrix[Point]){
  def this(pointslength: Int, guesslegth: Int, filling: Point) = this(new Matrix(pointslength, guesslegth, filling))
  val eol = sys.props("line.separator")

  def bar(barWidth: Int = matrix.pointslength / 2): String =
    val s = ("+---" * barWidth + "+")
    val x = (s + " " + s + eol)
    x.toString()

  //def vec(row: Int): IndexedSeq[Point] = (0 until matrix.pointslength / 2).map(matrix.rows(row))

  def cells(row : Int): String = 
    //val first = vec(row).map(_.toString()).map(" " + _ + " " ).mkString("|","|","|")
    val first = (0 until (matrix.pointslength - 1) / 2).map(matrix.rows(row)).map(_.toString()).map(" " + _ + " " ).mkString("|","|","|")
    val second = ((matrix.pointslength - 1) / 2 until matrix.pointslength - 1).map(matrix.rows(row)).map(_.toString()).map(" " + _ + " " ).mkString("|","|","|")
    val x = first + " " + second + eol
    x.toString()

  def mesh(cellWidth: Int = matrix.pointslength / 2): String =
    (0 until matrix.guesslegth).map(cells(_)).mkString(bar(cellWidth), bar(cellWidth), bar(cellWidth))

  override def toString = mesh()
  def put(point: Point, x: Int, y: Int) = copy(matrix.replaceCell(x, y, point))
}
