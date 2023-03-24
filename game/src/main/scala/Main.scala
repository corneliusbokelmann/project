@main def mastermind: Unit =
  println("Willkommen zu Mastermind!")
  println(mesh())

val eol = sys.props("line.separator")
def bar(barWidth: Int = 2) = "+---" * barWidth + "+" + eol
def cells(cellWidth: Int = 2) = "|   " * cellWidth + "|" + eol
def mesh(width: Int = 1, length: Int = 3) =
  (bar(width) + cells(width)) * length + bar(width)