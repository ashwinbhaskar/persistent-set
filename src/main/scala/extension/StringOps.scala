package extension

import adt.{NonEmptyString, Command, Error}

extension StringOps on (str: String):
    def toCommandAndValue: Either[Error, (Command, NonEmptyString)] = 
        str.split(" ") match
            case Array(first, second, _*) => 
                for
                    command <- Command.fromString(first)
                    value <- NonEmptyString.safe(second)
                yield
                    (command, value)
            case _ => Left(Error.INVALID_INPUT)