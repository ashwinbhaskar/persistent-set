package extension

import adt.{Error, NonEmptyString}
import adt.NonEmptyStringOps
import java.io.{File, FileWriter, PrintWriter}
import java.nio.channels.FileChannel
import scala.io.Source

extension FileOps on (file: File):
    def addLinesUnique(lines: Set[NonEmptyString]): Either[Error, List[NonEmptyString]] = 
        val linesToAppend = Source.fromFile(file).getLines
            .filterNot(l => lines.contains(NonEmptyString(l)))
            .toList
        if (linesToAppend.isEmpty)
            Right(List.empty[NonEmptyString])
        else
            val fileWriter = new FileWriter(file, true)
            val writer = new PrintWriter(fileWriter)
            val allLinesAsOne = linesToAppend.mkString("\n")
            writer.println(allLinesAsOne)
            writer.close
            Right(linesToAppend.map(NonEmptyString.apply))

    def addLineUnique(lineToAdd: NonEmptyString): Either[Error.DUPLICATE_LINE.type, Unit] = 
        val exists = Source.fromFile(file).getLines.exists(line => lineToAdd.isEqualTo(line))
        if(exists)
           Left(Error.DUPLICATE_LINE)
        else
            val fileWriter = new FileWriter(file, true)
            val writer = new PrintWriter(fileWriter)
            writer.println(lineToAdd)
            writer.close
            Right(())