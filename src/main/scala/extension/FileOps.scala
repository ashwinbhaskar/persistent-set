package extension

import adt.{Error, NonEmptyString}
import adt.NonEmptyStringOps
import java.io.{File, FileWriter, PrintWriter}
import java.nio.channels.FileChannel
import scala.io.Source

extension FileOps on (file: File):

    def appendLines(lines: Set[NonEmptyString]): Unit = 
        val fileWriter = new FileWriter(file, true)
        val writer = new PrintWriter(fileWriter)
        val allLinesAsOne = lines.mkString("\n")
        writer.println(allLinesAsOne)
        writer.close
    
    def fillLines(lines: Set[NonEmptyString]): Unit = 
        if(lines.nonEmpty)
            val fileWriter = new FileWriter(file, false)
            val writer = new PrintWriter(fileWriter)
            val allLinesAsOne = lines.mkString("\n")
            writer.println(allLinesAsOne)
            writer.close
        else
            new FileWriter(file).close

    def removeExistingLines(lines: Set[NonEmptyString]): Set[NonEmptyString] = 
        val commonLines = Source.fromFile(file).getLines
                .filter(l => lines.contains(NonEmptyString(l)))
                .map(NonEmptyString.apply)
                .toSet
        lines diff commonLines

    def addLinesUnique(lines: Set[NonEmptyString], appendLinesFileName: File): Either[Error, Unit] = 
        val linesToAppend = file.removeExistingLines(lines)
        if(linesToAppend.nonEmpty)
            file.appendLines(linesToAppend)
        appendLinesFileName.fillLines(linesToAppend)
        Right(())

    def addLinesUnique(lines: Set[NonEmptyString]): Either[Error, Set[NonEmptyString]] = 
        val linesToAppend = file.removeExistingLines(lines)
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