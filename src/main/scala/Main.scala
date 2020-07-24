import scala.io.StdIn
import java.io.File
import adt.{Command, Error, NonEmptyString, NonEmptyStringOps}
import adt.Command._
import extension.FileOps
import scala.util.chaining._
import scala.language.implicitConversions

@main def run(command: String, value: String, delimiter: String, databasePath: String, appendedLinesPath: String) = 
    val fileName = s"$databasePath/database.txt"
    val appendedLinesFileName = s"$appendedLinesPath/unique_lines.txt"
    val file = new File(fileName)
    val appendedLinesFile = new File(appendedLinesFileName)
    if(!file.exists)
        file.createNewFile
    if(!appendedLinesFile.exists)
        appendedLinesFile.createNewFile
    for
        c <- Command.fromString(command)
        v <- NonEmptyString.safe(value)
    yield
        c match
            case ADD => file.addLineUnique(v)
            case BATCH_ADD => 
                val delim = delimiter match
                    case null | "" => "\n"
                    case somethingElse => somethingElse
                val lines: Array[NonEmptyString] = v.split(delim)
                val uniqueLines = lines.toSet
                file.addLinesUnique(uniqueLines, appendedLinesFile)