package adt

enum Command:
    case ADD, REMOVE

object Command:
    def fromString(str: String): Either[Error.INVALID_COMMAND.type, Command] = 
        str.toUpperCase match
            case "ADD" => Right(ADD)
            case "REMOVE" => Right(REMOVE)
            case _ => Left(Error.INVALID_COMMAND)
