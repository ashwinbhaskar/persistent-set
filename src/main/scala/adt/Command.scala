package adt

enum Command:
    case ADD, BATCH_ADD

object Command:
    def fromString(str: String): Either[Error.INVALID_COMMAND.type, Command] = 
        str.toUpperCase match
            case "ADD" => Right(ADD)
            case "BATCH_ADD" => Right(BATCH_ADD)
            case _ => Left(Error.INVALID_COMMAND)
