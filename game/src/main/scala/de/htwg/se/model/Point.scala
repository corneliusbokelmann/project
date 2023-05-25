
package de.htwg.se.model 

enum Point(stringRepresentation: String):
    override def toString = stringRepresentation
    case w extends Point("W")
    case b extends Point("B")
    case g extends Point("G")
    case r extends Point("R")
    case e extends Point(" ")
