// Define some variables with different data types
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

println("test")
