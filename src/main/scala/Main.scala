import scala.io.StdIn
import java.io.File
import adt.{Command, Error, NonEmptyString}
import adt.Command._
import extension.{StringOps, NonEmptyStringOps, FileOps}

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
            case (REMOVE, value) => file.deleteLine(value)
        }       