/*// Define some variables with different data types
val name = "John"
val age = 30
val height = 1.8
val isMarried = false

// Print out the variables
println(s"Name: $name")
println(s"Age: $age") 
println(s"Height: $height")
println(s"Married: $isMarried")

// Define an array of integers
val numbers = Array(1, 2, 3, 4, 5)

// Loop over the array and print out each element
for (num <- numbers) {
  println(num)
}

// Define a case class
case class Person(name: String, age: Int)

// Create an instance of the case class
val person = Person("Alice", 25)

// Print out the person object
println(person)

case class Point(colour: Int) {
  def sameColourPoint(c: Point): Boolean =
    if (this.colour == c.colour) then return true
    else return false
}

case class Line(point: Array[Point])

case class Field(line: Array[Line])

val p0 = Point(1)
val p1 = Point(1)
val p2 = Point(2)
val p3 = Point(2)
val p4 = Point(2)
val p5 = Point(3)

p0.sameColourPoint(p1)
p0.sameColourPoint(p2)

val line1 = Line(Array.ofDim[Point](4))
line1.point(0) = p0
line1.point(1) = p1
line1.point(2) = p2
val line2 = Line(Array.ofDim[Point](4))
line2.point(0) = p3
line2.point(1) = p4
line2.point(2) = p5

line1.point(0).colour
line1.point(1).colour
line1.point(2).colour
line2.point(0).colour
line2.point(1).colour
line2.point(2).colour

line1.point(2).sameColourPoint(line2.point(1))

val field = Field(Array.ofDim[Line](7))
field.line(0) = line1
field.line(1) = line2

field.line(0).point(0).colour
field.line(0).point(1).colour
field.line(0).point(2).colour
field.line(1).point(0).colour
field.line(1).point(1).colour
field.line(1).point(2).colour

field.line(0).point(0).sameColourPoint(field.line(0).point(1))
*/