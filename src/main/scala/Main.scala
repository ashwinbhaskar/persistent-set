import scala.io.StdIn
import java.io.File
import adt.{Command, Error, NonEmptyString, NonEmptyStringOps}
import adt.Command._
import extension.{StringOps, FileOps}
import scala.util.chaining._
import scala.language.implicitConversions

@main def run: Unit = 
    println("preparing..")
    val fileName = "database.txt"
    val file = new File(fileName)
    if(!file.exists)
        file.createNewFile
    println("Enter a command: ")
    val line: String = StdIn.readLine
    line
        .toCommandAndValue
        .flatMap {
            case (ADD, value: NonEmptyString) => file.addLineUnique(value)
            case (BATCH_ADD, values) => 
                val lines: Array[NonEmptyString] = values.split("\n")
                val uniqueLines = lines.toSet
                file.addLinesUnique(uniqueLines)
        }       