package extension

import adt.{Error, NonEmptyString}
import adt.NonEmptyStringOps
import java.io.{File, FileWriter, PrintWriter}
import java.nio.channels.FileChannel
import scala.io.Source

extension FileOps on (file: File):
    def addLinesUnique(lines: Set[NonEmptyString]): Either[Error, Set[NonEmptyString]] = 
        val commonLines = Source.fromFile(file).getLines
                .filter(l => lines.contains(NonEmptyString(l)))
                .map(NonEmptyString.apply)
                .toSet
        val linesToAppend = lines diff commonLines
                
        if (linesToAppend.isEmpty)
            Right(Set.empty[NonEmptyString])
        else
            val fileWriter = new FileWriter(file, true)
            val writer = new PrintWriter(fileWriter)
            val allLinesAsOne = linesToAppend.mkString("\n")
            writer.println(allLinesAsOne)
            writer.close
            Right(linesToAppend)

    def addLineUnique(lineToAdd: NonEmptyString): Either[Error.DUPLICATE_LINE.type, Unit] = 
        val exists = Source.fromFile(file).getLines.exists(line => lineToAdd.equals(line))
        if(exists)
           Left(Error.DUPLICATE_LINE)
        else
            val fileWriter = new FileWriter(file, true)
            val writer = new PrintWriter(fileWriter)
            writer.println(lineToAdd)
            writer.close
            Right(())