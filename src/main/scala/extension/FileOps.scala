package extension

import adt.{Error, NonEmptyString}
import java.io.{File, FileWriter, PrintWriter}
import scala.io.Source

extension FileOps on (file: File):
    def deleteLine(line: NonEmptyString): Either[Error, Unit] = ???
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