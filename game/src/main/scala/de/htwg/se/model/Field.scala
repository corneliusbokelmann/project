
package de.htwg.se.model

case class Field(matrix: Matrix[Point]){
  def this(pointslength: Int, guesslegth: Int, filling: Point) = this(new Matrix(pointslength, guesslegth, filling))
  val eol = sys.props("line.separator")

  def bar(barWidth: Int = matrix.pointslength): String = "+---" * barWidth + "+" + eol
  def cells(row : Int): String = 
    matrix.row(row).map(_.toString()).map(" " + _ + " " ).mkString("|","|","|") + eol
  def mesh(cellWidth: Int = matrix.pointslength): String =
    (0 until matrix.guesslegth).map(cells(_)).mkString(bar(cellWidth), bar(cellWidth), bar(cellWidth))

  // def bar(cellWidth: Int, cellNum: Int): String = (("+" + "-" * cellWidth) * cellNum) + "+" + eol
  // def cells(row : Int, cellWidth: Int) = 
  //   matrix.row(row).map(_.toString()).map(" " * ((cellWidth - 1) / 2) + _ + " " * ((cellWidth - 1) / 2)).mkString("|","|","|") + eol
  // def mesh(cellWidth: Int = 5): String =
  //   (0 until matrix.guesslegth).map(cells(_,cellWidth)).mkString(bar(cellWidth, matrix.pointslength), bar(cellWidth, matrix.pointslength), bar(cellWidth, matrix.pointslength))
  override def toString = mesh()
  def put(point: Point, x: Int, y: Int) = copy(matrix.replaceCell(x, y, point))
}
