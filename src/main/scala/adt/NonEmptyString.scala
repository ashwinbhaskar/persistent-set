package adt

opaque type NonEmptyString = String

object NonEmptyString:
    def safe(s: String): Either[Error.EMPTY_VALUE.type, NonEmptyString] = 
        s match
            case "" | null => Left(Error.EMPTY_VALUE)
            case somethingElse => Right(somethingElse)